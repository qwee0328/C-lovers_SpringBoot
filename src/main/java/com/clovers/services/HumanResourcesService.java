package com.clovers.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clovers.dao.HumanResourcesDAO;
import com.clovers.dto.MemberDTO;

@Service
public class HumanResourcesService {
	// 인사 서비스 레이어
	
	@Autowired
	private HumanResourcesDAO hrdao;
	
//	public String selectByIdGetName(String id) {
//		return hrdao.selectByIdGetName(id);
//	}
	
	public MemberDTO selectById(String id) {
		return hrdao.selectById(id);
	}
	
	public String reChangePw(String id) {
		return hrdao.reChangePw(id);
	}
	
	public int update(String id,String profile_img,String company_phone,String phone,String email) {
		
		Map<String,String> param = new HashMap<>();
		param.put("id", id);
		param.put("profile_img", profile_img);
		param.put("company_phone", company_phone);
		param.put("phone", phone);
		param.put("email", email);
		
		return hrdao.update(param);
	}
}
