package com.clovers.interceptors;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.clovers.services.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class HumanResourcesValidator implements HandlerInterceptor {
	// 인사 권한 유효성 검사
	@Autowired
	private HttpSession session;

	@Autowired
	private MemberService mservice;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		session = request.getSession();
		String loginID = (String) session.getAttribute("loginID");

		boolean result = false;
		boolean full = false;
		boolean accounting = false;
		List<String> permission = mservice.getAuthorityCategory(loginID);

		if (permission.size() != 0) {
			for (String per : permission) {

				if (per.equals("인사")) {
			
					result = true;
				} else if (per.equals("총괄")) {
					result = true;
					full = true;
				} else if (per.equals("회계")) {
					accounting = true;
				}
			}
		
			if (!full&&!result ) {
				response.sendRedirect("/");
			}
		}
		return result;
	}
}
