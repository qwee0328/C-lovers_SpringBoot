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
	
	// 주소 관련
	public int insert(AddressBookDTO dto) { 
		return db.insert("AddressBook.insert",dto); 
	}
	public List<Map<String,Object>> select(Map<String,Object> param) {
		return db.selectList("AddressBook.select",param);
	}
	
	
	// 주소록 페이지 네이션
	public int getCount(Map<String,Object> param) {
		return db.selectOne("AddressBook.getCount",param);
	}
	
	
	public Map<String,Object> selectById(Map<String, Object> param) {
		return db.selectOne("AddressBook.selectById",param);
	}
	
	public int delete(Map<String,Object> param) {
		return db.delete("AddressBook.delete",param);
	}
	
	public int update(AddressBookDTO dto) {
		return db.update("AddressBook.update",dto);
	}
	
	public int trash(Map<String,Object> param) {
		return db.update("AddressBook.trash", param);
	}
	
	// 주소 복사 (공유 <-> 개인)
	public int copyAddress(Map<String,Object> param) {
		return db.insert("AddressBook.copyAddress",param);
	}
	
	// 태그 관련
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
	
	public int existTag(int id) {
		return db.selectOne("AddressBook.existTag",id);
	}
	
	public int tagDelete(int id) {
		return db.delete("AddressBook.tagDelete",id);
	}
	
	// 태그 목록 관련
	public int tagListInsert(Map<String, Object> param) {
		return db.insert("AddressBook.tagListInsert",param);
	}
	public int tagListDelete(int address_book_id) {
		return db.delete("AddressBook.tagListDelete",address_book_id);
	}
	
	
	// 즐겨찾기 관련
	public int favoriteInsert(Map<String, Object> param) {
		return db.insert("AddressBook.favoriteInsert",param);
	}
	
	public int isFavorite(Map<String,Object> param) {
		return db.selectOne("AddressBook.isFavorite", param);
	}
	
	public int favoriteDelete(Map<String,Object> param) {
		return db.delete("AddressBook.favoriteDelete",param);
	}
	
	// 휴지통에서 30일 경과한 데이터 삭제
	public void autoDeleteInTrash() {
		db.delete("AddressBook.autoDeleteInTrash");
	}

	// 휴지통 즉시 비우기
	public int immediatelyEmpty(String emp_id) {
		return db.delete("AddressBook.immediatelyEmpty",emp_id);
	}
	

	// numberType 가져오기
	public String isNumberType(String id) {
		return db.selectOne("AddressBook.isNumberType",id);
	}
	
	// humanResource ~ 주소록에 사내 이메일과 휴대폰 업데이트
	public int updateCompanyEmailPhone(Map<String,String> param) {
		return db.update("AddressBook.updateCompanyEmailPhone", param);
	}
	
	// 주소록에 있는 오피스 네임 업데이트
	public void updateOfficeName(String name) {
		db.update("AddressBook.updateOfficeName",name);
	}

}
