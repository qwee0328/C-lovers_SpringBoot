package com.clovers.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminMappingController {
	@RequestMapping("/admin/**")
	public String adminRoute() {
		return "forward:/index.html";
	}
}
