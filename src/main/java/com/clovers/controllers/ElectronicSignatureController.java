package com.clovers.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/electronicsignature/")
public class ElectronicSignatureController {
	// 전자결재 컨트롤러
	
	// 메인 화면 
	@RequestMapping("")
	public String main() {
		String title = "전자결재";
		String currentMenu = "대기";
		
		return "/electronicsignature/wait";
	}
}
