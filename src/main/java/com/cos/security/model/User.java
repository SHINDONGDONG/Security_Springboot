package com.cos.security.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data //getter setter 자동생성(롬복)
@Entity //테이블을 만들어줌 (자동생성)
public class User {

	@Id //primarykey
	@GeneratedValue(strategy = GenerationType.IDENTITY)//db의 규칙을 따른다는 뭐그런거임
	private int id;
	private String username;
	private String password;
	private String email;
	
//	@Enumerated(EnumType.STRING)
	private String role; //롤타입으로 강제성부여
	
	private String provider;
	private String providerId;
	
	
	@CreationTimestamp
	private Timestamp createDate; //sql의 timestapm
	
}
