package com.ldb.reportcustom.controller;

import com.ldb.reportcustom.entities.Company;
import com.ldb.reportcustom.exceptions.DetailMessage.ExceptionResponse;
import com.ldb.reportcustom.exceptions.ExceptionStatus.*;
import com.ldb.reportcustom.messages.request.GroupcompanyReq;
import com.ldb.reportcustom.messages.request.RequestReportByStartDate;
import com.ldb.reportcustom.messages.response.DataResponse;
import com.ldb.reportcustom.messages.response.ProvinceReponse;
import com.ldb.reportcustom.messages.response.reportSW.RespSingleWinDaily;
import com.ldb.reportcustom.utils.APIMappingPaths;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ldb.reportcustom.services.GetCompanyService;

import javax.naming.ServiceUnavailableException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(
        value = {"${url.mapping}" + APIMappingPaths.API_MB_GATEWAY_VERSION_PATH
                + APIMappingPaths.API_MB_REPORT_PATH})
public class CompanyGroupController {
    @Autowired
    private GetCompanyService getCompanyService;
    //----
    @RequestMapping(
            value = APIMappingPaths.ReportAdmin.API_CUSTOM_SINGLE_WINDOW_GATEWAY_COMPANY_GROUP_PATH,
            method = RequestMethod.POST
    )
    public ResponseEntity<?> getProvince(GroupcompanyReq  borderCode){
        DataResponse responseBorder = new DataResponse();
        try {
            //provinceService
            responseBorder.setDataResponse(getCompanyService.ListCompany(borderCode));
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
}
