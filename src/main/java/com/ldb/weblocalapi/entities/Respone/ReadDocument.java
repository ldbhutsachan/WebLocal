package com.ldb.weblocalapi.entities.Respone;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "V_DOC_VIEW")
public class ReadDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    @Column(name = "DOC_ID", nullable = false)
    private String docId;

    @Column(name = "DOC_NO")
    private String docNo;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "SEC_ID", nullable = false)
    private String secId;

    @Column(name = "SECNAME", nullable = false)
    private String secName;

    @Column(name = "PROVINCE_CODE", nullable = false)
    private String proCode;

    @Column(name = "PROVINCE_NAME", nullable = false)
    private String proName;

    @Column(name = "MOBILE", nullable = false)
    private String mobile;

    @Column(name = "MAIL", nullable = false)
    private String mail;

    @Column(name = "READ_DATE", nullable = false)
    private String readDate;
}
