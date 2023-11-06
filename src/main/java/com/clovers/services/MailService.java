package com.clovers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clovers.dao.MailDAO;
import com.clovers.dto.EmailDTO;

@Service
public class MailService {
	// 메일 서비스 레이어
	
	@Autowired
	MailDAO dao;
	
	public int submitSend(EmailDTO dto) {
		return dao.submitSend(dto);
	}
}
