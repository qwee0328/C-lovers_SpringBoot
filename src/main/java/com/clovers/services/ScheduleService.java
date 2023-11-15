package com.clovers.services;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clovers.dao.ScheduleDAO;
import com.clovers.dto.ScheduleDTO;
import com.clovers.dto.ScheduleRecurringDTO;

@Service
public class ScheduleService {
	// 일정 서비스 레이어
	
	@Autowired
	private ScheduleDAO dao;
	
	public ScheduleDTO insert(ScheduleDTO dto) {
		dao.insert(dto);
		return dto;
	}
	
	public int insertRuccuring(ScheduleRecurringDTO	dto) {
		return dao.insertRuccuring(dto);
	}
	
	public int delete(int id) {
		return dao.delete(id);
	}
	
	public List<HashMap<String,Object>> selectAll(){
		return dao.selectAll();
	}
	
	public HashMap<String,Object> selectById(String id){
		return dao.selectById(id);
	}
	
	public List<HashMap<String, Object>> calendarByEmpId(String emp_id){
		return dao.calendarByEmpId(emp_id);
	}
	
	public int scheduleUpdate(ScheduleDTO dto) {
		return dao.scheduleUpdate(dto);
	}
}
