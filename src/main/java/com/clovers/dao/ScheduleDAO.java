package com.clovers.dao;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clovers.dto.ScheduleDTO;
import com.clovers.dto.ScheduleRecurringDTO;

@Repository 
public class ScheduleDAO {
	// 일정 DAO
	
	@Autowired
	private SqlSession db;
	
	// 일정 추가
	public ScheduleDTO insert(ScheduleDTO dto) {
		db.insert("Schedule.insert",dto);
		return dto;
	}
	
	// 반복 일정 정보 추가
	public int insertReccuring(ScheduleRecurringDTO	dto) {
		db.insert("Schedule.insertReccuring",dto);
		return dto.getId();
	}
	
	// 일정 삭제
	public int delete(int id) {
		return db.delete("Schedule.delete",id);
	}
	
	// 반복 일정 삭제
	public int deleteRecurring(int id) {
		return db.delete("Schedule.deleteRecurring",id);
	}
	
	// 반복 일정 정보가 테이블에서 삭제될 때, 참조중인 내용들 삭제
	public int deleteRecurringId(int recurring_id) {
		return db.update("Schedule.deleteRecurringId",recurring_id);
	}
	
	// 일정 불러오기
	public List<HashMap<String,Object>> selectAll(String emp_id){
		return db.selectList("Schedule.selectAll",emp_id);
	}
	
	// 일정 검색
	public List<HashMap<String,Object>> selectByKeyword(Map<String,Object> param){
		return db.selectList("Schedule.selectByKeyword",param);
	}
	
	
	// 일정 세부정보 불러오기
	public HashMap<String,Object> selectById(int id){
		return db.selectOne("Schedule.selectById",id);
	}
	
	public int selectRecurringIdById(int id) {
		return db.selectOne("Schedule.selectRecurringIdById",id);
	}
	
	public List<HashMap<String, Object>> selectCalendarByEmpId(Map<String,Object> param){
		return db.selectList("Schedule.selectCalendarByEmpId",param);
	}
	
	public int scheduleUpdate(ScheduleDTO dto) {
		return db.update("Schedule.scheduleUpdate", dto);
	}
	
	public int recurringScheduleUpdate(ScheduleRecurringDTO dto) {
		return db.update("Schedule.recurringScheduleUpdate",dto);
	}
	
	// 캘린더 추가
	public int calendarInsert(Map<String,Object> param) {
		db.insert("Schedule.calendarInsert",param);
		return ((BigInteger) param.get("id")).intValue();
	}
	
	// 캘린더 권한
	public int calendarAuthInsert(Map<String,Object> param) {
		return db.insert("Schedule.calendarAuthInsert",param);
	}
	
	// 선택한 캘린더에 포함된 일정 불러오기
	public List<HashMap<String,Object>> selectByCalendarIdSchedule(List<Integer> list){
		return db.selectList("Schedule.selectByCalendarIdSchedule",list);
	}
	
	
	// 캘린더 정보 불러오기 (수정용)
	public Map<String,Object> selectCaledarInfoByCalendarId(Map<String,Object> param){
		return db.selectOne("Schedule.selectCaledarInfoByCalendarId",param);
	}
	
	
	// 캘린더 정보 수정
	public int updateCalendar(Map<String, Object> param) {
		return db.update("Schedule.updateCalendar",param);
	}
	
	// 캘린더 권한 수정 (삭제 -> 추가는 calendarAuthInsert 이용)
	public int deleteCalendarAuth(int calendar_id) {
		return db.delete("Schedule.deleteCalendarAuth",calendar_id);
	}
	
	// 캘린더 휴지통 or 복원
	public int trashCalendar(Map<String, Integer> param) {
		return db.update("Schedule.trashCalendar",param);
	}
	
	// 캘린더 영구삭제
	public int deleteCalendar(int id) {
		return db.delete("Schedule.deleteCalendar",id);
	} 
	
	// 휴지통에서 30일 경과한 데이터 삭제
	public void autoDeleteInTrash() {
		db.delete("Schedule.autoDeleteInTrash");
	}

}
