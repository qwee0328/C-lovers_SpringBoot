package com.clovers.services;

import java.util.List;

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
	
	public int insert(AddressBookDTO dto) {
		return dao.insert(dto);
	}
	
	public int tagInsert(AddressBookTagDTO dto) {
		return dao.tagInsert(dto);
	}
	
	public List<AddressBookTagDTO> tagSelect(String emp_id){
		return dao.tagSelect(emp_id);
	}
}
