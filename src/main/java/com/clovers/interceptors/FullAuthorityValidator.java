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
public class FullAuthorityValidator implements HandlerInterceptor {
	// 총괄 권한 유효성 검사
	@Autowired
	private HttpSession session;

	@Autowired
	private MemberService mservice;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		session = request.getSession();
		String loginID = (String) session.getAttribute("loginID");
		System.out.println("총괄 유효성" + loginID);
		boolean result = false;
		boolean humanResource = false;
		boolean accounting = false;
		List<String> permission = mservice.getAuthorityCategory(loginID);
		System.out.println(permission.toString());
		if (permission.size() != 0) {
			for (String per : permission) {
				System.out.println("총괄 있?" + per);
				if (per.equals("총괄")) {
					return true;
				} else if (per.equals("인사")) {
					humanResource = true;
				} else if (per.equals("회계")) {
					accounting = true;
				}
			}
			System.out.println(humanResource +" "+ accounting);
			if (!humanResource || !accounting) {
				System.out.println("메인으로 돌아가");
				response.sendRedirect("/");
			}
		}
		return result;
	}
}
