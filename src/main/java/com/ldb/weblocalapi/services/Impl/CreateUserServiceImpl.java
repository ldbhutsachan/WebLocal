package com.ldb.weblocalapi.services.Impl;

import com.ldb.weblocalapi.Model.ChangePasswordReq;
import com.ldb.weblocalapi.entities.CreateUser;
import com.ldb.weblocalapi.services.CreateUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
@Slf4j
public class CreateUserServiceImpl implements CreateUserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    org.springframework.security.crypto.password.PasswordEncoder encoder
            = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    @Override
    public int changePassword(String password,String userName) {
        try {
            System.out.println("SHOW USERNAME:"+userName);
            System.out.println("SHOW PASS:"+password);
            String crUser = "update  USER_LOGIN set PASSWORD=? where USER_NAME=? ";
            return jdbcTemplate.update(crUser,password,userName
            );
        }catch (Exception ex){
            ex.printStackTrace();
            return 0;
        }
    }
    public List<CreateUser> checkOldPassword(String oldPassword,String userName) {
        try {
            System.out.println("SHOW USERNAME:"+userName);
            System.out.println("SHOW PASS:"+oldPassword);
         String sql = "SELECT PASSWORD FROM USER_LOGIN WHERE USER_NAME ='"+userName+"'";
         return jdbcTemplate.query(sql, new RowMapper<CreateUser>() {
             @Override
             public CreateUser mapRow(ResultSet rs, int rowNum) throws SQLException {
                 CreateUser tr =new CreateUser();
                 tr.setPassword(rs.getString("PASSWORD"));
                 return tr;
             }
         });
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

}
