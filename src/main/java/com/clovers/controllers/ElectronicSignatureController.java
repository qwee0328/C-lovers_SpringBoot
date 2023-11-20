package com.clovers.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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

	// 로그인한 결재자의 순서인 문서 번호를 담은 리스트 반환
	public List<String> ExcludedIds(String loginID) {
		// 직전 결재자들의 결재 결과
		List<Map<String, String>> approvalStatus = esservices.previousApprovalResult(loginID);

		// 결재 리스트에서 제외될 문서 번호를 담은 배열
		List<String> ExcludedIds = new ArrayList<>();

		for (Map<String, String> status : approvalStatus) {
			String documentId = status.get("document_id");
			String approverStatus = status.get("approver_status");

			// 직전 결재자들의 결재 결과가 승인이 아니고, 문서 번호 배열에 포함되지 않았을 경우 문서 번호 저장
			if (!"승인".equals(approverStatus) && !ExcludedIds.contains(documentId)) {
				ExcludedIds.add(documentId);
			}
		}
		return ExcludedIds;
	}

	// 기안 또는 결재 구분
	public void setDivision(String loginID, List<Map<String, Object>> list) {
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
	}

	// 품의서 또는 휴가 신청서 구분
	public void setCategory(List<Map<String, Object>> list) {
		for (Map<String, Object> item : list) {
			// document_id에 '지출'이 포함되어 있으면 지출 결의서
			if (item.get("document_id").toString().contains("지출")) {
				item.put("category", "지출 결의서");
			}
			// document_id에 '휴가'가 포함되어 있으면 휴가 신청서
			else if (item.get("document_id").toString().contains("휴가")) {
				item.put("category", "휴가 신청서");
			} else if (item.get("document_id").toString().contains("업무")) {
				item.put("category", "업무연락");
			}
		}
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
	public List<Map<String, Object>> progressTotalList() {
		String loginID = (String) session.getAttribute("loginID");

		// 결재 리스트에서 제외할 문서 번호
		List<String> ExcludedIds = ExcludedIds(loginID);

		List<Map<String, Object>> list = esservices.progressTotalList(loginID, ExcludedIds);
		setDivision(loginID, list);

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
		List<String> ExcludedIds = ExcludedIds(loginID);

		List<Map<String, Object>> list = esservices.proggressWaitLlist(loginID, ExcludedIds);
		setDivision(loginID, list);
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
		List<String> ExcludedIds = ExcludedIds(loginID);

		List<Map<String, Object>> list = esservices.progressCheckList(loginID, ExcludedIds);
		setDivision(loginID, list);
		return list;
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
	public List<Map<String, Object>> progressList() {
		String loginID = (String) session.getAttribute("loginID");
		List<String> ExcludedIds = ExcludedIds(loginID);

		List<Map<String, Object>> list = esservices.progressList(loginID, ExcludedIds);
		setDivision(loginID, list);
		return list;
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
	public List<Map<String, Object>> documentTotalList() {
		String loginID = (String) session.getAttribute("loginID");
		List<Map<String, Object>> list = esservices.documentTotalList(loginID);
		setDivision(loginID, list);
		setCategory(list);
		return list;
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
	public List<Map<String, Object>> documentDraftingList() {
		String loginID = (String) session.getAttribute("loginID");
		List<Map<String, Object>> list = esservices.documentDraftingList(loginID);
		setCategory(list);
		return list;
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
	public List<Map<String, Object>> documentApprovalList() {
		String loginID = (String) session.getAttribute("loginID");
		List<Map<String, Object>> list = esservices.documentApprovalList(loginID);
		setCategory(list);

		return list;
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
	public List<Map<String, Object>> documentRejectionList() {
		String loginID = (String) session.getAttribute("loginID");
		List<Map<String, Object>> list = esservices.documentRejectionList(loginID);
		setCategory(list);

		return list;
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
	public List<Map<String, Object>> temporaryList() {
		String loginID = (String) session.getAttribute("loginID");
		List<Map<String, Object>> list = esservices.temporaryList(loginID);
		setCategory(list);

		return list;
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
	public String insertDocument(String[] applicationEmployeeIDList, String[] processEmployeeIDList, String esDocumentType, int esPreservationPeriod, String esSecurityLevel, String esSpender, String documentTitle,MultipartFile[] uploadFiles) throws Exception {
		System.out.println("applicationEmployeeIDList"+applicationEmployeeIDList);
		System.out.println("processEmployeeIDList"+processEmployeeIDList);
		System.out.println("uploadFiles"+uploadFiles);
		System.out.println("esPreservationPeriod"+esPreservationPeriod);
		System.out.println("esSpender"+esSpender);
		esservices.insertDocument(applicationEmployeeIDList,processEmployeeIDList,esDocumentType,esPreservationPeriod,esSecurityLevel,esSpender,documentTitle,uploadFiles);
		return "redirect:/electronicsignature"; //esservices.inserDocument(documentType,preservationPeriod,securityLevel,applicationEmployeeIDArray,processEmployeeIDArray,year,month,spender, summary,fileList);
	}

}
