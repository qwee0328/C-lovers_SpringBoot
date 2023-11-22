package com.clovers.controllers;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.clovers.constants.Constants;
import com.clovers.dto.EmailDTO;
import com.clovers.dto.EmailFileDTO;
import com.clovers.services.MailService;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@EnableScheduling
@RequestMapping("/mail")
public class MailController {
	// 메일 컨트롤러

	@Autowired
	private HttpSession session;

	@Autowired
	MailService mservice;

	// ---------- sideNavi ----------

	// 메인 화면
	@RequestMapping("")
	public String main() {
		String title = "메일";
		String currentMenu = "받은 편지함";

		session.setAttribute("title", title);
		session.setAttribute("currentMenu", currentMenu);

		return "mail/inBox";
	}

	// 편지 쓰기 (메일 작성)
	@RequestMapping("/send")
	public String send() {
		return "mail/send";
	}

	// 받은 편지함으로 이동
	@RequestMapping("/inBox")
	public String inBox() {
		String currentMenu = "받은 편지함";
		session.setAttribute("currentMenu", currentMenu);
		return "/mail/inBox";
	}

	// 보낸 편지함으로 이동
	@RequestMapping("/sentBox")
	public String sentBox() {
		String currentMenu = "보낸 편지함";
		session.setAttribute("currentMenu", currentMenu);
		return "/mail/sentBox";
	}

	// 임시 편지함으로 이동
	@RequestMapping("/tempBox")
	public String tempBox() {
		String currentMenu = "임시 편지함";
		session.setAttribute("currentMenu", currentMenu);
		return "/mail/tempBox";
	}

	// 보낼 편지함으로 이동
	@RequestMapping("/outBox")
	public String outBox() {
		String currentMenu = "보낼 편지함";
		session.setAttribute("currentMenu", currentMenu);
		return "/mail/outBox";
	}

	// 휴지통으로 이동
	@RequestMapping("/trash")
	public String trash() {
		String currentMenu = "휴지통";
		session.setAttribute("currentMenu", currentMenu);
		return "mail/trash";
	}

	// ---------- commons ----------

	// 파일 유무
	@ResponseBody
	@RequestMapping("/haveFile")
	public boolean haveFile(@RequestParam("email_id") int email_id) {
		return mservice.selectFileByEmailId(email_id);
	}

	// 답장
	@RequestMapping("/send/reply")
	public String replyMail(int id, Model model) {
		EmailDTO reply = mservice.selectAllById(id);

		model.addAttribute("reply", reply);
		model.addAttribute("isReply", true);

		return "/mail/send";
	}

	// 삭제 (휴지통)
	@RequestMapping("/deleteMail")
	public String deleteMail(@RequestParam("selectedMails[]") List<String> selectedMails) {
		for (int i = 0; i < selectedMails.size(); i++) {
			int id = Integer.parseInt(selectedMails.get(i));

			mservice.deleteMail(id);
		}
		return "redirect:/mail";
	}

	// 완전삭제
	@RequestMapping("/perDeleteMail")
	public String perDeleteMail(@RequestParam("selectedMails[]") List<String> selectedMails) throws Exception {
		for (int i = 0; i < selectedMails.size(); i++) {
			int id = Integer.parseInt(selectedMails.get(i));
			mservice.perDeleteMail(id);
		}
		return "redirect:/mail";
	}

	// 발송 시간 출력
	private String formatTimestamp(Timestamp time) {
		LocalDateTime currentTime = LocalDateTime.now();
		LocalDateTime sendTime = time.toLocalDateTime();

		// 시간 차이 계산
		Duration duration = Duration.between(sendTime, currentTime);
		long minutes = duration.toMinutes();
		long hours = duration.toHours();

		if (minutes < 60) {
			return minutes + "분 전";
		} else if (hours < 24) {
			return hours + "시간 전";
		} else {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			return sendTime.format(formatter);
		}
	}

	// ---------- inBox ----------

	// 받은 메일 리스트
	@ResponseBody
	@RequestMapping("/inBoxList")
	public Map<String, Object> inBoxList(@RequestParam("cpage") String cpage) {
		int currentPage = (cpage.isEmpty()) ? 1 : Integer.parseInt(cpage);

		// 로그인한 사용자의 이메일 가져오기
		String receive_id = mservice.getEmailByLoginID((String) session.getAttribute("loginID"));

		List<EmailDTO> mail = mservice.inBoxList(receive_id,
				(currentPage * Constants.RECORD_COUNT_PER_PAGE - (Constants.RECORD_COUNT_PER_PAGE - 1)),
				(currentPage * Constants.RECORD_COUNT_PER_PAGE));
		String[] send_date = new String[mail.size()];
		for (int i = 0; i < mail.size(); i++) {
			send_date[i] = formatTimestamp(mail.get(i).getSend_date());
		}

		Map<String, Object> param = new HashMap<>();
		param.put("mail", mail);
		param.put("send_date", send_date);
		param.put("recordTotalCount", mservice.inBoxTotalCount(receive_id));
		param.put("recordCountPerPage", Constants.RECORD_COUNT_PER_PAGE);
		param.put("naviCountPerPage", Constants.NAVI_COUNT_PER_PAGE);
		param.put("lastPageNum", currentPage);

		return param;
	}

