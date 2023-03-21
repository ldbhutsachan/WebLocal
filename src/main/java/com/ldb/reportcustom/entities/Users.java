package com.ldb.reportcustom.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ldb.reportcustom.entities.audit.DateAudit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 *
 * @author Noh
 * @edit_by KHAMPHAI
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "TAX_USER_LOGIN",
        uniqueConstraints = {
                @UniqueConstraint(name = "USER_UK", columnNames = "USER_NAME")
        }
)
public class Users extends DateAudit {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ")
//    @SequenceGenerator(name = "USERS_SEQ", sequenceName = "USERS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "USER_NAME", length = 36, nullable = false)
    private String username;

    @Column(name = "PASSWORD", length = 128, nullable = false)
    private String password;

    @Column(name = "ENABLED", length = 1, nullable = false)
    private Boolean enabled;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "BORDER_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_BORDER" ))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Border border;

    @Column(name = "ACCOUNT_NON_EXPIRED", length = 1, nullable = false, columnDefinition = "NUMBER(1) default 1")
    private Boolean accountNonExpired;

    @Column(name = "ACCOUNT_NON_LOCKED", length = 1, nullable = false, columnDefinition = "NUMBER(1) default 1")
    private Boolean accountNonLocked ;

    @Column(name = "CREDENTIALS_NON_EXPIRED", length = 1, nullable = false, columnDefinition = "NUMBER(1) default 1")
    private Boolean credentialsNonExpired;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "TAX_USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<Roles> roles = new HashSet<>();


    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();

        String jsonString = "";
        try {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            jsonString = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}