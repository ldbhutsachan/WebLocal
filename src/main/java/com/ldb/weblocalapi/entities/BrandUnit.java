package com.ldb.weblocalapi.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "BRANCH_UNIT")
public class BrandUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KEY_ID")
    private String keyId;

    @Column(name = "BRANCH_UNIT_ID",nullable = false)
    private String branchUnitId;

    @Column(name = "BRANCH_UNIT_NAME",nullable = false)
    private String branchUnitName;

//    @Column(name = "BRANCH_CODE",nullable = false)
//    private String branchCode;
}
