package com.clovers.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clovers.dao.ChatGroupDAO;
import com.clovers.dao.ChatMessageDAO;
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
	private ChatMessageDAO cmdao;
	
	@Autowired
	private MemberDAO mdao;

	// 최초로 만든 채팅그룹에 초대할 때 사용하는 함수.
	@Transactional
	public int inviteInitChatGroup(String emp_id, String chat_room_id, String name) {
		Map<String, Object> param = new HashMap<>();
		param.put("emp_id", emp_id);
		param.put("chat_room_id", chat_room_id);
		int latestMsgId = cmdao.selectLatestChatMsgIdForInvite();
		param.put("msg_start_id", latestMsgId);
		param.put("msg_end_id", latestMsgId);
		param.put("name", name);
		return cgdao.inviteChatGroup(param);
	}
	
	// 이미 존재하는 채팅그룹에 초대할 때 사용하는 함수.
	@Transactional
	public int inviteAlreadyExistChatGroup(String emp_id, String chat_room_id, String name) {
		Map<String, Object> param = new HashMap<>();
		param.put("emp_id", emp_id);
		param.put("chat_room_id", chat_room_id);
		int latestMsgId = cmdao.selectLatestChatMsgIdByChatRoomIdForUpdate(chat_room_id);
		param.put("msg_start_id", latestMsgId);
		param.put("msg_end_id", latestMsgId);
		param.put("name", name);
		return cgdao.inviteChatGroup(param);
	}
	
	// 최초로 만든 1:1채팅 그룹에 초대
	@Transactional
	public String setInitChattingRoomAndGroup(String employee_id, String loginID) {
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
	        this.inviteInitChatGroup(employee_id, newChatroomId,loginName);
	        this.inviteInitChatGroup(loginID, newChatroomId,employeeName);
	        return newChatroomId;
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return null;
	    }
	}
	
	
	// 최초로 만든 그룹채팅 그룹에 초대
	@Transactional
	public String setGroupChattingRoomAndGroup(List<String> selectedEmployees, String loginID) {
		try {
			String newChatroomId = crdao.createChatRoom();
			String loginName = mdao.selectNameById(loginID);
			this.inviteInitChatGroup(loginID, newChatroomId,"그룹채팅 made by " + loginName);
			for(String employee_id : selectedEmployees) {
				this.inviteInitChatGroup(employee_id, newChatroomId,"그룹채팅 made by " + loginName);
			}
			return newChatroomId;
		}catch(Exception e) {
			e.printStackTrace();
	        return null;
		}
	}
	
	// 이미 만들어진 채팅그룹에 초대
	@Transactional
	public String setAlreadyExistChatGroupInvite(List<String> selectedEmployees, String chat_room_id,String loginID) {
		try {
			String loginName = mdao.selectNameById(loginID);
			for(String employee_id : selectedEmployees) {
				this.inviteAlreadyExistChatGroup(employee_id, chat_room_id,"그룹채팅 made by " + loginName);
			}
			return chat_room_id;
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
	
	public int updateChatGroupName(Map<String, Object> param) {
		return cgdao.updateChatGroupName(param);
	}

}
