package com.clovers.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	// 실제 db에 저장된 실 사용자 수 불러오기
	public int selectRealEmpCount() {
		return dao.selectRealEmpCount();
	}

	// 사번 입력할때 사용할 사용자 수 불러오기 -> 삭제된 사용자까지 합쳐서 숫자 셈
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

	// 사내 전화번호 사용중인번호인지 체크
	public int usingCompanyPhoneCheck(String companyPhone) {
		return dao.usingCompanyPhoneCheck(companyPhone);
	}

	// 사용자 등록하기
	@Transactional
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

		// 입사일을 timestamp값으로 변경
		System.out.println(dto.getHire_date());

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

	// 부서별 부서명, 인원 수 불러오기
	public List<Map<String, Object>> selectDeptInfo() {
		return dao.selectDeptInfo();
	}

	// 부서별 팀별 인원 수 불러오기
	public List<Map<String, Object>> selectTaskInfo() {
		return dao.selectTaskInfo();
	}

	// 부서별 인원 정보 불러오기
	public List<Map<String, Object>> selectDepartmentEmpInfo(String dept_id) {
		return dao.selectDepartmentEmpInfo(dept_id);
	}

	// 모든 부서별 정보 불러오기 - 이름, 부서명, id
	public List<Map<String, Object>> selectAllEmpInfo() {
		return dao.selectAllEmpInfo();
	}
	
	// 팀별 인원 정보 불러오기 - 이름, 부서명, id
	public List<Map<String, Object>> selectDetpTaskEmpInfo(String task_id){
		return dao.selectDetpTaskEmpInfo(task_id);
	}
	
	// 팀별(생산1팀,2팀..)인원수, 부서명
	public List<Map<String, Object>> selectAllTaskNameEmpo(){
		return dao.selectAllTaskNameEmpo();
	}
	
	// 임직원 정보에서 부서 클릭하면 정보
	public List<Map<String, Object>> selectDeptEmpo(){
		return dao.selectDeptEmpo();
	}
	
	// 부서 클릭하면 정보 
	public List<Map<String, Object>> selectByDeptName(String dept_name){
		return dao.selectByDeptName(dept_name);
	}
	
	// 팀 클릭하면 정보
	public List<Map<String, Object>> selectByTaskName(String task_name){
		return dao.selectByTaskName(task_name);
	}
	
	// 임직원 검색
	public List<Map<String, Object>> searchByName(String name){
		return dao.searchByName(name);
	}
}
