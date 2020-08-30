package com.cos.security.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cos.security.model.RoleType;
import com.cos.security.model.User;

import lombok.Getter;

//시큐리티가 /login주소 요청이오면 낚아채서 로그인을 진행시킨다.
//로그인을 진행이  완료가되면 시큐리티 session을 만들어줍니다. (시큐리티 자신만의 세션) (SecurityContextHolder)
//이때 오브젝트는 =>Authentication 타입객체 안에 =>User정보가 있어야하는데
//이때 User오브젝트의 타입은 =>Userdetails타입 객체여야함

//Security session => Authentication => Userdetails

@Getter
public class PrincipalDetails implements UserDetails,OAuth2User{

	private User user; //User정보를 넣어야 하기때문에 콤포지션을 해준다.
	
	//생성자 
	public PrincipalDetails(User user) {
		this.user =user; 
	}
	
	//해당 유저의 권한을 리턴하는 곳
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>(); //String Type을 반한하기 위하여 collection.add로 메서드 오버라이드
		collection.add(new GrantedAuthority() {					
			@Override
			public String getAuthority() {
				return user.getRole().toString();  //String Type을 리턴해줘야하는데 Collcetion GrantedAuthority타입으로 정해져있음
			}
		});
		return collection;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override //계정이 만료되었니?
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override //계정 잠겼어? 
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override//계정이 일년이지났니?너무 오래사용한건 아니니?
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override //계정이 활성화 되어있니?
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

}
