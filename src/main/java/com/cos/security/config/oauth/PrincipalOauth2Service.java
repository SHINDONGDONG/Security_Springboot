package com.cos.security.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.security.auth.PrincipalDetails;
import com.cos.security.model.RoleType;
import com.cos.security.model.User;
import com.cos.security.repository.UserRepository;

@Service 
public class PrincipalOauth2Service extends DefaultOAuth2UserService{

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	
	//구글로받은 userRequest데이터에 대한 후처리되는 함수
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("userRequest : " +userRequest.getClientRegistration() );
		System.out.println("getAccessToken : " +userRequest.getAccessToken() );
		//구글로그인 버튼 -> 구글로그인 창 -> 로그인을 완료 -> code을 리턴(Oauth-Client라이브러리) ->AccessToken요청
		//userRequest 정보 ->　loadUser함수 호출 ->구글로부터 프로필은 받아준다.
		System.out.println("getAttributes : " +super.loadUser(userRequest).getAttributes());
		System.out.println("userrequest : "+super.loadUser(userRequest));
		
		//회원가입을 강제로 진행예정
		
		OAuth2User oauth2User = super.loadUser(userRequest);
		String provider = userRequest.getClientRegistration().getRegistrationId(); //Google;
		String providerId = oauth2User.getAttribute("sub");
		String username = provider +"_"+ providerId;
		String password = bCryptPasswordEncoder.encode("kimschool");
		String email = oauth2User.getAttribute("email");
		RoleType role =RoleType.USER;
		User userEntity = userRepository.findByUsername(username);
		if(userEntity == null) {
			userEntity = User.builder()
					.username(username)
					.password(password)
					.email(email)
					.role(role)
					.provider(provider)
					.providerId(providerId)
					.build();
			userRepository.save(userEntity);
		}
		return new PrincipalDetails(userEntity,oauth2User.getAttributes());
	}
}
