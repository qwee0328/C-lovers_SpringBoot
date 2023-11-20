package com.clovers.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clovers.dto.BusinessContactInfoDTO;
import com.clovers.dto.DocumentApprovalsDTO;
import com.clovers.dto.DocumentDTO;
import com.clovers.dto.DocumentDrafterDTO;
import com.clovers.dto.DocumentFileDTO;
import com.clovers.dto.ExpenseResolutioinInfoDTO;
import com.clovers.dto.VacationApplicationInfoDTO;

@Repository
public class ElectronicSignatureDAO {
	// 전자결재 DAO
	@Autowired
	private SqlSession db;

	// 멤버의 전자 결재를 위한 전자선 정렬 -> job_id의 순서대로 정렬
	public List<Map<String, Object>> selectEmpJobLevel(List<String> userList) {
		return db.selectList("ElectronicSignature.selectEmpJobLevel", userList);
	}
	
	// 멤버의 전자 결재를 위한 전자선 정렬 -> job_id의 순서대로 정렬
	public List<Map<String, Object>> selectEmpJobLevelList(String[] userList) {
		return db.selectList("ElectronicSignature.selectEmpJobLevelList", userList);
	}

	// 휴가 문서 번호 구하기
	public int selectDocmuentCount(String keyword) {
		Integer result = db.selectOne("ElectronicSignature.selectVcationDocmuentCount", keyword);
		return (result != null) ? result.intValue() : 0;
	}

	// 문서 생성
	public int insertDocument(DocumentDTO document) {
		return db.insert("ElectronicSignature.insertDocument", document);
	}

	// 문서 등록자 정보 생성
	public int insertDrafter(DocumentDrafterDTO drafter) {
		return db.insert("ElectronicSignature.insertDrafter", drafter);
	}
	
	// 문서 등록자 정보 생성
	public int insertDrafters(List<DocumentDrafterDTO> drafters) {
		return db.insert("ElectronicSignature.insertDrafterList", drafters);
	}

	// 문서 결재선 등록
	public int insertApprovals(List<DocumentApprovalsDTO> approvals) {
		return db.insert("ElectronicSignature.insertApprovals", approvals);
	}

	// 휴가 신청일 정보 등록
	public int insertVacationApplicationInfo(List<VacationApplicationInfoDTO> vacationInfoList) {
		return db.insert("ElectronicSignature.insertVacationApplicationInfo", vacationInfoList);
	}
	
	// 지출 결의서 정보 등록
	public int insertExpenseResolutionInfo(ExpenseResolutioinInfoDTO expense) {
		return db.insert("ElectronicSignature.insertExpenseResolutionInfo", expense);
	}
	
	// 업무 연락 정보 등록
	public int insertBusinessContactInfo(BusinessContactInfoDTO business) {
		return db.insert("ElectronicSignature.insertBusinessContactInfo", business);
	}
	
	// 문서 파일 추가
	public int insertDocumentFile(DocumentFileDTO dto) {
		return db.insert("ElectronicSignature.insertDocumentFile",dto);
	}

	// 직전 결재자들의 결재 결과
	public List<Map<String, String>> previousApprovalResult(String loginID) {
		return db.selectList("ElectronicSignature.previousApprovalResult", loginID);
	}
	
	// 로그인한 사용자의 직급 가져옴
	public int getJobRank(String loginID) {
		return db.selectOne("ElectronicSignature.getJobRank", loginID);
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

	// 문서함 전체 리스트 출력
	public List<Map<String, Object>> documentList(String loginID) {
		return db.selectList("ElectronicSignature.documentTotalList", loginID);
	}

	// 문서함 기안 리스트 출력
	public List<Map<String, Object>> documentDraftingList(String loginID) {
		return db.selectList("ElectronicSignature.documentDraftingList", loginID);
	}

	// 문서함 결재 리스트 출력
	public List<Map<String, Object>> documentApprovalList(String loginID) {
		return db.selectList("ElectronicSignature.documentApprovalList", loginID);
	}

	// 문서함 반려 리스트 출력
	public List<Map<String, Object>> documentRejectionList(String loginID) {
		return db.selectList("ElectronicSignature.documentRejectList", loginID);
	}

	// 임시저장 리스트 출력
	public List<Map<String, Object>> temporaryList(String loginID) {
		return db.selectList("ElectronicSignature.temporaryList", loginID);
	}

	// 문서 번호에 따른 결재 정보 출력
	public List<Map<String, Object>> selectAllByDocumentId(String document_id) {
		return db.selectList("ElectronicSignature.selectAllByDocumentId", document_id);
	}

	// 기안자의 부서 가져오기
	public String getDeptNameByDrafterId(String drafter_id) {
		return db.selectOne("ElectronicSignature.getDeptNameByDrafterId", drafter_id);
	}

	// 사용자의 직급 가져오기
	public List<String> getRankByDrafterId(String drafter_id) {
		return db.selectList("ElectronicSignature.getRankByUserId", drafter_id);
	}

	// 결재자의 이름 및 직급 가져오기
	public List<Map<String, String>> getApproverRankByDocunetId(String document_id) {
		return db.selectList("ElectronicSignature.getApproverRankByDocunetId", document_id);
	}
}
