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

    // 삽입: 부서 정보를 데이터베이스에 등록
    public int insert(DepartmentDTO dto) {
        return ddao.insert(dto);
    }

    // 조회: 아이디를 기준으로 부서 정보를 검색
    public DepartmentDTO selectById(String id) {
        return ddao.selectById(id);
    }

    // 조회: 부서명을 기준으로 아이디를 검색
    public String selectIdByDeptName(String dept_name) {
        return ddao.selectIdByDeptName(dept_name);
    }

    // 조회: 부서명으로 부서 정보 리스트를 조회
    public List<DepartmentDTO> selectByDeptName(String dept_name) {
        return ddao.selectByDeptName(dept_name);
    }


    // 조회: 회사를 제외한 모든 부서의 정보를 리스트로 조회
    public List<DepartmentDTO> selectAll() {
        return ddao.selectAll();
    }

    // 조회: 특정 부서의 직원 수를 조회
    public int selectEmpCountById(String id) {
        return ddao.selectEmpCountById(id);
    }
    
    // 조회: 모든 부서의 직원 수를 조회
    public int selectEmpCount() {
        return ddao.selectEmpCount();
    }
    

    // 수정: 부서 정보를 업데이트
    public int update(String dept_name, String id) {
        Map<String, String> param = new HashMap<>();
        param.put("dept_name", dept_name);
        param.put("id", id);
        return ddao.update(param);
    }


    // 삭제: 아이디를 기준으로 부서 정보를 삭제
    public int deleteById(String id) {
        return ddao.deleteById(id);
    }
    
    // 도우미 메서드 : 최신 아이디를 생성하기 위해 마지막으로 삽입된 아이디를 조회 후 새 아이디를 반환
    @Transactional
    public String generateNewId() {
        String lastId = ddao.selectLastestIdForUpdate();
        String prefix = lastId.substring(0, 1);
        int numericPart = Integer.parseInt(lastId.substring(1)) + 1;
        return prefix + String.format("%04d", numericPart);
    }
}
