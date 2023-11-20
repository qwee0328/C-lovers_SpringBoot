package com.clovers.services;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.clovers.dao.ElectronicSignatureDAO;
import com.clovers.dao.MemberDAO;
import com.clovers.dto.DocumentApprovalsDTO;
import com.clovers.dto.DocumentDTO;
import com.clovers.dto.DocumentDrafterDTO;
import com.clovers.dto.VacationApplicationInfoDTO;

import jakarta.servlet.http.HttpSession;

@Service
public class ElectronicSignatureService {
	// 전자결재 서비스 레이어
	@Autowired
	private HttpSession session;

	@Autowired
	private ElectronicSignatureDAO dao;

	@Autowired
	private MemberDAO mdao;

	// 멤버의 전자 결재를 위한 전자선 정렬 -> job_id의 순서대로 정렬
	public List<Map<String, Object>> selectEmpJobLevel(List<String> userList) {
		return dao.selectEmpJobLevel(userList);
	}

	// 휴가 문서 생성
	@Transactional
	public int insertVacation(String emp_id, List<String> processEmployeeIDArray, List<String> vacationDateList,
			List<String> vacationTypeList, String reson) throws Exception {
		DocumentDTO document = new DocumentDTO();

		// 휴가 문서 번호 불러오기
		int documentCount = dao.selectDocmuentCount("%휴가%") + 1;
		String documentNumber = String.format("%04d", documentCount);

		// 오늘 날짜 구하기
		LocalDate today = LocalDate.now();

		// 날짜를 원하는 형태로 포맷팅
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String formattedDate = today.format(formatter);

		// 정보 설정
		String documentID = "CD-휴가-" + formattedDate + "-" + documentNumber;
		document.setId(documentID);
		document.setSave_period(3);
		document.setSecurity_grade("B등급");
		document.setDocument_type_id("휴가 신청서");

		String writerName = mdao.selectNameById((String) session.getAttribute("loginID"));
		String title = "휴가 신청서 (" + writerName + ")";
		document.setTitle(title);

		document.setEmp_id((String) session.getAttribute("loginID"));

		// 휴가 문서 등록
		dao.insertDocument(document);

		// 문서 등록자 등록
		String writerId = (String) session.getAttribute("loginID");
		DocumentDrafterDTO drafter = new DocumentDrafterDTO();
		drafter.setDocument_id(documentID);
		drafter.setEmp_id(emp_id);

		dao.insertDrafter(drafter);

		// 휴가 결재선 등록
		List<Map<String, Object>> approvalsLevel = dao.selectEmpJobLevel(processEmployeeIDArray);
		List<DocumentApprovalsDTO> approvals = new ArrayList<DocumentApprovalsDTO>();
		for (int i = 0; i < processEmployeeIDArray.size(); i++) {
			DocumentApprovalsDTO approval = new DocumentApprovalsDTO();
			approval.setDocument_id(documentID);
			approval.setEmp_id((String) approvalsLevel.get(i).get("id"));
			approval.setSec_level((int) approvalsLevel.get(i).get("sec_level"));
			approvals.add(approval);
		}
		dao.insertApprovals(approvals);

//		// 휴가 신청일 정보 등록
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<VacationApplicationInfoDTO> vacationInfoList = new ArrayList<VacationApplicationInfoDTO>();
		for (int i = 0; i < vacationDateList.size(); i++) {
			VacationApplicationInfoDTO info = new VacationApplicationInfoDTO();
			info.setDocument_id(documentID);
			Date parsedDate = dateFormat.parse(vacationDateList.get(i));
			Timestamp timestampDate = new Timestamp(parsedDate.getTime());
			info.setVacation_date(timestampDate);
			info.setRest_reson_type(vacationTypeList.get(i));
			info.setVacation_reason(reson);
			vacationInfoList.add(info);
		}
		// dao.insertVacationApplicationInfo(vacationInfoList);
		// System.out.println(reson);

		return dao.insertVacationApplicationInfo(vacationInfoList);
	}

