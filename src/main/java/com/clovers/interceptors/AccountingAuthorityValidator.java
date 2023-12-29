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
		boolean result = false; // 회계 권한 결과
		boolean full = false; // 총괄 정보
		boolean humanResource = false; // 인사 정보
		// 사용자가 가진 권한 정보 가져오기
		List<String> permission = mservice.getAuthorityCategory(loginID);
		for (String per : permission) {
			if (per.equals("회계")) {
				result= true;
			} else if (per.equals("총괄")) {
				result= true;
				full = true;
			} else if (per.equals("인사")) {
				humanResource = true;
			}
		}
		// 회계관리, 총괄 관리가 아니라면 접근하지 못하도록 차단
		if (!full&&!result) {
			response.sendRedirect("/");
		}
		return result;
	}
}
