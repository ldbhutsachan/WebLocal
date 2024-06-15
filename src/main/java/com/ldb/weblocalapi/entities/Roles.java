package com.ldb.weblocalapi.entities;

import com.ldb.weblocalapi.entities.audit.UserAudit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "ROLE"
)
public class Roles extends UserAudit<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID", nullable = false)
    private Long roleId;

    @Column(name = "ROLE_NAME", length = 30, nullable = false)
    private String roleName;

    @Column(name = "ROLE_DISPLAY", length = 100, nullable = true)
    private String roleDisplay;

}