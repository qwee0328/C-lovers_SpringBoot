package com.clovers.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clovers.dao.AddressBookDAO;
import com.clovers.dto.AddressBookDTO;
import com.clovers.dto.AddressBookTagDTO;

@Service
public class AddressBookService {
	// 주소록 서비스 레이어
	@Autowired
	private AddressBookDAO dao;
	
	
	// 주소 관련
	
	// 병행 제어 필요
	// 태그 선택 후 주소 추가
	public int insert(AddressBookDTO dto, List<Integer> selectedTagArray) {
		Map<String, Object> param = new HashMap<>();
		param.put("selectedTagArray", selectedTagArray);	
		dao.insert(dto);
		param.put("address_book_id",dto.getId());
		return dao.tagListInsert(param);
	}
	
	// 태그 없이 주소 추가
	public int insert(AddressBookDTO dto) {
		return dao.insert(dto);
	}
	
	// 주소 출력
	public List<Map<String,Object>> select(String emp_id, String key, int value, String keyword, int auth) {
		Map<String,Object> param = new HashMap<>();
		param.put("emp_id", emp_id);
		param.put("key", key);
		param.put("value", value);
		param.put("keyword", keyword);
		param.put("auth", auth);
		
		System.out.println(auth);
					
		if(!key.equals("is_share") && dao.existTag(value)==0) { // 태그가 삭제된 경우
			Map<String,Object> result = new HashMap<>();
			result.put("deleteTag", true);
			List<Map<String,Object>> deleteTag = new ArrayList<>();
			deleteTag.add(result);
			return deleteTag;
		}
			

		return dao.select(param);
	}
	
	// 주소 상세보기 출력
	public Map<String,Object> selectById(int id, String emp_id) {
		Map<String,Object> param = new HashMap<>();
		param.put("id",id);
		param.put("emp_id", emp_id);
		return dao.selectById(param);
	}
	
	// 주소 휴지통으로 이동
	public int trash(int id, int trash) {
		Map<String,Object> param = new HashMap<>();
		param.put("id", id);
		param.put("trash", trash);
		return dao.trash(param);
	}
	
	public int trash(List<Integer> ids, int trash) {
		Map<String,Object> param = new HashMap<>();
		param.put("ids", ids);
		param.put("trash", trash);
		return dao.trash(param);
	}
	
	// 주소 완전 삭제
	public int delete(int id) {
		Map<String,Object> param = new HashMap<>();
		param.put("id", id);
		return dao.delete(param);
	}
	public int delete(List<Integer> ids) {
		Map<String,Object> param = new HashMap<>();
		param.put("ids", ids);
		return dao.delete(param);
	}
		
	
	// 주소 복사 (공유 <-> 개인)
	public int copyAddress(int is_share, List<Integer> ids, String id) {
		Map<String,Object> param = new HashMap<>();
		param.put("is_share", is_share);
		param.put("ids", ids);
		param.put("id", id);
		return dao.copyAddress(param);
	}
	
	
	// 병행 제어
	// 주소 변경 (태그 없음)
	public int update(AddressBookDTO dto) {
		dao.tagListDelete(dto.getId()); // 기존 태그 내용 삭제
		return dao.update(dto);
	}
	
	// 주소 변경 (태그 있음)
	public int update(AddressBookDTO dto, List<Integer> selectedTagArray) {	
		dao.tagListDelete(dto.getId()); // 기존 태그 내용 삭제
		
		// 새로운 태그 내용 추가
		Map<String, Object> param = new HashMap<>();
		param.put("selectedTagArray", selectedTagArray);	
		param.put("address_book_id",dto.getId());
		dao.tagListInsert(param);
		return dao.update(dto);
	}
	
	
	
	
	// 태그 관련
	
	// 태그 추가
	public int tagInsert(AddressBookTagDTO dto) {
		return dao.tagInsert(dto);
	}
	
	// 태그 출력
	public List<AddressBookTagDTO> tagSelect(String emp_id){
		return dao.tagSelect(emp_id);
	}
	
	// 주소 추가 모달창에 선택 가능한 태그 출력
	public List<AddressBookTagDTO> tagSelectByIsShare(String emp_id, int is_share){
		Map<String,Object> param = new HashMap<>();
		param.put("emp_id", emp_id);
		param.put("is_share", is_share);
		return dao.tagSelectByIsShare(param);
	}
	
	
	// 태그 삭제
	public int tagDelete(int id) {
		return dao.tagDelete(id);
	}
	
	
	

	
	
	
	// 즐겨찾기
	// 이미 즐겨찾기 된 내용인지 판단 후 즐겨찾기 추가
	public int favoriteInsertIfNotExist(int address_book_id, String emp_id) {
		Map<String,Object> param = new HashMap<>();
		param.put("address_book_id", address_book_id);
		param.put("emp_id", emp_id);
		
		if(dao.isFavorite(param)==1) // 이미 존재한다면
			return 0;
		return dao.favoriteInsert(param); // 존재하지 않으면
	}

	// 즐겨찾기 삭제
	public int favoriteDelete(int address_book_id, String emp_id) {
		Map<String,Object> param = new HashMap<>();
		param.put("address_book_id", address_book_id);
		param.put("emp_id", emp_id);
		
		return dao.favoriteDelete(param);
	}
	
	// 휴지통에서 30일 경과한 주소록 삭제
	public void autoDeleteInTrash() {
		dao.autoDeleteInTrash();
	}
	
	
	// 휴지통 즉시 비우기
	public int immediatelyEmpty(String emp_id) {
		return dao.immediatelyEmpty(emp_id);
	}
}
