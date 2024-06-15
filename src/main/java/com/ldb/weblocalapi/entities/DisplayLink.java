package com.ldb.weblocalapi.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Entity
@Data
@Table(name = "DISPLAYLINK")
public class DisplayLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private String keyId;

    @Column(name = "NAME",length = 200 , nullable = false)
    private String name;

    @Column(name = "NOTE",length = 200 , nullable = false)
    private String note;

    @Column(name = "LINK",length = 200 , nullable = false)
    private String link;

    @Column(name = "SAVEBY",length = 200 , nullable = false)
    private String saveBy;

    @Column(name = "IMAGE_PATH")
    private String imagePath;


}
