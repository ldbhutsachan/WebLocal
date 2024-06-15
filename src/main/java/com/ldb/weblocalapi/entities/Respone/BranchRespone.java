package com.ldb.weblocalapi.entities.Respone;

import com.ldb.weblocalapi.entities.BrandUnit;
import com.ldb.weblocalapi.entities.ChildMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "BRANCH")
public class BranchRespone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID",nullable = false)
    private String iD;

    @Column(name = "NAME",nullable = false)
    private String name;

    @Column(name = "BRANCH_CODE",nullable = false)
    private String branchCode;

    @OneToMany
    @JoinColumn(name = "BRANCH_CODE")
    private List<BrandUnit> brandUnits;

}
