package com.ldb.weblocalapi.entities;

import com.ldb.weblocalapi.entities.audit.UserAudit;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;


/**
 *
 * Update at 2019-01-21
 * @author Noh
 * @Edit_By KHAMPHAI
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "TAX_ROLES",
        uniqueConstraints = {
                @UniqueConstraint(name = "SYS_ROLE_UK", columnNames = "Role_Name")
        }
)
public class Roles extends UserAudit<Long> {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE_SEQ")
//    @SequenceGenerator(name = "ROLE_SEQ", sequenceName = "ROLE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID", nullable = false)
    private Long roleId;

//    @Enumerated(EnumType.STRING)
//    @NaturalId
    @Column(name = "Role_Name", length = 30, nullable = false)
    private String roleName;

    @Column(name = "ROLE_DISPLAY", length = 100, nullable = true)
    private String roleDisplay;

}