package com.ldb.weblocalapi.entities;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "DOCUMENT")
public class Document {
            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            @Column(name = "ID")
            private Long id;

            @Column(name = "DOC_NO",length = 150 , nullable = false)
            private String docNo;

            @Column(name = "DOC_TYPE",length = 150 , nullable = false)
            private String docType;

            @Column(name = "DOC_STATUS",length = 50 , nullable = false)
            private String docStatus;

            @Column(name = "DOC_PATH")
            private String docPath;

            @Column(name = "SAVE_BY",length = 150 , nullable = false)
            private String saveBy;

            @Column(name = "SAVE_DATE", nullable = false)
            private Date saveDate;

            @Column(name = "DOC_DATE" , nullable = false)
            private String docDate;

            @Column(name = "UPDATE_BY",length = 150 , nullable = false)
            private String updateBy;

            @Column(name = "UPDATE_DATE" , nullable = false)
            private Date updateDate;

            @Column(name = "NOTE",length = 900 , nullable = false)
            private String note;

            @Column(name = "DOCNAME",length = 300 , nullable = false)
            private String docName;

            @Column(name = "POPUP",length = 150 , nullable = false)
            private String popup;

            @Column(name = "TYPE",length = 150 , nullable = false)
            private String type;

            @Column(name = "RELATION_UNIT",length = 150 , nullable = false)
            private String relationUnit;

            @Column(name = "RELATION_UNIT_SEC",length = 150 , nullable = false)
            private String relationUnitSec;

            @Column(name = "PUPUP_START",length = 150 , nullable = false)
            private String popupStart;

            @Column(name = "PUPUP_END",length = 150 , nullable = false)
            private String popupEnd;
}
