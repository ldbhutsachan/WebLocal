package com.ldb.weblocalapi.entities;

import com.ldb.weblocalapi.entities.audit.UserAudit;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "MAP_USER_MENU")
public class MapUserMenu{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID",nullable = false)
    private Long iD;

    @Column(name = "MENU_ID",nullable = false)
    private Long menuId;

    @Column(name = "USER_ID",nullable = false)
    private Long userId;


}
