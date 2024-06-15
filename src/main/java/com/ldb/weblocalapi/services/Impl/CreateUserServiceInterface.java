package com.ldb.weblocalapi.services.Impl;

import com.ldb.weblocalapi.Model.BranchReq;
import com.ldb.weblocalapi.Model.ChangePasswordReq;
import com.ldb.weblocalapi.entities.CreateUser;
import com.ldb.weblocalapi.messages.response.DataResponse;
import com.ldb.weblocalapi.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CreateUserServiceInterface {
    @Autowired
    CreateUserServiceImpl createUserService;
    org.springframework.security.crypto.password.PasswordEncoder encoder
            = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    public DataResponse ChangePassword(ChangePasswordReq docReq) {
        DataResponse dataResponse = new DataResponse();
        try {
          String newPassword = docReq.getNewPassword();
          String confirmPassword = docReq.getConfirmPassword();
          String oldPassword = docReq.getOldPassword();
          String userName = docReq.getUserName();
            //--------------------------------check 2 password********************
          if(!newPassword.equals(confirmPassword)){
              dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_USER);
              dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DATA_NOT_DIFICATE);
          }else {
              List<CreateUser> checkOldPassword = createUserService.checkOldPassword(oldPassword,userName);
              String checkOldPass = checkOldPassword.get(0).getPassword();
              String hashedPassword = checkOldPass;
              String plainTextPassword = oldPassword;
              BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
              boolean isPasswordValid = encoder.matches(plainTextPassword, hashedPassword);
              System.out.println("Is password valid? " + isPasswordValid);
              if (!isPasswordValid) {
                  dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_PASS);
                  dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DATA_OLD_PASS_FAIL);
              }else {
                  String plainTextPassword11 = newPassword;
                  BCryptPasswordEncoder encoder11 = new BCryptPasswordEncoder();
                  String encodedPassword = encoder11.encode(plainTextPassword11);
                  createUserService.changePassword(encodedPassword,userName);
                  dataResponse.setStatus(Constant.RESPONSE_CODE.SUCCESS);
                  dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DATA_DON_CHANGE);
              }
          }
            //--------------------------------check old password********************

        }catch (Exception ex){
            dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
            dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DATA_NOT_FOUND_MSG);
        }
        return dataResponse;
    }
}
