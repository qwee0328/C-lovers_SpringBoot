package com.clovers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/members/")
public class MemberController {
	// 멤버 로그인, 비밀번호 확인
	
	@Autowired
	private HttpSession hsession;
	
	@RequestMapping("goLogin")
	public String goLogin() {
		return "member/login";
	}
	
	@RequestMapping(value="login", method = RequestMethod.POST)
	public String login(String id, String pw) {
		
		
		
		return "/";
	}
}
