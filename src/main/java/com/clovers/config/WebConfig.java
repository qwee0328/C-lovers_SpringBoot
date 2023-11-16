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
		registry.addResourceHandler("/mailUploads/**").addResourceLocations("file:/Users/mailUploads/");
	}
	
//	인터셉터
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginValidator)
				.addPathPatterns("/")
				.addPathPatterns("/mail/**")
				.addPathPatterns("/schedule/**")
				.addPathPatterns("/humanResources/**")
				.addPathPatterns("/accounting/**")
				.addPathPatterns("/addressbook/**")
				.addPathPatterns("/admin/**")
				.addPathPatterns("/board/**")
				.addPathPatterns("/chat/**")
				.addPathPatterns("/electronicSignature/**")
				.addPathPatterns("/employee/**")
				.addPathPatterns("/members/**")
				.addPathPatterns("/office/**")
				.addPathPatterns("/org/")
				.addPathPatterns("/reservation/**")
				.excludePathPatterns("/members/**");
	}
	
	public void addCorsMapping(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*");
	}
}
