package com.ldb.weblocalapi.controller;

import com.ldb.weblocalapi.MediaUpload.MediaUploadService;
import com.ldb.weblocalapi.entities.DisplayLink;
import com.ldb.weblocalapi.entities.Document;
import com.ldb.weblocalapi.entities.Respone.DocumentRespone;
import com.ldb.weblocalapi.exceptions.DetailMessage.ExceptionResponse;
import com.ldb.weblocalapi.exceptions.Exception2.BadRequestException;
import com.ldb.weblocalapi.exceptions.Exception2.ForbiddenException;
import com.ldb.weblocalapi.exceptions.Exception2.NotFoundException;
import com.ldb.weblocalapi.exceptions.ExceptionStatus.InternalServerError;
import com.ldb.weblocalapi.exceptions.ExceptionStatus.UnAuthorizedException;
import com.ldb.weblocalapi.messages.response.DataResponse;
import com.ldb.weblocalapi.services.DisplayLinkService;
import com.ldb.weblocalapi.utils.APIMappingPaths;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.ServiceUnavailableException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(
        value = {"${url.mapping}" + APIMappingPaths.API_MB_GATEWAY_VERSION_PATH
                + APIMappingPaths.API_MB_REPORT_PATH}
)
public class DisPlayController {
    @Autowired
    MediaUploadService mediaUploadService;
    @Autowired
    DisplayLinkService displayLinkService;

