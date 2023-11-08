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
	
	// 부서 정보를 데이터베이스에 삽입하고 결과로 영향 받은 행의 수를 반환
	public int insert(DepartmentDTO dto) {
		return db.insert("Department.insert",dto);
	}
	
	// 부서 정보를 업데이트하고 결과로 영향 받은 행의 수를 반환. 매개변수로 Map을 사용하여 동적으로 쿼리를 생성
	public int update(Map<String,String> param) {
		return db.update("Department.update",param);
	}
	
	// 주어진 아이디를 가진 부서 정보를 삭제하고 결과로 영향 받은 행의 수를 반환
	public int deleteById(String id) {
		return db.delete("Department.delete");
	}
	
	// 주어진 아이디를 기반으로 부서 정보를 조회하여 반환
	public DepartmentDTO selectById(String id) {
		return db.selectOne("Department.selectById",id);
	}
	
	// 부서명으로 부서의 아이디를 조회하여 반환
	public String selectIdByDeptName(String dept_name) {
		return db.selectOne("Department.selectIdByDeptName",dept_name );
	}
	
	// 최신 아이디를 가져옴(부서서비스에서 삽입로직으로 사용)
	public String selectLastestIdForUpdate() {
		return db.selectOne("Department.selectLastestIdForUpdate");
	}
	
	// 부서명으로 부서 정보 리스트를 조회하여 반환
	public List<DepartmentDTO> selectByDeptName(String dept_name){
		return db.selectList("Department.selectByDeptName",dept_name);
	}
	
	// id가 D1001(회사)정보를 조회하여 반환
	public DepartmentDTO selectOffice() {
		return db.selectOne("Department.selectOffice");
	}
	
	// 회사 정보를 제외한 모든 부서 정보를 리스트로 조회하여 반환합니다.
	public List<DepartmentDTO> selectAllWithOutOfficeId(){
		return db.selectList("Department.selectAllWithOutOfficeId");
	}
	
	// 모든 부서 정보를 조회하는 메서드입니다. 현재 비활성화 상태입니다.
	// public List<DepartmentDTO> selectAll(){
	// 	return db.selectList("Department.selectAll");
	// }
	

}
