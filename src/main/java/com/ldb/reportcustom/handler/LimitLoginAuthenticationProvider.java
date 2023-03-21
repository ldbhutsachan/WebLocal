package com.ldb.reportcustom.handler;

import com.ldb.reportcustom.Security.service.UserDetailsDao;
import com.ldb.reportcustom.entities.UserAttempts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component("authenticationProvider")
public class LimitLoginAuthenticationProvider extends DaoAuthenticationProvider {

	@Autowired
	UserDetailsDao userDetailsDao;

	@Bean
	private BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}


	@Autowired
	@Qualifier("userDetailsService")
	@Override
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		super.setUserDetailsService(userDetailsService);
		super.setPasswordEncoder(passwordEncoder());
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try {

			Authentication auth = super.authenticate(authentication);


			// if reach here, means login success, else an exception will be thrown
			// reset the user_attempts
			userDetailsDao.resetFailAttempts(authentication.getName());
//			System.out.println("Authentication !! " + auth);
			return auth;

		} catch (BadCredentialsException e) {
			// invalid login, update to user_attempts
			userDetailsDao.updateFailAttempts(authentication.getName());
			log.info("invalid login = {}", e.getMessage() );
			throw new BadCredentialsException("User or Password invalid");

		} catch (LockedException e) {
			// this user is locked!
			String error = "";
			UserAttempts userAttempts = userDetailsDao.getUserAttempts(authentication.getName());

			if (userAttempts != null) {
				Date lastAttempts = userAttempts.getLastModified();
				//, new String[] {authentication.getName(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastAttempts)}
				error = "User account is locked! \n Username : " + authentication.getName()
						+ "\n Last Attempts : " + new SimpleDateFormat("yyyy-MM-dd HH.mm.ss").format(lastAttempts);
//				error = "User account is locked! <br><br>Username : "
//						+ authentication.getName() + "<br>Last Attempts : " +  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastAttempts);

//				error = messageConfig.getMessage("user.account.locked",
//						new String[] {authentication.getName(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastAttempts)}) ;
			} else {
				error = e.getMessage();
			}
			throw new LockedException(error);
		}

	}

}
