package com.clovers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.clovers.services.HumanResourcesService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/addressbook")
public class AddressBookController {
	// 주소록 컨트롤러
	
	//인사 컨트롤러
	@Autowired
	private HttpSession session;
	
	@RequestMapping("")
	public String main() {
		String title="주소록";
		String currentMenu = "비밀";
		session.setAttribute("title", title);
		session.setAttribute("currentMenu", currentMenu);
		return "/addressBook/addBook";
	}
}
