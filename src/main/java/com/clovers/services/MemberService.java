package com.clovers.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clovers.dao.MemberDAO;
import com.clovers.dto.MemberDTO;

@Service
public class MemberService {
	
	@Autowired
	private MemberDAO mdao;
	
	public boolean login(String id, String pw) {
		
		Map<String, String> param = new HashMap<>();
		param.put("id",id);
		param.put("pw", pw);
		
		return mdao.login(param);
	}
	
	public int updatePW(String id, String pw) {
		Map<String,String> param = new HashMap<>();
		param.put("id", id);
		param.put("pw", pw);
		
		return mdao.updatePW(param);
	}
	
	public Map<String,String> selectUserInfo(String loginID){
		return mdao.selectUserInfo(loginID);
	}
	
	public String selectNameById(String id) {
		return mdao.selectNameById(id);
	}
}
