package com.ldb.reportcustom.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Valid
    @Column(name = "dEBIT_ACCT_NO", length = 50, nullable = false)
    private String dEBIT_ACCT_NO;
    @Column(name = "dEBIT_ACCT_NAME", length = 50, nullable = false)
    private String dEBIT_ACCT_NAME;

}
