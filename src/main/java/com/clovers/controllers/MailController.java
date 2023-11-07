package com.clovers.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.clovers.dto.EmailDTO;
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
	
	// 메인 화면 (받은 메일함)
	@RequestMapping(value = {"", "/inBox"})
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
	
	// 받은 메일 리스트
	@ResponseBody
	@RequestMapping("inBoxList")
	public List<EmailDTO> inboxList() {
		String recieve_id = (String) session.getAttribute("loginID");
		return mservice.inboxList(recieve_id);
	}
	
	// 파일 유무
	@ResponseBody
	@RequestMapping("haveFile")
	public boolean haveFile(@RequestParam("email_id") String email_id) {
		boolean result = mservice.selectFileByEmailId(email_id);
		System.out.println(result);
		return result;
	}
	
	// 편지 쓰기 (메일 작성)
	@RequestMapping("send")
	public String send() {
		return "mail/send";
	}
	
	// 보내기 (메일 발송)
	@RequestMapping("submitSend")
	public String submitSend(EmailDTO dto, MultipartFile[] files) throws Exception {
		int email_id = mservice.submitSend(dto, files);
		
		return "redirect:/mail";
	}
	
	// 삭제 (휴지통)
	@RequestMapping("deleteMail")
	public String deleteMail(@RequestParam("selectedMails[]") List<String> selectedMails) {
		for(int i = 0; i < selectedMails.size(); i++) {
			int id = Integer.parseInt(selectedMails.get(i));
			mservice.deleteMail(id);
		}
		
		return "redirect:/mail";
	}
	
	
}
