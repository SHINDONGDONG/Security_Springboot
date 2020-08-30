package com.cos.security.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service 
public class PrincipalOauth2Service extends DefaultOAuth2UserService{

	//구글로받은 userRequest데이터에 대한 후처리되는 함수
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("userRequest : " +userRequest.getClientRegistration() );
		System.out.println("getAccessToken : " +userRequest.getAccessToken() );
		//구글로그인 버튼 -> 구글로그인 창 -> 로그인을 완료 -> code을 리턴(Oauth-Client라이브러리) ->AccessToken요청
		//userRequest 정보 ->　loadUser함수 호출 ->구글로부터 프로필은 받아준다.
		System.out.println("getAttributes : " +super.loadUser(userRequest).getAttributes());
		
		
		OAuth2User oatAuth2User = super.loadUser(userRequest);
		//회원가입을 강제로 진행예정
		return super.loadUser(userRequest);
	}
}
