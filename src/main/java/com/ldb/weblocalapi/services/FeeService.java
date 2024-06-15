package com.ldb.weblocalapi.services;

import com.ldb.weblocalapi.Model.BranchReq;
import com.ldb.weblocalapi.Model.Fee;
import com.ldb.weblocalapi.messages.response.DataResponse;

import java.util.List;

public interface FeeService {
    public DataResponse getListFeeAll();
    public DataResponse getListFeeAllByCondition(BranchReq docReq);

}
