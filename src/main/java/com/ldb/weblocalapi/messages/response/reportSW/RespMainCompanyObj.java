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
 * Create at 12/12/2022 - 5:58 PM
 * Project Name reportCustom
 *
 * @author kataii
 */
@Data
@JsonPropertyOrder({
        "reference",
        "companyName",
        "paymentDate",
        "typeInvoice",
        "invoiceNumber",
        "instance",
        "receiptNumber",
        "amount",
        "paymentChanel",
        "printBill",
        "border",
        "borderName"



})
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespMainCompanyObj {
    @JsonProperty("reference")
    private String reference;
    @JsonProperty("paymentDate")
    private String paymentDate;

    @JsonProperty("typeInvoice")
    private String typeInvoice;

    @JsonProperty("invoiceNumber")
    private String invoiceNumber;

    @JsonProperty("instance")
    private String instance;

    @JsonProperty("receiptNumber")
    private String receiptNumber;

    @JsonProperty("amount")
    private Double amount;


    @JsonProperty("paymentChanel")
    private String paymentChanel;

    @JsonProperty("companyName")
    private String companyName;

    @JsonProperty("border")
    private String border;

    @JsonProperty("borderName")
    private String borderName;
//reference

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
