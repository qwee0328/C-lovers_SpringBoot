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
	public String main(Model model) { // index 데이터
		String title="주소록";
		String currentMenu = "개인 전체";
		session.setAttribute("title", title);
		session.setAttribute("currentMenu", currentMenu);
		return "/addressbook/addBook";
	}
	
	@ResponseBody
	@RequestMapping("/insert") // 주소록에 내용 추가
	public int insert(AddressBookDTO dto, @RequestParam(value="selectedTagArray[]", required=false)List<Integer> selectedTagArray) {
		dto.setEmp_id((String)session.getAttribute("loginID")); // 주소록 등록인을 로그인한 아이디로 지정
		if(selectedTagArray == null || selectedTagArray.isEmpty()) // 주소록 태그를 선택하지 않았다면
			return sbservice.insert(dto); // 테이블에 주소록에 내용만 추가
		return sbservice.insert(dto, selectedTagArray); // 태그 선택했다면 테이블에 주소록 내용추가, 태그 추가
	}
	
	@ResponseBody
	@RequestMapping("/tagInsert") // 태그 생성
	public int tagInsert(AddressBookTagDTO dto) {
		dto.setEmp_id((String)session.getAttribute("loginID")); // 태그 생성자는 로그인한 아이디로 지정
		return sbservice.tagInsert(dto);
	}
	
	
	@ResponseBody
	@RequestMapping("/select") // 주소록 검색
	public List<AddressBookDTO> select(String key, int value) {
		return sbservice.select((String)session.getAttribute("loginID"), key, value);
		// key : 전체 주소록을 검색할 것인지, 태그로 주소록을 검색할 것인지 (key 값이 is_shard일 경우 개인 전체/공유 전체이며, key 값이 id일 경우 태그로 검색함.)
		// value : key 값에 대한 실제 값 (개인: personal, 공유: shared, id: id 값(기본키)
	}

	
	@ResponseBody
	@RequestMapping("/tagSelect")
	public List<AddressBookTagDTO> tagSelect() {
		return sbservice.tagSelect((String)session.getAttribute("loginID")); // index 출력을 위한 태그 전체 검색
	}
	
	@ResponseBody
	@RequestMapping("/tagSelectByIsShare")
	public List<AddressBookTagDTO> tagSelectByIsShare(int is_share) {
		return sbservice.tagSelectByIsShare((String)session.getAttribute("loginID"),is_share); // 주소 추가 시 select tag(태그 선택) option에 사용 가능한 태그 출력 -> 선택한 주소록에 따라 달라짐.
	}
	
}
