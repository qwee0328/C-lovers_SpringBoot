package com.clovers.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clovers.dao.ChatGroupDAO;
import com.clovers.dao.ChatMessageDAO;
import com.clovers.dao.MemberDAO;
import com.clovers.dto.ChatMessageDTO;

@Service
public class ChatMessageService {
	
	
	@Autowired
	private ChatMessageDAO cmdao;	
	
	@Autowired
	private ChatGroupDAO cgdao;
	
	@Autowired
	private MemberDAO mdao;
	
	
	public int recordChat(ChatMessageDTO cdto) {
		return cmdao.recordChat(cdto);
	}
	
	public List<ChatMessageDTO> selectMessagesByChatRoomId(String chat_room_id){
		return cmdao.selectMessagesByChatRoomId(chat_room_id);
	}
	
	public List<Map<String,Object>> selectMessageListByChatRoomId(String chat_room_id){
		return cmdao.selectMessageListByChatRoomId(chat_room_id);
	}
	
	public int selectLatestChatMsgIdByChatRoomId(String chat_room_id) {
		return cmdao.selectLatestChatMsgIdByChatRoomIdForUpdate(chat_room_id);
	}
	
	public int deleteExecModifyChatMessageById(int id) {
		return cmdao.deleteExecModifyChatMessageById(id);
	}
	
	
	@Transactional
	public Map<String,Object> selectByChatRoomId(String emp_id, String chat_room_id){
		Map<String, String> param = new HashMap<>();
		param.put("emp_id", emp_id);
		param.put("chat_room_id", chat_room_id);
		Map<String,Object> group = cgdao.selectByEmpIdNChatRoomID(param);
		
		Map<String,Object> list = new HashMap<>();
		list.put("group", group);
		
		List<Map<String,Object>> msg = cmdao.selectMessageListByChatRoomId(chat_room_id);
		list.put("chat", msg);
		
		return list;
	}
}
