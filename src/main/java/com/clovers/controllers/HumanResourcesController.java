package com.clovers.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clovers.services.HumanResourcesService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/humanResources")
public class HumanResourcesController {
	// 인사 컨트롤러
	@Autowired
	private HttpSession session;
	
	@Autowired 
	private HumanResourcesService hrservice;
	
	@RequestMapping("")
	public String main() {
		String title="인사";
		session.setAttribute("title", title);
		return "humanresources/hrMain";
	}
	
	// 사용자 근무 규칙 정보 불러오기
	@ResponseBody
	@RequestMapping("/selectEmployeeWorkRule")
	public Map<String, String> selectEmployeeWorkRule() {
		String id = (String)session.getAttribute("loginID");
		Map<String, String> userWorkRule = hrservice.selectEmployeeWorkRule(id);
		System.out.println(userWorkRule.toString());
		return userWorkRule;
	}
}
