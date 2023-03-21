package com.ldb.reportcustom.entities.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;


/**
 * Create at 2019-01-22
 * @author KHAMPHAI
 */
@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"created_by", "created_at", "modified_by", "modified_at"},
        allowGetters = true
)
public abstract class UserAudit<T> implements Serializable {

    @CreatedBy
    @Column( updatable = false)
    private T createdBy;

    @CreatedDate
    @Column( updatable = false)
    private Instant createdAt;

    @LastModifiedBy
    @Column()
    private T modifiedBy;

    @LastModifiedDate
    @Column()
    private Instant modifiedAt;

}
