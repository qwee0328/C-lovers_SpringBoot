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
	@RequestMapping(value = "/insertRuccuring", method = RequestMethod.POST)
	public ScheduleDTO insertRuccuring(ScheduleRecurringDTO	srdto, ScheduleDTO sdto) {
		sdto.setEmp_id((String) session.getAttribute("loginID"));
		sdto.setRecurring_id(sService.insertRuccuring(srdto));
		return sService.insert(sdto);
	}
	
//	@ResponseBody
//	@RequestMapping(value = "/insertRuccuring", method = RequestMethod.POST)
//	public int insertRuccuring(ScheduleRecurringDTO srdto) {
//		return sService.insertRuccuring(srdto);
//	}
	
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public int delete(int id) {
		return sService.delete(id);
	}
	
	@ResponseBody
	@RequestMapping(value="/selectAll")
	public List<HashMap<String,Object>> selectAll(){
		return sService.selectAll();
	}
	
	
}
