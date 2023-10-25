package com.ldb.reportcustom.Security.service;

import com.ldb.reportcustom.entities.Users;
import com.ldb.reportcustom.exceptions.ResourceNotFoundException;
import com.ldb.reportcustom.repositories.UserRepository;
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

/**
 * Create at 2019-01-21
 * @author KHAMPHAI
 */
@Slf4j
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "com.ldb.kiwi.Repositories",
//        entityManagerFactoryRef = "entityManagerFactory",
//        transactionManagerRef = "transactionManager")
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
//	@Value("SELECT * from boder.USERS where username = ?")
    //@Value("SELECT * from border.TAX_USER_LOGIN where USER_NAME = ?")
    @Value("SELECT * from TAX_USER_LOGIN where USER_NAME = ?")
    public void setUsersByUsernameQuery(String usersByUsernameQueryString) {
        super.setUsersByUsernameQuery(usersByUsernameQueryString);
    }

    @Override
//	@Value("select username, role from boder.user_roles where username =?")
    @Value("SELECT C.USER_NAME, B.ROLE_NAME from TAX_USER_ROLE A "
            + "JOIN TAX_ROLES B ON A.ROLE_ID = B.ROLE_ID "
            + "LEFT OUTER JOIN TAX_USER_LOGIN C ON A.USER_ID = C.USER_ID "
            + "WHERE C.USER_NAME =?")
    public void setAuthoritiesByUsernameQuery(String queryString) {
        super.setAuthoritiesByUsernameQuery(queryString);
    }


    // override to get accountNonLocked
    @Override
    public List<UserDetails> loadUsersByUsername(String username) {
//		Users user = this.userDao.findUserAccount(username);
//		UsersSingleton userSingleton = UsersSingleton.getInstatance();
//		userSingleton.setUser(user);
//
//		System.out.println("USER : " +super.getUsersByUsernameQuery() + " User name = " + username);
//		this.user = user;
        return getJdbcTemplate().query(super.getUsersByUsernameQuery(), new String[] { username },	new RowMapper<UserDetails>() {
            public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                String username = rs
                        .getString("USER_NAME");
                String password = rs.getString("PASSWORD");
                boolean enabled = rs.getBoolean("ENABLED");
//						boolean accountNonExpired = true;
//						boolean credentialsNonExpired = true;
//						boolean accountNonLocked = true;

                boolean accountNonExpired = rs.getBoolean("ACCOUNT_NON_EXPIRED");
                boolean credentialsNonExpired = rs.getBoolean("CREDENTIALS_NON_EXPIRED");
                boolean accountNonLocked = rs.getBoolean("ACCOUNT_NON_LOCKED");
//                List<GrantedAuthority> authorities = rs.getString("").stream().map(role ->
//                        new SimpleGrantedAuthority(role.getRoleName().name())
//                ).collect(Collectors.toList());
//						return new User(username, password, enabled, accountNonExpired, credentialsNonExpired,
//								accountNonLocked, AuthorityUtils.NO_AUTHORITIES);

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

//		return new User(returnUsername, userFromUserQuery.getPassword(), userFromUserQuery.isEnabled(),
//				userFromUserQuery.isAccountNonExpired(), userFromUserQuery.isCredentialsNonExpired(),
//				userFromUserQuery.isAccountNonLocked(), combinedAuthorities);

        Optional<Users> user = userRepository.findByUsername(username);
        final UserPrincipal userDetails = new UserPrincipal(user.get(), returnUsername, userFromUserQuery.getPassword(), userFromUserQuery.isEnabled(),
                userFromUserQuery.isAccountNonExpired(), userFromUserQuery.isCredentialsNonExpired(),
                userFromUserQuery.isAccountNonLocked(), combinedAuthorities);

//		myUserDetails.setId(((UserPrincipal) userFromUserQuery).getId());
//		System.out.println("USER LOGIN createUserDetails = " + userDetails);
        return userDetails;
    }


/*    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Let people login with either username
        Users user = userRepository.findByUserName(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username : " + username));

        log.warn("print: " + UserPrincipal.create(user));

        return UserPrincipal.create(user);
    }*/

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
//        System.out.println("find user name : " + userRepository.findById(id));
//        Users user = userRepository.findById(id).orElseThrow(
//                () -> new ResourceNotFoundException("User", "id", id)
//        );
        Users user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("User", "username", username)
        );
        return UserPrincipal.create(user);
    }
}
