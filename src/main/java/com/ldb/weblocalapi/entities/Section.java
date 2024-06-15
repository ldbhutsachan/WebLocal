package com.ldb.weblocalapi.entities;

import com.ldb.weblocalapi.entities.audit.DateAudit;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "SECTION")
public class Border extends DateAudit {

    @Id
    @Column(name = "SEC_ID", length = 10, nullable = false)
    private String secId;

    @Column(name = "BRIDGE", length = 3)
    private String bridge;

    @Column(name = "code", length = 5)
    private String code;

    @Column(name = "enable")
    private Boolean enable;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PROVINCE_CODE", length = 3)
    private String provinceCode;

    @Column(name = "PROVINCE_NAME", nullable = false)
    private String provinceName;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "WEB_LOCAL_ROLE", joinColumns = @JoinColumn(name = "BORDER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<Roles> roles = new HashSet<>();





}
