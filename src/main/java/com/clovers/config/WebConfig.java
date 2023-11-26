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
		String [] loginAddPath={"/","/mail/**","/schedule/**","/humanResources/**","/accounting/**","/addressbook/**","/admin/**","/chat/**","/electronicSignature/**","/employee/**","/members/**","/office/**","/org/**"};
		registry.addInterceptor(loginValidator)
				.addPathPatterns(loginAddPath)
//				.addPathPatterns("/mail/**")
//				.addPathPatterns("/schedule/**")
//				.addPathPatterns("/humanResources/**")
//				.addPathPatterns("/accounting/**")
//				.addPathPatterns("/addressbook/**")
//				.addPathPatterns("/admin/**")
//				.addPathPatterns("/chat/**")
//				.addPathPatterns("/electronicSignature/**")
//				.addPathPatterns("/employee/**")
//				.addPathPatterns("/members/**")
//				.addPathPatterns("/office/**")
//				.addPathPatterns("/org/**")
				.excludePathPatterns("/members/**");
		
		String[] excludePath = {"/mail/**","/schedule/**","/humanResources/**","/accounting/**","/addressbook/**","/chat/**","/electronicSignature/**","/employee/**","/members/**","/office/**","/org/**"};
		String[] authorityPath= {"/admin/office/organization","/admin/office/user","/admin/office/positionduty","/admin/office/administrator"};
		registry.addInterceptor(fullAuthorityValidator)
				.addPathPatterns("/admin/**")
				.excludePathPatterns(excludePath)
				.excludePathPatterns(authorityPath)
				.excludePathPatterns("/admin/accounting/**");
//				.excludePathPatterns("/mail/**")
//				.excludePathPatterns("/schedule/**")
//				.excludePathPatterns("/humanResources/**")
//				.excludePathPatterns("/accounting/**")
//				.excludePathPatterns("/addressbook/**")
//				.excludePathPatterns("/chat/**")
//				.excludePathPatterns("/electronicSignature/**")
//				.excludePathPatterns("/employee/**")
//				.excludePathPatterns("/members/**")
//				.excludePathPatterns("/office/**")
//				.excludePathPatterns("/org/**")
//				.excludePathPatterns("/admin/office/organization")
//				.excludePathPatterns("/admin/office/user")
//				.excludePathPatterns("/admin/office/positionduty")
//				.excludePathPatterns("/admin/office/administrator")
//				.excludePathPatterns("/admin/accounting/**");
		
		registry.addInterceptor(humanResourcesValidator)
				.addPathPatterns(authorityPath)
//				.addPathPatterns("/admin/office/organization")
//				.addPathPatterns("/admin/office/user")
//				.addPathPatterns("/admin/office/positionduty")
//				.addPathPatterns("/admin/office/administrator")
				.excludePathPatterns(excludePath);
//				.excludePathPatterns("/mail/**")
//				.excludePathPatterns("/schedule/**")
//				.excludePathPatterns("/humanResources/**")
//				.excludePathPatterns("/accounting/**")
//				.excludePathPatterns("/addressbook/**")
//				.excludePathPatterns("/chat/**")
//				.excludePathPatterns("/electronicSignature/**")
//				.excludePathPatterns("/employee/**")
//				.excludePathPatterns("/members/**")
//				.excludePathPatterns("/office/**")
//				.excludePathPatterns("/org/**");
		
		registry.addInterceptor(accountingAuthorityValidator)
				.addPathPatterns("/admin/accounting/**")
				.excludePathPatterns(excludePath);
//				.excludePathPatterns("/mail/**")
//				.excludePathPatterns("/schedule/**")
//				.excludePathPatterns("/humanResources/**")
//				.excludePathPatterns("/accounting/**")
//				.excludePathPatterns("/addressbook/**")
//				.excludePathPatterns("/chat/**")
//				.excludePathPatterns("/electronicSignature/**")
//				.excludePathPatterns("/employee/**")
//				.excludePathPatterns("/members/**")
//				.excludePathPatterns("/office/**")
//				.excludePathPatterns("/org/**");
		
		
		
		System.out.println("a");
	}
	
	
	public void addCorsMapping(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*");
	}
}
