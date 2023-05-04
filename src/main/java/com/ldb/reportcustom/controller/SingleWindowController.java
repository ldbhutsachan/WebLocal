package com.ldb.reportcustom.controller;

import com.ldb.reportcustom.exceptions.DetailMessage.ExceptionResponse;
import com.ldb.reportcustom.exceptions.ExceptionStatus.*;
import com.ldb.reportcustom.messages.request.*;
import com.ldb.reportcustom.messages.response.DataResponse;
import com.ldb.reportcustom.messages.response.reportSW.RespSingleWinDaily;
import com.ldb.reportcustom.services.SingleWindowService;
import com.ldb.reportcustom.utils.APIMappingPaths;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Context;
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

/**
 * Create at 12/12/2022 - 6:25 PM
 * Project Name reportCustom
 *
 * @author kataii
 */
@Slf4j
@RestController
@RequestMapping(
        value = {"${url.mapping}" + APIMappingPaths.API_MB_GATEWAY_VERSION_PATH
                + APIMappingPaths.API_MB_REPORT_PATH}
)
public class SingleWindowController {

    @Autowired
    private SingleWindowService singleWindowService;

    @ApiOperation(
            value = "Report Custom in daily Tap 1",
            authorizations = {@Authorization(value = "apiKey")},
            response = RespSingleWinDaily.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = RespSingleWinDaily.class),
            @ApiResponse(code = 201, message = "Created", response = ExceptionResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = UnAuthorizedException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = InternalServerError.class),
            @ApiResponse(code = 503, message = "Service Unavailable", response = ServiceUnavailableException.class)
    })
    @RequestMapping(
            value = APIMappingPaths.REPORT_SERVICE.API_CUSTOM_SINGLE_WINDOW_GATEWAY_PATH,
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST
    )
//    @PreAuthorize("hasRole('ROLE_BORDER')")
//    @Secured({"ROLE_BORDER"})
//    @PreAuthorize("hasAnyRole(@environment.getProperty('ROLE_REPORT_MOBILE_ADMIN')) or hasRole(@environment.getProperty('ROLE_REPORT_MOBILE_VISA'))")
    @ResponseBody
    public ResponseEntity<?> reportCustom(
            @ApiParam(
                    name = "Body Request",
                    value = "JSON body request to check information",
                    required = true)
            @Valid
            @RequestBody RequestReportByStartDate dataRequest,
            @Context HttpServletRequest request
    ) throws Exception {
        log.info("\t\t --> Custom Request controller >>>>>>>>>>>>>>>>>>>>>>");
        String clientIpAddress = request.getRemoteAddr();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        log.info("Client IP Address = " + clientIpAddress);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        log.info("auth == " + auth.getName());
        log.info("auth username == " + auth.getPrincipal());
        log.info("data body request: " + dataRequest.toString());


        DataResponse response = singleWindowService.paymentReport(dataRequest);
        log.info("\t\t --> End Custom Request controller <<<<<<<<<<<<<<<<<<<");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ApiOperation(
            value = "Report Custom Compare : Tap 2",
            authorizations = {@Authorization(value = "apiKey")},
            response = RespSingleWinDaily.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = RespSingleWinDaily.class),
            @ApiResponse(code = 201, message = "Created", response = ExceptionResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = UnAuthorizedException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = InternalServerError.class),
            @ApiResponse(code = 503, message = "Service Unavailable", response = ServiceUnavailableException.class)
    })
    @RequestMapping(
            value = APIMappingPaths.REPORT_SERVICE.API_CUSTOM_SINGLE_WINDOW_GATEWAY_COMPARE_PATH,
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST
    )
