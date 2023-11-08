package com.clovers.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAO {
	
	@Autowired
	private SqlSession db;
	
	public boolean login(Map<String,String> param) {
		
		return db.selectOne("member.login",param);
	}
	
	public int updatePW(Map<String, String> param) {
		return db.update("member.updatePW",param);
	}

	public List<String> isManager(String id) {
		return db.selectList("member.isManager", id);
	}
	
}
