package com.ldb.weblocalapi.controller;

import com.ldb.weblocalapi.Model.BranchReq;
import com.ldb.weblocalapi.exceptions.DetailMessage.ExceptionResponse;
import com.ldb.weblocalapi.exceptions.Exception2.BadRequestException;
import com.ldb.weblocalapi.exceptions.Exception2.ForbiddenException;
import com.ldb.weblocalapi.exceptions.Exception2.NotFoundException;
import com.ldb.weblocalapi.exceptions.ExceptionStatus.InternalServerError;
import com.ldb.weblocalapi.exceptions.ExceptionStatus.UnAuthorizedException;
import com.ldb.weblocalapi.messages.request.DocReq;
import com.ldb.weblocalapi.messages.response.DataResponse;
import com.ldb.weblocalapi.services.DocumentService;
import com.ldb.weblocalapi.utils.APIMappingPaths;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.naming.ServiceUnavailableException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;

@Slf4j
@RestController
@RequestMapping(
        value = {"${url.mapping}" + APIMappingPaths.API_MB_GATEWAY_VERSION_PATH
                + APIMappingPaths.API_MB_REPORT_PATH}
)
public class BranchListDocMenuController {
    @Autowired
    DocumentService documentService;
    @ApiOperation(
            value = "BranchListDocMenuController in DocuementController",
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
            value = APIMappingPaths.BAND_MENUCONDITION.API_BAND_MENU,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST
    )
    @ResponseBody
    public ResponseEntity<?> getSaveDocument(@ApiParam(
            name = "Body Request",
            value = "JSON body request to check information",
            required = true) HttpServletRequest request) throws Exception {
        log.info("\t\t --> BranchListDocMenuController compare Request controller >>>>>>>>>>>>>>>>>>>>>>");
        String clientIpAddress = request.getRemoteAddr();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        log.info("Client IP Address = " + clientIpAddress);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth == " + auth.getName());
        log.info("auth username == " + auth.getPrincipal());
        log.info("data body request: " + request.toString());
        DataResponse response = documentService.documentListByBandMenu();
        log.info("\t\t --> End BranchListDocMenuController compare Request controller <<<<<<<<<<<<<<<<<<<");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @ApiOperation(
            value = "Docuement in DocuementController",
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
            value = APIMappingPaths.BAND_MENUCONDITION.API_BAND_MENU_CONDITION,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST
    )
    @ResponseBody
    public ResponseEntity<?> SectionListDocByDate(@ApiParam(
            name = "Body Request",
            value = "JSON body request to check information",
            required = true) @Valid @RequestBody BranchReq documentRespone, HttpServletRequest request) throws Exception {
        log.info("\t\t --> Custom compare Request controller >>>>>>>>>>>>>>>>>>>>>>");
        String clientIpAddress = request.getRemoteAddr();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        log.info("Client IP Address = " + clientIpAddress);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth == " + auth.getName());
        log.info("auth username == " + auth.getPrincipal());
        log.info("data body request: " + request.toString());
        DataResponse response = documentService.documentListByBandMenuByDate(documentRespone,request);
        log.info("\t\t --> End Custom compare Request controller <<<<<<<<<<<<<<<<<<<");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
