package com.ldb.weblocalapi.messages.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Create at 03/08/2022 - 10:12 AM
 * Project Name border-report-api
 *
 * @author yor
 */
@Data
@ApiModel(
        value = "RequestReportDate",
        description = "Request data"
)
@JsonPropertyOrder({
        "startDate",
        "endDate"
})
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestReportDate {
    @JsonProperty("startDate")
    public String startDate;

    @JsonProperty("endDate")
    public String endDate;

    @JsonProperty("inFoBox")
    public String inFoBox;

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
