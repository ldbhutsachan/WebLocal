package com.ldb.reportcustom.services.Impl;

import com.ldb.reportcustom.entities.Account;
import com.ldb.reportcustom.entities.Company;
import com.ldb.reportcustom.messages.request.AccountReq;
import com.ldb.reportcustom.messages.request.RequestDatebyCompany;
import com.ldb.reportcustom.messages.response.DataResponse;
import com.ldb.reportcustom.messages.response.reportSW.RespMainCompanyObj;
import com.ldb.reportcustom.messages.response.reportSW.RespSingleWinDaily;
import com.ldb.reportcustom.services.GetAccountService;
import com.ldb.reportcustom.utils.LnswFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GetListAccount implements GetAccountService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private LnswFunction lnswFunction;
    @Override
    public List<Account> ListAccount() {

        StringBuilder sb = new StringBuilder();
        sb.append("select distinct DEBIT_ACCT_NO,DEBIT_ACCT_NAME from TAX_INVOICE ");

        String sql = sb.toString();
        System.out.println("SQL:"+ sql);
        return this.jdbcTemplate.query(sql, new RowMapper<Account>() {
            @Override
            public Account mapRow(ResultSet rs, int i) throws SQLException {
                Account tr = new Account();
                tr.setDEBIT_ACCT_NO(rs.getString("DEBIT_ACCT_NO"));
                tr.setDEBIT_ACCT_NAME(rs.getString("DEBIT_ACCT_NAME"));
                return tr;
            }
        });
    }
    //*****************************

    @Override
    public List<RespSingleWinDaily> ReportAccountByAccNoAll(AccountReq dataRequest) {
        StringBuilder sb = new StringBuilder();
        log.info("req issuer_name:"+dataRequest.getDebitAccNo());
        try {
            String condit = "";
            if(dataRequest.getDebitAccNo().equals("ALL")){
                condit += " AND TO_CHAR(A.UPDATED_AT, 'YYYYMMDD') between " + dataRequest.getStartDate() + " AND " + dataRequest.getEndDate();
            }else {
                log.info("req issuer_name Req 0002:");
                condit += "AND DEBIT_ACCT_NO='"+dataRequest.getDebitAccNo()+"' AND TO_CHAR(A.UPDATED_AT, 'YYYYMMDD') between " + dataRequest.getStartDate() + " AND " + dataRequest.getEndDate();
            }
            //condit += lnswFunction.borderIdCondit("A.ISSUING_CUSTOMER_OFFICE");
            log.info("++++"+lnswFunction.borderIdCondit("A.ISSUING_CUSTOMER_OFFICE"));
            sb.append("select DEBIT_ACCT_NO,DEBIT_ACCT_NAME,TO_CHAR(A.UPDATED_AT, 'DD/MM/YYYY HH24:MI:SS') AS PAY_DATE, A.UPDATED_AT AS PAYMENT_DATE, SAD_TYPE, SAD_REG_NO, SAD_INSTANCE_ID, PAYMENT_REF,  TOTAL_AMOUNT, INVOICE_STATUS, PAYMENT_CHANNEL, \n" +
                    "            TB.name AS BORDER_NAME, TIN_NAME, REFERENCE, INVOICE_ID, ISSUING_DATE, C.TAX_RECEIPT_NAME, B.AMOUNT, B.TAX_CODE, B.MORE_INFO, A.ISSUING_CUSTOMER_OFFICE AS BORDER_ID \n" +
                    "            from TAX_INVOICE A ");
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
                    rpt.setDebitAccNo(rs.getString("DEBIT_ACCT_NO"));
                    rpt.setDebitAccName(rs.getString("DEBIT_ACCT_NAME"));
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
}
