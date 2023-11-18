package com.clovers.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clovers.dao.AddressBookDAO;
import com.clovers.dto.AddressBookDTO;
import com.clovers.dto.AddressBookTagDTO;

@Service
public class AddressBookService {
	// 주소록 서비스 레이어
	@Autowired
	private AddressBookDAO dao;
	
	// 병행 제어 필요
	public int insert(AddressBookDTO dto, List<Integer> selectedTagArray) {
		Map<String, Object> param = new HashMap<>();
		param.put("selectedTagArray", selectedTagArray);	
		dao.insert(dto);
		param.put("address_book_id",dto.getId());
		return dao.tagListInsert(param);
	}
	
	public int insert(AddressBookDTO dto) {
		return dao.insert(dto);
	}
	
	public int tagInsert(AddressBookTagDTO dto) {
		return dao.tagInsert(dto);
	}
	
	public int favoriteInsertIfNotExist(int address_book_id, String emp_id) {
		Map<String,Object> param = new HashMap<>();
		param.put("address_book_id", address_book_id);
		param.put("emp_id", emp_id);
		
		if(dao.isFavorite(param)==1) // 이미 존재한다면
			return 0;
		return dao.favoriteInsert(param); // 존재하지 않으면
	}
	
	public List<AddressBookTagDTO> tagSelect(String emp_id){
		return dao.tagSelect(emp_id);
	}
	
	public List<AddressBookTagDTO> tagSelectByIsShare(String emp_id, int is_share){
		Map<String,Object> param = new HashMap<>();
		param.put("emp_id", emp_id);
		param.put("is_share", is_share);
		return dao.tagSelectByIsShare(param);
	}
	
	public List<Map<String,Object>> select(String emp_id, String key, int value, String keyword) {
		Map<String,Object> param = new HashMap<>();
		param.put("emp_id", emp_id);
		param.put("key", key);
		param.put("value", value);
		param.put("keyword", keyword);
		
					
		if(!key.equals("is_share") && dao.existTag(value)==0) { // 태그가 삭제된 경우
			Map<String,Object> result = new HashMap<>();
			result.put("deleteTag", true);
			List<Map<String,Object>> deleteTag = new ArrayList<>();
			deleteTag.add(result);
			return deleteTag;
		}
			

		return dao.select(param);
	}
	
	public Map<String,Object> selectById(int id) {
		return dao.selectById(id);
	}
	
	public int delete(int id) {
		return dao.delete(id);
	}
	
	public int tagDelete(int id) {
		return dao.tagDelete(id);
	}
	
	
	public int favoriteDelete(int address_book_id, String emp_id) {
		Map<String,Object> param = new HashMap<>();
		param.put("address_book_id", address_book_id);
		param.put("emp_id", emp_id);
		
		return dao.favoriteDelete(param);
	}
	
	public int update(AddressBookDTO dto) {
		return dao.update(dto);
	}
}
