package com.ldb.weblocalapi.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "V_POPUP_DOC")
public class Popup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private String id;

    @Column(name = "DOC_NO",length = 150,nullable = false)
    private String docNo;

    @Column(name = "DOC_PATH",length = 1500,nullable = false)
    private String docPathPopup;
}
