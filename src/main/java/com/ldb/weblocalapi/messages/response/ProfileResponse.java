package com.ldb.weblocalapi.messages.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ldb.weblocalapi.entities.Province;
import com.ldb.weblocalapi.entities.Section;
import com.ldb.weblocalapi.entities.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

    private String username;
    private String imagePath;
    private boolean enabled;
    private Boolean accountNonExpired;
    private Boolean accountNonLocked ;
    private Boolean credentialsNonExpired;
    private Section sections;
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
