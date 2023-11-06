package com.clovers.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members/")
public class MemberController {
	// 멤버 로그인, 비밀번호 확인
	
	@RequestMapping("goLogin")
	public String goLogin() {
		return "member/login";
	}
}
