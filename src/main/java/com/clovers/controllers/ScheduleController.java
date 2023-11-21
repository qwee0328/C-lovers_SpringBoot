package com.clovers.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clovers.dto.ScheduleDTO;
import com.clovers.dto.ScheduleRecurringDTO;
import com.clovers.services.ScheduleService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
	// 일정 컨트롤러
	@Autowired
	private HttpSession session;

	@Autowired
	private ScheduleService sService;

	// 메인 화면 (받은 메일함)
	@RequestMapping("")
	public String main() {
		String title="일정";
		session.setAttribute("title", title);
		if(session.getAttribute("calIds") == null) { // 선택된 캘린더 정보 저장
			session.setAttribute("calIds", new ArrayList<Integer>());
		}

		return "/schedule/scheduleMain";
	}

	
	// 얼정 추가
	@ResponseBody
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ScheduleDTO insert(ScheduleDTO dto) { // 일정 추가
		dto.setEmp_id((String) session.getAttribute("loginID"));
		sService.insert(dto);
		return dto;
	}
	
	// 반복 일정 추가
	@ResponseBody
	@RequestMapping(value = "/insertReccuring", method = RequestMethod.POST)
	public ScheduleDTO insertRuccuring(ScheduleRecurringDTO	srdto, ScheduleDTO sdto) { // 반복일정 정보 추가
		sdto.setEmp_id((String) session.getAttribute("loginID"));
		sdto.setRecurring_id(sService.insertReccuring(srdto));
		return sService.insert(sdto);
	}
	
	// 일정 삭제
	// 병행제어 필요
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public int delete(int id) { // 일정 삭제
		
		int rid = sService.selectRecurringIdById(id);
		if(rid!=0) { // 반복 이벤트이면
			sService.deleteReccuring(rid); // 반복일정 정보 삭제 후
			return sService.delete(id); // 일정 삭제
		}else { // 반복 이벤트가 아니면
			return sService.delete(id); // 일정 삭제
		}
	}
	
	// 일정 불러오기
	@ResponseBody
	@RequestMapping(value="/selectAll")
	public List<HashMap<String,Object>> selectAll(){ // 일정 전체 불러오기 (캘린더에 표시하기 위함)
		return sService.selectAll((String)session.getAttribute("loginID"));
	}
	
	
	// 일정 세부정보 불러오기
	@ResponseBody
	@RequestMapping(value="/selectById")
	public HashMap<String,Object> selectById(int id){ // 특정 일정의 세부정보 불러오기
		return sService.selectById(id);
	}
	

	
	// 일정 변경
	@ResponseBody
	@RequestMapping(value="/scheduleUpdate",  method = RequestMethod.POST)
	public void scheduleUpdate(ScheduleDTO dto){ // 일정 변경 (일반 이벤트)
		if(dto.getRecurring_id()!=0)
			sService.deleteReccuring(dto.getRecurring_id()); // 반복이벤트가 존재했다가 사라진 일정이므로 반복 일정 정보 삭제
		sService.scheduleUpdate(dto); // 일정 변경
	}

	
	// 반복 일정 변경
	 @ResponseBody
	 @RequestMapping(value="/recurringScheduleUpdate", method = RequestMethod.POST) public void recurringScheduleUpdate(ScheduleRecurringDTO srdto, ScheduleDTO sdto){ // 일정 변경 (반복 이벤트)
		 if(sdto.getRecurring_id()==0) { // 반복 이벤트가 없었다가 생긴 경우
			 sdto.setRecurring_id(sService.insertReccuring(srdto)); // 새로운 반복 이벤트 정보 저장
			 sService.scheduleUpdate(sdto); // 일정 변경
		 }else { // 기존 반복 이벤트를 수정한 경우
			 srdto.setId(sdto.getRecurring_id());
			 sService.recurringScheduleUpdate(srdto); // 반복 이벤트 변경
			 sService.scheduleUpdate(sdto); // 일정 변경
		 }
		
	 }
 
	// 캘린더 정보 불러오기
	@ResponseBody
	@RequestMapping(value="/selectCalendarByEmpId")
	public List<HashMap<String, Object>> selectCalendarByEmpId(){ // 로그인한 유저의 캘린더 정보 불러오기
		return sService.selectCalendarByEmpId((String)session.getAttribute("loginID"));
	}
	
	
	// 캘린더 추가
	@ResponseBody
	@RequestMapping(value="/calendarInsert")
	public int calendarInsert(String name, String color, int is_share, @RequestParam(value="empIds[]", required=false)List<String> empIds){
//		if(empIds == null || empIds.isEmpty()) { // 권한이 비어있으면 개인 캘린더
//			empIds = new ArrayList<>();
//			empIds.add((String)session.getAttribute("loginID")); // 로그인한 사용자(등록한 사람)에 대하여 권한 추가
//			return sService.calendarInsert(name, color, empIds);
//		} // 개인 캘린더에 처음부터 자기자신 권한 배열에 추가해서 넘어옴.
		return sService.calendarInsert(name, color, is_share, empIds);
	}
	
	
	
	// 선택한 캘린더에 포함된 일정 불러오기
	@ResponseBody
	@RequestMapping(value="/selectByCalendarIdSchedule")
	public List<HashMap<String,Object>> selectByCalendarIdSchedule(@RequestParam(value="calIds[]", required=false)List<Integer> calIds, @RequestParam(value="init", required=false, defaultValue = "0")int init){
		if(init == 1) {		
			calIds = (List<Integer>) session.getAttribute("calIds");
		}
		if(calIds == null || calIds.isEmpty()) {
			session.setAttribute("calIds", new ArrayList<Integer>()); // 값이 없으면 빈 arrayList 추가
			return null;
		}
		session.setAttribute("calIds", calIds); // 선택한 캘린더 session에 저장
		
		return sService.selectByCalendarIdSchedule(calIds);
		
		
	}
	
	
	// 캘린더 정보 불러오기 (수정용)
	@ResponseBody
	@RequestMapping(value="/selectCaledarInfoByCalendarId")
	public Map<String,Object> selectCaledarInfoByCalendarId(int id){
		Map<String,Object> updateIfo = sService.selectCaledarInfoByCalendarId(id,(String) session.getAttribute("loginID"));
		updateIfo.put("loginID", (String) session.getAttribute("loginID"));
		return updateIfo;
	}
	
	
	
	// 캘린더 정보 수정하기
	@ResponseBody
	@RequestMapping(value="/calendarUpdate")
	public int calendarUpdate(int id, String name, String color, @RequestParam(value="empIds[]", required=false)List<String> empIds) {
		return sService.calendarUpdate(id, name, color, empIds);
	}
	
	
	// 캘린더 휴지통 or 복원
	@ResponseBody
	@RequestMapping(value="/trashCalendar")
	public int trashCalendar(int id, int trash) {
		if(session.getAttribute("calIds") != null) {
			List<Integer> calIds = (List<Integer>) session.getAttribute("calIds");
			calIds.remove(id);
		}
		return sService.trashCalendar(id,trash);
	}
	
	// 캘린더 영구삭제
	@ResponseBody
	@RequestMapping(value="/deleteCalendar")
	public int deleteCalendar(int id) {
		return sService.deleteCalendar(id);
	} 
}
