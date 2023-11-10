package com.clovers.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

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
	
	public int submitTempSend(EmailDTO dto) {
		return db.update("Mail.submitTempSend", dto);
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
	
	public List<EmailDTO> sentBoxList(Map<String, Object> param) {
		return db.selectList("Mail.sentBoxList", param);
	}
	
	public EmailDTO selectAllById(int id) {
		return db.selectOne("Mail.selectAllById", id);
	}
	
	public List<EmailFileDTO> selectAllFileById(int email_id) {
		return db.selectList("Mail.selectAllFileById", email_id);
	}
	
	public int deleteFiles(String sys_name) {
		return db.delete("Mail.deleteBySysname", sys_name);
	}
	
	public List<EmailDTO> outBoxList(String send_id) {
		return db.selectList("Mail.outBoxList", send_id);
	}
	
	public List<EmailDTO> selectAllReservationDate() {
		return db.selectList("Mail.selectAllReservationDate");
	}
	
	public int submitReservationMail(Map<String, Object> param) {
		return db.update("Mail.submitReservationMail", param);
	}
}
