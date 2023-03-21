package com.ldb.reportcustom.services.Impl;
import com.ldb.reportcustom.messages.response.BorderRespone;
import com.ldb.reportcustom.messages.response.ProvinceReponse;
import com.ldb.reportcustom.repositories.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Service
public class GetProvinceService implements ProvinceRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<ProvinceReponse> getProvince() {
        String ConSql = "select distinct province_code,province_name from tax_border  order by province_code asc ";
        return jdbcTemplate.query(ConSql, new RowMapper<ProvinceReponse>() {
            String AlO ="SELECT ALL";
            @Override
            public ProvinceReponse mapRow(ResultSet resultSet, int i) throws SQLException {
                ProvinceReponse resProvince= new ProvinceReponse();

                resProvince.setProvinceCode(resultSet.getString("province_code"));
               resProvince.setProvinceName(resultSet.getString("province_name"));
                return resProvince;
            }
        });

    }

    @Override
    public List<BorderRespone> getBorderByProvinceCode(String provinceCode) {
        String ConBoder = "select BORDER_ID,name from border.tax_border where province_code=? ";
       return jdbcTemplate.query(ConBoder, new RowMapper<BorderRespone>() {
            @Override
            public BorderRespone mapRow(ResultSet resultSet, int i) throws SQLException {
                BorderRespone borderRes = new BorderRespone();
                borderRes.setBorderCode(resultSet.getString("BORDER_ID"));
                borderRes.setBorderName(resultSet.getString("name"));
                return borderRes;
            }
        },provinceCode);

    }
}
