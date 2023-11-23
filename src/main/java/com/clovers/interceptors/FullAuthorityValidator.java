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
		String loginID = (String)session.getAttribute("loginID");
		boolean result = false;
		List<String> permission = mservice.getAuthorityCategory(loginID);
	      for(String per:permission) {
	         if(per.equals("총괄")) {
	            result = true;
	         }else {
	        	 sendUnauthorizedResponse(response);
	         }
	      }

		return result;
	}
	
	private void sendUnauthorizedResponse(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write("Unauthorized Access");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

}
