package com.ldb.weblocalapi.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "USER_LOGIN_ATTEMPTS")
public class UserAttempts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID", nullable = false)
	private Integer id;
	
	@Column(name = "USER_NAME", length = 36, nullable = false)
	private String username;
	
	@Column(name = "ATTEMPTS", length = 36, nullable = false)
	private Integer attempts;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_MODIFIED", nullable = true)
	private Date lastModified;
}
