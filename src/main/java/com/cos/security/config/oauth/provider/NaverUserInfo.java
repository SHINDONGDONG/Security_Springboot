package com.cos.security.config.oauth.provider;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo {

	private Map<String,Object> attributes; //getAttributes

	//id=73416029, email=homerjay@naver.com, name=신민철 정보가 넘어옴
	public  NaverUserInfo(Map<String,Object> attributes) {
		this.attributes = attributes;
	}
	
	@Override
	public String getProviderId() {
		return (String)attributes.get("id");
	}

	@Override
	public String getProvider() {
		return "naver";
	}

	@Override
	public String getEmail() {
		return (String)attributes.get("email");
	}

	@Override
	public String getName() {
		return (String)attributes.get("name");
	}

}
