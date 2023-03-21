package com.ldb.reportcustom.messages.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ldb.reportcustom.entities.Border;
import com.ldb.reportcustom.entities.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * Create at 2019-01-21
 * @author KHAMPHAI
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileResponse {

//    private String accessToken;
//    private String tokenType;
//    private Date expireDate;
    private String username;
    private boolean enabled;
    private Boolean accountNonExpired;
    private Boolean accountNonLocked ;
    private Boolean credentialsNonExpired;
    private Border borders;
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
