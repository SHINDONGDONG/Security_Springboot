package com.cos.security.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.security.model.RoleType;
import com.cos.security.model.User;

//시큐리티가 /login주소 요청이오면 낚아채서 로그인을 진행시킨다.
//로그인을 진행이  완료가되면 시큐리티 session을 만들어줍니다. (시큐리티 자신만의 세션) (SecurityContextHolder)
//이때 오브젝트는 =>Authentication 타입객체 안에 =>User정보가 있어야하는데
//이때 User오브젝트의 타입은 =>Userdetails타입 객체여야함

//Security session => Authentication => Userdetails

public class PrincipalDetails implements UserDetails{

	private User user; //User정보를 넣어야 하기때문에 콤포지션을 해준다.
	
	//생성자 
	public PrincipalDetails(User user) {
		this.user =user; 
	}
	
	//해당 유저의 권한을 리턴하는 곳
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();
		collection.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				String roleType = new (String)user.getRole();
				return (String)user.getRole();
			}
		})
		return collection;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