	// ---------- sentBox ----------

	// 보낸 메일 리스트
	@ResponseBody
	@RequestMapping("/sentBoxList")
	public Map<String, Object> sentBoxList(@RequestParam("cpage") String cpage) {
		int currentPage = (cpage.isEmpty()) ? 1 : Integer.parseInt(cpage);

		// 로그인한 사용자의 이메일 가져오기
		String send_id = mservice.getEmailByLoginID((String) session.getAttribute("loginID"));
		boolean temporary = false;
		List<EmailDTO> mail = mservice.sentBoxList(send_id, temporary,
				(currentPage * Constants.RECORD_COUNT_PER_PAGE - (Constants.RECORD_COUNT_PER_PAGE - 1)),
				(currentPage * Constants.RECORD_COUNT_PER_PAGE));
		String[] send_date = new String[mail.size()];
		for (int i = 0; i < mail.size(); i++) {
			send_date[i] = formatTimestamp(mail.get(i).getSend_date());
		}

		Map<String, Object> param = new HashMap<>();
		param.put("mail", mail);
		param.put("send_date", send_date);
		param.put("recordTotalCount", mservice.sentBoxTotalCount(send_id, temporary));
		param.put("recordCountPerPage", Constants.RECORD_COUNT_PER_PAGE);
		param.put("naviCountPerPage", Constants.NAVI_COUNT_PER_PAGE);
		param.put("lastPageNum", currentPage);

		return param;
	}

	// ---------- tempBox ----------

	// 임시 메일 리스트
	@ResponseBody
	@RequestMapping("/tempBoxList")
	public Map<String, Object> tempBoxList(@RequestParam("cpage") String cpage) {
		int currentPage = (cpage.isEmpty()) ? 1 : Integer.parseInt(cpage);

		// 로그인한 사용자의 이메일 가져오기
		String send_id = mservice.getEmailByLoginID((String) session.getAttribute("loginID"));
		boolean temporary = true;
		List<EmailDTO> mail = mservice.sentBoxList(send_id, temporary,
				(currentPage * Constants.RECORD_COUNT_PER_PAGE - (Constants.RECORD_COUNT_PER_PAGE - 1)),
				(currentPage * Constants.RECORD_COUNT_PER_PAGE));
		Map<String, Object> param = new HashMap<>();
		param.put("mail", mail);
		param.put("recordTotalCount", mservice.sentBoxTotalCount(send_id, temporary));
		param.put("recordCountPerPage", Constants.RECORD_COUNT_PER_PAGE);
		param.put("naviCountPerPage", Constants.NAVI_COUNT_PER_PAGE);
		param.put("lastPageNum", currentPage);

		return param;
	}

	// 임시 메일 작성하기
	@RequestMapping("/send/rewrite")
	public String rewriteMail(int id, Model model) {
		EmailDTO reply = mservice.selectAllById(id);
		List<EmailFileDTO> fileList = new ArrayList<>();

		boolean haveFile = mservice.selectFileByEmailId(id);
		if (haveFile) {
			fileList = mservice.selectAllFileById(id);
			model.addAttribute("fileList", fileList);
		}

		model.addAttribute("reply", reply);

		return "/mail/send";
	}

	// ---------- outBoxList ----------

	// 보낼 메일 리스트
	@ResponseBody
	@RequestMapping("/outBoxList")
	public Map<String, Object> outBoxList(@RequestParam("cpage") String cpage) {
		int currentPage = (cpage.isEmpty()) ? 1 : Integer.parseInt(cpage);

		// 로그인한 사용자의 이메일 가져오기
		String send_id = mservice.getEmailByLoginID((String) session.getAttribute("loginID"));
		List<EmailDTO> mail = mservice.outBoxList(send_id,
				(currentPage * Constants.RECORD_COUNT_PER_PAGE - (Constants.RECORD_COUNT_PER_PAGE - 1)),
				(currentPage * Constants.RECORD_COUNT_PER_PAGE));
		Map<String, Object> param = new HashMap<>();
		param.put("mail", mail);
		param.put("recordTotalCount", mservice.outBoxTotalCount(send_id));
		param.put("recordCountPerPage", Constants.RECORD_COUNT_PER_PAGE);
		param.put("naviCountPerPage", Constants.NAVI_COUNT_PER_PAGE);
		param.put("lastPageNum", currentPage);

		return param;
	}

