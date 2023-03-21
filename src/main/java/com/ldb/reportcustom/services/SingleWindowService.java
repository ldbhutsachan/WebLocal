package com.ldb.reportcustom.services;

import com.ldb.reportcustom.messages.request.RequestReportByStartDate;
import com.ldb.reportcustom.messages.request.RequestReportDate;
import com.ldb.reportcustom.messages.request.RequestReportStartDate;
import com.ldb.reportcustom.messages.response.DataResponse;

/**
 * Create at 12/12/2022 - 5:55 PM
 * Project Name reportCustom
 *
 * @author kataii
 */
public interface SingleWindowService {

    DataResponse paymentReport(RequestReportByStartDate dataRequest);
    DataResponse paymentCompareReport(RequestReportByStartDate dataRequest);

    DataResponse paymentTotalReport(RequestReportByStartDate dataRequest);
   // ReportCompany
   DataResponse ReportCompany(RequestReportDate dataRequest);

}
