package com.clovers.services;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.clovers.dao.MailDAO;
import com.clovers.dto.EmailDTO;
import com.clovers.dto.EmailFileDTO;

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
	
	public List<EmailDTO> inboxList(String recieve_id) {
		return dao.inboxList(recieve_id);
	}
	
	public boolean selectFileByEmailId(String email_id) {
		return dao.selectFileByEmailId(email_id);
	}
	
	public int deleteMail(int id) {
		return dao.deleteMail(id);
	}
	
	public int perDeleteMail(int id) {
		return dao.perDeleteMail(id);
	}
	
	public List<EmailDTO> trashList(String recieve_id) {
		return dao.trashList(recieve_id);
	}
	
	public int restoreMail(int id) {
		return dao.restoreMail(id);
	}
	
}
