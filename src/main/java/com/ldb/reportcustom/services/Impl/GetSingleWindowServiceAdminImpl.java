package com.ldb.reportcustom.services.Impl;

import com.ldb.reportcustom.messages.request.BorderRequestReportByBoderID;
import com.ldb.reportcustom.messages.request.LoginRequest;
import com.ldb.reportcustom.messages.request.RequestReportByStartDate;
import com.ldb.reportcustom.messages.request.RequestReportDate;
import com.ldb.reportcustom.messages.response.BorderRespone;
import com.ldb.reportcustom.messages.response.DataResponse;
import com.ldb.reportcustom.messages.response.reportSW.RespAccountBorder;
import com.ldb.reportcustom.messages.response.reportSW.RespMainObj;
import com.ldb.reportcustom.messages.response.reportSW.RespSingleWinDaily;
import com.ldb.reportcustom.messages.response.reportSW.RespSumTaxCode;
import com.ldb.reportcustom.services.CreateUserService;
import com.ldb.reportcustom.services.GetSingleWindowServiceAdmin;
import com.ldb.reportcustom.utils.LnswFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GetSingleWindowServiceAdminImpl implements GetSingleWindowServiceAdmin {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private LnswFunction lnswFunction;

    @Override
    public List<RespAccountBorder> findBorderAccountAdmin() {



        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ID, TAX_RECEIPT_NAME, BORDER_ID, TAX_CODE  FROM TAX_ACCOUNT_BORDER WHERE RECEIPT_TITLE = 'Y' ");
        String sql = sb.toString();
      // log.info("SQL : " + sql);
        return this.jdbcTemplate.query(sql, new RowMapper<RespAccountBorder>() {
            @Override
            public RespAccountBorder mapRow(ResultSet rs, int i) throws SQLException {
                RespAccountBorder rpt = new RespAccountBorder();
                rpt.setId(rs.getInt("ID"));
                rpt.setTaxName(rs.getString("TAX_RECEIPT_NAME"));
                rpt.setBorderCode(rs.getString("BORDER_ID"));
                rpt.setTaxCode(rs.getString("TAX_CODE"));

                return rpt;
            }
        });
    }
////NOW
    @Override
    public List<RespSingleWinDaily> listReportPaymentAdmin(BorderRequestReportByBoderID dataRequest) {
        StringBuilder sb = new StringBuilder();
        BorderRespone borderRespone = new BorderRespone();
       // log.info("getBorDerID"+dataRequest.getBorDerID());

        List<RespSingleWinDaily> resoop= new ArrayList<>();
        try {

            String condit = "";
            if (!dataRequest.getStartDate().equals("") && !dataRequest.getEndDate().equals("")) {
                condit += " AND TO_CHAR(A.UPDATED_AT, 'YYYYMMDD') BETWEEN " + dataRequest.getStartDate() + " AND " + dataRequest.getEndDate();
            }

            condit+=" AND A.ISSUING_CUSTOMER_OFFICE ='"+dataRequest.getBorDerID()+"' ";

            sb.append("\nSELECT TO_CHAR(A.UPDATED_AT, 'DD/MM/YYYY HH24:MI:SS') AS PAY_DATE, A.UPDATED_AT AS PAYMENT_DATE, SAD_TYPE, SAD_REG_NO, SAD_INSTANCE_ID, PAYMENT_REF,  TOTAL_AMOUNT, INVOICE_STATUS, PAYMENT_CHANNEL, ");
            sb.append("\nTB.RECEIPT_NAME AS BORDER_NAME, TIN_NAME, REFERENCE, INVOICE_ID, ISSUING_DATE, C.TAX_RECEIPT_NAME, B.AMOUNT, B.TAX_CODE, C.BORDER_ID ");
            sb.append("\nFROM TAX_INVOICE A ");
            sb.append("\nLEFT OUTER JOIN TAX_INVOICE_DETAIL B ON a.REFERENCE = b.REFERENCE_INV_ID ");
            sb.append("\nLEFT OUTER JOIN TAX_ACCOUNT_BORDER C on B.TAX_CODE = C.TAX_CODE AND C.RECEIPT_TITLE = 'Y' ");
            sb.append("\nLEFT OUTER JOIN TAX_BORDER TB on A.ISSUING_CUSTOMER_OFFICE = TB.BORDER_ID ");
            sb.append("\nWHERE INVOICE_STATUS = 'ACCEPTED' ").append(condit).append(" ORDER BY REFERENCE, b.AMOUNT DESC ");

            String sql = sb.toString();
           // log.info("SQL : " + sql);
            resoop = jdbcTemplate.query(sql, new RowMapper<RespSingleWinDaily>() {
                @Override
                public RespSingleWinDaily mapRow(ResultSet rs, int i) throws SQLException {
                    RespSingleWinDaily rpt = new RespSingleWinDaily();
                    rpt.setPaymentDate(rs.getString("PAY_DATE"));
                    rpt.setPayDate(rs.getDate("PAYMENT_DATE"));
                    rpt.setTypeInvoice(rs.getString("SAD_TYPE"));
                    rpt.setInvoiceNumber(rs.getString("SAD_REG_NO"));
                    rpt.setInstance(rs.getString("SAD_INSTANCE_ID"));
                    rpt.setReceiptNumber(rs.getString("PAYMENT_REF"));
                    rpt.setAmount(rs.getDouble("TOTAL_AMOUNT"));
                    rpt.setBroderName(rs.getString("BORDER_NAME"));
                    rpt.setPaymentChanel(rs.getString("PAYMENT_CHANNEL"));
                    rpt.setCompanyName(rs.getString("TIN_NAME"));
                    rpt.setRefId(rs.getString("REFERENCE"));
                    rpt.setSoi(rs.getString("INVOICE_ID"));
                    rpt.setIssueDate(rs.getString("ISSUING_DATE"));
                    rpt.setTaxReceiptName(rs.getString("TAX_RECEIPT_NAME"));
                    rpt.setDetailAmount(rs.getDouble("AMOUNT"));
                    rpt.setTaxCode(rs.getString("TAX_CODE"));
                    rpt.setBorderCode(rs.getString("BORDER_ID"));
                    log.info(rpt.toString());
                    return rpt;
                }
            });
          //  log.info("hut RRRQ",resoop.toString());
            return resoop;
        } catch (Exception e) {
            log.error("Cannot Get data = {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
/////compare Report

    @Override
    public List<RespSingleWinDaily> listReportPaymentCompareAdmin(BorderRequestReportByBoderID dataRequest) {
        StringBuilder sb = new StringBuilder();

        try {
            String condit = "";
            if (!dataRequest.getStartDate().equals("") && !dataRequest.getEndDate().equals("")) {
                condit += " AND TO_CHAR(A.UPDATED_AT, 'YYYYMMDD') BETWEEN " + dataRequest.getStartDate() + " AND " + dataRequest.getEndDate();
            }
           // log.info("getBorDerID:"+dataRequest.getBorDerID());
            //check admin
            if(dataRequest.getBorDerID() == null){
                condit += lnswFunction.borderIdCondit("A.ISSUING_CUSTOMER_OFFICE ");

            }else{
                condit+=" AND A.ISSUING_CUSTOMER_OFFICE ='"+dataRequest.getBorDerID()+"'";
            }
            System.out.println("show border:"+dataRequest.getBorDerID());

            sb.append("\nSELECT TO_CHAR(A.UPDATED_AT, 'DD/MM/YYYY HH24:MI:SS') AS PAY_DATE, A.UPDATED_AT AS PAYMENT_DATE, SAD_TYPE, SAD_REG_NO, SAD_INSTANCE_ID, PAYMENT_REF,  TOTAL_AMOUNT, INVOICE_STATUS, PAYMENT_CHANNEL, ");
            sb.append("\nTB.RECEIPT_NAME AS BORDER_NAME, TIN_NAME, REFERENCE, INVOICE_ID, ISSUING_DATE, C.TAX_RECEIPT_NAME, B.AMOUNT, B.TAX_CODE, B.MORE_INFO, A.ISSUING_CUSTOMER_OFFICE AS BORDER_ID ");
            sb.append("\nFROM TAX_INVOICE A ");
            sb.append("\nLEFT OUTER JOIN TAX_INVOICE_DETAIL B ON a.REFERENCE = b.REFERENCE_INV_ID ");
            sb.append("\nLEFT OUTER JOIN TAX_ACCOUNT_BORDER C on B.TAX_CODE = C.TAX_CODE AND C.RECEIPT_TITLE = 'Y' ");
            sb.append("\nLEFT OUTER JOIN TAX_BORDER TB on A.ISSUING_CUSTOMER_OFFICE = TB.BORDER_ID ");
            sb.append("\nWHERE INVOICE_STATUS = 'ACCEPTED' AND 1=1 ").append(condit).append(" ORDER BY REFERENCE, b.AMOUNT DESC ");

            String sql = sb.toString();
          //  log.info("SQL : " + sql);
            return jdbcTemplate.query(sql, new RowMapper<RespSingleWinDaily>() {
                @Override
                public RespSingleWinDaily mapRow(ResultSet rs, int i) throws SQLException {
                    RespSingleWinDaily rpt = new RespSingleWinDaily();
                    rpt.setPaymentDate(rs.getString("PAY_DATE"));
                    rpt.setPayDate(rs.getDate("PAYMENT_DATE"));
                    rpt.setTypeInvoice(rs.getString("SAD_TYPE"));
                    rpt.setInvoiceNumber(rs.getString("SAD_REG_NO"));
                    rpt.setInstance(rs.getString("SAD_INSTANCE_ID"));
                    rpt.setReceiptNumber(rs.getString("PAYMENT_REF"));
                    rpt.setAmount(rs.getDouble("TOTAL_AMOUNT"));
                    rpt.setBroderName(rs.getString("BORDER_NAME"));
                    rpt.setPaymentChanel(rs.getString("PAYMENT_CHANNEL"));
                    rpt.setCompanyName(rs.getString("TIN_NAME"));
                    rpt.setRefId(rs.getString("REFERENCE"));
                    rpt.setSoi(rs.getString("INVOICE_ID"));
                    rpt.setIssueDate(rs.getString("ISSUING_DATE"));
                    rpt.setTaxReceiptName(rs.getString("TAX_RECEIPT_NAME"));
                    rpt.setDetailAmount(rs.getDouble("AMOUNT"));
                    rpt.setTaxCode(rs.getString("TAX_CODE"));
                    rpt.setMoreInfo(rs.getString("MORE_INFO"));
                    rpt.setBorderCode(rs.getString("BORDER_ID"));

                    return rpt;
                }
            });
        } catch (Exception e) {
            log.error("Cannot Get data = {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    //END
    @Override
    public List<RespSumTaxCode> listReportSumTaxCodeLNSWAdmin(BorderRequestReportByBoderID dataRequest) {
        String condit = "";
        if (!dataRequest.getStartDate().equals("") && !dataRequest.getEndDate().equals("")) {
            condit += " AND TO_CHAR(A.UPDATED_AT, 'YYYYMMDD') = " + dataRequest.getStartDate() + " AND " + dataRequest.getEndDate();
        }

        condit+=" AND A.ISSUING_CUSTOMER_OFFICE ='"+dataRequest.getBorDerID()+"' ";

        condit += lnswFunction.borderIdCondit("A.ISSUING_CUSTOMER_OFFICE");
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT TO_CHAR(a.UPDATED_AT, 'DD/MM/YYYY') AS PAY_DATE, B.TAX_CODE, a.ISSUING_CUSTOMER_OFFICE as BORDER_ID, B.TAX_ACCOUNT_BORDER,REFERENCE,MORE_INFO , SUM(B.AMOUNT) as TOTAL, COUNT(*) AS NUM_TXN ");
        sb.append("FROM TAX_INVOICE A LEFT OUTER JOIN TAX_INVOICE_DETAIL B ON a.REFERENCE = b.REFERENCE_INV_ID ");
        sb.append("WHERE INVOICE_STATUS = 'ACCEPTED' AND 1=1 ").append(condit);
        sb.append(" GROUP BY REFERENCE,TO_CHAR(a.UPDATED_AT, 'DD/MM/YYYY'), B.TAX_CODE, a.ISSUING_CUSTOMER_OFFICE, B.TAX_ACCOUNT_BORDER,MORE_INFO ORDER BY TO_CHAR(a.UPDATED_AT, 'DD/MM/YYYY')");
        String sql = sb.toString();
       // log.info("SQL : " + sql);
        return this.jdbcTemplate.query(sql, new RowMapper<RespSumTaxCode>() {
            @Override
            public RespSumTaxCode mapRow(ResultSet rs, int i) throws SQLException {
                RespSumTaxCode rpt = new RespSumTaxCode();
                rpt.setPaymentDate(rs.getString("PAY_DATE"));
                rpt.setBorderCode(rs.getString("BORDER_ID"));
                rpt.setTaxCode(rs.getString("TAX_CODE"));
                rpt.setTaxId(rs.getInt("TAX_ACCOUNT_BORDER"));
                rpt.setTotalAmount(rs.getDouble("TOTAL"));
                rpt.setNumTrans(rs.getInt("NUM_TXN"));
                rpt.setReference(rs.getString("reference"));
                rpt.setMoreInfo(rs.getString("MORE_INFO"));
                rpt.setReference(rs.getString("reference"));
                return rpt;
            }
        });
    }
    //////Select All by Report Compare


}
