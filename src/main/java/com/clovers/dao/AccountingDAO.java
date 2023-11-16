package com.clovers.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.clovers.dto.AccountingDTO;

@Repository
public class AccountingDAO {
	// 회계지원 DAO
	
	@Autowired
	private SqlSession db;
	
	public List<AccountingDTO> selectAccountAll(){
		return db.selectList("Accounting.selectAll");
	}
	public List<AccountingDTO> searchBy(String keyword){
		String keyword2 = "%"+keyword+"%";
		return db.selectList("Accounting.searchBy",keyword2);
	}
	public int insert(AccountingDTO dto) {
		return db.insert("Accounting.insert",dto);
	}
	public int deleteAccount(String id){
		return db.delete("Accounting.deleteById",id);
	}
	public int update(AccountingDTO dto){
		return db.update("Accounting.update",dto);
	}
	/////
	public List<AccountingDTO> selectCardAll(){
		return db.selectList("Accounting.selectCardAll");
	}
	public List<AccountingDTO> searchCard(String keyword){
		String keyword2 = "%"+keyword+"%";
		return db.selectList("Accounting.searchCard",keyword2);
	}
	public int insertCard( AccountingDTO dto){
		return db.insert("Accounting.insertCard",dto);
	}
	public int deleteCard(String id){
		return db.delete("Accounting.deleteCard",id);
	}
	public int updateCard(AccountingDTO dto){
		return db.update("Accounting.updateCard",dto);
	}
}
