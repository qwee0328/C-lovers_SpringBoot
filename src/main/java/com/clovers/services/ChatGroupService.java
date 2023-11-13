package com.clovers.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clovers.dao.ChatGroupDAO;
import com.clovers.dto.ChatGroupDTO;
import com.clovers.dto.ChatMessageDTO;

import jakarta.servlet.http.HttpSession;

@Service
public class ChatGroupService {

	@Autowired
	private ChatGroupDAO dao;
	
	@Autowired
	private HttpSession session;
	
	
	
	public int inviteChatGroup(String emp_id, String chat_room_id, int msg_start_id, int msg_end_id, String name) {
		Map<String,Object> param = new HashMap<>();
		param.put("emp_id", emp_id);
		param.put("chat_room_id", chat_room_id);
		param.put("msg_start_id", msg_start_id);
		param.put("msg_end_id", msg_end_id);
		param.put("name", name);
		return dao.inviteChatGroup(param);
	}
	
	
	public List<Map<String, Object>> selectByEmpId(String employee_id) {
//		return dao.selectByEmpId((String)session.getAttribute("loginID"));
		return dao.selectByEmpId(employee_id);
	}
	
	// 채팅방에 사용자만을 위한 정보를 보여줌.
		public Map<String,Object> selectByEmpIdNChatRoomID(String emp_id, String chat_room_id){
			Map<String,String> param = new HashMap<>();
			param.put("emp_id", emp_id);
			param.put("chat_room_id", chat_room_id);
			return dao.selectByEmpIdNChatRoomID(param);
		}
	
	public List<Map<String,Object>> getAllOfficerInfo(){
		return dao.getAllOfficerInfo();
	}
	
	public Map<String,Object> getOfficerInfoByEmployeeId(String employee_id){
		return dao.getOfficerInfoByEmployeeId(employee_id);
	}
	
	public String getExist1v1ChatRoom(String employee_id, String session_id){
		Map<String,String> param = new HashMap<>();
		param.put("employee_id", employee_id);
		param.put("session_id", session_id);
		
		return dao.getExist1v1ChatRoom(param);
	}
	
	public List<ChatGroupDTO> selectChatGroupListByEmpId(String emp_id) {
		return dao.selectChatGroupListByEmpId(emp_id);
	}
	
	public Map<String,Object> selectByChatId(){
		Map<String,Object> group = new HashMap<>();
		group.put("chat_id", 1);
		group.put("name", "채팅방1");
		group.put("emp_cnt", 3);
		
		Map<String,Object> list = new HashMap<>();
		list.put("group", group);
		
		List<ChatMessageDTO> msg = new ArrayList<>();
//		for(int i=1; i<5; i++) {
//			msg.add(new ChatMessageDTO(i,1,"test","내용이에요ㅋㅋㅋㅋㅋㅋㅋㅋ"+i,new Timestamp(System.currentTimeMillis()),ChatMessageDTO.ChatMessageStates.CHAT));
//		}
//		for(int i=5; i<10; i++) {
//			msg.add(new ChatMessageDTO(i,1,"another","내용이에요ㅋㅋㅋㅋㅋㅋㅋㅋ"+i,new Timestamp(System.currentTimeMillis()),ChatMessageDTO.ChatMessageStates.CHAT));
//		}
//		msg.add(new ChatMessageDTO(9,1,"test","file",new Timestamp(System.currentTimeMillis()),ChatMessageDTO.ChatMessageStates.CHAT));
//		msg.add(new ChatMessageDTO(10,1,"another","file",new Timestamp(System.currentTimeMillis()),ChatMessageDTO.ChatMessageStates.CHAT));
		list.put("chat", msg);
		
		list.put("loginID",(String)session.getAttribute("loginID"));
		
		return list;
	}
}
