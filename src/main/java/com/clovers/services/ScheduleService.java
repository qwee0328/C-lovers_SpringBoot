package com.clovers.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public int insertReccuring(ScheduleRecurringDTO	dto) {
		return dao.insertReccuring(dto);
	}
	
	public int delete(int id) {
		return dao.delete(id);
	}
	
	// 반복 일정 정보가 테이블에서 삭제될 때, 참조중인 내용들 삭제
	public int deleteRecurring(int id) {
		dao.deleteRecurring(id);
		return dao.deleteRecurringId(id);
	}
	
	
	public List<HashMap<String,Object>> selectAll(String emp_id){
		return dao.selectAll(emp_id);
	}
	
	public HashMap<String,Object> selectById(int id){
		return dao.selectById(id);
	}
	
	public int selectRecurringIdById(int id) {
		return dao.selectRecurringIdById(id);
	}
	
	public List<HashMap<String, Object>> selectCalendarByEmpId(String emp_id){
		return dao.selectCalendarByEmpId(emp_id);
	}
	
	public int scheduleUpdate(ScheduleDTO dto) {
		return dao.scheduleUpdate(dto);
	}
	
	public int recurringScheduleUpdate(ScheduleRecurringDTO dto) {
		return dao.recurringScheduleUpdate(dto);
	}
	
	// 병행 제어 필요
	// 캘린더 및 캘린더 권한 추가 
	public int calendarInsert(String name, String color, int is_share, List<String> empIds) {
		Map<String,Object> param = new HashMap<>();
		param.put("name", name);
		param.put("color",color);
		param.put("is_share",is_share);
		param.put("id", null);
		
		Map<String,Object> param2 = new HashMap<>();
		param2.put("calendar_id",dao.calendarInsert(param));
		param2.put("empIds", empIds);
		return dao.calendarAuthInsert(param2);
	}
	
	// 선택한 캘린더에 포함된 일정 불러오기
	public List<HashMap<String,Object>> selectByCalendarIdSchedule(List<Integer> calIds){
		return dao.selectByCalendarIdSchedule(calIds);
	}
	
	
	// 캘린더 정보 불러오기 (수정용)
	public Map<String,Object> selectCaledarInfoByCalendarId(int id, String emp_id){
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("emp_id", emp_id);
		return dao.selectCaledarInfoByCalendarId(param);
	}
	
	// 캘린더 정보 수정하기
	public int calendarUpdate(int id, String name, String color, List<String> empIds) {
		Map<String,Object> param = new HashMap<>();
		param.put("id", id);
		param.put("name", name);
		param.put("color",color);
		dao.updateCalendar(param);
		dao.deleteCalendarAuth(id);
		
		Map<String,Object> param2 = new HashMap<>();
		param2.put("calendar_id",id);
		param2.put("empIds", empIds);
		return dao.calendarAuthInsert(param2);
	}
	
	// 캘린더 휴지통 or 복원
	public int trashCalendar(int id, int trash) {
		Map<String,Integer> param = new HashMap<>();
		param.put("id", id);
		param.put("trash", trash);
		return dao.trashCalendar(param);
	}
	
	// 캘린더 영구삭제
	public int deleteCalendar(int id) {
		return dao.deleteCalendar(id);
	} 
	
	// 휴지통에서 30일 경과한 캘린더 삭제
	public void autoDeleteInTrash() {
		dao.autoDeleteInTrash();
	}
}