    @ApiOperation(
            value = "DisplayLink in DisplayLinkController",
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
            value = APIMappingPaths.DISPLAYLINK.API_DISPLAYLINK,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST
    )
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @Secured({"ROLE_ADMIN"})
    @ResponseBody
    public ResponseEntity<?> getDisPlayLink(@ApiParam(
            name = "Body Request",
            value = "JSON body request to check information",
            required = true) @Valid @RequestBody DisplayLink displayLink, HttpServletRequest request) throws Exception {
        log.info("\t\t --> DisplayLink Request controller >>>>>>>>>>>>>>>>>>>>>>");
        String clientIpAddress = request.getRemoteAddr();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        log.info("Client IP Address = " + clientIpAddress);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth == " + auth.getName());
        log.info("auth username == " + auth.getPrincipal());
        log.info("data body request: " + request.toString());
        DataResponse response = displayLinkService.getDisPlayLink(displayLink,request);
        log.info("\t\t --> End Custom compare Request controller <<<<<<<<<<<<<<<<<<<");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //********************************searchByNameDisplayLink.service**************************************
    @ApiOperation(
            value = "DisplayLink in DisplayLinkController",
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
            value = APIMappingPaths.DISPLAYLINK.API_NAME_DISPLAYLINK,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST
    )
    @ResponseBody
    public ResponseEntity<?> searchByNameDisplayLink(@ApiParam(
            name = "Body Request",
            value = "JSON body request to check information",
            required = true) @Valid @RequestBody DisplayLink displayLink, HttpServletRequest request) throws Exception {
        log.info("\t\t --> DisplayLink Request controller >>>>>>>>>>>>>>>>>>>>>>");
        String clientIpAddress = request.getRemoteAddr();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        log.info("Client IP Address = " + clientIpAddress);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth == " + auth.getName());
        log.info("auth username == " + auth.getPrincipal());
        log.info("data body request: " + request.toString());
        DataResponse response = displayLinkService.getDisPlayLinkByName(displayLink,request);
        log.info("\t\t --> End Custom compare Request controller <<<<<<<<<<<<<<<<<<<");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //********************************************store disPlay Link **************************************************
    @ApiOperation(
            value = "DisplayLink in DisplayLinkController",
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
            value = APIMappingPaths.DISPLAYLINK.API_STORE_DISPLAYLINK,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST
    )
    @ResponseBody
    public ResponseEntity<?> StoreDisPlayLink(@ApiParam(
            name = "Body Request",
            value = "JSON body request to check information",
            required = true)@Valid  @RequestParam(name="files" , required=false)  MultipartFile[] files
            ,@RequestParam("name") String  name
            ,@RequestParam("note") String  note
            ,@RequestParam("link") String  link
            , HttpServletRequest request) throws Exception {
        log.info("\t\t --> DisplayLink Request controller >>>>>>>>>>>>>>>>>>>>>>");
        String clientIpAddress = request.getRemoteAddr();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        log.info("Client IP Address = " + clientIpAddress);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth == " + auth.getName());
        log.info("auth username == " + auth.getPrincipal());
        log.info("data body request: " + request.toString());
        DisplayLink data = new DisplayLink();
        data.setName(name);
        data.setNote(note);
        data.setLink(link);
        data.setSaveBy(auth.getName());
        //====================check doc file before upload this==============
        String fileName = "";
        List<String> fileNames = new ArrayList<>();
        if(files == null){
            log.warn("************* file name is null ****************");
            data.setImagePath("image.jpg");
        }else {
            Arrays.asList(files).stream().forEach(file -> {
                fileNames.add(mediaUploadService.uploadMedia(file));
            });
            log.info("Uploaded the files successfully: " + fileNames );
            fileName = StringUtils.join(fileNames, ',');
            data.setImagePath(fileName);
        }
        DataResponse response = displayLinkService.storeLink(data,request);
        log.info("\t\t --> End Custom compare Request controller <<<<<<<<<<<<<<<<<<<");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //********************************************Update disPlay Link **************************************************
    @ApiOperation(
            value = "DisplayLink in DisplayLinkController",
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
            value = APIMappingPaths.DISPLAYLINK.API_UPDATE_DISPLAYLINK,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST
            ,consumes = {"multipart/form-data"}  )

    @ResponseBody
    public ResponseEntity<?> UpdateDisPlayLink(@ApiParam(
            name = "Body Request",
            value = "JSON body request to check information",
            required = true)  @RequestParam(name="files",required = false) MultipartFile[] files
            ,@RequestParam("keyId") String  keyId
            ,@RequestParam("name") String  name
            ,@RequestParam("note") String  note
            ,@RequestParam("link") String  link, HttpServletRequest request) throws Exception {
        String clientIpAddress = request.getRemoteAddr();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        log.info("Client IP Address = " + clientIpAddress);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth == " + auth.getName());
        log.info("auth username == " + auth.getPrincipal());
        log.info("data body request: " + request.toString());
        DisplayLink data = new DisplayLink();
        data.setKeyId(keyId);
        data.setName(name);
        data.setNote(note);
        data.setLink(link);
        data.setSaveBy(auth.getName());
        //====================check doc file before upload this==============
        String fileName = "";
        List<String> fileNames = new ArrayList<>();
        if(files == null){
            log.warn("************* file name is null ****************");
            data.setImagePath("image.icon");
        }else {
            Arrays.asList(files).stream().forEach(file -> {
                fileNames.add(mediaUploadService.uploadMedia(file));
            });
            log.info("Uploaded the files successfully: " + fileNames );
            fileName = StringUtils.join(fileNames, ',');
            data.setImagePath(fileName);
        }
        DataResponse response = new DataResponse();
        log.info("show :"+files);
        if(files == null || files.length == 0 || files.equals(null) || files.equals("")){
            log.info("show no file");
            response = displayLinkService.updateDisplayLinkNoFile(data,request);
        }else {
            log.info("show have file");
            response = displayLinkService.updateLinkFile(data,request);
        }
        log.info("\t\t --> End displayLinkService compare Request controller <<<<<<<<<<<<<<<<<<<");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //********************************************Del disPlay Link **************************************************
    @ApiOperation(
            value = "DisplayLink in DisplayLinkController",
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
            value = APIMappingPaths.DISPLAYLINK.API_DEL_DISPLAYLINK,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST
    )
    @ResponseBody
    public ResponseEntity<?> deleteDisPlayLink(@ApiParam(
            name = "Body Request",
            value = "JSON body request to check information",
            required = true) @Valid @RequestBody DisplayLink displayLink, HttpServletRequest request) throws Exception {
        log.info("\t\t --> DisplayLink Request controller >>>>>>>>>>>>>>>>>>>>>>");
        String clientIpAddress = request.getRemoteAddr();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        log.info("Client IP Address = " + clientIpAddress);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth == " + auth.getName());
        log.info("auth username == " + auth.getPrincipal());
        log.info("data body request: " + request.toString());

        DataResponse response = displayLinkService.delLink(displayLink,request);
        log.info("\t\t --> End Custom compare Request controller <<<<<<<<<<<<<<<<<<<");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
