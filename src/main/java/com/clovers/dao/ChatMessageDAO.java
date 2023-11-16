package com.clovers.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clovers.dto.ChatMessageDTO;

@Repository
public class ChatMessageDAO {
	
	@Autowired
	private SqlSession db;
	
	
	// 채팅 그룹에 채팅을 남기기
	public int insert(ChatMessageDTO cdto) {
		return db.insert("ChatMessage.insert", cdto);
	}	

}
