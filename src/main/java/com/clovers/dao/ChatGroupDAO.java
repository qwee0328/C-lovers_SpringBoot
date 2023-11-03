package com.clovers.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ChatGroupDAO {

	@Autowired
	private SqlSession db;
	
	public List<Map<String, Object>> selectByEmpId(String emp_id) {
		return db.selectList("Chat.selectByEmpId",emp_id);
	}
}
