package com.ldb.reportcustom.services.Impl;

import com.ldb.reportcustom.entities.CreateUser;
import com.ldb.reportcustom.services.CreateUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class CreateUserServiceImpl implements CreateUserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    org.springframework.security.crypto.password.PasswordEncoder encoder
            = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
@Override
    public int  saveUsers(CreateUser createUser) {
    try {
        String crUser = "insert into tax_user_login(USER_NAME,PASSWORD,ENABLED,BORDER_ID,CREATED_AT,ACCOUNT_NON_EXPIRED,CREDENTIALS_NON_EXPIRED,ACCOUNT_NON_LOCKED) values (?,?,?,?,?,?,?,?)";
        return jdbcTemplate.update(crUser, createUser.getUsername(),encoder.encode(createUser.getPassword()), createUser.getEnabled(), createUser.getBorderId(),
                new Date(),createUser.getAccountNonExpires(),createUser.getCreditNonExpired(),createUser.getAccNonLock()
                );
    }catch (Exception ex){
        ex.printStackTrace();
        return 0;
    }
}

    //change password
    @Override
    public int  changePassword(CreateUser createUser) {
        try {
            System.out.println("show username hut:"+createUser.getUsername());
            String crUser = "update  tax_user_login set PASSWORD=? where USER_NAME=? ";
            return jdbcTemplate.update(crUser,encoder.encode(createUser.getPassword()),createUser.getUsername()
            );
        }catch (Exception ex){
            ex.printStackTrace();
            return 0;
        }
}
}
