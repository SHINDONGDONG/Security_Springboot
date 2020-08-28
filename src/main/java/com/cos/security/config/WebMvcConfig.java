package com.cos.security.config;

import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//IoC등록을 위해 Configration 어노테이션 추가
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	//오버라이드를 하여 내부구성을 바꿔준다
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		MustacheViewResolver resolver = new MustacheViewResolver();
		resolver.setCharset("UTF-8"); //기본적인 인코딩은 UTF-8이고
		resolver.setContentType("text/html; charset=UTF-8"); //던지는 데이터는 text/html파일이야(UTF-8로)
		resolver.setPrefix("classpath:/templates/"); //새로 재정의할 프리픽스는 /templates/ 이고
		resolver.setSuffix(".html"); //서픽스는 .html이야
		
		registry.viewResolver(resolver);
	}
}
