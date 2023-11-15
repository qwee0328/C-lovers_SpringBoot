package com.clovers.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clovers.dto.EmailDTO;
import com.clovers.dto.EmailFileDTO;
import com.clovers.dto.MemberDTO;

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
	
	public List<EmailDTO> inBoxList(Map<String, Object> param) {
		return db.selectList("Mail.inBoxList", param);
	}
	
	public int inBoxTotalCount(String receive_id) {
		return db.selectOne("Mail.inBoxTotalCount", receive_id);
	}
	
	public List<EmailDTO> sentBoxList(Map<String, Object> param) {
		return db.selectList("Mail.sentBoxList", param);
	}
	
	public int sentBoxTotalCount(Map<String, Object> param) {
		return db.selectOne("Mail.sentBoxTotalCount", param);
	}
	
	public List<EmailDTO> outBoxList(Map<String, Object> param) {
		return db.selectList("Mail.outBoxList", param);
	}
	
	public int outBoxTotalCount(String send_id) {
		return db.selectOne("Mail.outBoxTotalCount", send_id);
	}
	
	public List<EmailDTO> trashList(Map<String, Object> param) {
		return db.selectList("Mail.trashList", param);
	}
	
	public int trashTotalCount(String id) {
		return db.selectOne("Mail.trashTotalCount", id);
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
	
	public int restoreMail(int id) {
		return db.update("Mail.restoreMail", id);
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
	
	public List<EmailDTO> selectAllReservationDate() {
		return db.selectList("Mail.selectAllReservationDate");
	}
	
	public int submitReservationMail(Map<String, Object> param) {
		return db.update("Mail.submitReservationMail", param);
	}
	
	public int confirmation(int id) {
		return db.update("Mail.confirmation", id);
	}
	
	public List<Map<String, String>> autoComplete(String keyword) {
		return db.selectList("Mail.autoComplete", keyword);
	}

	public String getEmailByLoginID(String loginID) {
		return db.selectOne("Mail.getEmailByLoginID", loginID);
	}
}
