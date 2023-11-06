package com.clovers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private HttpSession session;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		String title = "오피스 홈";
		
		session.setAttribute("title", title);
		session.setAttribute("loginID", "test"); // 채팅 테스트 하려고 로그인 정보 넣음
		return "home";
	}
	
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e) {
		e.printStackTrace();
		return "error";
	}
}
