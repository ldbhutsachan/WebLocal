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
 * Create at 04/01/2023 - 11:11 AM
 * Project Name reportCustom
 *
 * @author kataii
 */
@Data
@JsonPropertyOrder({
        "paymentDate",
        "taxCode",
        "borderCode",
        "taxId",
        "totalAmount",
        "numTrans",
        "reference",
        "detailAmount",
        "moreInfo"
})
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespSumTaxCode {
    @JsonProperty("paymentDate")
    private String paymentDate;
    @JsonProperty("taxCode")
    private String taxCode;
    @JsonProperty("borderCode")
    private String borderCode;
    @JsonProperty("taxId")
    private Integer taxId;
    @JsonProperty("totalAmount")
    private Double totalAmount;
    @JsonProperty("numTrans")
    private Integer numTrans;

    @JsonProperty("reference")
    private String reference;

    @JsonProperty("detailAmount")
    private Double detailAmount;
    @JsonProperty("moreInfo")
    private String moreInfo;

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
