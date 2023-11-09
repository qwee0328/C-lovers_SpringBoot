package com.clovers.services;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clovers.commons.EncryptionUtils;
import com.clovers.dao.OfficeDAO;
import com.clovers.dto.DeptTaskDTO;
import com.clovers.dto.JobDTO;
import com.clovers.dto.MemberDTO;
import com.clovers.dto.OfficeDTO;

@Service
public class OfficeService {
	// 오피스 관리 서비스 레이어
	@Autowired
	private OfficeDAO dao;

	// 부서 명 불러오기
	public List<DeptTaskDTO> selectDeptTaskAll() {
		return dao.selectDeptTaskAll();
	}

	// 직급 명 불러오기
	public List<JobDTO> selectPositionAll() {
		return dao.selectPositionAll();
	}

	// 사용자 수 불러오기
	public int selectEmpCount() {
		return dao.selectEmpCount();
	}
	
	// 오피스 정보 불러오기
	public OfficeDTO selectOfficeInfo() {
		return dao.selectOfficeInfo();
	}

	// 사용자 리스트 불러오기
	public List<Map<String, String>> selectUserList() {
		return dao.selectUserList();
	}

	// 사용자 등록하기
	public int insertUser(MemberDTO dto) {
		// 입사 년도 구하기
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(dto.getHire_date());

		// 입사 순서 구하기
		int emp_num = dao.selectEmpCount() + 1;
		String joiningNumber = String.format("%03d", emp_num);

		// 사번 생성 및 아이디 설정
		// 사번은 입사년도+부서번호+입사 순서로 구성
		String id = year + dto.getDept_task_id() + joiningNumber;
		dto.setId(id);

		String engKeyboardConversionName = EncryptionUtils.kR_EnKeyboardConversion(dto.getName());
		System.out.println(engKeyboardConversionName);

		// 비밀번호는 이름으로 저장
		dto.setPw(EncryptionUtils.getSHA512(engKeyboardConversionName));

		// 사내 이메일은 id랑 똑같이 저장
		dto.setCompany_email(dto.getId());

		return dao.insertUser(dto);
	}

	// 사용자 삭제하기
	public int deleteUser(List<String> userID) {
		return dao.deleteUser(userID);
	}

	// 오피스 이름 수정
	public void updateOfficeName(OfficeDTO dto) {
		dao.updateOfficeName(dto);
	}

	// 사용자 직위 수정하기
	public void updateUserJob(List<MemberDTO> dtoList) {
		for (MemberDTO dto : dtoList) {
			dao.updateUserJob(dto);
		}
	}

	// 사용자 소속 조직 수정하기
	public void updateUserDeptTask(List<MemberDTO> dtoList) {
		for (MemberDTO dto : dtoList) {
			dao.updateUserDeptTask(dto);
		}
	}

	// 사용자 이름, id 검색하기
	public List<Map<String, String>> searchUser(String keyword) {
		return dao.searchUser(keyword);
	}
}
