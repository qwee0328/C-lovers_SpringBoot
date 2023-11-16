package com.clovers.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clovers.dto.AddressBookDTO;
import com.clovers.dto.AddressBookTagDTO;

@Repository
public class AddressBookDAO {
	// 주소록 DAO
	@Autowired
	private SqlSession db;
	
	public int insert(AddressBookDTO dto) { 
		return db.insert("AddressBook.insert",dto); 
	}
	
	public int tagListInsert(Map<String, Object> param) {
		return db.insert("AddressBook.tagListInsert",param);
	}
	
	public int tagInsert(AddressBookTagDTO dto) {
		db.insert("AddressBook.tagInsert",dto);
		return dto.getId();
	}
	
	public List<AddressBookTagDTO> tagSelect(String emp_id){
		return db.selectList("AddressBook.tagSelect",emp_id);
	}
	
	public List<AddressBookTagDTO> tagSelectByIsShare(Map<String,Object> param){
		return db.selectList("AddressBook.tagSelectByIsShare",param);
	}
	
	public List<AddressBookDTO> select(Map<String,Object> param) {
		return db.selectList("AddressBook.select",param);
	}
	
	
}
