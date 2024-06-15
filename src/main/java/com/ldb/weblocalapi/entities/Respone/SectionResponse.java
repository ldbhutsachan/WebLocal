package com.ldb.weblocalapi.entities.Respone;

import com.ldb.weblocalapi.entities.Roles;
import com.ldb.weblocalapi.entities.audit.DateAudit;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "SECTION")
public class SectionRespone extends DateAudit {

    @Id
    @Column(name = "SEC_ID", length = 10, nullable = false)
    private String secId;

    @Column(name = "NAME", nullable = false)
    private String secName;

    @Column(name = "code", length = 5)
    private String code;

    @Column(name = "enable")
    private Boolean enable;

    @Column(name = "PROVINCE_CODE", length = 3)
    private String provinceCode;

    @Column(name = "PROVINCE_NAME", nullable = false)
    private String provinceName;



}
