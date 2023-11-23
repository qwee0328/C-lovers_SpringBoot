package com.clovers.interceptors;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.clovers.services.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AccountingAuthorityValidator implements HandlerInterceptor {
	// 회계 권한 유효성 검사
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
		boolean humanResource = false;
		List<String> permission = mservice.getAuthorityCategory(loginID);
		for (String per : permission) {
			System.out.println("회계 있?" + per);
			if (per.equals("회계")) {
				result= true;
			} else if (per.equals("총괄")) {
				result= true;
				full = true;
			} else if (per.equals("인사")) {
				humanResource = true;
			}
		}
		System.out.println(full + " " + humanResource);
		if (!full||!result) {
			System.out.println("메인으로 돌아가");
			response.sendRedirect("/");
		}

		return result;
	}
}
