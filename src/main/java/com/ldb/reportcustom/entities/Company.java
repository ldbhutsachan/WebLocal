package com.ldb.reportcustom.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Data
public class Company  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Valid
    @Column(name = "tIN_NAME", length = 36, nullable = false)
    private String tIN_NAME;

   // @Column(name = "ISSUING_CUSTOMER_OFFICE", length = 128, nullable = false)
    //private String issuing_customer_office;
}
