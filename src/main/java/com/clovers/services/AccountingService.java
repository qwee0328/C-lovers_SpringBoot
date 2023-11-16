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
	
	public int insert( AccountingDTO dto){
		return adao.insert(dto);
	}
	
	public int deleteAccount(String id){
		return adao.deleteAccount(id);
	}
	
	public int update(AccountingDTO dto){
		return adao.update(dto);
	}
	
	/////////
	public List<AccountingDTO> selectCardAll(){
		return adao.selectCardAll();
	}
	public List<AccountingDTO> searchCard(String keyword){
		return adao.searchCard(keyword);
	}
	public int insertCard( AccountingDTO dto){
		return adao.insertCard(dto);
	}
	public int deleteCard(String id){
		return adao.deleteCard(id);
	}
	public int updateCard(AccountingDTO dto){
		return adao.updateCard(dto);
	}
}
