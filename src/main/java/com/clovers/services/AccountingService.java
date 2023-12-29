package com.clovers.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.clovers.dao.AccountingDAO;
import com.clovers.dto.AccountingDTO;

@Service
public class AccountingService {
	// 회계지원 서비스 레이어
	
	@Autowired
	private AccountingDAO adao;
	
	public List<AccountingDTO> selectAccountAll(){
		return adao.selectAccountAll();
	}
	
	public List<AccountingDTO> searchBy(String keyword){
		return adao.searchBy(keyword);
	}
	// 계좌 추가
	public String insert(AccountingDTO dto){
		String emp_id = dto.getEmp_id();
		
		// 계좌 추가 전에 유효한 사번인지 아닌지 확인 1:사원 맞음
		int isEmployee = adao.isEmployee(emp_id);
		
		// 직원이 맞음
		if(isEmployee==1) {
			
			// 1 이면 이미 등록 됨
			int isEmpId = adao.isEmpId(emp_id);
			
			// 계좌가 등록된 직원이 아니면
			if(isEmpId == 0) {
					adao.insert(dto); // 1 반환
				return "성공";
			}else {
				return "중복된 사용자는 등록할 수 없습니다.";
			}
		}else {
			return "사번이 등록되어있지 않습니다.";
		}
	}
	
	public int deleteAccount(String id){
		return adao.deleteAccount(id);
	}
	
	public int update(AccountingDTO dto){
		return adao.update(dto);
	}
	
	/////법인카드////
	// 카드 리스트 전부 부르기
	public List<AccountingDTO> selectCardAll(){
		return adao.selectCardAll();
	}
	// 검색
	public List<AccountingDTO> searchCard(String keyword){
		return adao.searchCard(keyword);
	}
	// 카드 추가
	public String insertCard( AccountingDTO dto){
		String emp_id = dto.getEmp_id();
		
		// 사번이 등록되어있는지 아닌지 확인
		int isEmployee = adao.isEmployee(emp_id);
		
		// 직원이 맞음
		if(isEmployee==1) {
			
			// 카드번호(유니크키) 중복 확인
			String cardNum = dto.getId();
			int cardResult = adao.isAlreadyCardNum(cardNum);
			
			// 카드번호가 중복 X
			if(cardResult ==0) {
				
				// 사번 등록되었는지 확인
				int isEmpId = adao.isEmpCardId(emp_id);
				
				// 사번 중복 X
				if(isEmpId == 0) {
					adao.insertCard(dto);
					return "성공";
				}else {
					return "중복된 사용자는 등록할 수 없습니다.";
				}
			}else {
				return "카드번호를 다시 확인해주세요";
			}
			
		}else { // 직원이 아님(사번이 등록 X)
			return "사번이 등록되어있지 않습니다.";
		}
		
		
	}
	// 카드 삭제
	public int deleteCard(String id){
		return adao.deleteCard(id);
	}
	// 카드 수정
	public int updateCard(AccountingDTO dto){
		return adao.updateCard(dto);
	}
}
