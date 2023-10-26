package com.ldb.reportcustom.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "TAX_USER_LOGIN_ATTEMPTS")
public class UserAttempts {

	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATTEMPTS_SEQ")
//	@SequenceGenerator(name = "ATTEMPTS_SEQ", sequenceName = "ATTEMPTS_SEQ", allocationSize = 1)
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

//	@Override
//	public String toString() {
//		ObjectMapper mapper = new ObjectMapper();
//
//		String jsonString = "";
//		try {
//			mapper.enable(SerializationFeature.INDENT_OUTPUT);
//			jsonString = mapper.writeValueAsString(this);
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//		return jsonString;
//	}
}
