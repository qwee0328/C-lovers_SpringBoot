package com.clovers.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clovers.dao.ChatMessageDAO;
import com.clovers.dto.ChatMessageDTO;

@Service
public class ChatMessageService {
	
	
	@Autowired
	private ChatMessageDAO cmdao;	
	
	public int recordChat(ChatMessageDTO cdto) {
		return cmdao.recordChat(cdto);
	}
	
	public List<ChatMessageDTO> selectMessagesByChatRoomId(String chat_room_id){
		return cmdao.selectMessagesByChatRoomId(chat_room_id);
	}
	
	public int selectLatestChatMsgIdByChatRoomId(String chat_room_id) {
		return cmdao.selectLatestChatMsgIdByChatRoomId(chat_room_id);
	}
	
	public int deleteExecModifyChatMessageById(int id) {
		return cmdao.deleteExecModifyChatMessageById(id);
	}
}
