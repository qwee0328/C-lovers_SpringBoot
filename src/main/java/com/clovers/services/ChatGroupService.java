package com.clovers.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clovers.dao.ChatGroupDAO;
import com.clovers.dto.ChatGroupDTO;

import jakarta.servlet.http.HttpSession;

@Service
public class ChatGroupService {

	@Autowired
	private ChatGroupDAO dao;

	public int inviteChatGroup(String emp_id, String chat_room_id, int msg_start_id, int msg_end_id, String name) {
		Map<String, Object> param = new HashMap<>();
		param.put("emp_id", emp_id);
		param.put("chat_room_id", chat_room_id);
		param.put("msg_start_id", msg_start_id);
		param.put("msg_end_id", msg_end_id);
		param.put("name", name);
		return dao.inviteChatGroup(param);
	}

	public List<Map<String, Object>> selectByEmpId(String employee_id) {
		return dao.selectByEmpId(employee_id);
	}

	// 채팅방에 사용자만을 위한 정보를 보여줌.
	public Map<String, Object> selectByEmpIdNChatRoomID(String emp_id, String chat_room_id) {
		Map<String, String> param = new HashMap<>();
		param.put("emp_id", emp_id);
		param.put("chat_room_id", chat_room_id);
		return dao.selectByEmpIdNChatRoomID(param);
	}

	public List<Map<String, Object>> getAllOfficerInfo() {
		return dao.getAllOfficerInfo();
	}

	public Map<String, Object> getOfficerInfoByEmployeeId(String employee_id) {
		return dao.getOfficerInfoByEmployeeId(employee_id);
	}

	public String getExist1v1ChatRoom(String employee_id, String session_id) {
		Map<String, String> param = new HashMap<>();
		param.put("employee_id", employee_id);
		param.put("session_id", session_id);

		return dao.getExist1v1ChatRoom(param);
	}

	public List<ChatGroupDTO> selectChatGroupListByEmpId(String emp_id) {
		return dao.selectChatGroupListByEmpId(emp_id);
	}

}
