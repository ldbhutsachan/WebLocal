package com.ldb.weblocalapi.messages.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DocReq {
private String startDate;
private String endDate;
private String code;
private String type;
private String docType;
private String inBoxText;
}
