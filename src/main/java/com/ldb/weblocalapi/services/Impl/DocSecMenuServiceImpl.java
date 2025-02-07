package com.ldb.weblocalapi.services.Impl;

import com.ldb.weblocalapi.Model.BranchReq;
import com.ldb.weblocalapi.Model.DocumentSecMenu;
import com.ldb.weblocalapi.entities.Respone.DocumentRespone;
import com.ldb.weblocalapi.entities.Respone.DocumentSecMenuRespone;
import com.ldb.weblocalapi.messages.request.DocReq;
import com.ldb.weblocalapi.repositories.DocumentRepository;
import com.ldb.weblocalapi.services.DocSecMenuServiceService;
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
public class DocSecMenuServiceImpl implements DocSecMenuServiceService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    DocumentRepository documentRepository;

    @Override
    public List<DocumentSecMenu> findDocAllDocumentListByBandAll(String secCode,BranchReq req) {
        String condiction = "";
        if(req.getType().equals("IN")){
            condiction = " AND TYPE ='' ";
        }
        else if(req.getType().equals("OUT")){
            condiction = " AND TYPE ='' ";
        }
        else {
            condiction = "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT");
        sb.append(" TYPE, ID,RELATION_UNIT, DOCNAME, DOC_NO, DOCTYPENO, DOC_TYPE_NAME, DOC_DATE, SAVE_DATE, NAME, SAVE_BY, READ_BY, DOC_PATH, DOC_STATUS,  (SELECT COUNT(*) FROM read_doc b where b.DOC_KEY_ID=id ) as total ");
        sb.append(" from  V_BAND_MENU_COUNTER where 1=1 and RELATION_UNIT='"+secCode+"' "+condiction+" ");

        String sql = sb.toString();
         log.info("SQL : " + sql);
        return this.jdbcTemplate.query(sql, new RowMapper<DocumentSecMenu>() {
            @Override
            public DocumentSecMenu mapRow(ResultSet rs, int i) throws SQLException {
                DocumentSecMenu tr = new DocumentSecMenu();
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
                tr.setAmtRead(rs.getInt("TOTAL"));
               // tr.setRelationUnit(rs.getString("RELATION_UNIT"));
                return tr;
            }
        });
    }
    @Override
    public List<DocumentSecMenu> findDocAllDocumentListByBranchMenu(BranchReq docReq) {
        String conditionDate = "";
        String conditionCode = "";
        String conditionCodeSec = "";
        String conditionType = "";
        String conditionDocTypeNo = "";
        String conditionInputText = "";
        String brandCode = docReq.getBrandCode();
        String brandCodeSec = docReq.getSmallBrandCode();
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
        if (!outputDateStart.equals("")) {
            conditionDate += " AND DOC_DATE >= '" + outputDateStart + "' AND DOC_DATE <= '" + outputDateEnd + "'";
        }
        if (!brandCode.equals("0")) {
            conditionCode += " AND RELATION_UNIT='" + brandCode + "' ";
        }
        if (!brandCodeSec.equals("0")) {
            conditionCodeSec += " AND RELATION_UNIT_SEC='" + brandCodeSec + "' ";
        }
        if (!type.equals("0")) {
            conditionType += " AND TYPE='" + type + "' ";
        }
        if (!docTypeNo.equals("0")) {
            conditionDocTypeNo += " AND DOCTYPENO='" + docTypeNo + "' ";
        }
        if (!inputText.equals("")) {
            conditionInputText += " AND DOC_NO like '%" + inputText + "%' OR  DOCNAME like '%" + inputText + "%' ";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("TYPE, ID,RELATION_UNIT, DOCNAME, DOC_NO, DOCTYPENO, DOC_TYPE_NAME, DOC_DATE, SAVE_DATE, NAME,SAVE_BY, READ_BY, DOC_PATH, DOC_STATUS,  (SELECT COUNT(*) FROM read_doc b where b.DOC_KEY_ID=id ) as total ");
        sb.append(" from  V_BAND_MENU_COUNTER where 1=1  ");
        sb.append(conditionDate); //*****check date not emt
        sb.append(conditionCode); //*****check band code
        sb.append(conditionType); //*****check type 0 in out
        sb.append(conditionDocTypeNo);//***check docTypeno
        sb.append(conditionInputText);//****check input from text
        sb.append(conditionCodeSec);//******check combo check small band
        String sql = sb.toString();
        log.info("SQL : " + sql);
        return this.jdbcTemplate.query(sql, new RowMapper<DocumentSecMenu>() {
            @Override
            public DocumentSecMenu mapRow(ResultSet rs, int i) throws SQLException {
                DocumentSecMenu tr = new DocumentSecMenu();
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
                tr.setAmtRead(rs.getInt("TOTAL"));
                tr.setRelationUnit(rs.getString("RELATION_UNIT"));
                return tr;
            }
        });
    }

    //------------------------Second
    @Override
    public List<DocumentSecMenuRespone> findSecMenu(DocReq docReq) {
        String conditionDate = "";
        String conditionCode = "";
        String conditionType = "";
        String conditionDocTypeNo = "";
        String conditionInputText = "";
        String code = docReq.getCode();

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
        if (!outputDateStart.equals("")) {
            conditionDate += " AND DOC_DATE >= '" + outputDateStart + "' AND DOC_DATE <= '" + outputDateEnd + "'";
        }
        if (!code.equals("0")) {
            conditionCode += " AND RELATION_UNIT='" + code + "' ";
        }
        if (!type.equals("0")) {
            conditionType += " AND TYPE='" + type + "' ";
        }
        if (!docTypeNo.equals("0")) {
            conditionDocTypeNo += " AND DOCTYPENO='" + docTypeNo + "' ";
        }
        if (!inputText.equals("")) {
            conditionInputText += " AND DOC_NO like '%" + inputText + "%' OR  DOCNAME like '%" + inputText + "%' ";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("TYPE, ID,RELATION_UNIT, DOCNAME, DOC_NO, DOCTYPENO, DOC_TYPE_NAME, DOC_DATE, SAVE_DATE, NAME,SAVE_BY, DOC_PATH, DOC_STATUS,  (SELECT COUNT(*) FROM read_doc b where b.DOC_KEY_ID=id ) as total ");
        sb.append(" from  V_SECTION_MENU_COUNTER where 1=1  ");
        sb.append(conditionDate); //*****check date not emt
        sb.append(conditionCode); //*****check band code
        sb.append(conditionType); //*****check type 0 in out
        sb.append(conditionDocTypeNo);//***check docTypeno
        sb.append(conditionInputText);//****check input from text
        String sql = sb.toString();
        log.info("SQL : " + sql);
        return this.jdbcTemplate.query(sql, new RowMapper<DocumentSecMenuRespone>() {
            @Override
            public DocumentSecMenuRespone mapRow(ResultSet rs, int i) throws SQLException {
                DocumentSecMenuRespone tr = new DocumentSecMenuRespone();
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
                tr.setRelationUnit(rs.getString("RELATION_UNIT"));


                return tr;
            }
        });
    }

}