	// 전자 결재 문서 생성
	@Transactional
	public int insertDocument(String[] applicationEmployeeIDList, String[] processEmployeeIDList, String esDocumentType,
			int esPreservationPeriod, String esSecurityLevel, String esSpender, String documentTitle, boolean temporary,
			MultipartFile[] uploadFiles) {
		DocumentDTO document = new DocumentDTO();

		// 정보 설정
		int documentCount = 0;
		String documentID = "";
		String documentNumber = "";

		// 오늘 날짜 구하기
		LocalDate today = LocalDate.now();

		// 날짜를 원하는 형태로 포맷팅
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String formattedDate = today.format(formatter);

		// 문서 번호 불러오기
		if (esDocumentType.equals("지출 결의서")) {
			documentCount = dao.selectDocmuentCount("%지출%") + 1;
			documentNumber = String.format("%04d", documentCount);
			documentID = "CD-지출-" + formattedDate + "-" + documentNumber;
		} else {
			documentCount = dao.selectDocmuentCount("%업무%") + 1;
			documentNumber = String.format("%04d", documentCount);
			documentID = "CD-업무-" + formattedDate + "-" + documentNumber;
		}

		document.setId(documentID);
		document.setSave_period(esPreservationPeriod);
		document.setSecurity_grade(esSecurityLevel);
		document.setDocument_type_id(esDocumentType);
		document.setTitle(documentTitle);
		document.setTemporary(temporary);
		document.setEmp_id((String) session.getAttribute("loginID"));

		// 전자 문서 등록
		dao.insertDocument(document);
		
		// 신청선 등록
		List<DocumentDrafterDTO> drafters = new ArrayList<DocumentDrafterDTO>();
		for(int i=0;i<applicationEmployeeIDList.length;i++) {
			DocumentDrafterDTO drafter = new DocumentDrafterDTO();
			drafter.setDocument_id(documentID);
			drafter.setEmp_id(applicationEmployeeIDList[i]);
			
			drafters.add(drafter);
		}
		dao.insertDrafters(drafters);

		// 결재선 등록
		List<Map<String, Object>> approvalsLevel = dao.selectEmpJobLevelList(processEmployeeIDList);
		List<DocumentApprovalsDTO> approvals = new ArrayList<DocumentApprovalsDTO>();
		for (int i = 0; i < processEmployeeIDList.length; i++) {
			DocumentApprovalsDTO approval = new DocumentApprovalsDTO();
			approval.setDocument_id(documentID);
			approval.setEmp_id((String) approvalsLevel.get(i).get("id"));
			approval.setSec_level((int) approvalsLevel.get(i).get("sec_level"));
			approvals.add(approval);
		}
		dao.insertApprovals(approvals);
		return 0;
	}

	// 직전 결재자들의 결재 결과
	public List<Map<String, String>> previousApprovalResult(String loginID) {
		return dao.previousApprovalResult(loginID);
	}

	// 진행 중인 문서 전체 리스트 출력
	public List<Map<String, Object>> progressTotalList(String loginID, List<String> ExcludedIds) {
		Map<String, Object> userInfo = new HashMap<>();
		userInfo.put("loginID", loginID);
		userInfo.put("ExcludedIds", ExcludedIds);
		return dao.progressTotalList(userInfo);
	}

	// 진행 중인 문서 대기 리스트 출력
	public List<Map<String, Object>> proggressWaitLlist(String loginID, List<String> ExcludedIds) {
		Map<String, Object> userInfo = new HashMap<>();
		userInfo.put("loginID", loginID);
		userInfo.put("ExcludedIds", ExcludedIds);
		return dao.progressWaitList(userInfo);
	}

	// 진행 중인 문서 확인 리스트 출력
	public List<Map<String, Object>> progressCheckList(String loginID, List<String> ExcludedIds) {
		Map<String, Object> userInfo = new HashMap<>();
		userInfo.put("loginID", loginID);
		userInfo.put("ExcludedIds", ExcludedIds);
		return dao.progressCheckList(userInfo);
	}

	// 진행 중인 문서 진행 리스트 출력
	public List<Map<String, Object>> progressList(String loginID, List<String> ExcludedIds) {
		Map<String, Object> userInfo = new HashMap<>();
		userInfo.put("loginID", loginID);
		userInfo.put("ExcludedIds", ExcludedIds);
		return dao.progressList(userInfo);
	}

	// 문서함 전체 리스트 출력
	public List<Map<String, Object>> documentTotalList(String loginID) {
		return dao.documentList(loginID);
	}

	// 문서함 기안 리스트 출력
	public List<Map<String, Object>> documentDraftingList(String loginID) {
		return dao.documentDraftingList(loginID);
	}

	// 문서함 결재 리스트 출력
	public List<Map<String, Object>> documentApprovalList(String loginID) {
		return dao.documentApprovalList(loginID);
	}

	// 문서함 반려 리스트 출력
	public List<Map<String, Object>> documentRejectionList(String loginID) {
		return dao.documentRejectionList(loginID);
	}

	// 임시저장 리스트 출력
	public List<Map<String, Object>> temporaryList(String loginID) {
		return dao.temporaryList(loginID);
	}
}
