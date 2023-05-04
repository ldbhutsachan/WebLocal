package com.ldb.reportcustom.services;


import com.ldb.reportcustom.entities.Company;
import com.ldb.reportcustom.messages.request.GroupcompanyReq;
import com.ldb.reportcustom.messages.response.BorderRespone;

import java.util.List;

public interface GetCompanyService {
    public List<Company> ListCompany(String  borderCode);
}
