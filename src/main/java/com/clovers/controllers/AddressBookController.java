package com.clovers.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clovers.constants.Constants;
import com.clovers.dto.AddressBookDTO;
import com.clovers.dto.AddressBookTagDTO;
import com.clovers.dto.EmailDTO;
import com.clovers.services.AddressBookService;
import com.clovers.services.MemberService;

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
	
	@Autowired
	private MemberService mservice;
	
	// 주소록 메인 홈
	@RequestMapping("")
	public String main() { // index 데이터
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

	
	// 주소 불러오기
	@ResponseBody
	@RequestMapping("/select") // 주소록 검색 (전체 / 태그별 / 검색어별)
	public Map<String,Object> select(String key, int value, int currentMenu, String keyword, @RequestParam(value="cpage", required=false) String cpage) {
		int currentPage = (cpage == null || cpage.isEmpty()) ? 1 : Integer.parseInt(cpage);
		session.setAttribute("currentMenu", currentMenu);
		if(keyword != null)
			keyword = "%"+keyword+"%";
		
		String loginID = (String)session.getAttribute("loginID");
		List<String> authority = mservice.getAuthorityCategory(loginID);
		int auth = 0;
		for (int i = 0; i < authority.size(); i++) {
			if (authority.get(i).equals("총괄")||authority.get(i).equals("인사")) {
				auth=1; break;
			}
		}
		
		
		Map<String,Object> addressList = abservice.select((String)session.getAttribute("loginID"), key, value, keyword,auth,
														(currentPage * Constants.RECORD_COUNT_PER_PAGE - (Constants.RECORD_COUNT_PER_PAGE - 1)-1),
														(currentPage * Constants.RECORD_COUNT_PER_PAGE));
		
		Map<String,Object> resp = new HashMap<>();
		resp.put("resp", addressList.get("resp"));
		resp.put("deleteTag", addressList.get("deleteTag"));
		resp.put("recordTotalCount", addressList.get("count"));
		resp.put("recordCountPerPage", Constants.RECORD_COUNT_PER_PAGE);
		resp.put("naviCountPerPage", Constants.NAVI_COUNT_PER_PAGE);
		resp.put("lastPageNum", currentPage);
		return resp;
	}

	
	// 주소 상세 보기
	@ResponseBody
	@RequestMapping("/selectById") // 주소록 상세 정보 불러오기 (상세보기 모달창 내용)
	public Map<String,Object> selectById(int id) {
		return abservice.selectById(id, (String)session.getAttribute("loginID"));
	}
	
	// 주소 휴지통으로 이동
	@ResponseBody
	@RequestMapping("/trash") 
	public int trash(@RequestParam(value="id", required=false, defaultValue = "-1")int id, @RequestParam(value="ids[]", required=false)List<Integer> ids) {
		if(ids == null || ids.isEmpty()) {
			return abservice.trash(id,1);
		}
		return abservice.trash(ids,1);
	}
	
	// 주소 휴지통에서 기존 태그로 복원
	@ResponseBody
	@RequestMapping("/restore") 
	public int restore(@RequestParam(value="id", required=false, defaultValue = "-1")int id, @RequestParam(value="ids[]", required=false)List<Integer> ids) {
		if(ids == null || ids.isEmpty()) {
			return abservice.trash(id,0);
		}
		return abservice.trash(ids,0);
	}
	
	// 주소 영구 삭제
	@ResponseBody
	@RequestMapping("/delete")
	public int delete(@RequestParam(value="id", required=false, defaultValue = "-1")int id, @RequestParam(value="ids[]", required=false)List<Integer> ids) {
		if(ids == null || ids.isEmpty()) {
			return abservice.delete(id);
		}
		return abservice.delete(ids);
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
	
	// 주소 복사 ( 개인 <-> 공유 )
	@ResponseBody
	@RequestMapping("/copyAddress") // 주소 변경
	public int copyAddress(int is_share, @RequestParam(value="ids[]")List<Integer> ids) {
		return abservice.copyAddress(is_share, ids, (String)session.getAttribute("loginID"));
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
	
	
	// 휴지통에서 30일 경과한 캘린더 삭제
	public void autoDeleteInTrash() {
		abservice.autoDeleteInTrash();
	}
	
	
	// 휴지통 즉시 비우기
	@ResponseBody
	@RequestMapping("/immediatelyEmpty")
	public int immediatelyEmpty() {
		return abservice.immediatelyEmpty((String)session.getAttribute("loginID"));
	}
}