	// 예약시간에 전송
	@Scheduled(cron = "0 */5 * * * *") // 5분마다 실행
	public void checkAndSendScheduled() {
		List<EmailDTO> rList = mservice.selectAllReservationDate();

		if (rList != null) {
			for (int i = 0; i < rList.size(); i++) {
				EmailDTO dto = rList.get(i);

				LocalDateTime currentDateTime = LocalDateTime.now();
				LocalDateTime reservationDateTime = dto.getReservation_date().toLocalDateTime();

				if (currentDateTime.isEqual(reservationDateTime) || currentDateTime.isAfter(reservationDateTime)) {
					// 예약 날짜가 현재 시간과 같다면
					System.out.println("시간 같아용");
					mservice.submitReservationMail(dto.getId(), dto.getReservation_date()); // 실제로 send() 메서드를 호출하는 부분
				}
			}
		}
	}

	// ---------- read ----------

	// 메일 내용
	@RequestMapping("/read")
	public String read(@RequestParam("id") int id, Model model) {
		EmailDTO mail = mservice.selectAllById(id);
		model.addAttribute("mail", mail);
		return "/mail/read";
	}

	// 삭제
	@RequestMapping("/read/delete")
	public String deleteAtRead(@RequestParam int id) {
		mservice.deleteMail(id);
		return "redirect:/mail";
	}

	// 완전삭제
	@RequestMapping("/read/perDelete")
	public String perDeleteAtRead(@RequestParam int id) throws Exception {
		mservice.perDeleteMail(id);
		return "redirect:/mail";
	}

	// 읽음 처리
	@RequestMapping("/confirmation")
	public String confirmation(int id, String receive_id) {
		String getReceiveId = mservice.getEmailByLoginID((String) session.getAttribute("loginID"));

		// 로그인한 사용자의 이메일과 받는 사람의 이메일이 같다면
		if (getReceiveId.equals(receive_id)) {
			mservice.confirmation(id);
		}
		return "redirect:/mail/read?id=" + id;
	}

	// 파일 리스트
	@ResponseBody
	@RequestMapping("/fileList")
	public List<EmailFileDTO> fileList(@RequestParam("email_id") int email_id) {
		List<EmailFileDTO> fileList = new ArrayList<>();

		boolean result = mservice.selectFileByEmailId(email_id);
		if (result) {
			fileList = mservice.selectAllFileById(email_id);
		}
		return fileList;
	}

	// 파일 다운로드
	@RequestMapping("/downloadFile")
	public void downloadFile(@RequestParam String sysname, @RequestParam String oriname, HttpServletResponse response)
			throws Exception {
		String realPath = "C:/mailUploads";
		File targetFile = new File(realPath + "/" + sysname);

		oriname = new String(oriname.getBytes("utf8"), "ISO-8859-1");
		response.setHeader("content-disposition", "attachement;filename=" + oriname);
		byte[] fileContents = new byte[(int) targetFile.length()];

		try (DataInputStream dis = new DataInputStream(new FileInputStream(targetFile))) {
			ServletOutputStream sos = response.getOutputStream();
			dis.readFully(fileContents);
			sos.write(fileContents);
			sos.flush();
		}
	}

	// ---------- send ----------

	// 로그인한 사용자의 이메일을 보낸 사람으로 세팅
	@ResponseBody
	@RequestMapping("/getUserEmail")
	public String getUserEmail() {
		String loginID = (String) session.getAttribute("loginID");

		// 로그인한 사용자의 이메일 가져오기
		String send_id = mservice.getEmailByLoginID((String) session.getAttribute("loginID"));

		return send_id;
	}

	// 보내기 (메일 발송)
	@RequestMapping("/submitSend")
	public String submitSend(EmailDTO dto, String reserve_date, String sysName, MultipartFile[] uploadFiles,
			String deleteUrl) throws Exception {
		String[] receiveIds = dto.getReceive_id().split("\\s*[,;]\\s*"); // 정규식 \s*는 0개 이상의 공백을 말함
		for (int i = 0; i < receiveIds.length; i++) {
			dto.setReceive_id(receiveIds[i]);

			// 예약 메일이라면
			if (!reserve_date.isEmpty() && dto.isTemporary() == false) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = dateFormat.parse(reserve_date);
				Timestamp reservation_date = new Timestamp(date.getTime());

				dto.setReservation(true);
				dto.setReservation_date(reservation_date);
				mservice.submitSend(dto, uploadFiles);

				return "redirect:/mail";
			}

			dto.setReservation(false);
			dto.setSend_date(new Timestamp(System.currentTimeMillis()));

			// 임시 메일이라면
			if (dto.isTemporary() == true) {
				boolean send = true;
				mservice.submitTempSend(dto, sysName, uploadFiles, send);
			} else {
				dto.setTemporary(false);
				mservice.submitSend(dto, uploadFiles);
			}
		}

