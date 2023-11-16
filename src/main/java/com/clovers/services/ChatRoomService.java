package com.clovers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clovers.dao.ChatRoomDAO;
import com.clovers.dto.ChatRoomDTO;

@Service
public class ChatRoomService {
	
	@Autowired
	private ChatRoomDAO crdao;
	
	public String createChatRoom() {
		return crdao.createChatRoom();
	}
	
	public int updateChatRoomState(ChatRoomDTO cdto) {
		return crdao.updateChatRoomState(cdto);
	}
	
	public int deleteChatRoom(int id) {
		return crdao.deleteChatRoom(id);
	}

}
