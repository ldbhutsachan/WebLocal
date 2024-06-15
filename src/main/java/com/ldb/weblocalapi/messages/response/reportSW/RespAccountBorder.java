package com.ldb.weblocalapi.messages.response.reportSW;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Create at 03/01/2023 - 2:56 PM
 * Project Name reportCustom
 *
 * @author kataii
 */

@Data
@JsonPropertyOrder({
        "id",
        "taxName",
        "borderCode",
        "taxCode"
})
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespAccountBorder {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("taxName")
    private String taxName;

    @JsonProperty("borderCode")
    private String borderCode;

    @JsonProperty("taxCode")
    private String taxCode;



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
