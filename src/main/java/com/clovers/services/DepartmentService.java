package com.clovers.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clovers.dao.DepartmentDAO;
import com.clovers.dto.DepartmentDTO;

@Service
public class DepartmentService {
	
	@Autowired
	private DepartmentDAO ddao;
	
//	부서등록
	public int insert(DepartmentDTO dto) {
		return ddao.insert(dto);
	}
	
//	테이블에 있는 최신 아이디를 기준으로 새로운 아이디 생성
//	이 함수는 동시성에 대한 처리로 인해 트랜잭션 처리를 함.
	@Transactional
	public String generateNewId() {
	    // 마지막으로 삽입된 아이디를 가져온다.
	    String lastId = ddao.selectLastestIdForUpdate(); // 해당 select문을 실행하면 하나의 트랜잭션을 끝내기 전까지 테이블에 다른 모든 작업을 막는다.
	    // 첫 글자를 따로 저장
	    String prefix = lastId.substring(0, 1);
	    // 숫자 부분만 추출하여 정수로 변환
	    int numericPart = Integer.parseInt(lastId.substring(1));
	    // 정수 값을 1 증가
	    numericPart++;   
	    // 새로운 아이디를 생성 ('D' + 새로운 숫자).
	    String newId = prefix + String.format("%04d", numericPart);
		return newId;
	}
	
//	부서정보변경
	public int update(String dept_name, String id) {
		Map<String,String> param = new HashMap<>();
		param.put("dept_name", dept_name);
		param.put("id", id);
		return ddao.update(param);
	}
	
//	아이디를 기반으로 부서정보 삭제
	public int deleteById(String id) {
		return ddao.deleteById(id);
	}
	
//	부서이름을 기반으로 아이디를 검색
	public String selectIdByDeptName(String dept_name) {
		return ddao.selectIdByDeptName(dept_name);
	}
	
//	아이디를 기반으로 부서를 검색
	public DepartmentDTO selectById(String id) {
		return ddao.selectById(id);
	}
	
//	부서명으로 검색
	public List<DepartmentDTO> selectByDeptName(String dept_name){
		return ddao.selectByDeptName(dept_name);
	}
	
//	회사 관련 내용을 제외한 모든 내용 반환
	public List<DepartmentDTO> selectAllWithOutOfficeId(){
		return ddao.selectAllWithOutOfficeId();
	}
	
//	public List<DepartmentDTO> selectAll(){
//		return ddao.selectAll();
//	}
	
	// 회사 관련 전용
	public DepartmentDTO selectOffice() {
		return ddao.selectOffice();
	}

}
