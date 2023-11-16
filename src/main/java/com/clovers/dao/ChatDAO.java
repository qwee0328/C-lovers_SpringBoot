package com.clovers.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clovers.dto.ChatDTO;

@Repository
public class ChatDAO {
	
	// 채팅방과 그 상태들을 관리하는 DAO.
	
	@Autowired
	private SqlSession db;
	
	// 채팅방 개설
	public int insert(ChatDTO cdto) {
		return db.insert("Chat.insert",cdto);
	}
	
	// 채팅방 상태 업데이트
	public int updateChatRoomState(ChatDTO cdto) {
		return db.update("Chat.updateChatRoomState", cdto);
	}
	
	// 채팅방 삭제
	public int delete(int id) {
		return db.delete("Chat.delete",id);
	}
}
