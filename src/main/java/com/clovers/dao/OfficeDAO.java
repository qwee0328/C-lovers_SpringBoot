package com.clovers.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clovers.dto.DeptTaskDTO;
import com.clovers.dto.JobDTO;
import com.clovers.dto.MemberDTO;

@Repository
public class OfficeDAO {
	// 오피스 관리 DAO
	@Autowired
	private SqlSession db;
	
	public List<DeptTaskDTO> selectDeptTaskAll(){
		return db.selectList("Office.selectDeptTaskAll");
	}
	
	public List<JobDTO> selectPositionAll(){
		return db.selectList("Office.selectPositionAll");
	}
	
	public int selectEmpCount() {
		return db.selectOne("Office.selectEmpCount");
	}
	
	public int insertUser(MemberDTO dto) {
		return db.insert("Office.insertUser", dto);
	}
}
