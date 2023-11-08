package com.clovers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
	// 일정 컨트롤러
	@Autowired
	private HttpSession session;
	
	// 메인 화면 (받은 메일함)
	@RequestMapping("")
	public String main() {
		
		String title = "일정";
		String naviBtn = "일정 추가";
		String naviBtnLocation = "send";
		String[] naviIcon = {"fa-chevron-up", "fa-plus"};
		String[] naviMenu = {"내 캘린더", "공유 캘린더"}; 
		int naviMenuLength = naviMenu.length;
		String currentMenu = "내 캘린더";
		
		session.setAttribute("title", title);
		session.setAttribute("naviBtn", naviBtn);
		session.setAttribute("naviBtnLocation", naviBtnLocation);
		session.setAttribute("naviIcon", naviIcon);
		session.setAttribute("naviMenu", naviMenu);
		session.setAttribute("naviMenuLength", naviMenuLength);
		session.setAttribute("currentMenu", currentMenu);
		
		return "/schedule/calendar_main";
	}
}
