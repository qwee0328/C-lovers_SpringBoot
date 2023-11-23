package com.clovers.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clovers.dao.ChatGroupDAO;
import com.clovers.dao.ChatMessageDAO;
import com.clovers.dao.ChatRoomDAO;
import com.clovers.dao.MemberDAO;
import com.clovers.dto.ChatGroupDTO;
import com.clovers.dto.ChatMessageDTO;
import com.clovers.dto.ChatRoomDTO;

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
	
	@Autowired
    private SimpMessageSendingOperations messagingTemplate;

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
	        String newChatroomId = crdao.createPersonalChat();
	        String loginName = mdao.selectNameById(loginID);
	        String employeeName = mdao.selectNameById(employee_id);
	        this.inviteInitChatGroup(employee_id, newChatroomId,loginName);
	        this.inviteInitChatGroup(loginID, newChatroomId,employeeName);
	        
	        ChatMessageDTO cdto = new ChatMessageDTO(0,newChatroomId,"2023DT11034"
	        		,loginName+"님이 "+ employeeName+"님을 초대하였습니다."
	        		,new Timestamp(System.currentTimeMillis()), ChatMessageDTO.ChatMessageStates.JOIN);
	        cmdao.recordChat(cdto);
	        messagingTemplate.convertAndSend("/sub/chat/room/"+newChatroomId,cdto);
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
			String newChatroomId = crdao.createGroupChat();
			String loginName = mdao.selectNameById(loginID);
			this.inviteInitChatGroup(loginID, newChatroomId,"그룹채팅 made by " + loginName);
			for(String employee_id : selectedEmployees) {
				this.inviteInitChatGroup(employee_id, newChatroomId,"그룹채팅 made by " + loginName);
			}
			
			List<String> empName = new ArrayList<>();
			
			for(String emp_id : selectedEmployees) {
				empName.add(mdao.selectNameById(emp_id));
			}
			String memberString = String.join(",", empName);
			ChatMessageDTO cdto = new ChatMessageDTO(0,newChatroomId,"2023DT11034",
					loginName+"님이 " +memberString+"님을 초대하였습니다.",
					new Timestamp(System.currentTimeMillis()),
					ChatMessageDTO.ChatMessageStates.JOIN);
			cmdao.recordChat(cdto);
			messagingTemplate.convertAndSend("/sub/chat/room/"+newChatroomId,cdto);
			
			return newChatroomId;
		}catch(Exception e) {
			e.printStackTrace();
	        return null;
		}
	}
	
	// 이미 만들어진 채팅그룹에 초대
	@Transactional
	public String setAlreadyExistChatGroupInvite(List<String> selectedEmployees,
			String chat_room_id, String loginID) {
		
		String roomState = crdao.selectStateByChatRoomId(chat_room_id);
		
		if(ChatRoomDTO.ChatRoomStates.ACTIVEPERSONAL.equals(
				Enum.valueOf(ChatRoomDTO.ChatRoomStates.class, roomState))) {
			ChatRoomDTO crdto = new ChatRoomDTO();
			crdto.setId(chat_room_id);
			crdto.setState(ChatRoomDTO.ChatRoomStates.ACTIVEGROUP);
			crdao.updateChatRoomState(crdto);
		}
		
		List<String> empList = this.selectEmpIDByChatRoomId(chat_room_id);
		List<String> empName = new ArrayList<>();
		
		for(String emp_id : empList) {
			empName.add(mdao.selectNameById(emp_id));
		}
		String memberString = String.join(",", empName);
		try {
			String loginName = mdao.selectNameById(loginID);
			for(String employee_id : selectedEmployees) {
				this.inviteAlreadyExistChatGroup(employee_id, chat_room_id,memberString);
			}
			
			List<String> inviteList = new ArrayList<>();
			for(String emp_id : selectedEmployees) {
				inviteList.add(mdao.selectNameById(emp_id));
			}
			String invitemember = String.join(",", inviteList);
			
			ChatMessageDTO cdto = new ChatMessageDTO(0,chat_room_id,"2023DT11034",
					loginName+"님이 "+invitemember+"님을 초대하였습니다.",
					new Timestamp(System.currentTimeMillis()),
					ChatMessageDTO.ChatMessageStates.JOIN);
			cmdao.recordChat(cdto);
			messagingTemplate.convertAndSend("/sub/chat/room/"+chat_room_id,cdto);
			
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
	
	public List<String> selectEmpIDByChatRoomId(String chat_room_id){
		return cgdao.selectEmpIDByChatRoomId(chat_room_id);
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
	
	// 채팅방 퇴장 로직
	public int deleteByEmpIdNChatId(String emp_id, String chat_room_id) {
		Map<String,Object> param = new HashMap<>();
		param.put("emp_id", emp_id);
		param.put("chat_room_id", chat_room_id);
		String exitmember = mdao.selectNameById(emp_id);
		ChatMessageDTO cdto = new ChatMessageDTO(0,chat_room_id,"2023DT11034",
				exitmember+"님이 채팅방을 나갔습니다.",new Timestamp(System.currentTimeMillis()),
				ChatMessageDTO.ChatMessageStates.EXIT);
		cmdao.recordChat(cdto);
		messagingTemplate.convertAndSend("/sub/chat/room/"+chat_room_id,cdto);
		return cgdao.deleteByEmpIdNChatId(param);
	}

}
