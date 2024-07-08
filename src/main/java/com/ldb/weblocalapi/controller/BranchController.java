package com.ldb.weblocalapi.controller;

import com.ldb.weblocalapi.entities.DocType;
import com.ldb.weblocalapi.entities.Respone.BranchRespone;
import com.ldb.weblocalapi.entities.Section;
import com.ldb.weblocalapi.exceptions.DetailMessage.ExceptionResponse;
import com.ldb.weblocalapi.exceptions.Exception2.BadRequestException;
import com.ldb.weblocalapi.exceptions.Exception2.ForbiddenException;
import com.ldb.weblocalapi.exceptions.Exception2.NotFoundException;
import com.ldb.weblocalapi.exceptions.ExceptionStatus.InternalServerError;
import com.ldb.weblocalapi.exceptions.ExceptionStatus.UnAuthorizedException;
import com.ldb.weblocalapi.messages.response.DataResponse;
import com.ldb.weblocalapi.repositories.BranchRepository;
import com.ldb.weblocalapi.repositories.SectionRepository;
import com.ldb.weblocalapi.utils.APIMappingPaths;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Branch;
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
public class BranchController {
    @Autowired
    BranchRepository branchRepository;

    @Autowired
    SectionRepository secListRepository;
    @ApiOperation(
            value = "Branch in BranchController",
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
            value = APIMappingPaths.BRANCH.API_BRANCH,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST
    )
    @ResponseBody
    public ResponseEntity<?> getBranch(@Valid @RequestBody BranchRespone branch, HttpServletRequest request) {
        String keyId = branch.getBranchCode();
        DataResponse dataResponse = new DataResponse();
        try {
            log.info("++ Branch Login Request Token ..............................");
            log.info("Client IP Address: " + request.getRemoteAddr());
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            log.info("auth == " + auth.getName());
            log.info("auth username == " + auth.getPrincipal());
            log.info("auth username == " + auth.getDetails());
            log.info("data body request: " + request.toString());
            List<Section> getSecInfo =secListRepository.findByBranchIdFromUserName(auth.getName());
            String secCod = getSecInfo.get(0).getSecId();

                dataResponse.setDataResponse(branchRepository.findDocTypeAll(secCod));
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
    //*************bandList
    @ApiOperation(
            value = "Branch in BranchController",
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
            value = APIMappingPaths.BRANCH.API_BRANCH_LIST,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST
    )
    @ResponseBody
    public ResponseEntity<?> getBandList( HttpServletRequest request) {
        DataResponse dataResponse = new DataResponse();
        try {
            log.info("++ Branch Login Request Token ..............................");
            log.info("Client IP Address: " + request.getRemoteAddr());
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            log.info("auth == " + auth.getName());
            log.info("auth username == " + auth.getPrincipal());
            log.info("data body request: " + request.toString());
            dataResponse.setDataResponse(branchRepository.findDocTypeAllOnlyBand());
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
