package com.ldb.weblocalapi.services.Impl;

import com.ldb.weblocalapi.Model.BranchReq;
import com.ldb.weblocalapi.Model.Fee;
import com.ldb.weblocalapi.messages.response.DataResponse;
import com.ldb.weblocalapi.services.FeeService;
import com.ldb.weblocalapi.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FeeServiceInterface implements FeeService {
    @Autowired
    FeeServiceImpl feeService;
    @Override
    public DataResponse getListFeeAll() {
        DataResponse dataResponse = new DataResponse();
        try {
            dataResponse.setDataResponse(feeService.getListFeeAll());
            if(dataResponse.getDataResponse() !=null){
                dataResponse.setStatus(Constant.RESPONSE_CODE.SUCCESS);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.SUCCESS_MSG);
            }else {
                dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DATA_NOT_FOUND_MSG);
            }
        }catch (Exception ex){
            dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
            dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DATA_NOT_FOUND_MSG);
        }
        return dataResponse;
    }

    @Override
    public DataResponse getListFeeAllByCondition(BranchReq docReq) {
        DataResponse dataResponse = new DataResponse();
        try {
            dataResponse.setDataResponse(feeService.getListFeeAllByCondition(docReq));
            if(dataResponse.getDataResponse() !=null){
                dataResponse.setStatus(Constant.RESPONSE_CODE.SUCCESS);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.SUCCESS_MSG);
            }else {
                dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DATA_NOT_FOUND_MSG);
            }
        }catch (Exception ex){
            dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
            dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DATA_NOT_FOUND_MSG);
        }
        return dataResponse;
    }
}

