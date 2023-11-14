package com.clovers.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.clovers.dto.MemberDTO;
import com.clovers.dto.WorkConditionDTO;


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
	public Map<String, Object> selectEmployeeWorkRule(String id) {
	    return db.selectOne("HumanResources.selectEmployeeWorkRule", id);
	}
	
	// 출근전인지 확인
	public Map<String, Object> selectAttendStatus(String id) {
		return db.selectOne("HumanResources.selectAttendStatus",id);
	}
	
	// 출근 기록 남기기
	public int insertAttendingWork(Map<String, Object> user) {
		System.out.println(user.toString()+"test");
		return db.insert("HumanResources.insertAttendingWork", user);
	}
	
	// 퇴근 기록 남기기
	public int updateLeavingWork(String id) {
		return db.update("HumanResources.updateLeavingWork", id);
	}
	
	// 업무 상태 기록 남기기
	public int insertWorkCondition(Map<String, Object> data) {
		db.insert("HumanResources.insertWorkCondition", data);
		return Integer.parseInt(data.get("id").toString());
	}
	
	// 업무 상태 종료시간 업데이트
	public int updateWorkCondtionEndTime(int attend_status_id) {
		return db.update("HumanResources.updateWorkCondtionEndTime", attend_status_id);
	}
	
	// 입력한 업무 상태 확인
	public WorkConditionDTO selectWorkCondition(int id){
		return db.selectOne("HumanResources.selectWorkCondition", id);
	}
	
	// 업무 상태 리스트 불러오기
		public List<WorkConditionDTO> selectWorkConditionsList(String id){
			return db.selectList("HumanResources.selectWorkConditionsList", id);
		}
}
