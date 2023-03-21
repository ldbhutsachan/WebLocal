package com.ldb.reportcustom.Security.service;

import com.ldb.reportcustom.entities.UserAttempts;

public interface UserDetailsDao {

	void updateFailAttempts(String username);
	void resetFailAttempts(String username);
	UserAttempts getUserAttempts(String username);
	
}
