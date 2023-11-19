package com.clovers.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clovers.dto.DocumentApprovalsDTO;
import com.clovers.dto.DocumentDTO;
import com.clovers.dto.DocumentDrafterDTO;
import com.clovers.dto.VacationApplicationInfoDTO;

@Repository
public class ElectronicSignatureDAO {
	// 전자결재 DAO
	@Autowired
	private SqlSession db;

	// 멤버의 전자 결재를 위한 전자선 정렬 -> job_id의 순서대로 정렬
	public List<Map<String, Object>> selectEmpJobLevel(List<String> processUserIDList) {
		return db.selectList("ElectronicSignature.selectEmpJobLevel", processUserIDList);
	}
	
	// 휴가 문서 번호 구하기
	public int selectVcationDocmuentCount() {
		return db.selectOne("ElectronicSignature.selectVcationDocmuentCount");
	}
	
	// 문서 생성
	public int insertDocument(DocumentDTO document) {
		return db.insert("ElectronicSignature.insertDocument", document);
	}
	
	// 문서 등록자 정보 생성
	public int insertDrafter(DocumentDrafterDTO drafter) {
		return db.insert("ElectronicSignature.insertDrafter", drafter);
	}
	
	// 문서 결재선 등록 
	public int insertApprovals(List<DocumentApprovalsDTO> approvals) {
		return db.insert("ElectronicSignature.insertApprovals", approvals);
	}


	// 휴가 신청일 정보 등록
	public void insertVacationApplicationInfo(List<VacationApplicationInfoDTO> vacationInfoList) {
		db.insert("ElectronicSignature.insertVacationApplicationInfo", vacationInfoList);
	}
	
	// 직전 결재자들의 결재 결과
	public List<Map<String, String>> previousApprovalResult(String loginID) {
		return db.selectList("ElectronicSignature.previousApprovalResult", loginID);
	}

	// 진행 중인 문서 전체 리스트 출력
	public List<Map<String, Object>> progressTotalList(Map<String, Object> userInfo) {
		return db.selectList("ElectronicSignature.progressTotalList", userInfo);
	}

	// 진행 중인 문서 대기 리스트 출력
	public List<Map<String, Object>> progressWaitList(Map<String, Object> userInfo) {
		return db.selectList("ElectronicSignature.progressWaitList", userInfo);
	}

	// 진행 중인 문서 확인 리스트 출력
	public List<Map<String, Object>> progressCheckList(Map<String, Object> userInfo) {
		return db.selectList("ElectronicSignature.progressCheckList", userInfo);
	}

	// 진행 중인 문서 진행 리스트 출력
	public List<Map<String, Object>> progressList(Map<String, Object> userInfo) {
		return db.selectList("ElectronicSignature.progressList", userInfo);
	}
}
