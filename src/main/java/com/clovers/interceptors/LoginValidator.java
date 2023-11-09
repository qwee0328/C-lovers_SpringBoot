package com.clovers.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LoginValidator implements HandlerInterceptor{
	//	로그인 유효성

	@Autowired
	private HttpSession session;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		session = request.getSession();
		String loginID = (String)session.getAttribute("loginID");
	
		System.out.println("loginValidator : "+loginID);
		if(loginID != null) {
			return true;
		}else {
			response.sendRedirect("/members/goLogin");
			return false;
		}
		
	}

}
