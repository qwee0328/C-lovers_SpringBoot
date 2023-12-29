package com.clovers.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	// 메일 전송
	@Transactional
	public int submitSend(EmailDTO dto, MultipartFile[] files) throws Exception {
		int email_id = dao.submitSend(dto);

		String upload = "C:/mailUploads";
		File uploadPath = new File(upload);
		if (!uploadPath.exists()) {
			uploadPath.mkdir();
		} // 만약 업로드 폴더가 존재하지 않는다면 생성

		if (!files[0].getOriginalFilename().equals("")) { // 클라이언트에서 submit한 데이터에 files가 존재할 경우
			for (MultipartFile file : files) {
				String oriName = file.getOriginalFilename();
				String sysName = UUID.randomUUID() + "_" + oriName; // UUID.randomUUID() : String 값 반환 (해시코드와 비슷)
				file.transferTo(new File(uploadPath + "/" + sysName)); // file을 uploadPath로 보냄(sysName도 같이 보내기 위해 new
																		// 생성)
				dao.submitFile(new EmailFileDTO(0, email_id, oriName, sysName));
			}
		}
		return email_id;
	}

	// 임시 메일 저장
	@Transactional
	public void submitTempSend(EmailDTO dto, String deleteSysName, MultipartFile[] files, boolean send)
			throws Exception {
		int email_id = dto.getId();

		// 임시 보관함 -> 보내기
		if (send == true) {
			dao.submitTempSend(dto);
			// 임시 보관함 -> 저장
		} else {
			dao.updateMail(dto);
		}

		String upload = "C:/mailUploads";
		File uploadPath = new File(upload);
		if (!uploadPath.exists()) {
			uploadPath.mkdir();
		} // 만약 업로드 폴더가 존재하지 않는다면 생성

		if (!files[0].getOriginalFilename().equals("")) { // 클라이언트에서 submit한 데이터에 files가 존재할 경우
			for (MultipartFile file : files) {
				String oriName = file.getOriginalFilename();
				String sysName = UUID.randomUUID() + "_" + oriName; // UUID.randomUUID() : String 값 반환 (해시코드와 비슷)
				file.transferTo(new File(uploadPath + "/" + sysName)); // file을 uploadPath로 보냄(sysName도 같이 보내기 위해 new
																		// 생성)
				dao.submitFile(new EmailFileDTO(0, email_id, oriName, sysName));
			}
		}

		String[] deleteFiles = deleteSysName.split(":");
		for (int i = 0; i < deleteFiles.length; i++) {
			String deleteRealPath = upload;
			File targetFile = new File(upload + "/" + deleteFiles[i]);
			targetFile.delete();

			dao.deleteFiles(deleteFiles[i]);
		}
	}

	// 받은 메일함 리스트 출력
	public List<EmailDTO> inBoxList(String receive_id, int start, int end) {
		Map<String, Object> param = new HashMap<>();
		param.put("receive_id", receive_id);
		param.put("start", start);
		param.put("end", end);
		return dao.inBoxList(param);
	}

	// 받은 메일함 총 개수
	public int inBoxTotalCount(String receive_id) {
		return dao.inBoxTotalCount(receive_id);
	}

	// 보낸 메일함 리스트 출력
	public List<EmailDTO> sentBoxList(String send_id, boolean temporary, int start, int end) {
		Map<String, Object> param = new HashMap<>();
		param.put("send_id", send_id);
		param.put("temporary", temporary);
		param.put("start", start);
		param.put("end", end);
		return dao.sentBoxList(param);
	}

	// 보낸 메일함 총 개수
	public int sentBoxTotalCount(String send_id, boolean temporary) {
		Map<String, Object> param = new HashMap<>();
		param.put("send_id", send_id);
		param.put("temporary", temporary);
		return dao.sentBoxTotalCount(param);
	}

	// 보낼 메일함 리스트 출력
	public List<EmailDTO> outBoxList(String send_id, int start, int end) {
		Map<String, Object> param = new HashMap<>();
		param.put("send_id", send_id);
		param.put("start", start);
		param.put("end", end);
		return dao.outBoxList(param);
	}

	// 보낼 메일함 총 개수
	public int outBoxTotalCount(String send_id) {
		return dao.outBoxTotalCount(send_id);
	}

	// 휴지통 리스트 출력
	public List<EmailDTO> trashList(String id, int start, int end) {
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("start", start);
		param.put("end", end);
		return dao.trashList(param);
	}

	// 휴지통 총 개수
	public int trashTotalCount(String id) {
		return dao.trashTotalCount(id);
	}

	// 첨부파일 유무
	public boolean selectFileByEmailId(int email_id) {
		return dao.selectFileByEmailId(email_id);
	}

	// 메일 삭제
	public int deleteMail(int id, String loginEmail) {
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("loginEmail", loginEmail);
		return dao.deleteMail(param);
	}
	
	// 발신자 또는 수신자가 완전삭제
	public int semiPerDeleteMail(int id, String loginEmail) {
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("loginEmail", loginEmail);
		return dao.semiPerDeleteMail(param);
	}
	
	// 발신자, 수신자 모두 완전삭제
	public boolean perDeleteBoth(int id) {
		return dao.perDeleteBoth(id);
	}

	// 발송 취소 & 발신자/수신자 모두 완전삭제
	@Transactional
	public int perDeleteMail(int id) throws Exception {
		String upload = "C:/mailUploads";
		List<EmailFileDTO> fileList = dao.selectAllFileById(id);

		// 파일 삭제
		for (EmailFileDTO file : fileList) {
			File filepath = new File(upload + "/" + file.getSys_name());
			filepath.delete();

			dao.deleteFiles(file.getSys_name());
		}

		// 이미지 삭제
		String content = dao.selectContentById(id);
		String pattern = "<img src=\"(.*?)\">"; // 이미지 태그 안의 src 뽑아냄
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(content);

		while (m.find()) {
			String getUrl = m.group(1);
			Path path = FileSystems.getDefault().getPath("C:" + getUrl);
			Files.deleteIfExists(path);
		}
		return dao.perDeleteMail(id);
	}

	// 메일 복구
	public int restoreMail(int id, String loginEmail) {
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("loginEmail", loginEmail);
		return dao.restoreMail(param);
	}

	// 메일 내용 가져오기
	public EmailDTO selectAllById(int id) {
		return dao.selectAllById(id);
	}

	// 파일 리스트 출력
	public List<EmailFileDTO> selectAllFileById(int email_id) {
		return dao.selectAllFileById(email_id);
	}

	// 예약 메일 내용 가져오기
	public List<EmailDTO> selectAllReservationDate() {
		return dao.selectAllReservationDate();
	}

	// 예약 메일 전송
	public int submitReservationMail(int id, Timestamp send_date) {
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("send_date", send_date);
		return dao.submitReservationMail(param);
	}

	// 읽음 유무
	public int confirmation(int id) {
		return dao.confirmation(id);
	}

	// 받는 사람 자동완성
	public List<Map<String, String>> autoComplete(String keyword) {
		return dao.autoComplete(keyword);
	}

	// 로그인한 사용자의 이메일
	public String getEmailByLoginID(String loginID) {
		return dao.getEmailByLoginID(loginID);
	}

	// summernote 이미지 저장
	public List<String> saveImage(MultipartFile[] image) throws Exception {
		String upload = "C:/mailUploads";
		File uploadPath = new File(upload);
		if (!uploadPath.exists()) {
			uploadPath.mkdir();
		}

		List<String> fileList = new ArrayList<>();

		if (image != null) {
			for (MultipartFile file : image) {
				String oriName = file.getOriginalFilename();
				String sysName = UUID.randomUUID() + "_" + oriName; // UUID.randomUUID() : String 값 반환 (해시코드와 비슷)
				file.transferTo(new File(uploadPath + "/" + sysName));
				fileList.add("/mailUploads/" + sysName);
			}
		}
		return fileList;
	}

	// 로그인한 사용자의 이메일 가져오기
	public String getUserEmail(String loginID) {
		return dao.getUserEmail(loginID);
	}
	
	// 존재하는 이메일인지
	public boolean existEmail(String email) {
		return dao.existEmail(email);
	}
}
