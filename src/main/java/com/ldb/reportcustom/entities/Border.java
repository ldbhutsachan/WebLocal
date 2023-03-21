package com.ldb.reportcustom.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ldb.reportcustom.entities.audit.DateAudit;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "TAX_BORDER")
public class Border extends DateAudit {

    @Id
    @Column(name = "BORDER_ID", length = 10, nullable = false)
    private String borderId;

    @Column(name = "BRIDGE", length = 3)
    private String bridge;

    /**
     * use for bill header
     */
    @Column(name = "code", length = 5)
    private String code;

    /**
     * true (1) or false (0)
     */
    @Column(name = "enable")
    private Boolean enable;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PROVINCE_CODE", length = 3)
    private String provinceCode;

    @Column(name = "PROVINCE_NAME", nullable = false)
    private String provinceName;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "TAX_BORDER_ROLE", joinColumns = @JoinColumn(name = "BORDER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
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
