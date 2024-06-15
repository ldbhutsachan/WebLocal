package com.ldb.weblocalapi.Security.service;

import com.ldb.weblocalapi.entities.UserAttempts;

public interface UserDetailsDao {

	void updateFailAttempts(String username);
	void resetFailAttempts(String username);
	UserAttempts getUserAttempts(String username);
	
}
