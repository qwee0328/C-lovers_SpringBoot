package com.clovers.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clovers.dto.MemberDTO;

@Repository
public class HumanResourcesDAO {
	// 인사 DAO
	
	@Autowired
	private SqlSession db;
	
//	public String selectByIdGetName(String id) {
//		return db.selectOne("HumanResources.selectByIdGetName",id);
//	}
	
	public MemberDTO selectById(String id) {
		return db.selectOne("HumanResources.selectById",id);
	}
	
	public String reChangePw(String id) {
		return db.selectOne("HumanResources.getPw",id);
	}
	
	public int update(Map<String,String> param) {
		return db.update("HumanResources.update",param);
	}
	// 사용자 근무 규칙 정보 불러오기
	public Map<String, String> selectEmployeeWorkRule(String id) {
		return db.selectMap("HumanResources.selectEmployeeWorkRule", id);
	}
}
