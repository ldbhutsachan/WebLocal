package com.ldb.reportcustom.services;
import com.ldb.reportcustom.entities.Account;
import com.ldb.reportcustom.messages.request.AccountReq;
import com.ldb.reportcustom.messages.request.RequestDatebyCompany;
import com.ldb.reportcustom.messages.response.DataResponse;
import com.ldb.reportcustom.messages.response.reportSW.RespSingleWinDaily;

import java.util.List;

public interface GetAccountService {
    public List<Account> ListAccount();
    public List<RespSingleWinDaily> ReportAccountByAccNoAll(AccountReq  dataRequest);
}
