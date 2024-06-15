package com.ldb.weblocalapi.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordReq {
    private String newPassword;
    private String confirmPassword;
    private String oldPassword;
    private String userName;
}
