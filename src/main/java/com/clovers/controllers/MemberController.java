package com.clovers.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clovers.commons.EncryptionUtils;
import com.clovers.services.EmailService;
import com.clovers.services.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
//@RestController
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
//	@GetMapping("logout")
//	@ResponseBody
	public String logout(HttpServletRequest request) {
		
		session.invalidate();
//		return "";
		return "redirect:/";
		
	}
	
//	@GetMapping("adminLogout")
//	@ResponseBody
//	public void adminLogout(HttpServletRequest request) {
//		
//		session.invalidate();
////		return "";
////		return "redirect:/";
//		
//	}
	
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
		// 여기는 로그인을 안한 상태(session이 없음)로 비밀번호를 바꾸는 것
//		암호화한거
		String pwEnc = EncryptionUtils.getSHA512(pw);
		mservice.updatePW(id, pwEnc);
		
		
		return "redirect:/";
	}
	
	// 관리자 로그인 정보 불러오기
	@ResponseBody
	@GetMapping("/getUserInfo")
	public ResponseEntity<Map<String,String>> getUserInfo(){
		String loginID = (String) session.getAttribute("loginID");
		System.out.println(loginID);
		Map<String,String> userInfo = null;
		if(loginID!=null) {
			System.out.println("로그인 되어있음");
			userInfo = mservice.selectUserInfo(loginID);
		}else {
			System.out.println("로그인 안되어있음");
		}
		return ResponseEntity.ok(userInfo);
	}
	
//	changePW.jsp-> 비밀번호 변경 시
	@RequestMapping("/changePW")
	public String changePW(String pw) {
		// 여기는 로그인을 한 상태(session이 남아있음)로 비밀번호를 바꾸는 것
		String id = (String)session.getAttribute("loginID");
		String pwEnc = EncryptionUtils.getSHA512(pw);
		
		System.out.println(id+" : "+pw);
		
		mservice.updatePW(id,pwEnc);
		return "redirect:/humanResources/mypage";
	}
	
// 관리자 접근 권한 불러오기
	@ResponseBody
	@RequestMapping("/isAdmin")
	public List<String> getAuthorityCategory() {
		String id = (String)session.getAttribute("loginID");
		List<String> authority = mservice.getAuthorityCategory(id);
		for(int i = 0; i < authority.size(); i++) {	
			if(authority.get(i).equals("인사") || authority.get(i).equals("총괄")) {
				session.setAttribute("HumanResourcesAdmin", true);
			}
			if(authority.get(i).equals("전자결재") || authority.get(i).equals("총괄")) {
				session.setAttribute("ElectronicSignatureAdmin", true);
			}
		}
		
		return mservice.getAuthorityCategory(id);
	}
	
}

