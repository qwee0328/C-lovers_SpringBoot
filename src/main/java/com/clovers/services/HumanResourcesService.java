package com.clovers.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clovers.dao.HumanResourcesDAO;

@Service
public class HumanResourcesService {
	// 인사 서비스 레이어
	@Autowired
	private HumanResourcesDAO dao;
	
	// 사용자 근무 규칙 정보 불러오기
	public Map<String, String> selectEmployeeWorkRule(String id) {
		return dao.selectEmployeeWorkRule(id);
	}
}
