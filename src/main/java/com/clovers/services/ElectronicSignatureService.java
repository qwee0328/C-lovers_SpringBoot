package com.clovers.services;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.clovers.dao.ElectronicSignatureDAO;
import com.clovers.dao.MemberDAO;
import com.clovers.dao.OfficeDAO;
import com.clovers.dto.AnnualUseMemoryDTO;
import com.clovers.dto.BusinessContactInfoDTO;
import com.clovers.dto.DocumentApprovalsDTO;
import com.clovers.dto.DocumentDTO;
import com.clovers.dto.DocumentDrafterDTO;
import com.clovers.dto.DocumentFileDTO;
import com.clovers.dto.ExpenseResolutioinInfoDTO;
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

	@Autowired
	private OfficeDAO odao;

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

		String writerName = mdao.selectNameById(emp_id);
		String title = "휴가 신청서 (" + writerName + ")";
		document.setTitle(title);

		document.setEmp_id(emp_id);

		String jobID = odao.searchByJobID(emp_id);
		String jobName = odao.selectJobName(jobID);
		if (jobName.equals("대표이사") || jobName.equals("사장") || jobName.equals("상무") || jobName.equals("이사")) {
			document.setStatus("승인");

			// LocalDate를 LocalDateTime으로 변환
			LocalDateTime localDateTime = today.atStartOfDay();
			// LocalDateTime을 Timestamp로 변환
			Timestamp timestamp = Timestamp.valueOf(localDateTime);
			
			// 승인 일자 설정
			document.setApproval_date(timestamp);
		} else {
			document.setStatus("대기");
		}

		// 휴가 문서 등록
		dao.insertDocument(document);

		// 문서 등록자 등록
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
			if (jobName.equals("대표이사") || jobName.equals("사장") || jobName.equals("상무") || jobName.equals("이사")) {
				approval.setApproval("승인");
			} else {
				approval.setApproval("대기");
			}
			approvals.add(approval);
		}
		dao.insertApprovals(approvals);