		System.out.println("deleteUrl: " + deleteUrl);
		// 삭제한 이미지가 있다면
		if (!deleteUrl.isEmpty()) {
			String[] urls = deleteUrl.split(":");
			for (int i = 1; i < urls.length; i++) {
				System.out.println("urls: " + urls[i]);
				deleteImage(urls[i]);
			}
		}

		return "redirect:/mail";
	}

	// 저장하기
	@RequestMapping("/tempSend")
	public String tempSend(EmailDTO dto, String sysName, MultipartFile[] uploadFiles) throws Exception {

		// 기존 메일 업데이트
		if (dto.getId() != 0) {
			boolean send = false;
			mservice.submitTempSend(dto, sysName, uploadFiles, false);
			// 새 메일 작성
		} else {
			dto.setTemporary(true);
			mservice.submitSend(dto, uploadFiles);
		}

		return "redirect:/mail";
	}

	// 받는 사람 자동완성
	@ResponseBody
	@RequestMapping("/autoComplete")
	public List<Map<String, String>> autoComplete(@RequestParam String keyword) {
		String search = "%" + keyword + "%";
		List<Map<String, String>> result = mservice.autoComplete(search);
		return mservice.autoComplete(search);
	}

	// summernote 이미지 경로 설정
	@ResponseBody
	@RequestMapping("/uploadImage")
	public List<String> uploadImage(@RequestParam("files") MultipartFile[] files) throws Exception {
		List<String> fileList = mservice.saveImage(files);
		return fileList;
	}

	// summernote 이미지 경로에서 삭제
	@RequestMapping("/deleteImage")
	public void deleteImage(@RequestParam("src") String src) throws Exception {
		Path path = FileSystems.getDefault().getPath("C:/" + src); // String을 Path 객체로 변환
		Files.deleteIfExists(path);
	}

	// ---------- trash ----------

	// 휴지통 리스트
	@ResponseBody
	@RequestMapping("/trashList")
	public Map<String, Object> trashList(@RequestParam("cpage") String cpage) {
		int currentPage = (cpage.isEmpty()) ? 1 : Integer.parseInt(cpage);

		String id = mservice.getEmailByLoginID((String) session.getAttribute("loginID"));
		List<EmailDTO> mail = mservice.trashList(id,
				(currentPage * Constants.RECORD_COUNT_PER_PAGE - (Constants.RECORD_COUNT_PER_PAGE - 1)),
				(currentPage * Constants.RECORD_COUNT_PER_PAGE));
		String[] send_date = new String[mail.size()];
		for (int i = 0; i < mail.size(); i++) {
			if (mail.get(i).getSend_date() != null) {
				send_date[i] = formatTimestamp(mail.get(i).getSend_date());
			} else {
				send_date[i] = "";
			}
		}

		Map<String, Object> param = new HashMap<>();
		param.put("mail", mail);
		param.put("send_date", send_date);
		param.put("recordTotalCount", mservice.trashTotalCount(id));
		param.put("recordCountPerPage", Constants.RECORD_COUNT_PER_PAGE);
		param.put("naviCountPerPage", Constants.NAVI_COUNT_PER_PAGE);
		param.put("lastPageNum", currentPage);

		return param;

	}

	// 복원 (휴지통 -> 받은 메일함)
	@RequestMapping("/restoreMail")
	public String restoreMail(@RequestParam("selectedMails[]") List<String> selectedMails) {
		for (int i = 0; i < selectedMails.size(); i++) {
			int id = Integer.parseInt(selectedMails.get(i));
			mservice.restoreMail(id);
		}
		return "redirect:/mail";
	}

	// ------------- 주소록에서 이메일 눌렀을 때 메일 창으로 이동
	@RequestMapping("/sendSetEmail")
	public String sendSetEmail(@RequestParam("addressEmail") String addressEmail, Model model) {
		EmailDTO reply = new EmailDTO();
		reply.setSend_id(addressEmail);
		model.addAttribute("reply", reply);
		return "/mail/send";
	}
}
