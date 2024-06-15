package com.ldb.weblocalapi.repositories;

import com.ldb.weblocalapi.messages.response.BorderRespone;
import com.ldb.weblocalapi.messages.response.ProvinceReponse;

import java.util.List;

public interface ProvinceRepository {
    public List<ProvinceReponse> getProvince(ProvinceReponse provinceReponse);
    public  List<BorderRespone> getBorderByProvinceCode(String provinceCode);


}
