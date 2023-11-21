package com.clovers.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clovers.dto.DepartmentDTO;

// DepartmentDAO 클래스는 데이터베이스의 부서 정보를 관리하기 위한 데이터 접근 객체
@Repository
public class DepartmentDAO {

	@Autowired
	private SqlSession db; // MyBatis의 SqlSession을 자동 주입받아 데이터베이스 연결 및 쿼리 실행을 관리

	// 삽입: 부서 정보를 데이터베이스에 삽입하고 결과로 영향 받은 행의 수를 반환
	public int insert(DepartmentDTO dto) {
		return db.insert("Department.insert", dto);
	}

	// 조회: 모든 부서 정보를 조회하는 메서드입니다
	public List<DepartmentDTO> selectAll() {
		return db.selectList("Department.selectAll");
	}

	// 조회: 주어진 아이디로 부서 정보를 조회하여 반환
	public DepartmentDTO selectById(String id) {
		return db.selectOne("Department.selectById", id);
	}

	// 조회: 부서명으로 부서 아이디를 조회하여 반환
	public String selectIdByDeptName(String dept_name) {
		return db.selectOne("Department.selectIdByDeptName", dept_name);
	}

	// 조회: 최신 아이디를 조회하며, 동시에 해당 행에 대한 락을 걸어 동시성 문제를 방지
	public String selectLastestIdForUpdate() {
		return db.selectOne("Department.selectLastestIdForUpdate");
	}

	// 조회: 부서명으로 부서 정보 리스트를 조회하여 반환
	public List<DepartmentDTO> selectByDeptName(String dept_name) {
		return db.selectList("Department.selectByDeptName", dept_name);
	}

	// 조회: 회사를 제외한 모든 부서의 정보를 리스트로 조회하여 반환
	public List<DepartmentDTO> selectAllWithOutOfficeId() {
		return db.selectList("Department.selectAllWithOutOfficeId");
	}

	// 조회: 특정 부서의 직원 수를 조회하여 반환
	public int selectEmpCountById(String id) {
		return db.selectOne("Department.selectEmpCountById", id);
	}

	// 조회: 모든 부서의 직원 수를 조회하여 반환
	public int selectEmpCount() {
		return db.selectOne("Department.selectEmpCount");
	}

	// 수정: 주어진 매개변수를 이용하여 부서 정보를 업데이트하고 결과로 영향 받은 행의 수를 반환
	public int update(Map<String, String> param) {
		return db.update("Department.update", param);
	}

	// 수정: 회사의 이름을 업데이트하고 결과로 영향 받은 행의 수를 반환
	public int updateOfficeName(String dept_name) {
		return db.update("Department.updateOfficeName", dept_name);
	}

	// 삭제: 주어진 아이디를 가진 부서 정보를 삭제하고 결과로 영향 받은 행의 수를 반환
	public int deleteById(String id) {
		return db.delete("Department.deleteById", id);
	}

}
