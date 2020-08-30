package com.cos.security.model;

import lombok.Getter;

@Getter
public enum RoleType {
		USER("ROLE_USER"),ADMIN("ROLE_ADMIN");
	
	RoleType(String key){
		this.key = key;
	}
	private String key;
}
