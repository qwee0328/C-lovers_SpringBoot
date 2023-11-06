package com.clovers.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clovers.dto.EmailDTO;

@Repository
public class MailDAO {
	// 메일 DAO
	
	@Autowired
	private SqlSession db;
	
	public int submitSend(EmailDTO dto) {
		return db.insert("Mail.submitSend", dto);
	}
}
