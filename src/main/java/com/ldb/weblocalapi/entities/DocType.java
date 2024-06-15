package com.ldb.weblocalapi.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "DOCUMENT_TYPE")
public class DocType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KEY_ID")
    private String keyId;

    @Column(name = "DOC_TYPE_NAME",length = 200 , nullable = false)
    private String docTypeName;

    @Column(name = "SAVEUSER")
    private String saveBy;

    @Column(name = "SAVEDATE")
    private Date saveDate;

    @Column(name = "STATUS",length = 200 , nullable = false)
    private String status;



}
