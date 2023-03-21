package com.ldb.reportcustom.controller;

import com.ldb.reportcustom.entities.CreateUser;
import com.ldb.reportcustom.messages.response.DataResponse;
import com.ldb.reportcustom.services.CreateUserService;
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
                + APIMappingPaths.API_MB_REPORT_PATH + APIMappingPaths.API_AUTHENTICATION_GATEWAY_PATH})

public class CreateUserController {

    @Autowired
   private CreateUserService createUserService;
    @RequestMapping(
            value = APIMappingPaths.CREATEUSERS.API_CREATE_USER_GATEWAY_PATH,
            method = RequestMethod.POST
    )
    public ResponseEntity<?> saveUser(@Valid  @RequestBody CreateUser createUser){
        DataResponse responseBorder = new DataResponse();
        try {
           int ii = createUserService.saveUsers(createUser);
            if(ii > 0 ){
                responseBorder.setStatus("00");
                responseBorder.setMessage("Insert to db Success");
            }else {
                responseBorder.setStatus("05");
                responseBorder.setMessage("Data Fail");
            }
        }catch (Exception ex){

        }
        return ResponseEntity.ok(responseBorder);
    }
    //change password
    @RequestMapping(
            value = APIMappingPaths.CREATEUSERS.API_CHANGE_PASSWORD_USER_GATEWAY_PATH,
            method = RequestMethod.POST
    )
    public ResponseEntity<?> changePassword(@Valid  @RequestBody CreateUser createUser){
        DataResponse responseBorder = new DataResponse();
        try {
            int ii = createUserService.changePassword(createUser);
            if(ii > 0 ){
                responseBorder.setStatus("00");
                responseBorder.setMessage("update to db Success");
            }else {
                responseBorder.setStatus("05");
                responseBorder.setMessage("Data Fail");
            }
        }catch (Exception ex){

        }
        return ResponseEntity.ok(responseBorder);
    }

}
