package com.ldb.weblocalapi.services.Impl;

import com.ldb.weblocalapi.Model.BranchReq;
import com.ldb.weblocalapi.Model.DocumentSecMenu;
import com.ldb.weblocalapi.Model.Fee;
import com.ldb.weblocalapi.messages.request.DocReq;
import com.ldb.weblocalapi.services.FeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Slf4j
@Service
public class FeeServiceImpl {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Fee> getListFeeAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM V_FEE_MENU where 1=1 order by KEY_ID desc ");
        String sql = sb.toString();
        log.info("SQL : " + sql);
        return this.jdbcTemplate.query(sql, new RowMapper<Fee>() {
            @Override
            public Fee mapRow(ResultSet rs, int i) throws SQLException {
                Fee tr = new Fee();
                tr.setKeyId(rs.getString("ID"));
                tr.setDocName(rs.getString("DOCNAME"));
                tr.setDocNo(rs.getString("DOC_NO"));
                tr.setDocTypeNo(rs.getString("DOCTYPENO"));
                tr.setDocTypeName(rs.getString("DOC_TYPE_NAME"));
                tr.setDocDate(rs.getString("DOC_DATE"));
                tr.setSaveDate(rs.getString("SAVE_DATE"));
                tr.setSaveBy(rs.getString("SAVE_BY"));
                tr.setName(rs.getString("NAME"));
                tr.setDocPath(rs.getString("DOC_PATH"));
                tr.setDocStatus(rs.getString("DOC_STATUS"));
                tr.setTypeDocIn_Out(rs.getString("TYPE"));
                tr.setAmtRead(rs.getString("TOTAL"));
                return tr;
            }
        });
    }
    public List<Fee> getListFeeAllByCondition(BranchReq docReq) {
        StringBuilder sb = new StringBuilder();
        try{
            String conditionDate = "";
            String conditionType = "";
            String conditionDocTypeNo = "";
            String conditionInputText = "";
            String type = docReq.getType();
            String docTypeNo = docReq.getDocType();
            String inputText = docReq.getInBoxText();
            //********************************check date ******************************************
            String startDate = docReq.getStartDate();
            String endDate = docReq.getEndDate();
            String outputFormat = "dd/MM/yyyy";
            LocalDate date = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
            String outputDateStart = date.format(DateTimeFormatter.ofPattern(outputFormat));
            LocalDate dateEnd = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
            String outputDateEnd = dateEnd.format(DateTimeFormatter.ofPattern(outputFormat));
            //********************FEE= 0001 ຄ່າທໍານຽມ  ITR= 0002 ອັດຕາດອກເບ້ຍ  ITR_CR=0003 ອັດຕາດອກເບ້ຍເງິນກຼ້ -------
            if (type.equals("FEE")) {
                conditionType += " AND DOCTYPENO='66' ";
            }
            if (type.equals("ITR")) {
                conditionType += " AND DOCTYPENO='67' ";
            }
            if (type.equals("ITR_CR")) {
                conditionType += " AND DOCTYPENO='68' ";
            }
            if (!outputDateStart.equals("")) {
                conditionDate += " AND DOC_DATE >= '" + outputDateStart + "' AND DOC_DATE <= '" + outputDateEnd + "'";
            }
            if (!docTypeNo.equals("0")) {
                conditionDocTypeNo += " AND DOCTYPENO='" + docTypeNo + "' ";
            }
            if (!inputText.equals("")) {
                conditionInputText += " AND DOC_NO like '%" + inputText + "%' OR  DOCNAME like '%" + inputText + "%' ";
            }
            sb.append("SELECT * FROM V_FEE_MENU where 1=1  ");
            sb.append(conditionType);
            sb.append(conditionDate);
            sb.append(conditionDocTypeNo);
            sb.append(conditionInputText);
            sb.append(" order by KEY_ID desc");
            String sql = sb.toString();
            log.info("SQL : " + sql);
            return this.jdbcTemplate.query(sql, new RowMapper<Fee>() {
                @Override
                public Fee mapRow(ResultSet rs, int i) throws SQLException {
                    Fee tr = new Fee();
                    tr.setKeyId(rs.getString("ID"));
                    tr.setDocName(rs.getString("DOCNAME"));
                    tr.setDocNo(rs.getString("DOC_NO"));
                    tr.setDocTypeNo(rs.getString("DOCTYPENO"));
                    tr.setDocTypeName(rs.getString("DOC_TYPE_NAME"));
                    tr.setDocDate(rs.getString("DOC_DATE"));
                    tr.setSaveDate(rs.getString("SAVE_DATE"));
                    tr.setSaveBy(rs.getString("SAVE_BY"));
                    tr.setName(rs.getString("NAME"));
                    tr.setDocPath(rs.getString("DOC_PATH"));
                    tr.setDocStatus(rs.getString("DOC_STATUS"));
                    tr.setTypeDocIn_Out(rs.getString("TYPE"));
                    tr.setAmtRead(rs.getString("TOTAL"));
                    return tr;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
