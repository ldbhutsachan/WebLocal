package com.ldb.weblocalapi.messages.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountRespone {
    private String debitAccNo;
    private String debitAccName;
}
