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
	
	public int insert(DeptTaskDTO dto) {
		return db.insert("DeptTask.insert",dto);
	}
	
	public int update(Map<String,String> param) {
		return db.update("DeptTask.update",param);
	}
	
	public int deleteById(String id) {
		return db.delete("DeptTask.deleteById", id);
	}
	
	public String selectLastestIdForUpdate() {
		return db.selectOne("DeptTask.selectLastestIdForUpdate");
	}
	
	public List<DeptTaskDTO> selectAll(){
		return db.selectList("DeptTask.selectAll");
	}
	
	public List<DeptTaskDTO> selectByDeptId(String dept_id){
		return db.selectList("DeptTask.selectByDeptId",dept_id);
	}

}
