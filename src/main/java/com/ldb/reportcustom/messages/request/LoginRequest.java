package com.ldb.reportcustom.messages.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Create at 2019-01-21
 * @author KHAMPHAI
 */
@Data
public class LoginRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
