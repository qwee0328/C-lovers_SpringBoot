package com.clovers.controllers;

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
		String naviBtn = "편지 쓰기";
		String naviBtnLocation = "send";
		String[] naviIcon = {"fa-inbox", "fa-paper-plane", "fa-box-archive", "fa-clock", "fa-trash"};
		String[] naviMenu = {"받은 편지함", "보낸 편지함", "임시 편지함", "보낼 편지함", "휴지통"}; 
		String [] naviMenuLocation = {"inBox", "sentBox", "tempBox", "outBox", "trash"};
		int naviMenuLength = naviMenu.length;
		String currentMenu = "받은 편지함";
		
		session.setAttribute("title", title);
		session.setAttribute("naviBtn", naviBtn);
		session.setAttribute("naviBtnLocation", naviBtnLocation);
		session.setAttribute("naviIcon", naviIcon);
		session.setAttribute("naviMenu", naviMenu);
		session.setAttribute("naviMenuLocation", naviMenuLocation);
		session.setAttribute("naviMenuLength", naviMenuLength);
		session.setAttribute("currentMenu", currentMenu);
		
		return "mail/inBox";
	}
	
	// 편지 쓰기 (메일 작성)
	@RequestMapping("/send")
	public String send() {
		return "mail/send";
	}
	
	// 보내기 (메일 발송)
	@RequestMapping("/submitSend")
	public String submitSend(EmailDTO dto, String reserve_date, String sysName, MultipartFile[] uploadFiles) throws Exception {
		
		// 예약 메일이라면
		if(!reserve_date.isEmpty()) {
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
		if(dto.isTemporary() == true) {
			mservice.submitTempSend(dto, sysName, uploadFiles);
		} else {
			dto.setTemporary(false);
			mservice.submitSend(dto, uploadFiles);
		}

		return "redirect:/mail";
	}
	
	// 저장하기
	@RequestMapping("/tempSend")
	public String tempSend(EmailDTO dto, MultipartFile[] uploadFiles) throws Exception {
		dto.setTemporary(true);
		mservice.submitSend(dto, uploadFiles);
		
		return "redirect:/mail";
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
	
	// 파일 리스트
	@ResponseBody
	@RequestMapping("/fileList")
	public List<EmailFileDTO> fileList(@RequestParam("email_id") int email_id) {
		List<EmailFileDTO> fileList = new ArrayList<>();
		
		boolean result = mservice.selectFileByEmailId(email_id);
		if(result) {
			fileList = mservice.selectAllFileById(email_id);
		} 
		
		return fileList;
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
		for(int i = 0; i < selectedMails.size(); i++) {
			int id = Integer.parseInt(selectedMails.get(i));
			mservice.deleteMail(id);
		}
		return "redirect:/mail";
	}
	
	// 완전삭제
	@RequestMapping("/perDeleteMail")
	public String perDeleteMail(@RequestParam("selectedMails[]") List<String> selectedMails) {
		for(int i = 0; i < selectedMails.size(); i++) {
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
		
		if(minutes < 60) {
			return minutes + "분 전";
		} else if(hours < 24) {
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
		
		String receive_id = (String) session.getAttribute("loginID");
		List<EmailDTO> mail = mservice.inBoxList(receive_id, (currentPage * Constants.RECORD_COUNT_PER_PAGE - (Constants.RECORD_COUNT_PER_PAGE-1)), (currentPage * Constants.RECORD_COUNT_PER_PAGE));
		String[] send_date = new String[mail.size()];
		for(int i = 0; i < mail.size(); i++) {
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
		
		String send_id = (String) session.getAttribute("loginID");
		boolean temporary = false;
		List<EmailDTO> mail = mservice.sentBoxList(send_id, temporary, (currentPage * Constants.RECORD_COUNT_PER_PAGE - (Constants.RECORD_COUNT_PER_PAGE-1)), (currentPage * Constants.RECORD_COUNT_PER_PAGE));
		String[] send_date = new String[mail.size()];
		for(int i = 0; i < mail.size(); i++) {
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
		
		String send_id = (String) session.getAttribute("loginID");
		boolean temporary = true;
		List<EmailDTO> mail = mservice.sentBoxList(send_id, temporary, (currentPage * Constants.RECORD_COUNT_PER_PAGE - (Constants.RECORD_COUNT_PER_PAGE-1)), (currentPage * Constants.RECORD_COUNT_PER_PAGE));
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
		if(haveFile) {
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
		
		String send_id = (String) session.getAttribute("loginID");
		List<EmailDTO> mail = mservice.outBoxList(send_id, (currentPage * Constants.RECORD_COUNT_PER_PAGE - (Constants.RECORD_COUNT_PER_PAGE-1)), (currentPage * Constants.RECORD_COUNT_PER_PAGE));
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
        	for(int i = 0; i < rList.size(); i++) {
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
	public String deleteAtRead(int id) {
		mservice.deleteMail(id);
		return "redirect:/mail";
	}
	
	// 완전삭제
	@RequestMapping("/read/perDelete")
	public String perDeleteAtRead(int id) {
		mservice.perDeleteMail(id);
		return "redirect:/mail";
	}
	
	// 읽음 처리
	@RequestMapping("/confirmation")
	public String confirmation(int id) {
		mservice.confirmation(id);
		return "redirect:/mail/read?id="+id;
	}
	
	
	// ---------- trash ----------
	
	// 휴지통 리스트
	@ResponseBody
	@RequestMapping("/trashList")
	public Map<String, Object> trashList(@RequestParam("cpage") String cpage) {
		int currentPage = (cpage.isEmpty()) ? 1 : Integer.parseInt(cpage);
		
		String id = (String) session.getAttribute("loginID");
		List<EmailDTO> mail = mservice.trashList(id, (currentPage * Constants.RECORD_COUNT_PER_PAGE - (Constants.RECORD_COUNT_PER_PAGE-1)), (currentPage * Constants.RECORD_COUNT_PER_PAGE));
		String[] send_date = new String[mail.size()];
		for(int i = 0; i < mail.size(); i++) {
			send_date[i] = formatTimestamp(mail.get(i).getSend_date());
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
		for(int i = 0; i < selectedMails.size(); i++) {
			int id = Integer.parseInt(selectedMails.get(i));
			mservice.restoreMail(id);
		}
		return "redirect:/mail";
	}
}
