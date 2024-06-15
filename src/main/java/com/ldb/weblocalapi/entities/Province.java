package com.ldb.weblocalapi.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "PROVINCE")
public class Province {
    @Id
    @Column(name = "KEY_ID", length = 10, nullable = false)
    private String keyId;

    @Column(name = "PROID", length = 100)
    private String proId;

    @Column(name = "PRONAME", length = 200)
    private String proName;
}
