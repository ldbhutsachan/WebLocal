package com.ldb.weblocalapi.Model;

import com.ldb.weblocalapi.entities.Respone.ReadDocument;
import lombok.*;

import javax.persistence.*;
import java.util.List;
@Entity
@Data
@Table(name = "V_BAND_MENU_COUNTER")
public class DocumentSecMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private String keyId;

    @Column(name = "DOCNAME",length = 100,nullable = false)
    private String docName;

    @Column(name = "DOC_NO", length = 100,nullable = false)
    private String docNo;

    @Column(name = "DOCTYPENO",length = 100,nullable = false)
    private String docTypeNo;

    @Column(name = "DOC_TYPE_NAME",length = 100,nullable = false)
    private String docTypeName;

    @Column(name = "DOC_DATE",length = 100,nullable = false)
    private String docDate;

    @Column(name = "SAVE_DATE",length = 100,nullable = false)
    private String saveDate;

    @Column(name = "SAVE_BY",nullable = false)
    private String saveBy;

    @Column(name = "NAME",length = 100,nullable = false)
    private String name;

    @Column(name = "DOC_PATH",length = 1500,nullable = false)
    private String docPath;

    @Column(name = "DOC_STATUS",length = 1500,nullable = false)
    private String docStatus;

    @Column(name = "TYPE",length = 1500,nullable = false)
    private String typeDocIn_Out;

    @Column(name = "TOTAL",length = 1500,nullable = false)
    private int amtRead;

    @Column(name = "RELATION_UNIT",length = 1500,nullable = false)
    private String relationUnit;

    //ReadDocument
    @OneToMany
    @JoinColumn(name = "DOC_ID")
    private List<ReadDocument> readByUserInfo;

}
