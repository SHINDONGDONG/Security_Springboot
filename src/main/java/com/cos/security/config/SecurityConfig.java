package com.cos.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.security.config.oauth.PrincipalOauth2Service;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록이됨
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true) //secured어노테이션 활성화 //prePostEnable
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private PrincipalOauth2Service principalOauth2Service;
	
	@Bean
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http
			.authorizeRequests()
			.antMatchers("/user/**").authenticated()
			.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll() //위의 세개의 주소 빼고는 권한이 다 허용됨
			.and()
			.formLogin()
			.loginPage("/loginForm") //위의 세개의 주소가 걸리면 "/login" 페이지로 가도록 설정해줌 
			.loginProcessingUrl("/login")//login 주소가 호출이되면 시큐리티가 낚아채서 대신 로그인해줌  그러므로 컨트롤러에 /login을 만들지 않아도됨!!! 개편리
			.defaultSuccessUrl("/")
			.and()
			.oauth2Login() //현재 사용하고있는 라이브러리 oauth2
			.loginPage("/loginForm") //구글로그인 링크로 들어가도 loginForm 으로 똑같이 갈 수 있도록 설정
			//코드 X (엑세스토큰 + 사용자프로필정보 O )
			.userInfoEndpoint()
			.userService(principalOauth2Service); 
	}
	
}
