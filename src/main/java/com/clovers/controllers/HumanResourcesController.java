package com.clovers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.clovers.commons.EncryptionUtils;
import com.clovers.dto.MemberDTO;
import com.clovers.services.HumanResourcesService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/humanResources")
public class HumanResourcesController {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private HumanResourcesService hrService;
	
	// 인사 컨트롤러
	
	// view로 mypage에 필요한 정보 가져오기
	@RequestMapping("/mypage")
	public String select(Model model) {
		
		String id = (String)session.getAttribute("loginID");
		
//		String name = hrService.selectByIdGetName(id);
//		model.addAttribute("name", name);
		
		MemberDTO list = hrService.selectById(id);
		model.addAttribute("list",list);
		
		return "humanresources/myPage";
	}
	
//	비밀번호 변경안했으면 변경하게..
	@ResponseBody
	@RequestMapping("/recommendChangPw")
	public String reChangePw(String id,String name) {
		System.out.println(id+" : "+name);
		String pw = hrService.reChangePw(id);
		
		String enPw = EncryptionUtils.getSHA512(EncryptionUtils.kR_EnKeyboardConversion(name));
		
		if(pw.equals(enPw)) {
			return "변경추천";
		}
		return "변경완료";
		
		
	}
	
//	비밀번호 변경하는 페이지로 이동
	@RequestMapping("/goChangePw")
	public String goChangePw() {
		return "member/changePW";
	}
	
	
//	사내전화,휴대전화,개인이메일 정보 업데이트
	@RequestMapping("/update")
	public String update(MultipartFile profile_img,String company_phone,String phone,String email) {
		
		String id = (String)session.getAttribute("loginID");
		
		
		System.out.println(profile_img);
		
		
		
		
		//hrService.update(id,profile_img,company_phone,phone,email);
		
		return "redirect:/humanResources/mypage";
	}
	
}
