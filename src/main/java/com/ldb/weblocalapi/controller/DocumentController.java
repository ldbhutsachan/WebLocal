package com.ldb.weblocalapi.controller;


import com.ldb.weblocalapi.MediaUpload.MediaUploadService;
import com.ldb.weblocalapi.entities.Document;
import com.ldb.weblocalapi.entities.DocumentSharing;
import com.ldb.weblocalapi.entities.Respone.DocumentRespone;
import com.ldb.weblocalapi.entities.Respone.ReadDocument;
import com.ldb.weblocalapi.entities.Respone.UploadByUser;
import com.ldb.weblocalapi.exceptions.DetailMessage.ExceptionResponse;
import com.ldb.weblocalapi.exceptions.Exception2.BadRequestException;
import com.ldb.weblocalapi.exceptions.Exception2.ForbiddenException;
import com.ldb.weblocalapi.exceptions.Exception2.NotFoundException;
import com.ldb.weblocalapi.exceptions.ExceptionStatus.InternalServerError;
import com.ldb.weblocalapi.exceptions.ExceptionStatus.UnAuthorizedException;
import com.ldb.weblocalapi.messages.response.DataResponse;
import com.ldb.weblocalapi.repositories.DocumentSharingRepository;
import com.ldb.weblocalapi.repositories.StoreDocumentRepository;
import com.ldb.weblocalapi.services.DocumentService;
import com.ldb.weblocalapi.utils.APIMappingPaths;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.ServiceUnavailableException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(
        value = {"${url.mapping}" + APIMappingPaths.API_MB_GATEWAY_VERSION_PATH
                + APIMappingPaths.API_MB_REPORT_PATH}
)
public class DocumentController {
@Autowired
    DocumentService documentService;
@Autowired
DocumentSharingRepository documentSharingRepository;
@Autowired
    MediaUploadService mediaUploadService;
 //============================================================= document home ==================================================
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
            value = APIMappingPaths.DOCUMENT.API_DOCUMENT,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST
    )
    @ResponseBody
    public ResponseEntity<?> getSaveDocument( @ApiParam(
            name = "Body Request",
            value = "JSON body request to check information",
            required = true) @Valid @RequestBody DocumentRespone documentRespone, HttpServletRequest request) throws Exception {
        log.info("\t\t --> Custom compare Request controller >>>>>>>>>>>>>>>>>>>>>>");
        String clientIpAddress = request.getRemoteAddr();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        log.info("Client IP Address = " + clientIpAddress);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth == " + auth.getName());
        log.info("auth username == " + auth.getPrincipal());
        log.info("data body request: " + request.toString());
        DataResponse response = documentService.documentList(documentRespone,request);
        log.info("\t\t --> End Custom compare Request controller <<<<<<<<<<<<<<<<<<<");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //============================================================= document textbox serach ==================================================
    @ApiOperation(
            value = "textbox in DocuementController",
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
            value = APIMappingPaths.DOCUMENT.API_DOCUMENT_BYTEXTBOX,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST
    )
    @ResponseBody
    public ResponseEntity<?> getSaveDocumentByTextBox( @ApiParam(
            name = "Body Request",
            value = "JSON body request to check information",
            required = true) @Valid @RequestBody DocumentRespone documentRespone, HttpServletRequest request) throws Exception {
        log.info("\t\t --> Custom compare Request controller >>>>>>>>>>>>>>>>>>>>>>");
        String clientIpAddress = request.getRemoteAddr();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        log.info("Client IP Address = " + clientIpAddress);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth == " + auth.getName());
        log.info("auth username == " + auth.getPrincipal());
        log.info("data body request: " + request.toString());
        DataResponse response = documentService.documentListByTextBox(documentRespone,request);
        log.info("\t\t --> End Custom compare Request controller <<<<<<<<<<<<<<<<<<<");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

 @ApiOperation(
            value = "ReadDocDocumentByDocNo in DocuementController",
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
            value = APIMappingPaths.DOCUMENT.API_USER_READ_DOCUMENT,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST
    )
    @ResponseBody
    public ResponseEntity<?> getReadDocument(@ApiParam(
            name = "Body Request",
            value = "JSON body request to check information",
            required = true) @Valid @RequestBody ReadDocument readDocument, HttpServletRequest request) throws Exception {
        log.info("\t\t --> ReadDocDocumentByDocNo compare Request controller >>>>>>>>>>>>>>>>>>>>>>");
        String clientIpAddress = request.getRemoteAddr();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        log.info("Client IP Address = " + clientIpAddress);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth == " + auth.getName());
        log.info("auth username == " + auth.getPrincipal());
        log.info("data body request: " + request.toString());
        DataResponse response = documentService.ReadDocDocumentByDocNo(readDocument,request);
        log.info("\t\t --> End Custom compare Request controller <<<<<<<<<<<<<<<<<<<");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(
            value = "getUserSaveInfo in DocuementController",
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
            value = APIMappingPaths.DOCUMENT.API_USER_VIEW_INFO_BUTTON,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST
    )
    @ResponseBody
    public ResponseEntity<?> getUserSaveInfo(@ApiParam(
            name = "Body Request",
            value = "JSON body request to check information",
            required = true) @Valid @RequestBody UploadByUser uploadByUser, HttpServletRequest request) throws Exception {
        log.info("\t\t --> Custom compare Request controller >>>>>>>>>>>>>>>>>>>>>>");
        String clientIpAddress = request.getRemoteAddr();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        log.info("Client IP Address = " + clientIpAddress);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth == " + auth.getName());
        log.info("auth username == " + auth.getPrincipal());
        log.info("data body request: " + request.toString());
        DataResponse response = documentService.getUserSaveInfo(uploadByUser,request);
        log.info("\t\t --> End getUserSaveInfo Request controller <<<<<<<<<<<<<<<<<<<");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

   //=============================================================store document ==================================================
   @ApiOperation(
           value = "Save in DocuementController",
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
           value = APIMappingPaths.DOCUMENT.API_SAVE_DOCUMENT,
           produces = {
                   MediaType.APPLICATION_JSON_VALUE
           },
           method = RequestMethod.POST
   )
   @ResponseBody
   public ResponseEntity<DataResponse>saveDocument(@ApiParam(
           name = "Body Request",
           value = "JSON body request to check information",
           required = true) @Valid  @RequestParam(name="files" , required=false) MultipartFile files
           ,@RequestParam("docNo") String  docNo
           ,@RequestParam("docType") String  docType
           ,@RequestParam("docDate") String  docDate
           ,@RequestParam("note") String  note
           ,@RequestParam("docName") String  docName
           ,@RequestParam("popup") String  popup
           ,@RequestParam("type") String  type
           ,@RequestParam("docStatus") String  status
           ,@RequestParam("relationUnit") String relationUnit
           ,@RequestParam("relationUnitSec") String relationUnitSec
           ,@RequestParam("popupStart") String popupStart
           ,@RequestParam("popupEnd") String popupEnd,
                                                   HttpServletRequest request) throws Exception {
       log.info("\t\t --> DisplayLink Request controller >>>>>>>>>>>>>>>>>>>>>>");
       String clientIpAddress = request.getRemoteAddr();
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
       log.info("Client IP Address = " + clientIpAddress);
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       log.info("auth == " + auth.getName());
       log.info("auth username == " + auth.getPrincipal());

        DataResponse dataResponse = new DataResponse();
       String inputDate02 = docDate;
       SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
       Date date = inputFormat.parse(inputDate02);
       SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MMM-yy");
       String outputDate = outputFormat.format(date);

       String poStart = popupStart;
       SimpleDateFormat inputFormatStart = new SimpleDateFormat("dd/MM/yyyy");
       Date dateStart = inputFormatStart.parse(poStart);
       SimpleDateFormat outputFormatStart = new SimpleDateFormat("dd-MMM-yy");
       String outputDateStart = outputFormatStart.format(dateStart);

       String poEnd = popupEnd;
       SimpleDateFormat inputFormatEnd = new SimpleDateFormat("dd/MM/yyyy");
       Date dateEnd = inputFormatEnd.parse(poStart);
       SimpleDateFormat outputFormatEnd = new SimpleDateFormat("dd-MMM-yy");
       String outputDateEnd = outputFormatEnd.format(dateEnd);
                    Document data = new Document();
                        data.setDocNo(docNo);
                        data.setDocType(docType);
                        data.setSaveBy(auth.getName());
                        data.setSaveDate(new Date());
                        data.setDocDate(outputDate);
                        data.setNote(note);
                        data.setDocName(docName);
                        data.setPopup(popup);
                        data.setType(type);
                        data.setDocStatus(status);
                        data.setRelationUnit(relationUnit);
                        data.setRelationUnitSec(relationUnitSec);
                        data.setPopupStart(outputDateStart);
                        data.setPopupEnd(outputDateEnd);
       //====================check doc file before upload this==============
       log.info("file upload:"+files.getOriginalFilename());
       String originFile = files.getOriginalFilename();
       String fileName = "";
       List<String> fileNames = new ArrayList<>();
       if(files == null){
           log.warn("************* file name is null ****************");
           data.setDocPath("image.jpg");
       }else {
           Arrays.asList(files).stream().forEach(file -> {
               fileNames.add(mediaUploadService.uploadMedia(file,data,originFile));
           });
           log.info("Uploaded the files successfully: " + fileNames );
           fileName = StringUtils.join(fileNames, ',');
           data.setDocPath(fileName);
       }
       log.info("file path:"+fileName);
       dataResponse =  documentService.save(data);
       documentService.saveSharingDocument(relationUnit,docNo);
       if(!relationUnitSec.isEmpty()){
           log.info("insert band small ================><====================");
           documentService.saveSharingDocumentSec(relationUnitSec,docNo);
       }
       return new ResponseEntity<>(dataResponse , HttpStatus.OK);
   }

   //=============================================================Update document ==================================================
   @ApiOperation(
           value = "Update in DocuementController",
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
           value = APIMappingPaths.DOCUMENT.API_UPDATE_DOCUMENT,
           produces = {
                   MediaType.APPLICATION_JSON_VALUE
           },
           method = RequestMethod.POST
   )
   @ResponseBody
   public ResponseEntity<DataResponse>updateDocument(@ApiParam(
           name = "Body Request",
           value = "JSON body request to check information",
           required = true) @Valid  @RequestParam(name="files" , required=false) MultipartFile files
          //,@RequestParam("keyId") Long  keyId
           ,@RequestParam("docNo") String  docNo
           ,@RequestParam("docType") String  docType
           ,@RequestParam("docDate") String  docDate
           ,@RequestParam("note") String  note
           ,@RequestParam("docName") String  docName
           ,@RequestParam("popup") String  popup
           ,@RequestParam("type") String  type
           ,@RequestParam("docStatus") String  status
           ,@RequestParam("relationUnit") String relationUnit
           ,@RequestParam("relationUnitSec") String relationUnitSec
           ,@RequestParam("popupStart") String popupStart
           ,@RequestParam("popupEnd") String popupEnd,
                                                   HttpServletRequest request) throws Exception {
       log.info("\t\t --> DisplayLink Request controller >>>>>>>>>>>>>>>>>>>>>>");
       String clientIpAddress = request.getRemoteAddr();
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
       log.info("Client IP Address = " + clientIpAddress);
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       log.info("auth == " + auth.getName());
       log.info("auth username == " + auth.getPrincipal());

        DataResponse dataResponse = new DataResponse();
        DataResponse dataResponseArray = new DataResponse();
            String inputDate02 = docDate;
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = inputFormat.parse(inputDate02);
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MMM-yy");
            String outputDate = outputFormat.format(date);

            String poStart = popupStart;
            SimpleDateFormat inputFormatStart = new SimpleDateFormat("dd/MM/yyyy");
            Date dateStart = inputFormatStart.parse(poStart);
            SimpleDateFormat outputFormatStart = new SimpleDateFormat("dd-MMM-yy");
            String outputDateStart = outputFormatStart.format(dateStart);

            String poEnd = popupEnd;
            SimpleDateFormat inputFormatEnd = new SimpleDateFormat("dd/MM/yyyy");
            Date dateEnd = inputFormatEnd.parse(poStart);
            SimpleDateFormat outputFormatEnd = new SimpleDateFormat("dd-MMM-yy");
            String outputDateEnd = outputFormatEnd.format(dateEnd);
                    Document data = new Document();
                      //  data.setId(keyId);
                        data.setDocNo(docNo);
                        data.setDocType(docType);
                        data.setSaveBy(auth.getName());
                        data.setSaveDate(new Date());
                        data.setDocDate(outputDate);
                        data.setNote(note);
                        data.setDocName(docName);
                        data.setPopup(popup);
                        data.setType(type);
                        data.setDocStatus(status);
                        data.setRelationUnit(relationUnit);
                        data.setRelationUnitSec(relationUnitSec);
                        data.setPopupStart(outputDateStart);
                        data.setPopupEnd(outputDateEnd);
       //====================check doc file before upload this==============
       log.info("file upload:"+files.getOriginalFilename());
       String originFile = files.getOriginalFilename();
       String fileName = "";
       List<String> fileNames = new ArrayList<>();
       if(files == null){
           log.warn("************* file name is null ****************");
           data.setDocPath("image.jpg");
       }else {
           Arrays.asList(files).stream().forEach(file -> {
               fileNames.add(mediaUploadService.uploadMedia(file,data,originFile));
           });
           log.info("Uploaded the files successfully: " + fileNames );
           fileName = StringUtils.join(fileNames, ',');
           data.setDocPath(fileName);
       }
       log.info("file path:"+fileName);
       DataResponse response = new DataResponse();
       if(files.isEmpty()){
           dataResponse =  documentService.updateNoFile(data);
       }else {
           dataResponse =  documentService.update(data);
       }
       //************************clean data relationUnit frist*******************************
        documentService.upDateSharingDocument(relationUnit,docNo);
       //************************upload new data relationUnit *******************************
       documentService.saveSharingDocument(relationUnit,docNo);
       if(!relationUnitSec.isEmpty()){
           //************************clean data relationUnitSec frist*******************************
            documentService.upDateSharingDocumentSec(docNo);
           //************************clean data relationUnitSec frist*******************************
           documentService.saveSharingDocumentSec(relationUnitSec,docNo);
       }
       return new ResponseEntity<>(dataResponse , HttpStatus.OK);
   }
   //=============================================================delDocument.service document ==================================================
   @ApiOperation(
           value = "delDocument.service in DocuementController",
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
           value = APIMappingPaths.DOCUMENT.API_DEL_DOCUMENT,
           produces = {
                   MediaType.APPLICATION_JSON_VALUE
           },
           method = RequestMethod.POST
   )
   @ResponseBody
   public ResponseEntity<DataResponse>DelDocument(@ApiParam(
           name = "Body Request",
           value = "JSON body request to check information",
           required = true) @Valid @RequestBody Document document,
                                                   HttpServletRequest request) throws Exception {
       log.info("\t\t --> DisplayLink Request controller >>>>>>>>>>>>>>>>>>>>>>");
       String clientIpAddress = request.getRemoteAddr();
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
       log.info("Client IP Address = " + clientIpAddress);
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       log.info("auth == " + auth.getName());
       log.info("auth username == " + auth.getPrincipal());
        DataResponse dataResponse =  documentService.delDocumentFile(document);
       return new ResponseEntity<>(dataResponse , HttpStatus.OK);
   }
}
