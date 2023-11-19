package com.clovers.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clovers.services.ElectronicSignatureService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/electronicsignature")
public class ElectronicSignatureController {
	// 전자결재 컨트롤러

	@Autowired
	private HttpSession session;

	@Autowired
	private ElectronicSignatureService esservices;
	
	// 메인 화면으로 이동
	@RequestMapping("")
	public String main() {
		String title = "전자결재";
		String currentMenu = "대기";
		
		session.setAttribute("title", title);
		session.setAttribute("currentMenu", currentMenu);
		
		return "/electronicsignature/progressWait";
	}
	
	// 진행 중인 문서 전체로 이동
	@RequestMapping("/progressTotal")
	public String progressTotal() {
		String currentMenu = "진행전체";
		
		session.setAttribute("currentMenu", currentMenu);
		return "/electronicsignature/progressTotal";
	}
	
	// 진행 중인 문서 전체 리스트 출력
	@ResponseBody 
	@RequestMapping("/progressTotalList")
	public List<Map<String, Object>> progressTotalList() {
		String loginID = (String) session.getAttribute("loginID");
		
		boolean isTurn = esservices.isApproverTurn(loginID);
		
		List<Map<String, Object>> list = esservices.progressTotalList(loginID);
		
	    for (Map<String, Object> item : list) {
	        // approver_id와 loginID가 같으면 "결재"
	        if (item.get("approver_id").equals(loginID)) {
	            item.put("division", "결재");
	        }
	        // drafter_id와 loginID가 같으면 "기안"
	        if (item.get("drafter_id").equals(loginID)) {
	            item.put("division", "기안");
	        }
	    }

		return list;
	}
	
	// 대기로 이동
	@RequestMapping("/progressWait")
	public String progressWait() {
		String currentMenu = "대기";
		
		session.setAttribute("currentMenu", currentMenu);
		return "/electronicsignature/progressWait";
	}
	
	// 진행 중인 문서 대기 리스트 출력
	@ResponseBody
	@RequestMapping("/progressWaitList")
	public List<Map<String, Object>> progressWaitList() {
		String loginID = (String) session.getAttribute("loginID");
		List<Map<String, Object>> list = esservices.proggressWaitLlist(loginID);
		
		for (Map<String, Object> item : list) {
	        // approver_id와 loginID가 같으면 "결재"
	        if (item.get("approver_id").equals(loginID)) {
	            item.put("division", "결재");
	        }
	        // drafter_id와 loginID가 같으면 "기안"
	        if (item.get("drafter_id").equals(loginID)) {
	            item.put("division", "기안");
	        }
	    }
		
		return list;
	}
	
	// 확인으로 이동
	@RequestMapping("/progressCheck")
	public String progressCheck() {
		String currentMenu = "확인";
		
		session.setAttribute("currentMenu", currentMenu);
		return "/electronicsignature/progressCheck";
	}
	
	// 진행 중인 문서 확인 리스트 출력
	@ResponseBody
	@RequestMapping("/progressCheckList")
	public List<Map<String, Object>> progressCheckList() {
		String loginID = (String) session.getAttribute("loginID");
		List<Map<String, Object>> list = esservices.progressCheckList(loginID);
		
		for (Map<String, Object> item : list) {
	        // approver_id와 loginID가 같으면 "결재"
	        if (item.get("approver_id").equals(loginID)) {
	            item.put("division", "결재");
	        }
	        // drafter_id와 loginID가 같으면 "기안"
	        if (item.get("drafter_id").equals(loginID)) {
	            item.put("division", "기안");
	        }
	    }
		
		return list;
	}
	
	// 진행으로 이동
	@RequestMapping("/progress")
	public String progress() {
		String currentMenu = "진행";
		
		session.setAttribute("currentMenu", currentMenu);
		return "/electronicsignature/progress";
	}
	
	// 문서함 전체로 이동
		@RequestMapping("/documentTotal")
		public String documentTotal() {
			String currentMenu = "문서전체";
			
			session.setAttribute("currentMenu", currentMenu);
			return "/electronicsignature/documentTotal";
		}

	// 멤버의 전자 결재를 위한 전자선 정렬 -> job_id의 순서대로 정렬
	@ResponseBody
	@RequestMapping("/selectEmpJobLevel")
	public List<Map<String, Object>> selectEmpJobLevel(
			@RequestParam("processUserID[]") List<String> processUserIDList) {
		// List<String> userList = requestBody.get("userIdList");
		System.out.println(processUserIDList);
		// return null;
		return esservices.selectEmpJobLevel(processUserIDList);
	}

	// 휴가 문서 생성
	@ResponseBody
	@RequestMapping("/insertVacation")
	public int insertVacation(@RequestParam("processEmployeeIDArray[]") List<String> processEmployeeIDArray,
			@RequestParam("vacationDateList[]") List<String> vacationDateList,
			@RequestParam("vacationTypeList[]") List<String> vacationTypeList,
			@RequestParam("reson") String reson) throws Exception {
		String emp_id = (String) session.getAttribute("loginID");
		return esservices.insertVacation(emp_id, processEmployeeIDArray, vacationDateList, vacationTypeList, reson);
	}
}
