package com.clovers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clovers.dao.ChatRoomDAO;
import com.clovers.dto.ChatMessageDTO;
import com.clovers.dto.ChatRoomDTO;

@Service
public class ChatRoomService {
	
	@Autowired
	private ChatRoomDAO crdao;
	
	public String createPersonalChat() {
		return crdao.createPersonalChat();
	}
	
	public String createGroupChat() {
		return crdao.createGroupChat();
	}
	
	public int updateChatRoomStateToGroup(ChatRoomDTO cdto) {
		cdto.setState(ChatRoomDTO.ChatRoomStates.ACTIVEGROUP);
		return crdao.updateChatRoomState(cdto);
	}
	
	public int updateChatRoomStateToInactive(ChatRoomDTO cdto) {
		cdto.setState(ChatRoomDTO.ChatRoomStates.INACTIVE);
		return crdao.updateChatRoomState(cdto);
	}
	
	public int deleteChatRoom(int id) {
		return crdao.deleteChatRoom(id);
	}

}
