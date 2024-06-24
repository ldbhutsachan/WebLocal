package com.ldb.weblocalapi.controller;


import com.ldb.weblocalapi.Model.ChangePasswordReq;
import com.ldb.weblocalapi.Security.jwt.JwtTokenProvider;
import com.ldb.weblocalapi.Security.service.CustomUserDetailsService;
import com.ldb.weblocalapi.Security.service.UserPrincipal;
import com.ldb.weblocalapi.entities.DisplayLink;
import com.ldb.weblocalapi.entities.Menu;
import com.ldb.weblocalapi.entities.Users;
import com.ldb.weblocalapi.exceptions.DetailMessage.ExceptionResponse;
import com.ldb.weblocalapi.exceptions.Exception2.BadRequestException;
import com.ldb.weblocalapi.exceptions.Exception2.ForbiddenException;
import com.ldb.weblocalapi.exceptions.Exception2.NotFoundException;
import com.ldb.weblocalapi.exceptions.ExceptionStatus.InternalServerError;
import com.ldb.weblocalapi.exceptions.ExceptionStatus.UnAuthorizedException;
import com.ldb.weblocalapi.messages.request.LoginRequest;
import com.ldb.weblocalapi.messages.response.DataResponse;
import com.ldb.weblocalapi.messages.response.LoginResponse;
import com.ldb.weblocalapi.messages.response.ProfileResponse;
import com.ldb.weblocalapi.repositories.MenuRepository;
import com.ldb.weblocalapi.repositories.UserRepository;
import com.ldb.weblocalapi.services.Impl.CreateUserServiceInterface;
import com.ldb.weblocalapi.utils.APIMappingPaths;
import com.ldb.weblocalapi.utils.JSONUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.naming.ServiceUnavailableException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(
        value = {"${url.mapping}" + APIMappingPaths.API_MB_GATEWAY_VERSION_PATH
                + APIMappingPaths.API_MB_REPORT_PATH + APIMappingPaths.API_AUTHENTICATION_GATEWAY_PATH}
)
public class LoginController {
    @Value("${app.jwtExpirationInMs}") // 3  minus
    private int jwtExpirationInMs;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    CreateUserServiceInterface createUserServiceInterface;
    @Autowired
    private JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    public LoginController(@Lazy UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @RequestMapping(
            value = APIMappingPaths.LOGIN.API_LOGIN_GATEWAY_PATH,
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST
    )
    @ResponseBody
    public ResponseEntity<?> getMobileAuthenticate(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        HttpHeaders responseHeaders = new HttpHeaders();
        DataResponse dataResponse = new DataResponse();
        LoginResponse loginResponse =  new LoginResponse();
        dataResponse.setStatus("05");
        dataResponse.setMessage("ຢຸດເຊີ ຫລື ລະຫັດຜ່ານບໍ່ຖືກຕ້ອງ");
        try {
            log.info("++ Mobile Login Request Token ..............................");
            log.info("Client IP Address: " + request.getRemoteAddr());
            // Request username and password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            // Generate Token key
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);
            // Find Users by userId get from boder.Token key
            UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Users users = userPrincipal.getUser();
            long seconds = TimeUnit.MILLISECONDS.toSeconds(jwtExpirationInMs);
            List<Menu> listMenu = menuRepository.findByMenuMapUserIdFromUserName(users.getUsername());
            List<Long> menuGroupId = listMenu.stream().map(Menu::getMenuId).distinct().collect(Collectors.toList());
            log.info("show log{}",menuGroupId);
             loginResponse = new LoginResponse(
                    jwt, "Bearer",
                    seconds,users.getUsername(),users.getImagePath(), users.getEnabled(), users.getSection(),listMenu
            );
            dataResponse.setStatus("00");
            dataResponse.setMessage("success");
            dataResponse.setDataResponse(loginResponse);
        } catch (Exception e) {
            dataResponse.setStatus("05");
            dataResponse.setMessage("ຢຸດເຊີ ຫລື ລະຫັດຜ່ານບໍ່ຖືກຕ້ອງ");
            dataResponse.setDataResponse(loginResponse);
        }
        return ResponseEntity.ok().headers(responseHeaders).body(dataResponse);
    }
    @ApiOperation(
            value = "getProfile in ProFile show data",
            authorizations = {@Authorization(value = "apiKey")},
            response = DocumentTypeController.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = DocumentTypeController.class),
            @ApiResponse(code = 201, message = "Created", response = ExceptionResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = UnAuthorizedException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = InternalServerError.class),
            @ApiResponse(code = 503, message = "Service Unavailable", response = ServiceUnavailableException.class)
    })
    @RequestMapping(
            value = APIMappingPaths.LOGIN.API_PROFILE_GATEWAY_PATH,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST
    )
    @ResponseBody
    public ResponseEntity<?> getProfile(Authentication auth, HttpServletRequest request) {
        DataResponse dataResponse = new DataResponse();
        dataResponse.setStatus("05");
        dataResponse.setMessage("ຢຸດເຊີ ຫລື ລະຫັດຜ່ານບໍ່ຖືກຕ້ອງ");
        try {
            log.info("++ Mobile Login Request Token ..............................");
            log.info("Client IP Address: " + request.getRemoteAddr());
            Users users = userRepository.findByUsername(auth.getName()).get();
            ProfileResponse loginResponse = new ProfileResponse(
                    users.getUsername(),
                    users.getImagePath(),
                    users.getEnabled(),
                    users.getAccountNonExpired(),
                    users.getAccountNonLocked(),
                    users.getCredentialsNonExpired(),
                    users.getSection()
            );
            dataResponse.setStatus("00");
            dataResponse.setMessage("success");
            dataResponse.setDataResponse(loginResponse);
            log.info("loginResponse: " + JSONUtils.toJSONString(loginResponse));

        } catch (Exception e) {
            log.error("Authentication error = " + e.getMessage());
        }
        return ResponseEntity.ok().body(dataResponse);
    }
    //**************************************change password ***********************************************
    @ApiOperation(
            value = "getProfile in ProFile show data",
            authorizations = {@Authorization(value = "apiKey")},
            response = DocumentTypeController.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = DocumentTypeController.class),
            @ApiResponse(code = 201, message = "Created", response = ExceptionResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = UnAuthorizedException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = InternalServerError.class),
            @ApiResponse(code = 503, message = "Service Unavailable", response = ServiceUnavailableException.class)
    })
    @RequestMapping(
            value = APIMappingPaths.LOGIN.API_CHANGE_PASSWORD_MENU,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST
    )
    @ResponseBody
    public ResponseEntity<?> changePassword(
            @ApiParam(
                name = "Body Request",
                value = "JSON body request to check information",
        required = true) @Valid @RequestBody ChangePasswordReq changePasswordReq, HttpServletRequest request) throws Exception {
            log.info("\t\t --> DisplayLink Request controller >>>>>>>>>>>>>>>>>>>>>>");
            String clientIpAddress = request.getRemoteAddr();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            log.info("Client IP Address = " + clientIpAddress);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            log.info("auth == " + auth.getName());
            log.info("auth username == " + auth.getPrincipal());
            log.info("data body request: " + request.toString());
            changePasswordReq.setUserName(auth.getName());
            DataResponse response = createUserServiceInterface.ChangePassword(changePasswordReq);
            log.info("\t\t --> End Custom compare Request controller <<<<<<<<<<<<<<<<<<<");
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
