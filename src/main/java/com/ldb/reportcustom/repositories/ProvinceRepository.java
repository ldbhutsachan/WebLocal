package com.ldb.reportcustom.repositories;

import com.ldb.reportcustom.messages.request.RequestReportByStartDate;
import com.ldb.reportcustom.messages.response.BorderRespone;
import com.ldb.reportcustom.messages.response.DataResponse;
import com.ldb.reportcustom.messages.response.ProvinceReponse;

import java.util.List;

public interface ProvinceRepository {
    public List<ProvinceReponse> getProvince(ProvinceReponse provinceReponse);
    public  List<BorderRespone> getBorderByProvinceCode(String provinceCode);


}
