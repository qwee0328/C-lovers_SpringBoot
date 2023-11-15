package com.clovers.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

		String title = "일정";
		String naviBtn = "일정 추가";
		String naviBtnLocation = "send";
		String[] naviIcon = { "fa-chevron-up", "fa-plus" };
		String[] naviMenu = { "내 캘린더", "공유 캘린더" };
		int naviMenuLength = naviMenu.length;
		String currentMenu = "내 캘린더";

		session.setAttribute("title", title);
		session.setAttribute("naviBtn", naviBtn);
		session.setAttribute("naviBtnLocation", naviBtnLocation);
		session.setAttribute("naviIcon", naviIcon);
		session.setAttribute("naviMenu", naviMenu);
		session.setAttribute("naviMenuLength", naviMenuLength);
		session.setAttribute("currentMenu", currentMenu);

		return "/schedule/scheduleMain";
	}

	@ResponseBody
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ScheduleDTO insert(ScheduleDTO dto) {
		dto.setEmp_id((String) session.getAttribute("loginID"));
		sService.insert(dto);
		return dto;
	}
	@ResponseBody
	@RequestMapping(value = "/insertReccuring", method = RequestMethod.POST)
	public ScheduleDTO insertRuccuring(ScheduleRecurringDTO	srdto, ScheduleDTO sdto) {
		sdto.setEmp_id((String) session.getAttribute("loginID"));
		sdto.setRecurring_id(sService.insertReccuring(srdto));
		return sService.insert(sdto);
	}
	
//	@ResponseBody
//	@RequestMapping(value = "/insertRuccuring", method = RequestMethod.POST)
//	public int insertRuccuring(ScheduleRecurringDTO srdto) {
//		return sService.insertRuccuring(srdto);
//	}
	
	// 병행제어 필요
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public int delete(int id) {
		
		int rid = sService.selectRecurringIdById(id);
		if(rid!=0) {
			sService.deleteReccuring(rid);
			return sService.delete(id);
		}else {
			return sService.delete(id);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/selectAll")
	public List<HashMap<String,Object>> selectAll(){
		return sService.selectAll();
	}
	
	@ResponseBody
	@RequestMapping(value="/selectById")
	public HashMap<String,Object> selectById(int id){
		return sService.selectById(id);
	}
	
	@ResponseBody
	@RequestMapping(value="/calendarByEmpId")
	public List<HashMap<String, Object>> calendarByEmpId(){
		return sService.calendarByEmpId((String)session.getAttribute("loginID"));
	}
	
	@ResponseBody
	@RequestMapping(value="/scheduleUpdate",  method = RequestMethod.POST)
	public void scheduleUpdate(ScheduleDTO dto){
		if(dto.getRecurring_id()!=0)
			sService.deleteReccuring(dto.getRecurring_id());
		sService.scheduleUpdate(dto);
	}

	

	 @ResponseBody
	 
	 @RequestMapping(value="/recurringScheduleUpdate", method =
	 RequestMethod.POST) public void recurringScheduleUpdate(ScheduleRecurringDTO srdto, ScheduleDTO sdto){ 
		 System.out.println(srdto);
		 System.out.println(sdto);
		 
		 if(sdto.getRecurring_id()==0) { // 반복 이벤트가 없었다가 생긴 경우
			 sdto.setRecurring_id(sService.insertReccuring(srdto));
			 sService.scheduleUpdate(sdto);
		 }else { // 기존 반복 이벤트를 수정한 경우
			 srdto.setId(sdto.getRecurring_id());
			 sService.recurringScheduleUpdate(srdto);
			 sService.scheduleUpdate(sdto);
		 }
		
	 }
	 
}
