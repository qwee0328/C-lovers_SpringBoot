package com.clovers.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.clovers.interceptors.AccountingAuthorityValidator;
import com.clovers.interceptors.FullAuthorityValidator;
import com.clovers.interceptors.LoginValidator;

@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer{
	
//	로그인 인증 Interceptor 설정
	@Autowired
	LoginValidator loginValidator;
	
	// 총괄 인증
	@Autowired
	private FullAuthorityValidator fullAuthorityValidator;
	
	// 회계 인증
	@Autowired
	private AccountingAuthorityValidator accountingAuthorityValidator;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/uploads/**").addResourceLocations("file:C:/C-lovers/");
		registry.addResourceHandler("/mailUploads/**").addResourceLocations("file:C:/mailUploads/");
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
		
		// 
		System.out.println("여기인가?");
		if(loginValidator != null) {
			if(fullAuthorityValidator!=null) {
				System.out.println("총괄기능됨");
				// 총괄 권한이 있을 때 
				registry.addInterceptor(fullAuthorityValidator)
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
			}else {
				System.out.println("총괄기능안됨");
			}
			System.out.println("로그인 되어있음");
			
		}
		
		
		
	}
	
	public void addCorsMapping(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*");
	}
}
