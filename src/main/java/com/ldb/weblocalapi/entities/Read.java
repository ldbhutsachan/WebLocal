package com.ldb.weblocalapi.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "READ_DOC")
public class Read {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "READ_ID")
    private long keyId;

    @Column(name = "READ_DATE",length = 100,nullable = false)
    private Date readDate;

    @Column(name = "USER_ID",length = 100,nullable = false)
    private String userId;

    @Column(name = "DOC_KEY_ID",length = 100,nullable = false)
    private String docKeyId;
}
