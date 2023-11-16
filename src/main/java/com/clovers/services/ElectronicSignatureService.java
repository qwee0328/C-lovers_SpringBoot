package com.clovers.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clovers.dao.ElectronicSignatureDAO;

@Service
public class ElectronicSignatureService {
	// 전자결재 서비스 레이어
	@Autowired
	private ElectronicSignatureDAO dao;
	
	// 멤버의 전자 결재를 위한 전자선 정렬 -> job_id의 순서대로 정렬
	public List<Map<String, Object>> selectEmpJobLevel(List<String> processUserIDList){
		return dao.selectEmpJobLevel(processUserIDList);
	}
	
	// 휴가 문서 생성
	public int insertVacation(String emp_id) {
		Map<String, Object> paramMap = new HashMap<>();
	    paramMap.put("emp_id", emp_id);
	    
		int documentID = dao.insertVacation(paramMap);
		System.out.println("documentid:"+documentID);
		return 0;
	}
}
