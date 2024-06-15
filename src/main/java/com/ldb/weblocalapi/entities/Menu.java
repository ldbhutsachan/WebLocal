package com.ldb.weblocalapi.entities;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "MENU")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENU_ID",nullable = false)
    private Long menuId;

    @Column(name = "ICON_MENU",nullable = false)
    private String iconMenu;

    @Column(name = "MENU_LO",nullable = false)
    private String menuLo;

    @Column(name = "STATUS",nullable = false)
    private String status;

    @Column(name = "MENU_PATH",nullable = false)
    private String to;

    @OneToMany
    @JoinColumn(name = "MENU_ID", referencedColumnName = "MENU_ID")
    private List<ChildMenu> childMenu;


}
