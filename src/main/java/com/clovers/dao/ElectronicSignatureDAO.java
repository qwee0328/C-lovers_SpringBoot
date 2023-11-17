package com.clovers.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clovers.dto.VacationApplicationInfoDTO;
import com.clovers.dto.VacationDocumentDTO;
import com.clovers.dto.VacationEmpApprovalsDTO;

@Repository
public class ElectronicSignatureDAO {
	// 전자결재 DAO
	@Autowired
	private SqlSession db;

	// 멤버의 전자 결재를 위한 전자선 정렬 -> job_id의 순서대로 정렬
	public List<Map<String, Object>> selectEmpJobLevel(List<String> processUserIDList) {
		return db.selectList("ElectronicSignature.selectEmpJobLevel", processUserIDList);
	}

	// 휴가 문서 생성
	public int insertVacation(VacationDocumentDTO document) {
		db.insert("ElectronicSignature.insertVacation", document);
		return document.getId();
	}

	// 휴가 결재선 등록
	public void insertVacationApprovals(List<VacationEmpApprovalsDTO> approvalsList) {
		db.insert("ElectronicSignature.insertVacationApprovals", approvalsList);
	}

	// 휴가 신청일 정보 등록
	public void insertVacationApplicationInfo(List<VacationApplicationInfoDTO> vacationInfoList) {
		db.insert("ElectronicSignature.insertVacationApplicationInfo", vacationInfoList);
	}
}
