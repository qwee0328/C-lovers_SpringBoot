package com.clovers.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HumanResourcesDAO {
	// 인사 DAO
	@Autowired
	private SqlSession db;

	// 사용자 근무 규칙 정보 불러오기
	public Map<String, String> selectEmployeeWorkRule(String id) {
		return db.selectMap("HumanResources.selectEmployeeWorkRule", id);
	}
}
