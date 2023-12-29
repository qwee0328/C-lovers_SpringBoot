package com.clovers.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		String title = "오피스 홈";
		
		session.setAttribute("title", title);
		Map<String, Object> userWorkRule = hrservice.selectEmployeeWorkRule((String)session.getAttribute("loginID"));

		session.setAttribute("daily_work_rule_id", userWorkRule.get("daily_work_rule_id"));
		session.setAttribute("attend_time", userWorkRule.get("attend_time"));
		session.removeAttribute("currentMenu");
		return "home";
	}
	
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e) {
		//e.printStackTrace();
		logger.error(e.getMessage());
		return "error";
	}
}
