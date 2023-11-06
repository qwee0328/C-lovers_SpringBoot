package com.clovers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.clovers.dto.EmailDTO;
import com.clovers.services.MailService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/mail")
public class MailController {
	// 메일 컨트롤러
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	MailService mservice;
	
	// 메인 화면 (받은 메일함)
	@RequestMapping(value = {"", "/inBox"})
	public String main() {
		String title = "메일";
		String naviBtn = "편지 쓰기";
		String naviBtnLocation = "send";
		String[] naviIcon = {"fa-inbox", "fa-paper-plane", "fa-box-archive", "fa-clock", "fa-trash"};
		String[] naviMenu = {"받은 편지함", "보낸 편지함", "임시 편지함", "보낼 편지함", "휴지통"}; 
		int naviMenuLength = naviMenu.length;
		String currentMenu = "받은 편지함";
		
		session.setAttribute("title", title);
		session.setAttribute("naviBtn", naviBtn);
		session.setAttribute("naviBtnLocation", naviBtnLocation);
		session.setAttribute("naviIcon", naviIcon);
		session.setAttribute("naviMenu", naviMenu);
		session.setAttribute("naviMenuLength", naviMenuLength);
		session.setAttribute("currentMenu", currentMenu);
		
		return "mail/inBox";
	}
	
	// 편지 쓰기 (메일 작성)
	@RequestMapping(value="send")
	public String send() {
		return "mail/send";
	}
	
	// 보내기 (메일 발송)
	@RequestMapping(value="submitSend")
	public String submitSend(EmailDTO dto) {
		mservice.submitSend(dto);
		
		return "redirect:/mail";
	}
}
