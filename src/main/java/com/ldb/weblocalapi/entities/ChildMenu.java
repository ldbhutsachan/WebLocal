package com.ldb.weblocalapi.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "CHILD_MENU")
public class ChildMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHILD_MENU_ID")
    private String childMenuId;

    @Column(name = "CHILD_MENU_LO", nullable = false)
    private String childMenuLo;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "MENU_ID", nullable = false)
    private String menuId;

    @Column(name = "TYPE_LEVEL", nullable = false)
    private String levelType;

    @Column(name = "CHILD_MENU_PATH", nullable = false)
    private String to;



}
