package com.ldb.reportcustom.controller;


import com.ldb.reportcustom.Security.jwt.JwtTokenProvider;
import com.ldb.reportcustom.Security.service.CustomUserDetailsService;
import com.ldb.reportcustom.Security.service.UserPrincipal;
import com.ldb.reportcustom.entities.Users;
import com.ldb.reportcustom.messages.request.LoginRequest;
import com.ldb.reportcustom.messages.response.DataResponse;
import com.ldb.reportcustom.messages.response.LoginResponse;
import com.ldb.reportcustom.messages.response.ProfileResponse;
import com.ldb.reportcustom.repositories.UserRepository;
import com.ldb.reportcustom.utils.APIMappingPaths;
import com.ldb.reportcustom.utils.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Create at 2019-01-21
 *
 * @author KHAMPHAI
 */
@Slf4j
@RestController
@RequestMapping(
        value = {"${url.mapping}" + APIMappingPaths.API_MB_GATEWAY_VERSION_PATH
                + APIMappingPaths.API_MB_REPORT_PATH + APIMappingPaths.API_AUTHENTICATION_GATEWAY_PATH}
)
//@RequestMapping(value = "/MBAPI/MB-REPORT")
public class LoginController {

    @Value("${app.jwtExpirationInMs}") // 3  minus
    private int jwtExpirationInMs;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    private final UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

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

//            System.out.println("user =" + users);
//            Date now = new Date();
//            Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
            long seconds = TimeUnit.MILLISECONDS.toSeconds(jwtExpirationInMs);
            System.out.println("seconds = " + seconds);
            // Initialize data to return object
            LoginResponse loginResponse = new LoginResponse(
                    jwt, "Bearer",
//                    new Date(seconds),
                    seconds,
                    users.getUsername(), users.getEnabled(), users.getBorder(), users.getRoles()

//                    user.get().getUsername(), user.get().getEnabled(),
//                    user.get().getBorder(), user.get().getRoles()
            );

//            responseHeaders.set("accessToken",  jwt);

            dataResponse.setStatus("00");
            dataResponse.setMessage("success");
            dataResponse.setDataResponse(loginResponse);
            //log.info("loginResponse: " + JSONUtils.toJSONString(loginResponse));

//        return ResponseEntity.ok("loginResponse");
        } catch (Exception e) {
            log.error("Authentication error = " + e.getMessage());
            dataResponse.setMessage(e.getMessage());
        }
        return ResponseEntity.ok().headers(responseHeaders).body(dataResponse);
    }

    @RequestMapping(
            value = APIMappingPaths.LOGIN.API_PROFILE_GATEWAY_PATH,
//            consumes = {
//                    MediaType.APPLICATION_JSON_VALUE
//            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.GET
    )
    @ResponseBody
    public ResponseEntity<?> getProfile(Authentication auth, HttpServletRequest request) {
//        HttpHeaders responseHeaders = new HttpHeaders();
        DataResponse dataResponse = new DataResponse();
        dataResponse.setStatus("05");
        dataResponse.setMessage("ຢຸດເຊີ ຫລື ລະຫັດຜ່ານບໍ່ຖືກຕ້ອງ");
        try {
            log.info("++ Mobile Login Request Token ..............................");
            log.info("Client IP Address: " + request.getRemoteAddr());
//            System.out.println("User name = "+ auth.getName());

            Users users = userRepository.findByUsername(auth.getName()).get();
//            System.out.println("User detail = " + users);

            // Initialize data to return object
            ProfileResponse loginResponse = new ProfileResponse(
                    users.getUsername(), users.getEnabled(),
                    users.getAccountNonExpired(),
                    users.getAccountNonLocked(),
                    users.getCredentialsNonExpired(),
                    users.getBorder(),
                    users.getRoles()
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
    ///get

}
