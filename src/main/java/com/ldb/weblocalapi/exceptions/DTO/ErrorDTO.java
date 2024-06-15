package com.ldb.weblocalapi.exceptions.DTO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ErrorDTO implements Serializable
{
    private final static long serialVersionUID = -878419164589248138L;

    @SerializedName("code")
    @Expose
    public String code;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("detailedErrors")
    @Expose
    public List<DetailedErrorDTO> detailedErrors = null;

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
