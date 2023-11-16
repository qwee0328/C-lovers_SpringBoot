package com.clovers.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clovers.dto.AddressBookDTO;
import com.clovers.dto.AddressBookTagDTO;
import com.clovers.services.AddressBookService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/addressbook")
public class AddressBookController {
	// 주소록 컨트롤러
	@Autowired
	private HttpSession session;
	
	@Autowired
	private AddressBookService sbservice;
	
	@RequestMapping("")
	public String main(Model model) {
		String title="주소록";
		String currentMenu = "개인 전체";
		session.setAttribute("title", title);
		session.setAttribute("currentMenu", currentMenu);
		return "/addressbook/addBook";
	}
	
	@ResponseBody
	@RequestMapping("/insert")
	public int insert(AddressBookDTO dto) {
		System.out.println(dto);
		dto.setEmp_id((String)session.getAttribute("loginID"));
		return sbservice.insert(dto);
	}
	
	@ResponseBody
	@RequestMapping("/tagInsert")
	public int tagInsert(AddressBookTagDTO dto) {
		System.out.println(dto);
		dto.setEmp_id((String)session.getAttribute("loginID"));
		return sbservice.tagInsert(dto);
	}
	
	@ResponseBody
	@RequestMapping("/tagSelect")
	public List<AddressBookTagDTO> tagSelect() {
		return sbservice.tagSelect((String)session.getAttribute("loginID"));
	}
	
}
