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
		return db.selectList("ChatRoom.findAllRoom");
	}
	
	public List<ChatRoomDTO> findRoomsByChatRoomId(String id){
		return db.selectList("ChatRoom.findRoomsByChatRoomId",id);
	}
	// 채팅방 개설
	public int insertChatRoom() {
		ChatRoomDTO crdto = ChatRoomDTO.create( ChatRoomDTO.ChatRoomStates.ACTIVATE);
		return db.insert("ChatRoom.insert",crdto);
	}
	
	// 채팅방 상태 업데이트
	public int updateChatRoomState(ChatRoomDTO cdto) {
		return db.update("ChatRoom.updateChatRoomState", cdto);
	}
	
	// 채팅방 삭제
	public int deleteChatRoom(int id) {
		return db.delete("ChatRoom.delete",id);
	}
}
