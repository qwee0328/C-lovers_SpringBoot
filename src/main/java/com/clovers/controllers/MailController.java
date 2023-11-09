package com.clovers.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.clovers.dto.EmailDTO;
import com.clovers.dto.EmailFileDTO;
import com.clovers.services.MailService;

import jakarta.servlet.http.HttpSession;

@Controller
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
	public String submitSend(EmailDTO dto, String sysName, MultipartFile[] uploadFiles) throws Exception {
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
		return "/mail/inBox";
	}
	
	// 보낸 편지함으로 이동
	@RequestMapping("/sentBox")
	public String sentBox() {
		return "/mail/sentBox";
	}
	
	// 임시 편지함으로 이동
	@RequestMapping("/tempBox")
	public String tempBox() {
		return "/mail/tempBox";
	}
	
	// 휴지통으로 이동
	@RequestMapping("/trash")
	public String trash() {
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
	
	
	// ---------- inBox ----------
	
	// 받은 메일 리스트
	@ResponseBody
	@RequestMapping("/inBoxList")
	public List<EmailDTO> inBoxList() {
		String recieve_id = (String) session.getAttribute("loginID");
		return mservice.inBoxList(recieve_id);
	}
	
	
	// ---------- sentBox ----------
	
	// 보낸 메일 리스트
	@ResponseBody
	@RequestMapping("/sentBoxList")
	public List<EmailDTO> sentBoxList() {
		String send_id = (String) session.getAttribute("loginID");
		boolean temporary = false;
		return mservice.sentBoxList(send_id, temporary);
	}
	
	// ---------- tempBox ----------
	
	// 임시 메일 리스트
	@ResponseBody
	@RequestMapping("/tempBoxList")
	public List<EmailDTO> tempBoxList() {
		String send_id = (String) session.getAttribute("loginID");
		boolean temporary = true;
		return mservice.sentBoxList(send_id, temporary);
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
	
	
	// ---------- trash ----------
	
	// 휴지통 리스트
	@ResponseBody
	@RequestMapping("/trashList")
	public List<EmailDTO> trashList() {
		String id = (String) session.getAttribute("loginID");
		return mservice.trashList(id);
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
