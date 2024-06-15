package com.ldb.weblocalapi.entities;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.Instant;

@Entity
@Data
public class CreateUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Valid
    @Column(name = "USER_NAME", length = 36, nullable = false)
    private String username;

    @Column(name = "PASSWORD", length = 128, nullable = false)
    private String password;

    @Column(name = "ENABLED", length = 1, nullable = false)
    private Boolean enabled;

    ///get bordr ID
    @Column(name = "BORDER_ID",length =20 ,nullable = false)
    private String borderId;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(name = "ACCOUNT_NON_EXPIRED",length =20 ,nullable = false)
    private String accountNonExpires;

    @Column(name = "CREDENTIALS_NON_EXPIRED",length =20 ,nullable = false)
    private String creditNonExpired;
    @Column(name = "ACCOUNT_NON_LOCKED",length =20 ,nullable = false)
    private String accNonLock;


}
