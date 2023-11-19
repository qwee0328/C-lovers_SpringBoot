package com.clovers.services;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public List<Map<String, Object>> selectEmpJobLevel(List<String> processUserIDList) {
		return dao.selectEmpJobLevel(processUserIDList);
	}

	// 휴가 문서 생성
	@Transactional
	public int insertVacation(String emp_id, List<String> processEmployeeIDArray, List<String> vacationDateList,
			List<String> vacationTypeList, String reson) throws Exception {
		DocumentDTO document = new DocumentDTO();
		
		// 휴가 문서 번호 불러오기
		int documentCount = dao.selectVcationDocmuentCount()+1;
		String documentNumber = String.format("%04d", documentCount);
		
		// 오늘 날짜 구하기
		// 현재 날짜 구하기
        LocalDate today = LocalDate.now();

        // 날짜를 원하는 형태로 포맷팅
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = today.format(formatter);
        
        // 정보 설정
		String documentID = "CD-휴가-"+formattedDate+"-"+documentNumber;
		document.setId(documentID);
		document.setSave_period(3);
		document.setSecurity_grade("B등급");
		document.setDocument_type_id("휴가 신청서");
		
		String writerName = mdao.selectNameById((String)session.getAttribute("loginID"));
		String title = "휴가 신청서 ("+writerName+")";
		document.setTitle(title);
		
		// 휴가 문서 등록
		dao.insertDocument(document);
		
		// 문서 등록자 등록
		String writerId = (String)session.getAttribute("loginID");
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
			approval.setEmp_id((String)approvalsLevel.get(i).get("id"));
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
		dao.insertVacationApplicationInfo(vacationInfoList);
		System.out.println(reson);

		return 0;
	}

	// 진행 중인 문서 전체 리스트 출력
	public List<Map<String, Object>> progressTotalList(String loginID) {
		return dao.progressTotalList(loginID);
	}
}
