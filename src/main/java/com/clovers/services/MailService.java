package com.clovers.services;

import java.io.File;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.clovers.dao.MailDAO;
import com.clovers.dto.EmailDTO;
import com.clovers.dto.EmailFileDTO;
import com.clovers.dto.MemberDTO;

@Service
public class MailService {
	// 메일 서비스 레이어
	
	@Autowired
	MailDAO dao;
	
	@Transactional
	public int submitSend(EmailDTO dto, MultipartFile[] files) throws Exception {
		int email_id = dao.submitSend(dto);
		
		String upload = "/Users/uploads";
		File uploadPath = new File(upload);
		if(!uploadPath.exists()) {uploadPath.mkdir();} // 만약 업로드 폴더가 존재하지 않는다면 생성
		
		if(!files[0].getOriginalFilename().equals("")) { // 클라이언트에서 submit한 데이터에 files가 존재할 경우
			for(MultipartFile file : files) {
			String oriName = file.getOriginalFilename();
			String sysName = UUID.randomUUID() + "_" + oriName; // UUID.randomUUID() : String 값 반환 (해시코드와 비슷)
			file.transferTo(new File(uploadPath+"/"+sysName)); // file을 uploadPath로 보냄(sysName도 같이 보내기 위해 new 생성)
			dao.submitFile(new EmailFileDTO(0, email_id, oriName, sysName));
			}
		}
		return email_id;
	}
	
	@Transactional
	public void submitTempSend(EmailDTO dto, String deleteSysName, MultipartFile[] files) throws Exception {
		dao.submitTempSend(dto);
		int email_id = dto.getId();
		
		String upload = "/Users/uploads";
		File uploadPath = new File(upload);
		if(!uploadPath.exists()) {uploadPath.mkdir();} // 만약 업로드 폴더가 존재하지 않는다면 생성
		
		if(!files[0].getOriginalFilename().equals("")) { // 클라이언트에서 submit한 데이터에 files가 존재할 경우
			for(MultipartFile file : files) {
			String oriName = file.getOriginalFilename();
			String sysName = UUID.randomUUID() + "_" + oriName; // UUID.randomUUID() : String 값 반환 (해시코드와 비슷)
			file.transferTo(new File(uploadPath+"/"+sysName)); // file을 uploadPath로 보냄(sysName도 같이 보내기 위해 new 생성)
			dao.submitFile(new EmailFileDTO(0, email_id, oriName, sysName));
			}
		}
		
		String[] deleteFiles = deleteSysName.split(":");
		for(int i = 0; i < deleteFiles.length; i++) {
			String deleteRealPath = upload;
			File targetFile = new File(upload + "/" + deleteFiles[i]);
			targetFile.delete();
			
			dao.deleteFiles(deleteFiles[i]);
		}
	}
	
	public List<EmailDTO> inBoxList(String receive_id, int start, int end) {
		Map<String, Object> param = new HashMap<>();
		param.put("receive_id", receive_id);
		param.put("start", start);
		param.put("end", end);
		return dao.inBoxList(param);
	}
	
	public int inBoxTotalCount(String receive_id) {
		return dao.inBoxTotalCount(receive_id);
	}
	
	public List<EmailDTO> sentBoxList(String send_id, boolean temporary, int start, int end) {
		Map<String, Object> param = new HashMap<>();
		param.put("send_id", send_id);
		param.put("temporary", temporary);
		param.put("start", start);
		param.put("end", end);
		return dao.sentBoxList(param);
	}
	
	public int sentBoxTotalCount(String send_id, boolean temporary) {
		Map<String, Object> param = new HashMap<>();
		param.put("send_id", send_id);
		param.put("temporary", temporary);
		return dao.sentBoxTotalCount(param);
	}
	
	public List<EmailDTO> outBoxList(String send_id, int start, int end) {
		Map<String, Object> param = new HashMap<>();
		param.put("send_id", send_id);
		param.put("start", start);
		param.put("end", end);
		return dao.outBoxList(param);
	}
	
	public int outBoxTotalCount(String send_id) {
		return dao.outBoxTotalCount(send_id);
	}
	
	public List<EmailDTO> trashList(String id, int start, int end) {
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("start", start);
		param.put("end", end);
		return dao.trashList(param);
	}
	
	public int trashTotalCount(String id) {
		return dao.trashTotalCount(id);
	}
	
	public boolean selectFileByEmailId(int email_id) {
		return dao.selectFileByEmailId(email_id);
	}
	
	public int deleteMail(int id) {
		return dao.deleteMail(id);
	}
	
	public int perDeleteMail(int id) {
		return dao.perDeleteMail(id);
	}
	
	public int restoreMail(int id) {
		return dao.restoreMail(id);
	}
	
	public EmailDTO selectAllById(int id) {
		return dao.selectAllById(id);
	}
	
	public List<EmailFileDTO> selectAllFileById(int email_id) {
		return dao.selectAllFileById(email_id);
	}
	
	public List<EmailDTO> selectAllReservationDate() {
		return dao.selectAllReservationDate();
	}
	
	public int submitReservationMail(int id, Timestamp send_date) {
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("send_date", send_date);
		return dao.submitReservationMail(param);
	}
	
	public int confirmation(int id) {
		return dao.confirmation(id);
	}
	
	public List<MemberDTO> autoComplete(String keyword) {
		return dao.autoComplete(keyword);
	}
		
}
