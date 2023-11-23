package com.clovers.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clovers.dto.AdminDTO;
import com.clovers.dto.AnnaulRestDTO;
import com.clovers.dto.MemberDTO;

@Repository
public class MemberDAO {

	@Autowired
	private SqlSession db;

	public boolean login(Map<String, String> param) {
		return db.selectOne("member.login", param);
	}

	public int updatePW(Map<String, String> param) {
		return db.update("member.updatePW", param);
	}

	public Map<String, String> selectUserInfo(String loginID) {
		System.out.println(db.selectOne("member.selectUserInfo", loginID).toString());
		return db.selectOne("member.selectUserInfo", loginID);
	}

	public String selectNameById(String id) {
		return db.selectOne("member.selectNameById", id);
	}

	public List<MemberDTO> selectMembersByDeptTaskId(String dept_task_id) {
		return db.selectList("member.selectMembersByDeptTaskID", dept_task_id);
	}

	public List<String> getAuthorityCategory(String id) {
		return db.selectList("member.getAuthorityCategory", id);
	}
	
	// 내 관리자 정보 불러오기
	public List<AdminDTO> getAuthorityInfo(String id){
		return db.selectList("member.getAuthorityInfo",id);
	}

	// 모든 사용자의 id랑 입사일 불러오기
	public List<MemberDTO> selectUserList() {
		return db.selectList("member.selectUserList");
	}

	// 사용자의 연차 기록이 있는지 불러오기
	public AnnaulRestDTO selectAnnaulRestById(String id) {
		AnnaulRestDTO result = db.selectOne("member.selectAnnaulRestById", id);
		return (result != null) ? result : new AnnaulRestDTO();
	}
	
	// 사용자 연차 기록 업데이트
	public void updateAutomaticAnnualRest(AnnaulRestDTO user) {
		db.update("member.updateAutomaticAnnualRest", user);
	}

	// 사용자의 연차 자동 등록
	public void insertAutomaticAnnaulRest(AnnaulRestDTO user) {
		db.insert("member.insertAutomaticAnnaulRest", user);
	}
}
