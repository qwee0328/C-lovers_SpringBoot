package com.clovers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clovers.commons.EncryptionUtils;
import com.clovers.services.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/members/")
public class MemberController {
	// 멤버 로그인, 비밀번호 확인
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private MemberService mservice;
	
//	로그인 페이지 이동
	@RequestMapping("goLogin")
	public String goLogin() {
		return "member/login";
	}
	
//	ajax 로 로그인 됐는지 안됐는지 확인
	@ResponseBody
	@RequestMapping(value="login", method = RequestMethod.POST)
	public boolean login(String id, String pw) {
		
//		암호화한거
//		String pwEnc = EncryptionUtils.getSHA512(pw);
//		boolean result = mservice.login(id, pwEnc);
		
		boolean result = mservice.login(id, pw);
		
		if(result) {
			session.setAttribute("loginID", id);
			System.out.println("login( ) : "+ session.getAttribute("loginID"));
		}
		
		return result;
	}

}
