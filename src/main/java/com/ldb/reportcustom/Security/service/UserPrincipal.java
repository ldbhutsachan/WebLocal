package com.ldb.reportcustom.Security.service;

import com.ldb.reportcustom.entities.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserPrincipal extends User {
	 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Users user;
//    private int id;

    public UserPrincipal(Users user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.user = user;
    }
 
    public UserPrincipal(Users user, boolean enabled, boolean accountNonExpired,
                         boolean credentialsNonExpired,
                         boolean accountNonLocked,
                         Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(),
                enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.user = user;
    }

//    public UserPrincipal(String username, boolean enabled, String password, String passwordT24, Collection<? extends GrantedAuthority> authorities) {
//        this.username = username;
//        enabled = enabled;
//        password = password;
//        this.passwordT24 = passwordT24;
//        this.authorities = authorities;
//    }

    public UserPrincipal(Users user, String username, String password, boolean enabled,
                         boolean accountNonExpired, boolean credentialsNonExpired,
                         boolean accountNonLocked,
                         Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, authorities);
        this.user = user;

    }

    public UserPrincipal(String username, String password, boolean enabled,
                         boolean accountNonExpired, boolean credentialsNonExpired,
                         boolean accountNonLocked,
                         Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, authorities);
    }

    public static UserPrincipal create(Users user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getRoleName())
        ).collect(Collectors.toList());
        List<GrantedAuthority> grantList = new ArrayList<>();
//        GrantedAuthority authorities = new SimpleGrantedAuthority(user.getRoles());
//        grantList.add(authorities);
//        System.out.println("authorities " + grantList);
        return new UserPrincipal(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                user.getAccountNonExpired(),
                user.getCredentialsNonExpired(),
                user.getAccountNonLocked(),
                authorities
        );
    }
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Users getUser() {
        return this.user;
    }
}