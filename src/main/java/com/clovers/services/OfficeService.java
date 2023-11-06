package com.clovers.services;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clovers.commons.EncryptionUtils;
import com.clovers.dao.OfficeDAO;
import com.clovers.dto.DeptTaskDTO;
import com.clovers.dto.JobDTO;
import com.clovers.dto.MemberDTO;

@Service
public class OfficeService {
	// 오피스 관리 서비스 레이어
	@Autowired
	private OfficeDAO dao;

	public List<DeptTaskDTO> selectDeptTaskAll() {
		return dao.selectDeptTaskAll();
	}

	public List<JobDTO> selectPositionAll() {
		return dao.selectPositionAll();
	}

	public int insertUser(MemberDTO dto) {
		// 입사 년도 구하기
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		System.out.println(sdf.format(dto.getHire_date()));
		String year = sdf.format(dto.getHire_date());
		
		// 입사 순서 구하기
		int emp_num = dao.selectEmpCount() + 1;
		String joiningNumber = String.format("%03d", emp_num);
		
		// 사번 생성 및 아이디 설정
		// 사번은 입사년도+부서번호+입사 순서로 구성
		String id = year+dto.getDept_task_id()+joiningNumber;
		dto.setId(id);
		
		// 비밀번호는 이름으로 저장
		dto.setPw(EncryptionUtils.getSHA512(dto.getName()));
		
		return dao.insertUser(dto);
	}
}
