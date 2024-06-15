package com.ldb.weblocalapi.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BranchReq {
    private String startDate;
    private String endDate;
    private String brandCode;
    private String smallBrandCode;
    private String type;
    private String docType;
    private String inBoxText;
}