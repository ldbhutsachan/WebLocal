package com.ldb.reportcustom.services;

import com.ldb.reportcustom.entities.CreateUser;

public interface CreateUserService {
    public  int  saveUsers(CreateUser createUser);
    public int changePassword(CreateUser createUser);

}
