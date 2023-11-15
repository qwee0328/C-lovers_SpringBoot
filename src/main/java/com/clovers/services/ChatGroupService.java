package com.clovers.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clovers.dao.ChatGroupDAO;
import com.clovers.dao.ChatRoomDAO;
import com.clovers.dao.MemberDAO;
import com.clovers.dto.ChatGroupDTO;

@Service
public class ChatGroupService {

	@Autowired
	private ChatGroupDAO cgdao;
	
	@Autowired
	private ChatRoomDAO crdao;
	
	@Autowired
	private MemberDAO mdao;

	public int inviteChatGroup(String emp_id, String chat_room_id, int msg_start_id, int msg_end_id, String name) {
		Map<String, Object> param = new HashMap<>();
		param.put("emp_id", emp_id);
		param.put("chat_room_id", chat_room_id);
		param.put("msg_start_id", msg_start_id);
		param.put("msg_end_id", msg_end_id);
		param.put("name", name);
		return cgdao.inviteChatGroup(param);
	}
	
	@Transactional
	public String setChattingRoomAndGroup(String employee_id, String loginID) {
		// 이미 1대 1 채팅방이 있다면 생성을 막는 로직
		String chatroom = this.getExist1v1ChatRoom(employee_id, loginID);  
	    if (chatroom != null) {
	        return chatroom;
	    }
	    // 1:1 채팅방이 없다면 채팅방 생성 시작.
	    try {
	        String newChatroomId = crdao.createChatRoom();
	        String loginName = mdao.selectNameById(loginID);
	        String employeeName = mdao.selectNameById(employee_id);
	        this.inviteChatGroup(employee_id, newChatroomId, 0, 0, employeeName + ", " + loginName);
	        this.inviteChatGroup(loginID, newChatroomId, 0, 0, loginName + ", " + employeeName);
	        return newChatroomId;
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return null;
	    }
	}
	
	@Transactional
	public String setGroupChattingRoomAndGroup(List<String> selectedEmployees, String loginID) {
		try {
			String newChatroomId = crdao.createChatRoom();
			String loginName = mdao.selectNameById(loginID);
			this.inviteChatGroup(loginID, newChatroomId, 0, 0, "그룹채팅 made by " + loginName);
			for(String employee_id : selectedEmployees) {
				this.inviteChatGroup(employee_id, newChatroomId, 0, 0, "그룹채팅 made by " + loginName);
			}
			return newChatroomId;
		}catch(Exception e) {
			e.printStackTrace();
	        return null;
		}
	}

	
	@Transactional
	public Map<String, Object> getMainData(String loginID) {
		List<Map<String, Object>> officerList = cgdao.getAllOfficerInfo();
		Map<String, Object> myInfo = cgdao.getOfficerInfoByEmployeeId(loginID);

		Map<String, Object> resp = new HashMap<>();
		resp.put("officerList", officerList);
		resp.put("myInfo", myInfo);

		return resp;
	}
	


	public List<Map<String, Object>> selectByEmpId(String employee_id) {
		return cgdao.selectByEmpId(employee_id);
	}

	// 채팅방에 사용자만을 위한 정보를 보여줌.
	public Map<String, Object> selectByEmpIdNChatRoomID(String emp_id, String chat_room_id) {
		Map<String, String> param = new HashMap<>();
		param.put("emp_id", emp_id);
		param.put("chat_room_id", chat_room_id);
		return cgdao.selectByEmpIdNChatRoomID(param);
	}

	public List<Map<String, Object>> getAllOfficerInfo() {
		return cgdao.getAllOfficerInfo();
	}

	public Map<String, Object> getOfficerInfoByEmployeeId(String employee_id) {
		return cgdao.getOfficerInfoByEmployeeId(employee_id);
	}

	public String getExist1v1ChatRoom(String employee_id, String session_id) {
		Map<String, String> param = new HashMap<>();
		param.put("employee_id", employee_id);
		param.put("session_id", session_id);

		return cgdao.getExist1v1ChatRoom(param);
	}

	public List<ChatGroupDTO> selectChatGroupListByEmpId(String emp_id) {
		return cgdao.selectChatGroupListByEmpId(emp_id);
	}

}
