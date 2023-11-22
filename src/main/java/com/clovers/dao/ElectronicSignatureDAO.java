package com.clovers.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clovers.dto.AnnualUseMemoryDTO;
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
	
	// 휴가 사용기록 등록
	public int insertVacationUseMemoryInfo(List<AnnualUseMemoryDTO> vacationUseMemoryList) {
		return db.insert("ElectronicSignature.insertVacationUseMemoryInfo", vacationUseMemoryList);
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
	
	// 로그인한 사용자가 결재자인지
	public boolean isApprover(Map<String, String> param) {
		return db.selectOne("ElectronicSignature.isApprover", param);
	}
	
	// 결재한 결재자가 존재하는지
	public boolean existApproval(String document_id) {
		return db.selectOne("ElectronicSignature.existApproval", document_id);
	}

	// 직전 결재자들의 결재 결과
	public int previousApprovalResult(Map<String, String> param) {
		return db.selectOne("ElectronicSignature.previousApprovalResult", param);
	}
	
	// 로그인한 사용자의 직급 가져옴
	public int getJobRank(String loginID) {
		return db.selectOne("ElectronicSignature.getJobRank", loginID);
	}

	// 진행 중인 문서 전체 리스트 출력
	public List<Map<String, Object>> progressTotalList(Map<String, Object> param) {
		return db.selectList("ElectronicSignature.progressTotalList", param);
	}

	// 진행 중인 문서 대기 리스트 출력
	public List<Map<String, Object>> progressWaitList(Map<String, Object> param) {
		return db.selectList("ElectronicSignature.progressWaitList", param);
	}

	// 진행 중인 문서 확인 리스트 출력
	public List<Map<String, Object>> progressCheckList(Map<String, Object> param) {
		return db.selectList("ElectronicSignature.progressCheckList", param);
	}

	// 진행 중인 문서 예정 리스트 출력
	public List<Map<String, Object>> progressExpectedList(Map<String, Object> param) {
		return db.selectList("ElectronicSignature.progressExpectedList", param);
	}

	// 문서함 전체 리스트 출력
	public List<Map<String, Object>> documentList(Map<String, Object> param) {
		return db.selectList("ElectronicSignature.documentTotalList", param);
	}

	// 문서함 기안 리스트 출력
	public List<Map<String, Object>> documentDraftingList(Map<String, Object> param) {
		return db.selectList("ElectronicSignature.documentDraftingList", param);
	}

	// 문서함 결재 리스트 출력
	public List<Map<String, Object>> documentApprovalList(Map<String, Object> param) {
		return db.selectList("ElectronicSignature.documentApprovalList", param);
	}

	// 문서함 반려 리스트 출력
	public List<Map<String, Object>> documentRejectionList(Map<String, Object> param) {
		return db.selectList("ElectronicSignature.documentRejectList", param);
	}

	// 임시저장 리스트 출력
	public List<Map<String, Object>> temporaryList(Map<String, Object> param) {
		return db.selectList("ElectronicSignature.temporaryList", param);
	}

	// 문서 번호에 따른 결재 정보 출력
	public List<Map<String, Object>> selectAllByDocumentId(String document_id) {
		return db.selectList("ElectronicSignature.selectAllByDocumentId", document_id);
	}
	
	// 기안자들의 이름과 직급, 부서 가져오기
	public List<Map<String, Object>> getDraftersByDocumentId(String document_id) {
		return db.selectList("ElectronicSignature.getDraftersByDocumentId", document_id);
	}

	// 결재자들의 이름과 직급, 부서 가져오기
	public List<Map<String, String>> getApproversByDocumentId(String document_id) {
		return db.selectList("ElectronicSignature.getApproversByDocumentId", document_id);
	}

	// 로그인한 사용자가 기안자인지
	public boolean isDrafterByDocumentId(Map<String, String> param) {
		return db.selectOne("ElectronicSignature.isDrafterByDocumentId", param);
	}

	// 로그인한 사용자가 결재자인지
	public boolean isApproverByDocumentId(Map<String, String> param) {
		return db.selectOne("ElectronicSignature.isApproverByDocumentId", param);
	}

	// 휴가 신청서 정보 출력
	public List<Map<String, Object>> getVacationInfo(String document_id) {
		return db.selectList("ElectronicSignature.getVacationInfo", document_id);
	}

	// 대표 기안자의 부서 가져오기
	public Map<String, String> getMainDrafterDept(String document_id) {
		return db.selectOne("ElectronicSignature.getMainDrafterDept", document_id);
	}

	// 지출 결의서 정보 출력
	public Map<String, Object> getExpenseInfo(String document_id) {
		return db.selectOne("ElectronicSignature.getExpenseInfo", document_id);
	}

	// 개인 계좌 불러오기
	public Map<String, String> getPersonalAccount(String spender_id) {
		return db.selectOne("ElectronicSignature.getPersonalAccount", spender_id);
	}

	// 법인 계좌 불러오기
	public Map<String, String> getCorporateAccount(String spender_id) {
		return db.selectOne("ElectronicSignature.getCorporateAccount", spender_id);
	}

	// 업무 연락 정보 출력
	public Map<String, String> getBusinessInfo(String document_id) {
		return db.selectOne("ElectronicSignature.getBusinessInfo", document_id);
	}
	
	// 반려가 존재하는지
	public boolean existRejection(String loginID) {
		return db.selectOne("ElectronicSignature.existRejection", loginID);
	}
	
	// 이미 결재 내역이 존재하는지
	public boolean existMyApproval(Map<String, String> param) {
		return db.selectOne("ElectronicSignature.existMyApproval", param);
	}
	
	// 결재 결과 저장
	public int submitApproval(Map<String, String> param) {
		return db.update("ElectronicSignature.submitApproval", param);
	}
	
	// 마지막 결재자였는지 확인
	public boolean checkAllApprovals(String document_id) {
		return db.selectOne("ElectronicSignature.checkAllApprovals", document_id);
	}
	
	// 문서 상태 변경
	public int updateDocumentStatus(Map<String, String> param) {
		return db.update("ElectronicSignature.updateDocumentStatus", param);
	}
	
	// 문서 삭제
	public int deleteApproval(String document_id) {
		return db.delete("ElectronicSignature.deleteApproval", document_id);
	}
}
