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

	// 진행 중인 문서 전체 리스트 출력
	public List<Map<String, Object>> progressTotalList(String loginID) {
		return db.selectList("ElectronicSignature.progressTotalList", loginID);
	}
}
