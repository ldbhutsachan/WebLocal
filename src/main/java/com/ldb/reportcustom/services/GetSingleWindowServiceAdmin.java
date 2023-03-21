package com.ldb.reportcustom.services;

import com.ldb.reportcustom.messages.request.BorderRequestReportByBoderID;
import com.ldb.reportcustom.messages.request.RequestReportDate;
import com.ldb.reportcustom.messages.response.reportSW.RespAccountBorder;
import com.ldb.reportcustom.messages.response.reportSW.RespSingleWinDaily;
import com.ldb.reportcustom.messages.response.reportSW.RespSumTaxCode;

import java.util.List;

public interface GetSingleWindowServiceAdmin {

    List<RespAccountBorder> findBorderAccountAdmin();
    List<RespSingleWinDaily> listReportPaymentAdmin(BorderRequestReportByBoderID dataRequest);
    List<RespSingleWinDaily> listReportPaymentCompareAdmin(BorderRequestReportByBoderID dataRequest);
    List<RespSumTaxCode> listReportSumTaxCodeLNSWAdmin(BorderRequestReportByBoderID dataRequest);

}
