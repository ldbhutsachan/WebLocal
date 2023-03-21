package com.ldb.reportcustom.exceptions.DTO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class DetailedErrorDTO implements Serializable
{
    private final static long serialVersionUID = -3971443351587441335L;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("originalErrorCode")
    @Expose
    public String originalErrorCode;

    @SerializedName("originalErrorDesc")
    @Expose
    public String originalErrorDesc;

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
