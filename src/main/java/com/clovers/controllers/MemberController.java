package com.clovers.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clovers.commons.EncryptionUtils;
import com.clovers.services.EmailService;
import com.clovers.services.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/members/")
public class MemberController {
	// 멤버 로그인, 비밀번호 확인
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private MemberService mservice;
	
	@Autowired
	private EmailService EmailService;

	String num="";
	
//	로그인 페이지 이동
	@RequestMapping("goLogin")
	public String goLogin() {
		return "member/login";
	}
	
//	ajax 로 로그인 됐는지 안됐는지 확인
	@ResponseBody
	@RequestMapping(value="login", method = RequestMethod.POST)
	public boolean login(String id, String pw) {
		
//		암호화한거
		String pwEnc = EncryptionUtils.getSHA512(pw);
		boolean result = mservice.login(id, pwEnc);
		
		
		if(result) {
			session.setAttribute("loginID", id);
			System.out.println("login( ) : "+ session.getAttribute("loginID"));
		}
		
		return result;
	}
	
//	로그아웃
	@RequestMapping("logout")
	public String logout(HttpServletRequest request) {
		
		session.invalidate();
		
		return "redirect:/";
	}
	
//	비밀번호 페이지 이동
	@RequestMapping("goFindPW")
	public String goFindPW() {
		System.out.println("goFindPW ( )");
		return "member/findPW";
	}
	
//	이메일
	@ResponseBody
    @PostMapping("email")
    public String MailSend(String email){
		System.out.println(email);
    	
    	System.out.println("Cont- 이메일 전송 완료");

        int number = EmailService.sendMail(email);

        num = "" + number;

        return "이메일 성공";
    }
	
//	코드 확인
	@ResponseBody
	@PostMapping("emailChk")
	public String emailChk(String emailCode) {
		
		String emailSessionCode = session.getAttribute("emailCode")+"";
		
		if(emailCode.equals(emailSessionCode)) {
			return "true";
		}
		return "false";
		
	}
	
//	비밀번호 변경
	@RequestMapping("findPW")
	public String updatePW(String id, String pw) {
		
//		암호화한거
		String pwEnc = EncryptionUtils.getSHA512(pw);
		mservice.updatePW(id, pwEnc);
		
		
		return "redirect:/";
	}
	
//	관리자인지 확인
	@ResponseBody
	@RequestMapping("isManager")
	public List<String> isManager() {
		String id = (String)session.getAttribute("loginID");
		List<String> authority_category = new ArrayList<>();
		
		if(authority_category.size() > 0) {
			authority_category = mservice.isManager(id);
		}
		return authority_category;
	}

}
