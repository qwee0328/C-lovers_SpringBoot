package com.clovers.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.clovers.constants.Constants;
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

	// 기안 또는 결재 구분
	public void setDivision(String loginID, List<Map<String, Object>> list) {
		for(Map<String, Object> item : list) {
			boolean isDrafter = esservices.isDrafterByDocumentId((String)item.get("document_id"), loginID);
			boolean isApprover = esservices.isApproverByDocumentId((String)item.get("document_id"), loginID);
			
			if(isDrafter) {
				item.put("division", "기안");
			} else if(isApprover) {
				item.put("division", "결재");
			}
	    }
	}
	
	// 로그인한 사용자의 직급 가져옴
	public List<String> getSecurityGrade(String loginID) {
		int jobRank = esservices.getJobRank(loginID);
		
		List<String> secGrade = new ArrayList<>();
		// 5등급 이상(부장)은 A등급 관람 가능
		if(jobRank <= 5) {
			secGrade.addAll(Arrays.asList("A등급", "B등급", "C등급"));
		// 7등급 이상(과장)은 B등급 관람 가능
		} else if(jobRank <= 7) {
			secGrade.addAll(Arrays.asList("B등급", "C등급"));
		// C등급 관람 가능
		} else if(jobRank > 7) {
			secGrade.add("C등급");
		}
		
		return secGrade;
	}
	
	// 결재자인지, 만약 결재자라면 결재 순서인지
	public boolean isApprovalTurn(String document_id) {
		
		// 로그인한 사용자가 결재자인지
		String loginID = (String) session.getAttribute("loginID");
		boolean isApprover = esservices.isApprover(loginID, document_id);
		
		boolean isApprovalTurn;
		// 만약 결재자라면
		if(isApprover) {
			int previousApprover = esservices.previousApprovalResult(loginID, document_id);
			// 대기자가 존재한다면
			if(previousApprover != 0) {
				isApprovalTurn = false;
			} else {
				isApprovalTurn = true;
			}
		// 결재자가 아니라면
		} else {
			isApprovalTurn = false;
		}
		
		// 반려가 존재하는지
		boolean existRejection = esservices.existRejection(document_id);
		if(existRejection) {
			isApprovalTurn = false;
		}
		
		// 마지막 결재자였는지 확인
		boolean checkAllApprovals = esservices.checkAllApprovals(document_id);
		if(checkAllApprovals) {
			isApprovalTurn = false;
		}
		
		return isApprovalTurn;
	}

	// 메인 화면으로 이동
	@RequestMapping("")
	public String main() {
		String title = "전자결재";
		String currentMenu = "대기";

		session.setAttribute("title", title);
		session.setAttribute("currentMenu", currentMenu);

		return "/electronicsignature/progressWait";
	}

	// 전자 결제 작성 페이지로 이동
	@RequestMapping("/electronicSignatureWrite")
	public String electronicSignatureWrite() {
		String currentMenu = "";

		session.setAttribute("currentMenu", currentMenu);
		return "/electronicsignature/electronicSignatureApplication";
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
	public Map<String, Object> progressTotalList(@RequestParam("cpage") String cpage) {
		String loginID = (String) session.getAttribute("loginID");
		List<String> secGrade = getSecurityGrade(loginID);

		int currentPage = (cpage.isEmpty()) ? 1 : Integer.parseInt(cpage);
		List<Map<String, Object>> list = esservices.progressTotalList(loginID, secGrade, (currentPage * Constants.RECORD_COUNT_PER_PAGE - (Constants.RECORD_COUNT_PER_PAGE-1)) - 1, (currentPage * Constants.RECORD_COUNT_PER_PAGE));
		setDivision(loginID, list);
		
		Map<String, Object> param = new HashMap<>();
		param.put("list", list);
		param.put("recordTotalCount", esservices.progressTotalList(loginID, secGrade, 0, 0).size());
		param.put("recordCountPerPage", Constants.RECORD_COUNT_PER_PAGE);
		param.put("naviCountPerPage", Constants.NAVI_COUNT_PER_PAGE);
		param.put("lastPageNum", currentPage);
		
		return param;
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
	public Map<String, Object> progressWaitList(@RequestParam("cpage") String cpage) {
		String loginID = (String) session.getAttribute("loginID");
		List<String> secGrade = getSecurityGrade(loginID);
		
		int currentPage = (cpage.isEmpty()) ? 1 : Integer.parseInt(cpage);
		List<Map<String, Object>> list = esservices.progressWaitList(loginID, secGrade, (currentPage * Constants.RECORD_COUNT_PER_PAGE - (Constants.RECORD_COUNT_PER_PAGE-1)) - 1, (currentPage * Constants.RECORD_COUNT_PER_PAGE));
		setDivision(loginID, list);
		
		Map<String, Object> param = new HashMap<>();
		param.put("list", list);
		param.put("recordTotalCount", esservices.progressWaitList(loginID, secGrade, 0, 0).size());
		param.put("recordCountPerPage", Constants.RECORD_COUNT_PER_PAGE);
		param.put("naviCountPerPage", Constants.NAVI_COUNT_PER_PAGE);
		param.put("lastPageNum", currentPage);
		return param;
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
	public Map<String, Object> progressCheckList(@RequestParam("cpage") String cpage) {
		String loginID = (String) session.getAttribute("loginID");
		List<String> secGrade = getSecurityGrade(loginID);
		
		int currentPage = (cpage.isEmpty()) ? 1 : Integer.parseInt(cpage);
		List<Map<String, Object>> list = esservices.progressCheckList(loginID, secGrade, (currentPage * Constants.RECORD_COUNT_PER_PAGE - (Constants.RECORD_COUNT_PER_PAGE-1)) - 1, (currentPage * Constants.RECORD_COUNT_PER_PAGE));
		setDivision(loginID, list);
		
		Map<String, Object> param = new HashMap<>();
		param.put("list", list);
		param.put("recordTotalCount", esservices.progressCheckList(loginID, secGrade, 0, 0).size());
		param.put("recordCountPerPage", Constants.RECORD_COUNT_PER_PAGE);
		param.put("naviCountPerPage", Constants.NAVI_COUNT_PER_PAGE);
		param.put("lastPageNum", currentPage);
		return param;
	}

	// 진행으로 이동
	@RequestMapping("/progress")
	public String progress() {
		String currentMenu = "진행";

		session.setAttribute("currentMenu", currentMenu);
		return "/electronicsignature/progress";
	}

	// 진행 중인 문서 진행 리스트 출력
	@ResponseBody
	@RequestMapping("/progressList")
	public Map<String, Object> progressList(@RequestParam("cpage") String cpage) {
		String loginID = (String) session.getAttribute("loginID");
		List<String> secGrade = getSecurityGrade(loginID);
		
		int currentPage = (cpage.isEmpty()) ? 1 : Integer.parseInt(cpage);
		List<Map<String, Object>> list = esservices.progressList(loginID, secGrade, (currentPage * Constants.RECORD_COUNT_PER_PAGE - (Constants.RECORD_COUNT_PER_PAGE-1)) - 1, (currentPage * Constants.RECORD_COUNT_PER_PAGE));
		setDivision(loginID, list);
		
		Map<String, Object> param = new HashMap<>();
		param.put("list", list);
		param.put("recordTotalCount", esservices.progressList(loginID, secGrade, 0, 0).size());
		param.put("recordCountPerPage", Constants.RECORD_COUNT_PER_PAGE);
		param.put("naviCountPerPage", Constants.NAVI_COUNT_PER_PAGE);
		param.put("lastPageNum", currentPage);
		return param;
	}

	// 문서함 전체로 이동
	@RequestMapping("/documentTotal")
	public String documentTotal() {
		String currentMenu = "문서전체";

		session.setAttribute("currentMenu", currentMenu);
		return "/electronicsignature/documentTotal";
	}

	// 문서함 전체 리스트 출력
	@ResponseBody
	@RequestMapping("/documentTotalList")
	public Map<String, Object> documentTotalList(@RequestParam("cpage") String cpage) {
		String loginID = (String) session.getAttribute("loginID");
		
		int currentPage = (cpage.isEmpty()) ? 1 : Integer.parseInt(cpage);
		List<Map<String, Object>> list = esservices.documentTotalList(loginID, (currentPage * Constants.RECORD_COUNT_PER_PAGE - (Constants.RECORD_COUNT_PER_PAGE-1)) - 1, (currentPage * Constants.RECORD_COUNT_PER_PAGE));
		setDivision(loginID, list);
		
		Map<String, Object> param = new HashMap<>();
		param.put("list", list);
		param.put("recordTotalCount", esservices.documentTotalList(loginID, 0, 0).size());
		param.put("recordCountPerPage", Constants.RECORD_COUNT_PER_PAGE);
		param.put("naviCountPerPage", Constants.NAVI_COUNT_PER_PAGE);
		param.put("lastPageNum", currentPage);
		return param;
	}

	// 문서함 기안으로 이동
	@RequestMapping("/documentDrafting")
	public String documentDrafting() {
		String currentMenu = "기안";

		session.setAttribute("currentMenu", currentMenu);
		return "/electronicsignature/documentDrafting";
	}

	// 문서함 기안 리스트 출력
	@ResponseBody
	@RequestMapping("/documentDraftingList")
	public Map<String, Object> documentDraftingList(@RequestParam("cpage") String cpage) {
		String loginID = (String) session.getAttribute("loginID");
		
		int currentPage = (cpage.isEmpty()) ? 1 : Integer.parseInt(cpage);
		List<Map<String, Object>> list = esservices.documentDraftingList(loginID, (currentPage * Constants.RECORD_COUNT_PER_PAGE - (Constants.RECORD_COUNT_PER_PAGE-1)) - 1, (currentPage * Constants.RECORD_COUNT_PER_PAGE));

		Map<String, Object> param = new HashMap<>();
		param.put("list", list);
		param.put("recordTotalCount", esservices.documentDraftingList(loginID, 0, 0).size());
		param.put("recordCountPerPage", Constants.RECORD_COUNT_PER_PAGE);
		param.put("naviCountPerPage", Constants.NAVI_COUNT_PER_PAGE);
		param.put("lastPageNum", currentPage);
		return param;
	}

	// 문서함 결재로 이동
	@RequestMapping("/documentApproval")
	public String documentApproval() {
		String currentMenu = "결재";

		session.setAttribute("currentMenu", currentMenu);
		return "/electronicsignature/documentApproval";
	}

	// 문서함 결재 리스트 출력
	@ResponseBody
	@RequestMapping("/documentApprovalList")
	public Map<String, Object> documentApprovalList(@RequestParam("cpage") String cpage) {
		String loginID = (String) session.getAttribute("loginID");
		
		int currentPage = (cpage.isEmpty()) ? 1 : Integer.parseInt(cpage);
		List<Map<String, Object>> list = esservices.documentApprovalList(loginID, (currentPage * Constants.RECORD_COUNT_PER_PAGE - (Constants.RECORD_COUNT_PER_PAGE-1)) - 1, (currentPage * Constants.RECORD_COUNT_PER_PAGE));

		Map<String, Object> param = new HashMap<>();
		param.put("list", list);
		param.put("recordTotalCount", esservices.documentApprovalList(loginID, 0, 0).size());
		param.put("recordCountPerPage", Constants.RECORD_COUNT_PER_PAGE);
		param.put("naviCountPerPage", Constants.NAVI_COUNT_PER_PAGE);
		param.put("lastPageNum", currentPage);
		return param;
	}

	// 문서함 반려로 이동
	@RequestMapping("/documentRejection")
	public String documentRejection() {
		String currentMenu = "반려";

		session.setAttribute("currentMenu", currentMenu);
		return "/electronicsignature/documentRejection";
	}

	// 문서함 반려 리스트 출력
	@ResponseBody
	@RequestMapping("/documentRejectionList")
	public Map<String, Object> documentRejectionList(@RequestParam("cpage") String cpage) {
		String loginID = (String) session.getAttribute("loginID");
		
		int currentPage = (cpage.isEmpty()) ? 1 : Integer.parseInt(cpage);
		List<Map<String, Object>> list = esservices.documentRejectionList(loginID, (currentPage * Constants.RECORD_COUNT_PER_PAGE - (Constants.RECORD_COUNT_PER_PAGE-1)) - 1, (currentPage * Constants.RECORD_COUNT_PER_PAGE));

		Map<String, Object> param = new HashMap<>();
		param.put("list", list);
		param.put("recordTotalCount", esservices.documentRejectionList(loginID, 0, 0).size());
		param.put("recordCountPerPage", Constants.RECORD_COUNT_PER_PAGE);
		param.put("naviCountPerPage", Constants.NAVI_COUNT_PER_PAGE);
		param.put("lastPageNum", currentPage);
		return param;
	}

	// 임시저장으로 이동
	@RequestMapping("/temporary")
	public String temporary() {
		String currentMenu = "임시저장";

		session.setAttribute("currentMenu", currentMenu);
		return "/electronicsignature/temporary";
	}

	// 임시저장 리스트 출력
	@ResponseBody
	@RequestMapping("/temporaryList")
	public Map<String, Object> temporaryList(@RequestParam("cpage") String cpage) {
		String loginID = (String) session.getAttribute("loginID");
		
		int currentPage = (cpage.isEmpty()) ? 1 : Integer.parseInt(cpage);
		List<Map<String, Object>> list = esservices.temporaryList(loginID, (currentPage * Constants.RECORD_COUNT_PER_PAGE - (Constants.RECORD_COUNT_PER_PAGE-1)) - 1, (currentPage * Constants.RECORD_COUNT_PER_PAGE));

		Map<String, Object> param = new HashMap<>();
		param.put("list", list);
		param.put("recordTotalCount", esservices.temporaryList(loginID, 0, 0).size());
		param.put("recordCountPerPage", Constants.RECORD_COUNT_PER_PAGE);
		param.put("naviCountPerPage", Constants.NAVI_COUNT_PER_PAGE);
		param.put("lastPageNum", currentPage);
		return param;
	}
	
	// 문서 번호에 따른 정보 가져온 후 결재 양식으로 이동
	@RequestMapping("/viewApprovalForm")
	public String approvalForm(@RequestParam("document_id") String document_id, Model model) {
		List<Map<String, Object>> documentInfo = esservices.selectAllByDocumentId(document_id);
		
		// 대표 기안자의 부서 불러오기
		Map<String, String> mainDrafter = esservices.getMainDrafterDept(document_id);
		
		// 기안자들의 이름과 직급, 부서 불러오기
		List<Map<String, Object>> draftersInfo = esservices.getDraftersByDocumentId(document_id);
		
		// 결재자들의 이름과 직급, 결재 결과 가져오기
		List<Map<String, String>> approversInfo = esservices.getApproversByDocumentId(document_id);		
		for(Map<String, String> item : approversInfo) {
			if(item.get("approval").equals("대기")) {
				item.put("approval", "");
			}
		}
		
		model.addAttribute("mainDrafter", mainDrafter);
		model.addAttribute("documentInfo", documentInfo);
		model.addAttribute("draftersInfo", draftersInfo);
		model.addAttribute("approversInfo", approversInfo);
		
		return "/electronicsignature/viewApprovalForm";
	}
	
	// 휴가 신청서 정보 출력
	@ResponseBody
	@RequestMapping("/getVacationInfo")
	public Map<String, Object> getVacationInfo(String document_id) {
		List<Map<String, Object>> list = esservices.getVacationInfo(document_id);
		
		// 이 문서의 결재자인지, 결재자라면 결재 순서인지
		boolean isApprovalTurn = isApprovalTurn(document_id);
		
		Map<String, Object> vacation_info = new HashMap<>();
		vacation_info.put("vacation_info", list);
		vacation_info.put("isApprovalTurn", isApprovalTurn);
		return vacation_info;
	}
	
	// 지출 결의서 정보 출력
	@ResponseBody
	@RequestMapping("/getExpenseInfo")
	public Map<String, Object> getExpenseInfo(String document_id) {
		Map<String, Object> expense = esservices.getExpenseInfo(document_id);

		String spender_id = expense.get("spender_id").toString();
		
		Map<String, String> account = new HashMap<>();
		// 구분이 개인이면 개인 계좌
		if(expense.get("expense_category").toString().equals("개인")) {
			account = esservices.getPersonalAccount(spender_id);
		// 구분이 법인이면 법인 계좌
		} else if(expense.get("expense_category").toString().equals("법인")) {
			account = esservices.getCorporateAccount(spender_id);
		}
		
		// 이 문서의 결재자인지, 결재자라면 결재 순서인지
		boolean isApprovalTurn = isApprovalTurn(document_id);
		
		// 문서 첨부파일 리스트
		List<Map<String, String>> fileList = esservices.getDocumentFileList(document_id);
		
		Map<String, Object> expense_info = new HashMap<>();
		expense_info.put("expense_info", expense);
		expense_info.put("account", account);
		expense_info.put("isApprovalTurn", isApprovalTurn);
		expense_info.put("fileList", fileList);
		
		return expense_info;
	}
	
	// 업무 연락 정보 출력
	@ResponseBody
	@RequestMapping("/getBusinessInfo")
	public Map<String, Object> getBusinessInfo(String document_id) {
		Map<String, String> business = esservices.getBusinessInfo(document_id);
		business.put("content", removeHtmlTags(business.get("content")));
	
		// 이 문서의 결재자인지, 결재자라면 결재 순서인지
		boolean isApprovalTurn = isApprovalTurn(document_id);
		
		Map<String, Object> business_info = new HashMap<>();
		business_info.put("business_info", business);
		business_info.put("isApprovalTurn", isApprovalTurn);
		
		return business_info;
	}
	
	// 결재 결과 저장
	@ResponseBody
	@RequestMapping("/submitApproval")
	public int submitApproval(String document_id, String approval) {
		String loginID = (String) session.getAttribute("loginID");
		esservices.submitApproval(loginID, document_id, approval);
		
		// 결재자가 승인했다면
		if(approval.equals("승인")) {
			// 마지막 결재자였는지 확인
			boolean checkAllApprovals = esservices.checkAllApprovals(document_id);
			// 문서 상태를 승인으로 변경
			if(checkAllApprovals) {
				esservices.updateDocumentStatus(document_id, "승인");
			}
		// 결재자가 반려했다면
		} else if(approval.equals("반려")) {
			// 문서 상태를 반려로 변경
			esservices.updateDocumentStatus(document_id, "반려");
		}
		
		return 1;
	}

	// 멤버의 전자 결재를 위한 전자선 정렬 -> job_id의 순서대로 정렬
	@ResponseBody
	@RequestMapping("/selectEmpJobLevel")
	public List<Map<String, Object>> selectEmpJobLevel(@RequestParam("userList[]") List<String> userList) {
		// List<String> userList = requestBody.get("userIdList");
		System.out.println(userList);
		// return null;
		return esservices.selectEmpJobLevel(userList);
	}

	// 휴가 문서 생성
	@ResponseBody
	@RequestMapping("/insertVacation")
	public int insertVacation(@RequestParam("processEmployeeIDArray[]") List<String> processEmployeeIDArray,
			@RequestParam("vacationDateList[]") List<String> vacationDateList,
			@RequestParam("vacationTypeList[]") List<String> vacationTypeList, @RequestParam("reson") String reson)
			throws Exception {
		String emp_id = (String) session.getAttribute("loginID");
		return esservices.insertVacation(emp_id, processEmployeeIDArray, vacationDateList, vacationTypeList, reson);
	}

	// 전자결재 문서 생성
	@RequestMapping("/insertDocument")
	public String insertDocument(String[] applicationEmployeeIDList, String[] processEmployeeIDList,
			String esDocumentType, int esPreservationPeriod, String esSecurityLevel, String esSpender,
			String documentTitle, boolean temporary, String expense_category, String expenseYear, String expenseMonth,
			String summary, String content, MultipartFile[] uploadFiles) throws Exception {
		System.out.println("applicationEmployeeIDList" + applicationEmployeeIDList);
		System.out.println("processEmployeeIDList" + processEmployeeIDList);
		System.out.println("uploadFiles" + uploadFiles);
		System.out.println("esPreservationPeriod" + esPreservationPeriod);
		System.out.println("esSpender" + esSpender);
		esservices.insertDocument(applicationEmployeeIDList, processEmployeeIDList, esDocumentType,
				esPreservationPeriod, esSecurityLevel, esSpender, documentTitle, temporary, expense_category,
				expenseYear, expenseMonth, summary, content, uploadFiles);
		return "redirect:/electronicsignature";
	}
	
	// html 태그 제거
	public static String removeHtmlTags(String input) {
        // HTML 태그를 제거하는 정규식
        String regex = "<[^>]*>";
        return input.replaceAll(regex, "");
    }

}
