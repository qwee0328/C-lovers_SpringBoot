package com.clovers.dao;

import java.math.BigInteger;
import java.util.HashMap;
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
		return db.insert("AddressBook.tagInsert",dto);
	}
	
	public int favoriteInsert(Map<String, Object> param) {
		return db.insert("AddressBook.favoriteInsert",param);
	}
	
	public List<AddressBookTagDTO> tagSelect(String emp_id){
		return db.selectList("AddressBook.tagSelect",emp_id);
	}
	
	public List<AddressBookTagDTO> tagSelectByIsShare(Map<String,Object> param){
		return db.selectList("AddressBook.tagSelectByIsShare",param);
	}
	
//	public List<AddressBookDTO> select(Map<String,Object> param) {
//		return db.selectList("AddressBook.select",param);
//	}
	
	public List<Map<String,Object>> select(Map<String,Object> param) {
		return db.selectList("AddressBook.select",param);
	}
	
	public Map<String,Object> selectById(int id) {
		return db.selectOne("AddressBook.selectById",id);
	}
	
	public int isFavorite(Map<String,Object> param) {
		return db.selectOne("AddressBook.isFavorite", param);
	}
	
	public int delete(int id) {
		return db.delete("AddressBook.delete",id);
	}
	
	public int tagDelete(int id) {
		return db.delete("AddressBook.tagDelete",id);
	}
	
	public int favoriteDelete(Map<String,Object> param) {
		return db.delete("AddressBook.favoriteDelete",param);
	}

	public int update(AddressBookDTO dto) {
		return db.update("AddressBook.update",dto);
	}
}
