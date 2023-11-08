package com.clovers.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.clovers.dao.DeptTaskDAO;
import com.clovers.dto.DeptTaskDTO;

public class DeptTaskService {
	
	@Autowired
	private DeptTaskDAO dtdao;
	
	public int insert(DeptTaskDTO dto) {
		return dtdao.insert(dto);
	}
	
	@Transactional
	public String generateNewId() {
		// 마지막으로 삽입 된 아이디를 가져온다.
		String lastId = dtdao.selectLastestIdForUpdate();
		// 첫 두 글자를 따로 저장.
		String prefix = lastId.substring(0,2);
		// 세번째 글자부터는 숫자. 숫자를 추출
		int numericPart = Integer.parseInt(lastId.substring(2));
		// 숫자 증가
		numericPart++;
		// 새로운 아이디 생성
		String newId = prefix + String.format("%02d", numericPart);
		return newId;
	}
	
	public int update(String task_name, String id) {
		Map<String,String> param = new HashMap<>();
		param.put("task_name", task_name);
		param.put("id", id);
		return dtdao.update(param);
	}
	
	public int delete(String id) {
		return dtdao.deleteById(id);
	}
	
	public List<DeptTaskDTO> selectAll(){
		return dtdao.selectAll();
	}
	
	public List<DeptTaskDTO> selectByDeptId(String dept_id){
		return dtdao.selectByDeptId(dept_id);
	}
	

}
