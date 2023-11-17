package com.clovers.services;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clovers.dao.ElectronicSignatureDAO;
import com.clovers.dto.VacationApplicationInfoDTO;
import com.clovers.dto.VacationDocumentDTO;
import com.clovers.dto.VacationEmpApprovalsDTO;

@Service
public class ElectronicSignatureService {
	// 전자결재 서비스 레이어
	@Autowired
	private ElectronicSignatureDAO dao;

	// 멤버의 전자 결재를 위한 전자선 정렬 -> job_id의 순서대로 정렬
	public List<Map<String, Object>> selectEmpJobLevel(List<String> processUserIDList) {
		return dao.selectEmpJobLevel(processUserIDList);
	}

	// 휴가 문서 생성
	@Transactional
	public int insertVacation(String emp_id, List<String> processEmployeeIDArray, List<String> vacationDateList,
			List<String> vacationTypeList, String reson) throws Exception {
		VacationDocumentDTO document = new VacationDocumentDTO();
		document.setEmp_id(emp_id);

		int documentID = dao.insertVacation(document);
		System.out.println("documentid:" + documentID);

		// 휴가 결재선 등록
		List<VacationEmpApprovalsDTO> approvals = new ArrayList<VacationEmpApprovalsDTO>();
		for (int i = 0; i < processEmployeeIDArray.size(); i++) {
			VacationEmpApprovalsDTO approval = new VacationEmpApprovalsDTO();
			approval.setVacation_document_id(documentID);
			approval.setEmp_approvals_id(processEmployeeIDArray.get(i));
			approvals.add(approval);
		}
		dao.insertVacationApprovals(approvals);

		// 휴가 신청일 정보 등록
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<VacationApplicationInfoDTO> vacationInfoList = new ArrayList<VacationApplicationInfoDTO>();
		for (int i = 0; i < vacationDateList.size(); i++) {
			VacationApplicationInfoDTO info = new VacationApplicationInfoDTO();
			info.setVacation_document_id(documentID);
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
}
