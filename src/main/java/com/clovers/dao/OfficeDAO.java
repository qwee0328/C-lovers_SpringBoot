package com.clovers.dao;

import java.util.List;
import java.util.Map;

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

	// 부서 명 불러오기
	public List<DeptTaskDTO> selectDeptTaskAll() {
		return db.selectList("Office.selectDeptTaskAll");
	}

	// 직급 명 불러오기
	public List<JobDTO> selectPositionAll() {
		return db.selectList("Office.selectPositionAll");
	}

	// 사용자 수 불러오기
	public int selectEmpCount() {
		return db.selectOne("Office.selectEmpCount");
	}

	// 사용자 리스트 불러오기
	public List<Map<String, String>> selectUserList() {
		return db.selectList("Office.selectUserList");
	}

	// 사용자 등록하기
	public int insertUser(MemberDTO dto) {
		return db.insert("Office.insertUser", dto);
	}
	
	// 사용자 삭제하기
	public int deleteUser(List<String> userID) {
		return db.delete("Office.deleteUser", userID);
	}
	
	// 사용자 직위 수정하기
	public int updateUserJob(MemberDTO dto) {
		return db.update("Office.updateUserJob", dto);
	}
	
	// 사용자 소속 조직 수정하기
	public int updateUserDeptTask(MemberDTO dto) {
		return db.update("Office.updateUserDeptTask",dto);
	}
	
	// 사용자 이름, id 검색하기
	public List<Map<String, String>> searchUser(String keyword){
		keyword = "%"+keyword+"%";
		return db.selectList("Office.searchUser", keyword);
	}
}
