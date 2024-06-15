package com.ldb.weblocalapi.messages.response.reportSW;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * Create at 12/12/2022 - 5:58 PM
 * Project Name reportCustom
 *
 * @author kataii
 */
@Data
@JsonPropertyOrder({
        "debitAccNo",
        "debitAccName",
        "paymentDate",
        "payDate",
        "typeInvoice",
        "invoiceNumber",
        "instance",
        "receiptNumber",
        "amount",
        "borderName",
        "paymentChanel",
        "companyName",
        "refId",
        "soi",
        "issueDate",
        "taxReceiptName",
        "detailAmount",
        "taxCode",
        "borderCode"
})
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespSingleWinDaily {
    @JsonProperty("debitAccNo")
    private String debitAccNo;

    @JsonProperty("debitAccName")
    private String debitAccName;

    @JsonProperty("paymentDate")
    private String paymentDate;

    @JsonProperty("payDate")
    private Date payDate;
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

    @JsonProperty("broderName")
    private String broderName;

    @JsonProperty("paymentChanel")
    private String paymentChanel;

    @JsonProperty("companyName")
    private String companyName;

    @JsonProperty("refId")
    private String refId;

    @JsonProperty("soi")
    private String soi;

    @JsonProperty("issueDate")
    private String issueDate;

    @JsonProperty("taxReceiptName")
    private String taxReceiptName;

    @JsonProperty("detailAmount")
    private Double detailAmount;

    @JsonProperty("taxCode")
    private String taxCode;

    @JsonProperty("moreInfo")
    private String moreInfo;

    @JsonProperty("borderCode")
    private String borderCode;

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
