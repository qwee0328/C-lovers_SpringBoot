package com.clovers.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clovers.dto.DeptTaskDTO;

@Repository
public class DeptTaskDAO {
    
    @Autowired
    private SqlSession db;

    // 삽입: 새로운 부서 직무를 데이터베이스에 추가
    public int insert(DeptTaskDTO dto) {
        return db.insert("DeptTask.insert", dto);
    }

    // 조회: 모든 부서 직무 정보 조회
    public List<DeptTaskDTO> selectAll(){
        return db.selectList("DeptTask.selectAll");
    }
    
    // 조회: 특정 상위 부서 ID에 해당하는 직무 리스트 조회
    public List<DeptTaskDTO> selectByDeptId(String dept_id){
        return db.selectList("DeptTask.selectByDeptId", dept_id);
    }
    
    // 조회: 특정 부서ID에 해당하는 부서 정보 조회
    public DeptTaskDTO selectById(String id) {
    	return db.selectOne("DeptTask.selectById",id);
    }

    // 조회: 특정 부서 ID에 해당하는 직원 수 조회
    public int selectEmpCountById(String id) {
        return db.selectOne("DeptTask.selectEmpCountById", id);
    }

    // 수정: 직무명 업데이트
    public int updateTaskName(Map<String,String> param) {
        return db.update("DeptTask.updateTaskName", param);
    }
    
    // 수정: 부서의 상위 부서 ID 변경
    public int updateDeptId(Map<String,String> param) {
        return db.update("DeptTask.updateDeptId", param);
    }

    // 삭제: 특정 직무 정보 삭제
    public int deleteById(String id) {
        return db.delete("DeptTask.deleteById", id);
    }

    // 도우미 메서드: 새로운 직무 ID를 생성할 때 마지막으로 삽입된 아이디를 조회
    public String selectLastestIdForUpdate() {
        return db.selectOne("DeptTask.selectLastestIdForUpdate");
    }
}
