package com.clovers.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.clovers.interceptors.LoginValidator;

@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer{
	
//	로그인 인증 Interceptor 설정
	@Autowired
	LoginValidator loginValidator;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/uploads/**").addResourceLocations("file:C:/C-lovers/");
	}
	
//	인터셉터
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginValidator)
				.addPathPatterns("/")
				.excludePathPatterns("/members/**");
	}
	
//	public void addCorsMapping(CorsRegistry registry) {
//		registry.addMapping("/admin/**").allowedOrigins("http://localhost:3000").allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE");
//	}
}
