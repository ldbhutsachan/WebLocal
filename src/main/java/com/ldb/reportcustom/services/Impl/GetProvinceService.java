package com.ldb.reportcustom.services.Impl;
import com.ldb.reportcustom.messages.response.BorderRespone;
import com.ldb.reportcustom.messages.response.ProvinceReponse;
import com.ldb.reportcustom.repositories.ProvinceRepository;
import com.ldb.reportcustom.utils.LnswFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Slf4j
@Service
public class GetProvinceService implements ProvinceRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private LnswFunction lnswFunction;
    @Override
    public List<ProvinceReponse> getProvince(ProvinceReponse provinceReponse) {
       String test= lnswFunction.borderIdCondit("NAME");
        String ConSql="";
             ConSql = "select distinct province_code,province_name from border.tax_border  order by province_code asc ";
            log.info("SQL"+ConSql);
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
