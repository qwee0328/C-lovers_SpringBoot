package com.clovers.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clovers.dto.ChatRoomDTO;

@Repository
public class ChatRoomDAO {
	
	// 채팅방과 그 상태들을 관리하는 DAO.
	
	@Autowired
	private SqlSession db;
	
	
	public List<ChatRoomDTO> findAllRoom(){
		return db.selectList("Chat.findAllRoom");
	}
	
	public List<ChatRoomDTO> findRoomsByChatRoomId(String id){
		return db.selectList("Chat.findRoomsByChatRoomId",id);
	}
	// 채팅방 개설
	public int insertChatRoom(String chat_room_name) {
		ChatRoomDTO crdto = ChatRoomDTO.create(chat_room_name, ChatRoomDTO.ChatRoomStates.ACTIVATE);
		return db.insert("Chat.insert",crdto);
	}
	
	// 채팅방 상태 업데이트
	public int updateChatRoomState(ChatRoomDTO cdto) {
		return db.update("Chat.updateChatRoomState", cdto);
	}
	
	// 채팅방 삭제
	public int deleteChatRoom(int id) {
		return db.delete("Chat.delete",id);
	}
}
