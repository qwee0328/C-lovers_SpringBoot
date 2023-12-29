package com.clovers.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clovers.dao.MemberDAO;
import com.clovers.dto.AnnaulRestDTO;
import com.clovers.dto.MemberDTO;

import jakarta.servlet.http.HttpSession;

@Service
public class MemberService {
	@Autowired
	private MemberDAO mdao;

	public boolean login(String id, String pw) {

		Map<String, String> param = new HashMap<>();
		param.put("id", id);
		param.put("pw", pw);

		return mdao.login(param);
	}

	public int updatePW(String id, String pw) {
		Map<String, String> param = new HashMap<>();
		param.put("id", id);
		param.put("pw", pw);

		return mdao.updatePW(param);
	}

	public Map<String, String> selectUserInfo(String loginID) {
		return mdao.selectUserInfo(loginID);
	}

	public String selectNameById(String id) {
		return mdao.selectNameById(id);
	}

	public List<String> getAuthorityCategory(String id) {
		return mdao.getAuthorityCategory(id);
	}

	// 모든 사용자의 id랑 입사일 불러오기
	public List<MemberDTO> selectUserList() {
		return mdao.selectUserList();
	}

	// 사용자의 연차 기록이 있는지 불러오기
	public AnnaulRestDTO selectAnnaulRestById(String id) {
		return mdao.selectAnnaulRestById(id);
	}
	
	// 사용자 연차 기록 업데이트
	public void updateAutomaticAnnualRest(AnnaulRestDTO user) {
		mdao.updateAutomaticAnnualRest(user);
	}

	// 사용자의 연차 자동 등록
	public void insertAutomaticAnnaulRest(AnnaulRestDTO user) {
		mdao.insertAutomaticAnnaulRest(user);
	}
}
