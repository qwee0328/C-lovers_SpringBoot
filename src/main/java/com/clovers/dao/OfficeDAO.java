package com.clovers.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clovers.dto.DeptTaskDTO;
import com.clovers.dto.JobDTO;
import com.clovers.dto.MemberDTO;
import com.clovers.dto.OfficeDTO;

@Repository
public class OfficeDAO {
	// 오피스 관리 DAO
	@Autowired
	private SqlSession db;

	// 부서 명 불러오기
	public List<DeptTaskDTO> selectDeptTaskAll() {
		return db.selectList("Office.selectDeptTaskAll");
	}

	// 직급 명 불러오기
	public List<JobDTO> selectPositionAll() {
		return db.selectList("Office.selectPositionAll");
	}

	// 실제 db에 저장된 실 사용자 수 불러오기
	public int selectRealEmpCount() {
		return db.selectOne("Office.selectRealEmpCount");
	}

	// 사번 입력할때 사용할 사용자 수 불러오기 -> 삭제된 사용자까지 합쳐서 숫자 셈
	public int selectEmpCount() {
		return db.selectOne("Office.selectEmpCount");
	}

	// 사용자 리스트 불러오기
	public List<Map<String, String>> selectUserList() {
		return db.selectList("Office.selectUserList");
	}

	// 오피스 정보 가져오기
	public OfficeDTO selectOfficeInfo() {
		return db.selectOne("Office.selectOfficeInfo");
	}

	// 사내 전화번호 사용중인번호인지 체크
	public int usingCompanyPhoneCheck(String companyPhone) {
		return db.selectOne("Office.usingCompanyPhoneCheck", companyPhone);
	}

	// 사용자 등록하기
	public int insertUser(MemberDTO dto) {
		return db.insert("Office.insertUser", dto);
	}

	// 사용자 삭제하기
	public int deleteUser(List<String> userID) {
		return db.delete("Office.deleteUser", userID);
	}

	// 사용자 직위 수정하기
	public int updateUserJob(MemberDTO dto) {
		return db.update("Office.updateUserJob", dto);
	}

	// 오피스 이름 수정하기
	public int updateOfficeName(OfficeDTO dto) {
		return db.update("Office.updateOfficeName", dto);
	}

	// 사용자 소속 조직 수정하기
	public int updateUserDeptTask(MemberDTO dto) {
		return db.update("Office.updateUserDeptTask", dto);
	}

	// 사용자 이름, id 검색하기
	public List<Map<String, String>> searchUser(String keyword) {
		keyword = "%" + keyword + "%";
		return db.selectList("Office.searchUser", keyword);
	}

	// 부서별 부서명, 인원 수 불러오기
	public List<Map<String, Object>> selectDeptInfo() {
		return db.selectList("Office.selectDeptInfo");
	}

	// 부서별 팀별 인원 수 불러오기
	public List<Map<String, Object>> selectTaskInfo() {
		return db.selectList("Office.selectTaskInfo");
	}

	// 부서별 인원 정보 불러오기
	public List<Map<String, Object>> selectDepartmentEmpInfo(String dept_id) {
		return db.selectList("Office.selectDepartmentEmpInfo", dept_id);
	}

	// 모든 부서별 정보 불러오기 - 이름, 부서명, id
	public List<Map<String, Object>> selectAllEmpInfo() {
		return db.selectList("Office.selectAllEmpInfo");
	}
	
	// 팀별 인원 정보 불러오기 - 이름, 부서명, id
	public List<Map<String, Object>> selectDetpTaskEmpInfo(String task_id){
		return db.selectList("Office.selectDetpTaskEmpInfo",task_id);
	}
}
