package com.clovers.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.clovers.services.HumanResourcesService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private HttpSession session;
	
	@Autowired 
	private HumanResourcesService hrservice;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		String title = "오피스 홈";
		
		session.setAttribute("title", title);
		System.out.println("loginID : "+(String)session.getAttribute("loginID"));
		Map<String, Object> userWorkRule = hrservice.selectEmployeeWorkRule((String)session.getAttribute("loginID"));
		System.out.println(userWorkRule.toString());
		session.setAttribute("daily_work_rule_id", userWorkRule.get("daily_work_rule_id"));
		session.setAttribute("attend_time", userWorkRule.get("attend_time"));
		System.out.println(session.getAttribute("attend_time"));
		return "home";
	}
	
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e) {
		e.printStackTrace();
		return "error";
	}
}
