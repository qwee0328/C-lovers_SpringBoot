package com.clovers.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.clovers.interceptors.AccountingAuthorityValidator;
import com.clovers.interceptors.FullAuthorityValidator;
import com.clovers.interceptors.HumanResourcesValidator;
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
	
	// 인사 인증
	@Autowired
	private HumanResourcesValidator humanResourcesValidator;
	
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
		// login 사용자만 접근가능
		String [] loginAddPath={"/","/mail/**","/schedule/**","/humanResources/**","/accounting/**","/addressbook/**","/admin/**","/chat/**","/electronicSignature/**","/employee/**","/members/**","/office/**","/org/**"};
		registry.addInterceptor(loginValidator)
				.addPathPatterns(loginAddPath)
				.excludePathPatterns("/members/**");
		
		// 기본 권한으로 접근할 수 있는 경로
		String[] excludePath = {"/mail/**","/schedule/**","/humanResources/**","/accounting/**","/addressbook/**","/chat/**","/electronicSignature/**","/employee/**","/members/**","/office/**","/org/**"};
		// 관리자 권한이 필요한 경로
		String[] authorityPath= {"/admin/office/organization","/admin/office/user","/admin/office/positionduty","/admin/office/administrator"};
		
		registry.addInterceptor(fullAuthorityValidator)
				.addPathPatterns("/admin/**")
				.excludePathPatterns(excludePath)
				.excludePathPatterns(authorityPath)
				.excludePathPatterns("/admin/accounting/**");
		
		// 인사 권한
		registry.addInterceptor(humanResourcesValidator)
				.addPathPatterns(authorityPath)
				.excludePathPatterns(excludePath);
		
		// 회계 권한
		registry.addInterceptor(accountingAuthorityValidator)
				.addPathPatterns("/admin/accounting/**")
				.excludePathPatterns(excludePath);
		
	}
	
	
	public void addCorsMapping(CorsRegistry registry) {
		
		registry.addMapping("/**").allowedOrigins("*");
	}
}
