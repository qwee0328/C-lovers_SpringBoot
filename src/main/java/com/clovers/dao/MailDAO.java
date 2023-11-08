package com.clovers.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clovers.dto.EmailDTO;
import com.clovers.dto.EmailFileDTO;

@Repository
public class MailDAO {
	// 메일 DAO
	
	@Autowired
	private SqlSession db;
	
	public int submitSend(EmailDTO dto) {
		db.insert("Mail.submitSend", dto);
		return dto.getId();
	}
	
	public int submitFile(EmailFileDTO dto) {
		return db.insert("Mail.submitFile", dto);
	}
	
	public List<EmailDTO> inBoxList(String recieve_id) {
		return db.selectList("Mail.inBoxList", recieve_id);
	}
	
	public boolean selectFileByEmailId(int email_id) {
		Object result = db.selectOne("Mail.selectFileByEmailId", email_id);
		boolean exist;
		if(result != null) {
			exist = true;
		} else {
			exist = false;
		}
		return exist;
	}
	
	public int deleteMail(int id) {
		return db.update("Mail.deleteMail", id);
	}
	
	public int perDeleteMail(int id) {
		return db.delete("Mail.perDeleteMail", id);
	}
	
	public List<EmailDTO> trashList(String id) {
		return db.selectList("Mail.trashList", id);
	}
	
	public int restoreMail(int id) {
		return db.update("Mail.restoreMail", id);
	}
	
	public List<EmailDTO> sentBoxList(String send_id) {
		return db.selectList("Mail.sentBoxList", send_id);
	}
	
	public EmailDTO selectAllById(int id) {
		return db.selectOne("Mail.selectAllById", id);
	}
	
	public List<EmailFileDTO> selectAllFileById(int email_id) {
		return db.selectList("Mail.selectAllFileById", email_id);
	}
}
