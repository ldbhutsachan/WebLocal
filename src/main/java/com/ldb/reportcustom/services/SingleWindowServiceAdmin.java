package com.ldb.reportcustom.services;

import com.ldb.reportcustom.messages.request.BorderRequestReportByBoderID;
import com.ldb.reportcustom.messages.request.RequestReportDate;
import com.ldb.reportcustom.messages.response.DataResponse;

public interface SingleWindowServiceAdmin {
    DataResponse paymentReportAdmin(BorderRequestReportByBoderID dataRequest);
    DataResponse paymentCompareReportAdmin(BorderRequestReportByBoderID dataRequest);
    DataResponse paymentTotalReportAdmin(BorderRequestReportByBoderID dataRequest);



}
