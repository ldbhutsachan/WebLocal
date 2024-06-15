package com.ldb.weblocalapi.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Fee {
    private String keyId;
    private String docName;
    private String docNo;
    private String docTypeNo;
    private String docTypeName;
    private String docDate;
    private String saveDate;
    private String saveBy;
    private String name;
    private String docPath;
    private String docStatus;
    private String typeDocIn_Out;
    private String amtRead;
}
