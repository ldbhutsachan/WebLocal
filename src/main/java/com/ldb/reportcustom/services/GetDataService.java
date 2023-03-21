package com.ldb.reportcustom.services;

import com.ldb.reportcustom.messages.request.RequestReportByStartDate;
import com.ldb.reportcustom.messages.request.RequestReportDate;
import com.ldb.reportcustom.messages.request.RequestReportStartDate;
import com.ldb.reportcustom.messages.response.reportSW.RespAccountBorder;
import com.ldb.reportcustom.messages.response.reportSW.RespSingleWinDaily;
import com.ldb.reportcustom.messages.response.reportSW.RespSumTaxCode;

import java.util.List;

/**
 * Create at 03/01/2023 - 2:55 PM
 * Project Name reportCustom
 *
 * @author kataii
 */
public interface GetDataService {
    List<RespAccountBorder> findBorderAccount();
    List<RespSingleWinDaily> listReportPayment(RequestReportByStartDate dataRequest);
    List<RespSingleWinDaily> listReportPaymentCompare(RequestReportByStartDate dataRequest);

    List<RespSumTaxCode> listReportSumTaxCodeLNSW(RequestReportByStartDate dataRequest);
    //List<RespSumTaxCode> listReportSumTaxCodeLNSW(RequestReportByStartDate dataRequest);
}
