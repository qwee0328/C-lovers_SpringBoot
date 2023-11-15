package com.clovers.dao;

import java.util.HashMap;
import java.util.List;

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
	
	public ScheduleDTO insert(ScheduleDTO dto) {
		db.insert("Schedule.insert",dto);
		return dto;
	}
	
	public int insertRuccuring(ScheduleRecurringDTO	dto) {
		db.insert("Schedule.insertRuccuring",dto);
		return dto.getId();
	}
	
	public int delete(int id) {
		return db.delete("Schedule.delete",id);
	}
	
	
	public List<HashMap<String,Object>> selectAll(){
		return db.selectList("Schedule.selectAll");
	}
	
	public HashMap<String,Object> selectById(String id){
		return db.selectOne("Schedule.selectById",id);
	}
	
	public List<HashMap<String, Object>> calendarByEmpId(String emp_id){
		return db.selectList("Schedule.calendarByEmpId",emp_id);
	}
	
	public int scheduleUpdate(ScheduleDTO dto) {
		return db.update("Schedule.scheduleUpdate", dto);
	}
}
