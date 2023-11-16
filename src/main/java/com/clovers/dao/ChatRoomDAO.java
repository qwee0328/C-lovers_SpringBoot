package com.clovers.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clovers.dto.ChatRoomDTO;

@Repository
public class ChatRoomDAO {

	// 채팅방과 그 상태들을 관리하는 DAO.

	@Autowired
	private SqlSession db;

	// 채팅방 개설 후 아이디 값을 반환
	public String createChatRoom() {
		ChatRoomDTO crdto = ChatRoomDTO.create(ChatRoomDTO.ChatRoomStates.ACTIVATE);
		db.insert("ChatRoom.createChatRoom", crdto);
		return crdto.getId();
	}

	// 채팅방 상태 업데이트
	public int updateChatRoomState(ChatRoomDTO cdto) {
		return db.update("ChatRoom.updateChatRoomState", cdto);
	}

	// 채팅방 삭제
	public int deleteChatRoom(int id) {
		return db.delete("ChatRoom.deleteChatRoom", id);
	}
}
