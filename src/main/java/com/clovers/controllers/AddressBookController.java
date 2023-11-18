package com.clovers.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clovers.dto.AddressBookDTO;
import com.clovers.dto.AddressBookTagDTO;
import com.clovers.services.AddressBookService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/addressbook")
public class AddressBookController {
	// 주소록 컨트롤러
	@Autowired
	private HttpSession session;
	
	@Autowired
	private AddressBookService abservice;
	
	// 주소록 메인 홈
	@RequestMapping("")
	public String main(Model model, HttpServletRequest request) { // index 데이터
		String title="주소록";
		if(session.getAttribute("currentMenu") == null) { // 주소록 페이지 첫 접속 시 주소록 개인 전체 선택
			session.setAttribute("currentMenu", "0");
		}
	
		session.setAttribute("title", title);
		return "/addressbook/addBook";
	}
	
	
	// 주소 관련
	
	// 주소 추가
	@ResponseBody
	@RequestMapping("/insert") // 주소록에 내용 추가
	public int insert(AddressBookDTO dto, @RequestParam(value="selectedTagArray[]", required=false)List<Integer> selectedTagArray) {
		dto.setEmp_id((String)session.getAttribute("loginID")); // 주소록 등록인을 로그인한 아이디로 지정
		if(selectedTagArray == null || selectedTagArray.isEmpty()) // 주소록 태그를 선택하지 않았다면
			return abservice.insert(dto); // 테이블에 주소록에 내용만 추가
		return abservice.insert(dto, selectedTagArray); // 태그 선택했다면 테이블에 주소록 내용추가, 태그 추가
	}
	
	// 주소 검색
	@ResponseBody
	@RequestMapping("/select") // 주소록 검색 (전체 / 태그별 / 검색어별)
	public List<Map<String,Object>> select(String key, int value, int currentMenu, String keyword) {
		session.setAttribute("currentMenu", currentMenu);
		if(keyword != null)
			keyword = "%"+keyword+"%";
		return abservice.select((String)session.getAttribute("loginID"), key, value, keyword);
		// key : 전체 주소록을 검색할 것인지, 태그로 주소록을 검색할 것인지 (key 값이 is_shard일 경우 개인 전체/공유 전체이며, key 값이 id일 경우 태그로 검색함.)
		// value : key 값에 대한 실제 값 (개인: personal, 공유: shared, id: id 값(기본키)
		// keyword : 검색어
	}
	
	// 주소 상세 보기
	@ResponseBody
	@RequestMapping("/selectById") // 주소록 상세 정보 불러오기 (상세보기 모달창 내용)
	public Map<String,Object> selectById(int id) {
		return abservice.selectById(id);
	}
	
	// 주소 삭제
	@ResponseBody
	@RequestMapping("/delete") // 주소 삭제
	public int delete(int id) {
		return abservice.delete(id);
	}
	
	// 주소 변경
	@ResponseBody
	@RequestMapping("/update") // 주소 변경
	public int update(AddressBookDTO dto, @RequestParam(value="selectedTagArray[]", required=false)List<Integer> selectedTagArray) {
		if(selectedTagArray == null || selectedTagArray.isEmpty()) { // 선택된 태그가 없다면
			return abservice.update(dto); // 기존 태그 삭제 및 주소 내용 업데이트
		}
		return abservice.update(dto,selectedTagArray); // 기존 태그 삭제 및 새로운 태그 추가 및 주소 내용 업데이트
	}
	
	
	
	// 태그 관련
	
	// 태그 추가
	@ResponseBody
	@RequestMapping("/tagInsert") // 태그 생성
	public int tagInsert(AddressBookTagDTO dto) {
		dto.setEmp_id((String)session.getAttribute("loginID")); // 태그 생성자는 로그인한 아이디로 지정
		return abservice.tagInsert(dto);
	}
	
	// 태그 출력
	@ResponseBody
	@RequestMapping("/tagSelect")
	public List<AddressBookTagDTO> tagSelect() {
		return abservice.tagSelect((String)session.getAttribute("loginID")); // index 출력을 위한 태그 전체 검색
	}
	
	// 주소 추가 모달창 내 선택 가능한 태그 목록 출력
	@ResponseBody
	@RequestMapping("/tagSelectByIsShare")
	public List<AddressBookTagDTO> tagSelectByIsShare(int is_share) {
		return abservice.tagSelectByIsShare((String)session.getAttribute("loginID"),is_share); // 주소 추가 시 select tag(태그 선택) option에 사용 가능한 태그 출력 -> 선택한 주소록에 따라 달라짐.
	}
	
	// 태그 삭제
	@ResponseBody
	@RequestMapping("/tagDelete") // 태그 삭제
	public int tagDelete(int id) {
		return abservice.tagDelete(id);
	}
	
	
	
	// 즐겨찾기 관련
	// 즐겨찾기 추가
	@ResponseBody
	@RequestMapping("/favoriteInsert") // 즐겨찾기 추가
	public int favoriteInsert(int address_book_id) {
		return abservice.favoriteInsertIfNotExist(address_book_id, (String)session.getAttribute("loginID"));
	}
	
	// 즐겨찾기 삭제
	@ResponseBody
	@RequestMapping("/favoriteDelete") // 즐겨찾기 삭제
	public int favoriteDelete(int address_book_id) {
		return abservice.favoriteDelete(address_book_id, (String)session.getAttribute("loginID"));
	}
}
