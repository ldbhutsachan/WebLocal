package com.ldb.reportcustom.messages.response.reportSW;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Create at 21/12/2022 - 6:25 PM
 * Project Name reportCustom
 *
 * @author kataii
 */

@Data
@JsonPropertyOrder({
        "totalAmount",
        "nameAmount",
        "paymentDate",
        "qrCode"
})
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespPrintBillFooter {

    @JsonProperty("totalAmount")
    private String totalAmount;

    @JsonProperty("nameAmount")
    private String nameAmount;

    @JsonProperty("paymentDate")
    private String paymentDate;

    @JsonProperty("qrCode")
    private String qrCode;

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
