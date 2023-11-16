package com.clovers.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clovers.dto.MemberDTO;

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
	
	public Map<String,String> selectUserInfo(String loginID){
		System.out.println(db.selectOne("member.selectUserInfo", loginID).toString());
		return db.selectOne("member.selectUserInfo", loginID);
	}
	
	public String selectNameById(String id) {
		return db.selectOne("member.selectNameById",id);
	}
	
	public List<MemberDTO> selectMembersByDeptTaskId(String dept_task_id){
		return db.selectList("member.selectMembersByDeptTaskID",dept_task_id);
	}
	
	public String getAuthorityCategory(String id) {
		return db.selectOne("member.getAuthorityCategory", id);
	}

}
