package com.clovers.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clovers.dao.DeptTaskDAO;
import com.clovers.dto.DeptTaskDTO;

@Service
public class DeptTaskService {
    
    @Autowired
    private DeptTaskDAO dtdao;

    // 삽입: 새로운 부서 직무를 데이터베이스에 추가
    public int insert(DeptTaskDTO dto) {
        return dtdao.insert(dto);
    }

    // 조회: 모든 부서 직무 정보 조회
    public List<DeptTaskDTO> selectAll(){
        return dtdao.selectAll();
    }
    
    // 조회: 특정 상위부서 ID에 해당하는 부서 리스트 조회
    public List<DeptTaskDTO> selectByDeptId(String dept_id){
        return dtdao.selectByDeptId(dept_id);
    }
    
    // 조회 : 특정 ID에 해당하는 부서 정보 조회
    public DeptTaskDTO selectById(String id) {
    	return dtdao.selectById(id);
    }

    // 조회: 특정 부서 ID에 해당하는 직원 수 조회
    public int selectEmpCountById(String id) {
        return dtdao.selectEmpCountById(id);
    }

    // 수정: 직무명 업데이트
    public int updateTaskName(String task_name, String id) {
        Map<String,String> param = new HashMap<>();
        param.put("task_name", task_name);
        param.put("id", id);
        return dtdao.updateTaskName(param);
    }
    
    // 수정: 부서의 상위 부서 ID 변경
    public int updateDeptId(String dept_id, String id) {
        Map<String,String> param = new HashMap<>();
        param.put("dept_id", dept_id);
        param.put("id", id);
        return dtdao.updateDeptId(param);
    }

    // 삭제: 특정 직무 정보 삭제
    public int delete(String id) {
        return dtdao.deleteById(id);
    }

    // 도우미 메서드: 새로운 직무 ID 생성
    @Transactional
    public String generateNewId() {
        String lastId = dtdao.selectLastestIdForUpdate();
        String prefix = lastId.substring(0,2);
        int numericPart = Integer.parseInt(lastId.substring(2));
        numericPart++;
        String newId = prefix + String.format("%02d", numericPart);
        return newId;
    }
}
