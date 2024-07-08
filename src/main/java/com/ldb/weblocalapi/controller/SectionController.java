package com.ldb.weblocalapi.controller;

import com.ldb.weblocalapi.entities.DocType;
import com.ldb.weblocalapi.entities.Respone.SectionResponse;
import com.ldb.weblocalapi.entities.Section;
import com.ldb.weblocalapi.exceptions.DetailMessage.ExceptionResponse;
import com.ldb.weblocalapi.exceptions.Exception2.BadRequestException;
import com.ldb.weblocalapi.exceptions.Exception2.ForbiddenException;
import com.ldb.weblocalapi.exceptions.Exception2.NotFoundException;
import com.ldb.weblocalapi.exceptions.ExceptionStatus.InternalServerError;
import com.ldb.weblocalapi.exceptions.ExceptionStatus.UnAuthorizedException;
import com.ldb.weblocalapi.messages.response.DataResponse;
import com.ldb.weblocalapi.repositories.SectionRepository;
import com.ldb.weblocalapi.repositories.SectionResponseRepository;
import com.ldb.weblocalapi.utils.APIMappingPaths;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.naming.ServiceUnavailableException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(
        value = {"${url.mapping}" + APIMappingPaths.API_MB_GATEWAY_VERSION_PATH
                + APIMappingPaths.API_MB_REPORT_PATH}
)
public class SectionController {
    @Autowired
    SectionResponseRepository sectionRepository;

    @Autowired
    SectionRepository secListRepository;
    @ApiOperation(
            value = "API_SECTION in SectionController",
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
            value = APIMappingPaths.SECTION.API_SECTION,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST
    )
    @ResponseBody
    public ResponseEntity<?> getSection(@Valid @RequestBody SectionResponse section, HttpServletRequest request) {
        String secId = section.getSecId();
        DataResponse dataResponse = new DataResponse();
        try {
            log.info("++ getSection Login Request Token ..............................");
            log.info("Client IP Address: " + request.getRemoteAddr());

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            log.info("auth == " + auth.getName());
            log.info("auth username == " + auth.getPrincipal());
            log.info("data body request: " + request.toString());
            List<Section> getSecInfo =secListRepository.findByBranchIdFromUserName(auth.getName());
            String secCod = getSecInfo.get(0).getSecId();
            if(secId.equals("0") || secId == "0"){
                dataResponse.setDataResponse(sectionRepository.findByBranchIdFromAll(secCod));
            }else {
                dataResponse.setDataResponse(sectionRepository.findByBranchIdFromBySecId(secId));
            }
            if(dataResponse.getDataResponse() !=null){
                dataResponse.setStatus("00");
                dataResponse.setMessage("Success");
            }else {
                dataResponse.setStatus("05");
                dataResponse.setMessage("Data Not Found");
            }
        }catch (Exception ex){
            dataResponse.setStatus("05");
            dataResponse.setMessage("ຂໍ້ມູນບໍ່ຖືກຕ້ອງ");
        }
        return ResponseEntity.ok(dataResponse);
    }
}
