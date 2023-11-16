package com.clovers.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public int insert(AddressBookDTO dto, @RequestParam(value="selectedTagArray[]", required=false)List<Integer> selectedTagArray) {
		dto.setEmp_id((String)session.getAttribute("loginID"));
		if(selectedTagArray == null || selectedTagArray.isEmpty())
			return sbservice.insert(dto);
		return sbservice.insert(dto, selectedTagArray);
	}
	
	@ResponseBody
	@RequestMapping("/tagInsert")
	public int tagInsert(AddressBookTagDTO dto) {
		dto.setEmp_id((String)session.getAttribute("loginID"));
		return sbservice.tagInsert(dto);
	}
	
	
	@ResponseBody
	@RequestMapping("/select")
	public List<AddressBookDTO> select(String key, int value) {
		return sbservice.select((String)session.getAttribute("loginID"), key, value);
	}

	
	@ResponseBody
	@RequestMapping("/tagSelect")
	public List<AddressBookTagDTO> tagSelect() {
		return sbservice.tagSelect((String)session.getAttribute("loginID"));
	}
	
	@ResponseBody
	@RequestMapping("/tagSelectByIsShare")
	public List<AddressBookTagDTO> tagSelectByIsShare(int is_share) {
		return sbservice.tagSelectByIsShare((String)session.getAttribute("loginID"),is_share);
	}
	
}
