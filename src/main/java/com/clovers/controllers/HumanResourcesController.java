package com.clovers.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clovers.dto.WorkConditionDTO;
import com.clovers.services.HumanResourcesService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/humanResources")
public class HumanResourcesController {
	// 인사 컨트롤러
	@Autowired
	private HttpSession session;
	
	@Autowired 
	private HumanResourcesService hrservice;
	
	@RequestMapping("")
	public String main() {
		String title="인사";
		String currentMenu = "휴가/근무";
		session.setAttribute("title", title);
		session.setAttribute("currentMenu", currentMenu);
		return "humanresources/hrMain";
	}
	
	// 사용자 근무 규칙 정보 불러오기
	@ResponseBody
	@RequestMapping("/selectEmployeeWorkRule")
	public Map<String, Object> selectEmployeeWorkRule() {
		String id = (String)session.getAttribute("loginID");
		Map<String, Object> userWorkRule = hrservice.selectEmployeeWorkRule(id);
		System.out.println(userWorkRule.toString());
		return userWorkRule;
	}
	
	// 출근 전인지 확인
	@ResponseBody
	@RequestMapping("/selectAttendStatus")
	public Map<String, Object> selectAttendStatus() {
		String id = (String)session.getAttribute("loginID");
		Map<String, Object> result = hrservice.selectAttendStatus(id);
		System.out.println(result);
		if(result != null) {
			return result;
		}else {
			return new HashMap<>();
		}
	}
	
	// 출근 기록 남기기
	@ResponseBody
	@RequestMapping("/insertAttendingWork")
	public int insertAttendingWork() {
		String id = (String)session.getAttribute("loginID");
		return hrservice.insertAttendingWork(id);
	}
	
	// 퇴근 기록 남기기
	@ResponseBody
	@RequestMapping("/updateLeavingWork")
	public int updateLeavingWork() {
		String id = (String)session.getAttribute("loginID");
		return hrservice.updateLeavingWork(id);
	}
	
	// 업무 상태 기록 남기기
	@ResponseBody
	@RequestMapping("/insertWorkCondition")
	public WorkConditionDTO insertWorkCondition(@RequestParam("status") String status) {
		String id = (String)session.getAttribute("loginID");
		return hrservice.insertWorkCondition(id, status);
	}
	
	// 업무 상태 리스트 불러오기
	@ResponseBody
	@RequestMapping("/selectWorkConditionsList")
	public List<WorkConditionDTO> selectWorkConditionsList(){
		String id = (String)session.getAttribute("loginID");
		return hrservice.selectWorkConditionsList(id);
	}
}
