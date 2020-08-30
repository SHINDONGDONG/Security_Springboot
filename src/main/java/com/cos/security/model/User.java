package com.cos.security.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //getter setter 자동생성(롬복)
@Entity //테이블을 만들어줌 (자동생성)
@NoArgsConstructor
public class User {

	@Id //primarykey
	@GeneratedValue(strategy = GenerationType.IDENTITY)//db의 규칙을 따른다는 뭐그런거임
	private int id;
	private String username;
	private String password;
	private String email;
	
	@Enumerated(EnumType.STRING)
	private RoleType role; //롤타입으로 강제성부여
	
	private String provider;
	private String providerId;
	
	
	@CreationTimestamp
	private Timestamp createDate; //sql의 timestapm


	@Builder
	public User(String username, String password, String email, RoleType role, String provider, String providerId,
			Timestamp createDate) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.provider = provider;
		this.providerId = providerId;
		this.createDate = createDate;
	}
	
	
	
}
