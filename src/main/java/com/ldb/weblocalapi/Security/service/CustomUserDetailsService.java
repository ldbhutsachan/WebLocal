package com.ldb.weblocalapi.Security.service;

import com.ldb.weblocalapi.entities.Users;
import com.ldb.weblocalapi.exceptions.ResourceNotFoundException;
import com.ldb.weblocalapi.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service("userDetailsService")
public class CustomUserDetailsService extends JdbcDaoImpl {


    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Autowired
    private UserRepository userRepository;

    @Override
    @Value("SELECT * from USER_LOGIN where USER_NAME = ?")
    public void setUsersByUsernameQuery(String usersByUsernameQueryString) {
        super.setUsersByUsernameQuery(usersByUsernameQueryString);
    }

    @Override
    @Value("SELECT C.USER_NAME, B.ROLE_NAME from USER_ROLE A "
            + "JOIN ROLE B ON A.ROLE_ID = B.ROLE_ID "
            + "LEFT OUTER JOIN USER_LOGIN C ON A.USER_ID = C.USER_ID  "
            + "WHERE C.USER_NAME =?")
    public void setAuthoritiesByUsernameQuery(String queryString) {
        super.setAuthoritiesByUsernameQuery(queryString);
    }


    // override to get accountNonLocked
    @Override
    public List<UserDetails> loadUsersByUsername(String username) {
        return getJdbcTemplate().query(super.getUsersByUsernameQuery(), new String[] { username },	new RowMapper<UserDetails>() {
            public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                String username = rs
                        .getString("USER_NAME");
                String password = rs.getString("PASSWORD");
                boolean enabled = rs.getBoolean("ENABLED");
                boolean accountNonExpired = rs.getBoolean("ACCOUNT_NON_EXPIRED");
                boolean credentialsNonExpired = rs.getBoolean("CREDENTIALS_NON_EXPIRED");
                boolean accountNonLocked = rs.getBoolean("ACCOUNT_NON_LOCKED");
                UserPrincipal userDetails = new UserPrincipal(username, password, enabled, accountNonExpired, credentialsNonExpired,
                        accountNonLocked, AuthorityUtils.NO_AUTHORITIES);
						System.out.println("First Login : " + userDetails);
                return userDetails;
            }

        });
    }


    // override to pass accountNonLocked
    @Override
    public UserDetails createUserDetails(String username, UserDetails userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {
        String returnUsername = userFromUserQuery.getUsername();
//		System.out.println("USER LOGIN Password = " + userFromUserQuery.getPassword());
        if (super.isUsernameBasedPrimaryKey()) {
            returnUsername = username;
        }
        Optional<Users> user = userRepository.findByUsername(username);
        final UserPrincipal userDetails = new UserPrincipal(user.get(), returnUsername, userFromUserQuery.getPassword(), userFromUserQuery.isEnabled(),
                userFromUserQuery.isAccountNonExpired(), userFromUserQuery.isCredentialsNonExpired(),
                userFromUserQuery.isAccountNonLocked(), combinedAuthorities);
        return userDetails;
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
//        System.out.println("find user name : " + userRepository.findById(id));
        Users user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserByUserName(String username) {
        Users user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("User", "username", username)
        );
        return UserPrincipal.create(user);
    }
}
