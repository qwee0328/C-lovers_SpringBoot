package com.clovers.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ElectronicSignatureDAO {
	// 전자결재 DAO
	@Autowired
	private SqlSession db;

	// 멤버의 전자 결재를 위한 전자선 정렬 -> job_id의 순서대로 정렬
	public List<Map<String, Object>> selectEmpJobLevel(List<String> processUserIDList) {
		return db.selectList("ElectronicSignature.selectEmpJobLevel", processUserIDList);
	}

	// 휴가 문서 생성
	public int insertVacation(Map<String, Object> emp) {
		db.insert("ElectronicSignature.insertVacation", emp);
		Integer generatedKey = (Integer) emp.get("id");
		System.out.println("자동 생성된 키: " + generatedKey);
		return generatedKey;
	}
}
