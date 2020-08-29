package com.cos.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.security.model.User;
import com.cos.security.repository.UserRepository;

//시큐리티 설정에서 loginprocessinUrl("/login") 걸어놨기때문에
// /login 요청이오면 자동으로 UserDetailsService 타입으로 IoC 되어있는 loadUserByUsername 함수가 실행됨.  이건 불변의 법칙 규칙~~
@Service
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	
	//어디쪽으로 리턴이되냐?
	//security session = Authentication =Userdetails
	//리턴이되면 
	//security session(내부Authentication(내부Userdetails))
	@Override											//여기서 말하는 username은 입력받은 username임
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//입력받은 username이 있는지 확인하는 작업
		System.out.println("username : " + username);
		User userEntity = userRepository.findByUsername(username);
		if(userEntity != null) {
			return new PrincipalDetails(userEntity); //Principaldetails에 꼮!! User타입을 넣어줘야 활용할수있음 principaldetails는 userdetails를 품고있어서
		}
		return null;
	}

}
