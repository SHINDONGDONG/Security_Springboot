package com.cos.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security.auth.PrincipalDetails;
import com.cos.security.model.RoleType;
import com.cos.security.model.User;
import com.cos.security.repository.UserRepository;
import com.sun.org.apache.bcel.internal.generic.ATHROW;

@Controller //view를 리턴하겠다.
public class IndexController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	
	@GetMapping("/test/login")
	public @ResponseBody String testLogin(Authentication authentication
			,@AuthenticationPrincipal PrincipalDetails userDetails) {
		System.out.println("====================타입변환========================");
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		System.out.println("Authentication : " + principalDetails.getUser());
		
		System.out.println("====================DI주입========================");
		System.out.println("userDetails : " + userDetails.getUser());
		return "세션정보 확인하기";
	}
	
	@GetMapping("/test/oauth/login")
	public @ResponseBody String testOauthLogin(Authentication authentication
			,@AuthenticationPrincipal OAuth2User oauth) {
		System.out.println("====================타입변환========================");
		OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
		System.out.println("Authentication : " + oauth2User.getAttributes());
		
		System.out.println("====================oauth2========================");
		System.out.println("oauth : " + oauth.getAttributes());
		return " Oauth 세션정보 확인하기 ";
	}
	
	
	
	//localhost:8081
	@GetMapping({"","/"})
	public String index() {
		//머스태치를 사용해볼거임.
		//기본폴더 src/main/resource
		//뷰리졸버 : templates(prefix)  .mustache (suffix)       //yml파일에 저장되어있음 //그러나 yml에서 삭제해도됨 //의존성등록이되어잇어서
		return "index"; //src/main/resource/index.mustache 를찾을거임 
	}

	
	@GetMapping("/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println("principal : " + principalDetails.getUser());
		return "user";
	}
	
	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}
	
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}
	
	//스프링시큐리티가 해당 주소를 낚아챔  - securityconfig 파일에서 권한설정을 해주면 안낚임~~
	@GetMapping("/loginForm")
	public  String loginForm() {
		return "loginForm";
	}
	
	@GetMapping("/joinForm")
	public  String joinForm() {
		return "joinForm";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/info")
	public @ResponseBody String info(){
		return "개인정보";
	}

	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
	@GetMapping("/data")
	public @ResponseBody String data(){
		return "데이터정보";
	}
	
	@PostMapping("/join") //Get이 아니라 새로운 추가(생성이라 post) get (read) post(insert) put(update) delete(delete)
	public String join(User user) {
		user.setRole(RoleType.USER); //User role타입은 회원가입할때 없으니 넣어준다.
		String rawPassword = user.getPassword();  //입력받은 password를 rawPassword에 넣어준다.
		String encPassword = bCryptPasswordEncoder.encode(rawPassword); // encPassword는 rawpassword를 암호화 시켜준다
		user.setPassword(encPassword); //user DTO에 암호화된 password를 set해준다.
		userRepository.save(user); //userRepository 가 CRUD를 가지고 있다고 했잖아요. save(가 insert)
		//회원가입 잘됨 - > 그러나 비밀번호 1234로 로그인을 할 수 없음 (스프링 시큐리티 패스워드 암호화가 안되어있어서)
		
		return "redirect:/loginForm";
	}
	
}
 