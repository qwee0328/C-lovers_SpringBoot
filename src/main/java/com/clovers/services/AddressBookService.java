package com.clovers.services;

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
	
	public List<AddressBookTagDTO> tagSelect(String emp_id){
		return dao.tagSelect(emp_id);
	}
	
	public List<AddressBookTagDTO> tagSelectByIsShare(String emp_id, int is_share){
		Map<String,Object> param = new HashMap<>();
		param.put("emp_id", emp_id);
		param.put("is_share", is_share);
		return dao.tagSelectByIsShare(param);
	}
	
	public List<AddressBookDTO> select(String emp_id, String key, int value) {
		Map<String,Object> param = new HashMap<>();
		param.put("emp_id", emp_id);
		param.put("key", key);
		param.put("value", value);
		return dao.select(param);
	}
}
