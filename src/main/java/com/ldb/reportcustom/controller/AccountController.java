package com.ldb.reportcustom.controller;

import com.ldb.reportcustom.messages.request.AccountReq;
import com.ldb.reportcustom.messages.request.GroupcompanyReq;
import com.ldb.reportcustom.messages.response.DataResponse;
import com.ldb.reportcustom.services.GetAccountService;
import com.ldb.reportcustom.utils.APIMappingPaths;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(
        value = {"${url.mapping}" + APIMappingPaths.API_MB_GATEWAY_VERSION_PATH
                + APIMappingPaths.API_MB_REPORT_PATH})
public class AccountController {
    @Autowired
    private GetAccountService getAccountService;
    @RequestMapping(
            value = APIMappingPaths.ReportAdmin.API_CUSTOM_SINGLE_WINDOW_GATEWAY_ACC_LIST_PATH,
            method = RequestMethod.POST
    )
    public ResponseEntity<?> getAccListByNo(){
        DataResponse responseBorder = new DataResponse();
        try {
            //provinceService
            responseBorder.setDataResponse(getAccountService.ListAccount());
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
    //*********************************************report by ACCNO====================================
    @RequestMapping(
            value = APIMappingPaths.ReportAdmin.API_CUSTOM_SINGLE_WINDOW_GATEWAY_ACCNO_PATH,
            method = RequestMethod.POST
    )
    public ResponseEntity<?> getReportByAccNo(@Valid @RequestBody AccountReq req){
        DataResponse responseBorder = new DataResponse();
        try {
            //provinceService
            responseBorder.setDataResponse(getAccountService.ReportAccountByAccNoAll(req));
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

