package com.ldb.weblocalapi.controller;

import com.ldb.weblocalapi.entities.Popup;
import com.ldb.weblocalapi.entities.Read;
import com.ldb.weblocalapi.entities.Respone.DocumentRespone;

import com.ldb.weblocalapi.entities.Section;
import com.ldb.weblocalapi.exceptions.DetailMessage.ExceptionResponse;
import com.ldb.weblocalapi.exceptions.Exception2.BadRequestException;
import com.ldb.weblocalapi.exceptions.Exception2.ForbiddenException;
import com.ldb.weblocalapi.exceptions.Exception2.NotFoundException;
import com.ldb.weblocalapi.exceptions.ExceptionStatus.InternalServerError;
import com.ldb.weblocalapi.exceptions.ExceptionStatus.UnAuthorizedException;
import com.ldb.weblocalapi.messages.request.RequestReportDate;
import com.ldb.weblocalapi.messages.response.DataResponse;
import com.ldb.weblocalapi.messages.response.ProfileResponse;
import com.ldb.weblocalapi.repositories.PopupRepository;
import com.ldb.weblocalapi.repositories.SectionRepository;
import com.ldb.weblocalapi.services.DocumentService;
import com.ldb.weblocalapi.services.Impl.ReadDocServiceImpl;
import com.ldb.weblocalapi.services.ReadDocService;
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
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(
        value = {"${url.mapping}" + APIMappingPaths.API_MB_GATEWAY_VERSION_PATH
                + APIMappingPaths.API_MB_REPORT_PATH}
)
public class HomeController {
    @Autowired
    DocumentService documentService;
    @Autowired
    PopupRepository popupRepository;
    @Autowired
    ReadDocService readDocService;

    @Autowired
    SectionRepository sectionRepository;
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
            //Home.service
            value = APIMappingPaths.HOME.API_HOME,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST
    )
    @ResponseBody
    public ResponseEntity<?> getSaveDocumentCondition(@ApiParam(
            name = "Body Request",
            value = "JSON body request to check information",
            required = true) HttpServletRequest request) throws Exception {
        log.info("\t\t --> Custom compare Request controller >>>>>>>>>>>>>>>>>>>>>>");
        String clientIpAddress = request.getRemoteAddr();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        log.info("Client IP Address = " + clientIpAddress);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth == " + auth.getName());
        log.info("auth username == " + auth.getPrincipal());
        log.info("auth username == " + auth.getName());
        log.info("data body request: " + request.toString());
        List<Section> getSecInfo =sectionRepository.findByBranchIdFromUserName(auth.getName());
        String secCod = getSecInfo.get(0).getSecId();
        DataResponse response = documentService.HomeDocumentList(secCod);
        log.info("\t\t --> End Custom compare Request controller <<<<<<<<<<<<<<<<<<<");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //********
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
            //******getSaveDocumentCondition.service
            value = APIMappingPaths.HOME.API_HOME_CONDITION,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST
    )
    @ResponseBody
    public ResponseEntity<?> getSaveDocument(@ApiParam(
            name = "Body Request",
            value = "JSON body request to check information",
            required = true) @Valid @RequestBody RequestReportDate documentRespone, HttpServletRequest request) throws Exception {
        log.info("\t\t --> Custom compare Request controller >>>>>>>>>>>>>>>>>>>>>>");
        String clientIpAddress = request.getRemoteAddr();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        log.info("Client IP Address = " + clientIpAddress);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth == " + auth.getName());
        log.info("auth username == " + auth.getPrincipal());
        log.info("data body request: " + request.toString());
        List<Section> getSecInfo =sectionRepository.findByBranchIdFromUserName(auth.getName());
        String secCod = getSecInfo.get(0).getSecId();
        DataResponse responseHome = documentService.HomeDocumentListByContion(documentRespone,request,secCod);
        log.info("\t\t --> End Custom compare Request controller <<<<<<<<<<<<<<<<<<<");
        return new ResponseEntity<>(responseHome, HttpStatus.OK);
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
            value = APIMappingPaths.HOME.API_HOME_POPUP,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST
    )
    @ResponseBody
    public ResponseEntity<?> getPopup(@ApiParam(
            name = "Body Request",
            value = "JSON body request to check information",
            required = true)HttpServletRequest request) throws Exception {
        log.info("\t\t --> responsePopup compare Request controller >>>>>>>>>>>>>>>>>>>>>>");
        String clientIpAddress = request.getRemoteAddr();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        log.info("Client IP Address = " + clientIpAddress);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth == " + auth.getName());
        log.info("auth username == " + auth.getPrincipal());
        log.info("data body request: " + request.toString());
        List<Section> getSecInfo =sectionRepository.findByBranchIdFromUserName(auth.getName());
        String secCod = getSecInfo.get(0).getSecId();
        DataResponse responsePopup = new DataResponse();
        try{
        responsePopup.setDataResponse(popupRepository.findDocAllById(secCod));
        if(responsePopup.getDataResponse() !=null){
            responsePopup.setStatus("00");
            responsePopup.setMessage("ສໍາເລັດ ");
        }else {
            responsePopup.setStatus("05");
            responsePopup.setMessage("ບໍ່ມິຂໍ້ມູນ");
        }

    }catch (Exception ex){
        responsePopup.setStatus("05");
        responsePopup.setMessage("ຂໍ້ມູນບໍ່ຖືກຕ້ອງ");
    }
        return ResponseEntity.ok(responsePopup);
    }

    //***********************************************Read document by User******************
    @ApiOperation(
            value = "Read in DocuementController",
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
            value = APIMappingPaths.HOME.API_HOME_READ,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST
    )
    @ResponseBody
    public ResponseEntity<?> Read(@ApiParam(
            name = "Body Request",
            value = "JSON body request to check information",
            required = true) @Valid @RequestBody Read readDocument,HttpServletRequest request) throws Exception {
        log.info("\t\t --> Read compare Request controller >>>>>>>>>>>>>>>>>>>>>>");
        String clientIpAddress = request.getRemoteAddr();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        log.info("Client IP Address = " + clientIpAddress);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth == " + auth.getName());
        log.info("auth username == " + auth.getPrincipal());
        log.info("data body request: " + request.toString());
        readDocument.setUserId(auth.getName());
        readDocument.setReadDate(new Date());
        DataResponse responsePopup = new DataResponse();
        try{
            responsePopup.setDataResponse(readDocService.storeSaveRead(readDocument));
            if(responsePopup.getDataResponse() !=null){
                responsePopup.setStatus("00");
                responsePopup.setMessage("ສໍາເລັດ ");
            }else {
                responsePopup.setStatus("05");
                responsePopup.setMessage("ບໍ່ມິຂໍ້ມູນ");
            }

        }catch (Exception ex){
           ex.printStackTrace();
        }
        return ResponseEntity.ok(responsePopup);
    }
}
