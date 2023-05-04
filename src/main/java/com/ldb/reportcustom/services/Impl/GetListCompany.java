package com.ldb.reportcustom.services.Impl;

import com.ldb.reportcustom.entities.Company;
import com.ldb.reportcustom.messages.request.GroupcompanyReq;
import com.ldb.reportcustom.messages.response.reportSW.RespAccountBorder;
import com.ldb.reportcustom.services.GetCompanyService;
import com.ldb.reportcustom.utils.LnswFunction;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Slf4j
@Service
public class GetListCompany implements GetCompanyService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private LnswFunction lnswFunction;
    @Override
    public List<Company> ListCompany(String  borderCode) {
        String condit="";
        condit += lnswFunction.borderIdCondit("A.ISSUING_CUSTOMER_OFFICE");
        System.out.println("boder condit:"+condit);
        StringBuilder sb = new StringBuilder();
        sb.append("select to_char('ALL') as tin_name, to_char('ALL') as ISSUING_CUSTOMER_OFFICE from dual union " +
                "SELECT tin_name,ISSUING_CUSTOMER_OFFICE FROM BORDER.TAX_INVOICE A" +
                " LEFT OUTER JOIN BORDER.TAX_INVOICE_DETAIL B ON a.REFERENCE = b.REFERENCE_INV_ID where ISSUING_CUSTOMER_OFFICE='"+borderCode+"'  group by ISSUER_NAME,ISSUING_CUSTOMER_OFFICE \n");
        String sql = sb.toString();
        log.info("SQL : " + sql);
        return this.jdbcTemplate.query(sql, new RowMapper<Company>() {
            @Override
            public Company mapRow(ResultSet rs, int i) throws SQLException {
                Company tr = new Company();
                tr.setTIN_NAME(rs.getString("tin_name"));
               // tr.setIssuing_customer_office(rs.getString("ISSUING_CUSTOMER_OFFICE"));
                return tr;
            }
        });
    }
}
