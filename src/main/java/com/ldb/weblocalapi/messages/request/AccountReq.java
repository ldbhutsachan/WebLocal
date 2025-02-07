package com.ldb.weblocalapi.messages.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountReq {
    private String debitAccNo;
    private String startDate;
    private String endDate;
}
