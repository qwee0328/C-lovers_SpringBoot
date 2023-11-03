package com.clovers.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clovers.dto.ChatGroupDTO;

@Repository
public class ChatGroupDAO {

	@Autowired
	private SqlSession db;
	
	public List<ChatGroupDTO> selectByEmpId(String emp_id) {
		return db.selectList("Chat.selectByEmpId",emp_id);
	}
}
