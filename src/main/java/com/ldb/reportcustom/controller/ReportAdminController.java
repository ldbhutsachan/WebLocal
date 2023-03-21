package com.ldb.reportcustom.controller;

import com.ldb.reportcustom.exceptions.DetailMessage.ExceptionResponse;
import com.ldb.reportcustom.exceptions.ExceptionStatus.*;
import com.ldb.reportcustom.messages.request.BoderRequest;
import com.ldb.reportcustom.messages.request.BorderRequestReportByBoderID;
import com.ldb.reportcustom.messages.response.DataResponse;
import com.ldb.reportcustom.messages.response.reportSW.RespSingleWinDaily;
import com.ldb.reportcustom.repositories.ProvinceRepository;
import com.ldb.reportcustom.services.SingleWindowServiceAdmin;
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
@Slf4j
@RestController
@RequestMapping(
        value = {"${url.mapping}" + APIMappingPaths.API_MB_GATEWAY_VERSION_PATH
                + APIMappingPaths.API_MB_REPORT_PATH}
)
public class ReportAdminController {

    @Autowired
    private SingleWindowServiceAdmin SingleWindowServiceAdmin;
    @Autowired
    private ProvinceRepository provinceService;
    @RequestMapping(
            value = APIMappingPaths.ReportAdmin.API_CUSTOM_SINGLE_WINDOW_GATEWAY_PATH_PROVINCE,
            method = RequestMethod.GET
    )
    public ResponseEntity<?> getProvince(){
        DataResponse responseProvince = new DataResponse();
        try {
            //provinceService
            responseProvince.setDataResponse(provinceService.getProvince());
            if(responseProvince.getDataResponse() !=null){
                responseProvince.setStatus("00");
                responseProvince.setMessage("Success");
            }else {
                responseProvince.setStatus("05");
                responseProvince.setMessage("Data Not Found");
            }
        }catch (Exception ex){

        }
        return ResponseEntity.ok(responseProvince);
    }
    //path

    @RequestMapping(
            value = APIMappingPaths.ReportAdmin.API_CUSTOM_SINGLE_WINDOW_GATEWAY_PATH_ADMIN_BORDER,
            method = RequestMethod.POST
    )
    public ResponseEntity<?> getBorder(@RequestBody BoderRequest provinceCode){
        DataResponse responseBorder = new DataResponse();
        try {
            responseBorder.setDataResponse(provinceService.getBorderByProvinceCode(provinceCode.getProvinceCode()));
            if(responseBorder.getDataResponse() !=null){
                responseBorder.setStatus("00");
                responseBorder.setMessage("Success");
            }else {
                responseBorder.setStatus("05");
                responseBorder.setMessage("Data Not Found");
            }
        }catch (Exception ex){

        }
        return ResponseEntity.ok(responseBorder);
    }
    //Report singleWindowDailyAdmin
    @ApiOperation(
            value = "Report Custom in singleWindowDailyAdmin",
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
            value = APIMappingPaths.ReportAdmin.API_CUSTOM_SINGLE_WINDOW_GATEWAY_PATH_ADMIN,
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST
    )
    @ResponseBody
    public ResponseEntity<?> reportAdmin(
            @ApiParam(
                    name = "Body Request",
                    value = "JSON body request to check information",
                    required = true)
            @Valid
            @RequestBody BorderRequestReportByBoderID dataRequest,
            @Context HttpServletRequest request
    ) throws Exception {
        log.info("\t\t --> Custom Request controller admin>>>>>>>>>>>>>>>>>>>>>>");
        String clientIpAddress = request.getRemoteAddr();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        log.info("Client IP Address = " + clientIpAddress);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth == " + auth.getName());
        log.info("auth username == " + auth.getPrincipal());
        log.info("data body request: " + dataRequest.toString());
        DataResponse response = SingleWindowServiceAdmin.paymentReportAdmin(dataRequest);
        log.info("\t\t --> End Custom Request controller <<<<<<<<<<<<<<<<<<<");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //End report singleWindowDailyAdmin
    //Report compare show to admin customer

    @ApiOperation(
            value = "Report Custom Compare",
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
            value = APIMappingPaths.ReportAdmin.API_CUSTOM_SINGLE_WINDOW_GATEWAY_COMPARE_PATH_ADMIN,
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST
    )
    @ResponseBody
    public ResponseEntity<?> reportCustomCompareAdmin(
            @ApiParam(
                    name = "Body Request",
                    value = "JSON body request to check information",
                    required = true)
            @Valid
            @RequestBody BorderRequestReportByBoderID dataRequest,
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


        DataResponse response = SingleWindowServiceAdmin.paymentCompareReportAdmin(dataRequest);
        log.info("\t\t --> End Custom compare Request controller <<<<<<<<<<<<<<<<<<<");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
    //end

