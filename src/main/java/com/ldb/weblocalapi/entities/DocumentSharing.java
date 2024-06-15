package com.ldb.weblocalapi.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "DOC_SHARING")
public class DocumentSharing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KEY_ID")
    private String keyId;

    @Column(name = "CREATE_DATE",length = 150,nullable = false)
    private Date createDate;

    @Column(name = "DOC_NO",length = 150,nullable = false)
    private String docNo;

    @Column(name = "RELATION_UNIT",length = 150,nullable = false)
    private String relationUnit;

}