//    @PreAuthorize("hasRole('ROLE_BORDER')")
//    @Secured({"ROLE_BORDER"})
//    @PreAuthorize("hasAnyRole(@environment.getProperty('ROLE_REPORT_MOBILE_ADMIN')) or hasRole(@environment.getProperty('ROLE_REPORT_MOBILE_VISA'))")
    @ResponseBody
    public ResponseEntity<?> reportCustomCompare(
            @ApiParam(
                    name = "Body Request",
                    value = "JSON body request to check information",
                    required = true)
            @Valid
            @RequestBody RequestReportByStartDate dataRequest,
            @Context HttpServletRequest request
    ) throws Exception {
        log.info("\t\t --> Custom compare Request controller >>>>>>>>>>>>>>>>>>>>>>");
        String clientIpAddress = request.getRemoteAddr();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        log.info("Client IP Address = " + clientIpAddress);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        log.info("auth == " + auth.getName());
        log.info("auth username == " + auth.getPrincipal());
        log.info("data body request: " + dataRequest.toString());


        DataResponse response = singleWindowService.paymentCompareReport(dataRequest);
        log.info("\t\t --> End Custom compare Request controller <<<<<<<<<<<<<<<<<<<");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(
            value = "Report Custom total : Tap 3",
            authorizations = {@Authorization(value = "apiKey")},
            response = RespSingleWinDaily.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = RespSingleWinDaily.class),
            @ApiResponse(code = 201, message = "Created", response = ExceptionResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = UnAuthorizedException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = InternalServerError.class),
            @ApiResponse(code = 503, message = "Service Unavailable", response = ServiceUnavailableException.class)
    })
    @RequestMapping(
            value = APIMappingPaths.REPORT_SERVICE.API_CUSTOM_SINGLE_WINDOW_GATEWAY_TOTAL_PATH,
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST
    )
//    @PreAuthorize("hasRole('ROLE_BORDER')")
//    @Secured({"ROLE_BORDER"})
//    @PreAuthorize("hasAnyRole(@environment.getProperty('ROLE_REPORT_MOBILE_ADMIN')) or hasRole(@environment.getProperty('ROLE_REPORT_MOBILE_VISA'))")
    @ResponseBody
    public ResponseEntity<?> reportCustomTotal(
            @ApiParam(
                    name = "Body Request",
                    value = "JSON body request to check information",
                    required = true)
            @Valid
            @RequestBody RequestReportByStartDate dataRequest,
            @Context HttpServletRequest request
    ) throws Exception {
        log.info("\t\t --> Custom total Request controller >>>>>>>>>>>>>>>>>>>>>>");
        String clientIpAddress = request.getRemoteAddr();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        log.info("Client IP Address = " + clientIpAddress);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth == " + auth.getName());
        log.info("auth username == " + auth.getPrincipal());
        log.info("data body request: " + dataRequest.toString());
        DataResponse response = singleWindowService.paymentTotalReport(dataRequest);
        log.info("\t\t --> End Custom total Request controller <<<<<<<<<<<<<<<<<<<");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //Report total company
    @ApiOperation(
            value = "Report Custom in daily Tap 1",
            authorizations = {@Authorization(value = "apiKey")},
            response = RespSingleWinDaily.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = RespSingleWinDaily.class),
            @ApiResponse(code = 201, message = "Created", response = ExceptionResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = UnAuthorizedException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = InternalServerError.class),
            @ApiResponse(code = 503, message = "Service Unavailable", response = ServiceUnavailableException.class)
    })
    @RequestMapping(
            value = APIMappingPaths.ReportAdmin.API_CUSTOM_SINGLE_WINDOW_GATEWAY_COMPANY_PATH,
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST
    )
//    @PreAuthorize("hasRole('ROLE_BORDER')")
//    @Secured({"ROLE_BORDER"})
//    @PreAuthorize("hasAnyRole(@environment.getProperty('ROLE_REPORT_MOBILE_ADMIN')) or hasRole(@environment.getProperty('ROLE_REPORT_MOBILE_VISA'))")
    @ResponseBody
    public ResponseEntity<?> reportCompany(
            @ApiParam(
                    name = "Body Request",
                    value = "JSON body request to check information",
                    required = true)
            @Valid
            @RequestBody RequestDatebyCompany dataRequest,
            @Context HttpServletRequest request
    ) throws Exception {
        log.info("\t\t --> Custom Request controller >>>>>>>>>>>>>>>>>>>>>>");
        String clientIpAddress = request.getRemoteAddr();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        log.info("Client IP Address = " + clientIpAddress);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth == " + auth.getName());
        log.info("auth username == " + auth.getPrincipal());
        log.info("data body request: " + dataRequest.toString());
        DataResponse response = singleWindowService.ReportCompany(dataRequest);
        log.info("\t\t --> End Custom Request controller <<<<<<<<<<<<<<<<<<<");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
