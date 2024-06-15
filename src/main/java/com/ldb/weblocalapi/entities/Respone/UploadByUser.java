package com.ldb.weblocalapi.entities.Respone;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "V_INFO_USERSAVE")
public class UploadByUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long iD;
            @Column(name = "SAVE_BY",length = 100,nullable = false)
            private String saveBy;

            @Column(name = "NAME",length = 100,nullable = false)
            private String name;

            @Column(name = "SECNAME",length = 100,nullable = false)
            private String secName;

            @Column(name = "PROVINCE_NAME",length = 100,nullable = false)
            private String proName;

            @Column(name = "MAIL",length = 100,nullable = false)
            private String Mail;

            @Column(name = "MOBILE",length = 100,nullable = false)
            private String mobile;

            @Column(name = "IMAGEPATH",length = 1500,nullable = false)
            private String imagePath;

            @Column(name = "SAVE_DATE",length = 100,nullable = false)
            private String saveDate;

            @Column(name = "DOC_NO",length = 100,nullable = false)
            private String docNo;




}
