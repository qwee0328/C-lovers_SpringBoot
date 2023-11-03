package com.clovers.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/mail")
public class MailController {
	// 메일 컨트롤러
	
	@RequestMapping(value = "")
	public String main() {
		return "mail/inBox";
	}
}
