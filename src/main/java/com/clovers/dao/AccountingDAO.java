package com.clovers.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clovers.dto.AccountingDTO;

@Repository
public class AccountingDAO {
	// 회계지원 DAO
	
	@Autowired
	private SqlSession db;
	
	public List<AccountingDTO> selectAccountAll(){
		return db.selectList("Accounting.selectAll");
	}
}
