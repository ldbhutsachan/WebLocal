package com.ldb.weblocalapi.services;

import com.ldb.weblocalapi.Model.ChangePasswordReq;
import com.ldb.weblocalapi.entities.CreateUser;

public interface CreateUserService {
    public int changePassword(String password,String userName);

}