//		// 휴가 신청일 정보 등록
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<VacationApplicationInfoDTO> vacationInfoList = new ArrayList<VacationApplicationInfoDTO>();
		List<AnnualUseMemoryDTO> vacationUseMemoryList = new ArrayList<AnnualUseMemoryDTO>();
		for (int i = 0; i < vacationDateList.size(); i++) {
			VacationApplicationInfoDTO info = new VacationApplicationInfoDTO();
			info.setDocument_id(documentID);
			Date parsedDate = dateFormat.parse(vacationDateList.get(i));
			Timestamp timestampDate = new Timestamp(parsedDate.getTime());
			info.setVacation_date(timestampDate);
			info.setRest_reason_type(vacationTypeList.get(i));
			info.setVacation_reason(reson);
			vacationInfoList.add(info);

			if (jobName.equals("대표이사") || jobName.equals("사장") || jobName.equals("상무") || jobName.equals("이사")) {
				// 연차 사용 기록에 등록
				AnnualUseMemoryDTO memory = new AnnualUseMemoryDTO();
				memory.setEmp_id(emp_id);
				memory.setRest_reason_type_id(vacationTypeList.get(i));
				memory.setReason(reson);
				memory.setAnnual_date(timestampDate);
				vacationUseMemoryList.add(memory);
			}
		}
		// dao.insertVacationApplicationInfo(vacationInfoList);
		// System.out.println(reson);

		// 휴가 사용기록 등록
		if (jobName.equals("대표이사") || jobName.equals("사장") || jobName.equals("상무") || jobName.equals("이사")) {
			dao.insertVacationUseMemoryInfo(vacationUseMemoryList);
		}

		return dao.insertVacationApplicationInfo(vacationInfoList);
	}

	// 전자 결재 문서 생성
	@Transactional
	public int insertDocument(String[] applicationEmployeeIDList, String[] processEmployeeIDList, String esDocumentType,
			int esPreservationPeriod, String esSecurityLevel, String esSpender, String documentTitle, boolean temporary,
			String expense_category, String expenseYear, String expenseMonth, String summary, String content,
			MultipartFile[] uploadFiles) throws Exception {
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
		document.setStatus("대기");
		document.setEmp_id((String) session.getAttribute("loginID"));

		// 전자 문서 등록
		dao.insertDocument(document);

		// 신청선 등록
		List<DocumentDrafterDTO> drafters = new ArrayList<DocumentDrafterDTO>();
		for (int i = 0; i < applicationEmployeeIDList.length; i++) {
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
			approval.setApproval("대기");
			approvals.add(approval);
		}
		dao.insertApprovals(approvals);

		// 지출 결의서 정보 등록
		if (esDocumentType.equals("지출 결의서")) {
			ExpenseResolutioinInfoDTO expense = new ExpenseResolutioinInfoDTO();
			expense.setDocument_id(documentID);
			expense.setExpense_category(expense_category);
			System.out.println(expense_category);

			// 문자열로 localdatetime 생성
			System.out.println(expenseYear);
			String dateString = expenseYear + "-" + expenseMonth;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
			Date parsedDate = dateFormat.parse(dateString);
			Timestamp timestampDate = new Timestamp(parsedDate.getTime());
			expense.setExpense_date(timestampDate);
			expense.setSpender_id(esSpender);
			expense.setSummary(summary);

			dao.insertExpenseResolutionInfo(expense);
		} else {
			// 업무 연락 정보 등록
			BusinessContactInfoDTO business = new BusinessContactInfoDTO();
			business.setDocument_id(documentID);
			business.setBusiness_contents(content);

			dao.insertBusinessContactInfo(business);
		}

		// 파일 등록
		if (!uploadFiles[0].getOriginalFilename().equals("")) {
			String upload = "C:/C-lovers";
			File uploadPath = new File(upload);
			if (!uploadPath.exists()) {
				uploadPath.mkdir();
			}

			for (MultipartFile file : uploadFiles) {
				String oriName = file.getOriginalFilename();
				String sysName = UUID.randomUUID() + "_" + oriName;
				file.transferTo(new File(uploadPath + "/" + sysName));
				dao.insertDocumentFile(new DocumentFileDTO(0, documentID, oriName, sysName));
			}
		}
		return 0;
	}

	// 로그인한 사용자가 결재자인지
	public boolean isApprover(String loginID, String document_id) {
		Map<String, String> param = new HashMap<>();
		param.put("loginID", loginID);
		param.put("document_id", document_id);
		return dao.isApprover(param);
	}

	// 직전 결재자들의 결재 결과
	public int previousApprovalResult(String loginID, String document_id) {
		Map<String, String> param = new HashMap<>();
		param.put("loginID", loginID);
		param.put("document_id", document_id);
		return dao.previousApprovalResult(param);
	}

	// 로그인한 사용자의 직급 가져옴
	public int getJobRank(String loginID) {
		return dao.getJobRank(loginID);
	}

	// 진행 중인 문서 전체 리스트 출력
	public List<Map<String, Object>> progressTotalList(String loginID, List<String> keyword, int start, int end) {
		Map<String, Object> param = new HashMap<>();
		param.put("loginID", loginID);
		param.put("keyword", keyword);
		param.put("start", start);
		param.put("end", end);
		return dao.progressTotalList(param);
	}

	// 진행 중인 문서 대기 리스트 출력
	public List<Map<String, Object>> progressWaitList(String loginID, List<String> keyword, int start, int end) {
		Map<String, Object> param = new HashMap<>();
		param.put("loginID", loginID);
		param.put("keyword", keyword);
		param.put("start", start);
		param.put("end", end);
		return dao.progressWaitList(param);
	}

	// 진행 중인 문서 확인 리스트 출력
	public List<Map<String, Object>> progressCheckList(String loginID, List<String> keyword, int start, int end) {
		Map<String, Object> param = new HashMap<>();
		param.put("loginID", loginID);
		param.put("keyword", keyword);
		param.put("start", start);
		param.put("end", end);
		return dao.progressCheckList(param);
	}

	// 진행 중인 문서 진행 리스트 출력
	public List<Map<String, Object>> progressList(String loginID, List<String> keyword, int start, int end) {
		Map<String, Object> param = new HashMap<>();
		param.put("loginID", loginID);
		param.put("keyword", keyword);
		param.put("start", start);
		param.put("end", end);
		return dao.progressList(param);
	}

	// 문서함 전체 리스트 출력
	public List<Map<String, Object>> documentTotalList(String loginID, int start, int end) {
		Map<String, Object> param = new HashMap<>();
		param.put("loginID", loginID);
		param.put("start", start);
		param.put("end", end);
		return dao.documentList(param);
	}

	// 문서함 기안 리스트 출력
	public List<Map<String, Object>> documentDraftingList(String loginID, int start, int end) {
		Map<String, Object> param = new HashMap<>();
		param.put("loginID", loginID);
		param.put("start", start);
		param.put("end", end);
		return dao.documentDraftingList(param);
	}

	// 문서함 결재 리스트 출력
	public List<Map<String, Object>> documentApprovalList(String loginID, int start, int end) {
		Map<String, Object> param = new HashMap<>();
		param.put("loginID", loginID);
		param.put("start", start);
		param.put("end", end);
		return dao.documentApprovalList(param);
	}

	// 문서함 반려 리스트 출력
	public List<Map<String, Object>> documentRejectionList(String loginID, int start, int end) {
		Map<String, Object> param = new HashMap<>();
		param.put("loginID", loginID);
		param.put("start", start);
		param.put("end", end);
		return dao.documentRejectionList(param);
	}

	// 임시저장 리스트 출력
	public List<Map<String, Object>> temporaryList(String loginID, int start, int end) {
		Map<String, Object> param = new HashMap<>();
		param.put("loginID", loginID);
		param.put("start", start);
		param.put("end", end);
		return dao.temporaryList(param);
	}

	// 문서 번호에 따른 결재 정보 출력
	public List<Map<String, Object>> selectAllByDocumentId(String document_id) {
		return dao.selectAllByDocumentId(document_id);
	}

	// 기안자들의 이름과 직급, 부서 가져오기
	public List<Map<String, Object>> getDraftersByDocumentId(String document_id) {
		return dao.getDraftersByDocumentId(document_id);
	}

	// 결재자들의 이름과 직급, 부서 가져오기
	public List<Map<String, String>> getApproversByDocumentId(String document_id) {
		return dao.getApproversByDocumentId(document_id);
	}

	// 로그인한 사용자가 기안자인지
	public boolean isDrafterByDocumentId(String document_id, String loginID) {
		Map<String, String> param = new HashMap<>();
		param.put("document_id", document_id);
		param.put("loginID", loginID);
		return dao.isDrafterByDocumentId(param);
	}

	// 로그인한 사용자가 결재자인지
	public boolean isApproverByDocumentId(String document_id, String loginID) {
		Map<String, String> param = new HashMap<>();
		param.put("document_id", document_id);
		param.put("loginID", loginID);
		return dao.isApproverByDocumentId(param);
	}

	// 결재한 결재자가 존재하는지
	public boolean existApproval(String document_id) {
		return dao.existApproval(document_id);
	}

	// 휴가 신청서 정보 출력
	public List<Map<String, Object>> getVacationInfo(String document_id) {
		return dao.getVacationInfo(document_id);
	}

	// 대표 기안자의 부서 불러오기
	public Map<String, String> getMainDrafterDept(String document_id) {
		return dao.getMainDrafterDept(document_id);
	}

	// 지출 결의서 정보 출력
	public Map<String, Object> getExpenseInfo(String document_id) {
		return dao.getExpenseInfo(document_id);
	}

	// 개인 계좌 가져오기
	public Map<String, String> getPersonalAccount(String spender_id) {
		return dao.getPersonalAccount(spender_id);
	}

	// 법인 계좌 가져오기
	public Map<String, String> getCorporateAccount(String spender_id) {
		return dao.getCorporateAccount(spender_id);
	}

	// 업무 연락 정보 출력
	public Map<String, String> getBusinessInfo(String document_id) {
		return dao.getBusinessInfo(document_id);
	}

	// 반려가 존재하는지
	public boolean existRejection(String document_id) {
		return dao.existRejection(document_id);
	}

	// 결재 결과 저장
	public int submitApproval(String loginID, String document_id, String approval) {
		Map<String, String> param = new HashMap<>();
		param.put("loginID", loginID);
		param.put("document_id", document_id);
		param.put("approval", approval);
		return dao.submitApproval(param);
	}

	// 마지막 결재자였는지 확인
	public boolean checkAllApprovals(String document_id) {
		return dao.checkAllApprovals(document_id);
	}

	// 문서 상태 변경
	public int updateDocumentStatus(String docoment_id, String approval) {
		Map<String, String> param = new HashMap<>();
		param.put("document_id", docoment_id);
		param.put("approval", approval);
		return dao.updateDocumentStatus(param);
	}

	// 문서 삭제
	public int deleteApproval(String document_id) {
		return dao.deleteApproval(document_id);
	}
	
	// 문서에 파일이 있는지 확인
	public boolean selectFileByDocumentId(String id) {
		return dao.selectFileByDocumentId(id);
	}
	
	// 문서에 파일 리스트 반환
	public List<DocumentFileDTO> selectAllFileById(String id){
		return dao.selectAllFileById(id);
	}
}
