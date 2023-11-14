package com.clovers.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
