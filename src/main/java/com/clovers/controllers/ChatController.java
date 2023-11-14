package com.clovers.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clovers.services.ChatGroupService;
import com.clovers.services.ChatMessageService;
import com.clovers.services.ChatRoomService;
import com.clovers.services.MemberService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/chat")
public class ChatController {

	@Autowired
	private ChatGroupService cgService;
	
	@Autowired
	private ChatRoomService crService;
	
	@Autowired
	private ChatMessageService cmService;
	
	@Autowired
	private MemberService mService;
	
	@Autowired
	private HttpSession session;
	
	//------------------------------ 뷰 리졸버 관련 -----------------------------------------------------
	
	// 채팅 메인화면
	@GetMapping("/goMain")
	public String goMain(Model model) {
		List<Map<String,Object>> officerList = cgService.getAllOfficerInfo();
		Map<String,Object> myInfo = cgService.getOfficerInfoByEmployeeId((String)session.getAttribute("loginID"));
		model.addAttribute("officerList",officerList);
		model.addAttribute("myInfo",myInfo);
		return "chat/main";
	}
	
	
	@RequestMapping("/chatList")
	public String goChatList(Model model) {
		String loginId = (String) session.getAttribute("loginID");
		model.addAttribute("chatGroupList", cgService.selectChatGroupListByEmpId(loginId));
		return "chat/chatList";
	}
	
	
	@RequestMapping("/fileList")
	public String goFileList() {
		return "chat/fileList";
	}
	
	@RequestMapping("/goChatRoom")
	public String goChatRoom(@RequestParam("id") String chatRoomId, Model model) {
	    // chatRoomId를 사용하여 필요한 작업 수행
		String loginID = (String) session.getAttribute("loginID");
	    Map<String,Object> personalChatRoomInfo = cgService.selectByEmpIdNChatRoomID(loginID, chatRoomId);
	    model.addAttribute("chatRoomId", chatRoomId);
	    model.addAttribute("personalChatRoomInfo", personalChatRoomInfo);
	    return "chat/chatRoom";
	}
	
	
	//---------------------------------- RequestBody 관련 ------------------------------------------------
	
	// 1대 1 채팅을 할 때 실행되는 함수. 둘이 이미 있는 경우 이미 존재하는 채팅방 id를 반환, 
	// 그렇지 않다면 새로운 채팅방을 개설하고 채팅 그룹에 연관 관계를 작성한 다음 새로 만든 채팅방 id를 반환
	@ResponseBody
	@PostMapping("/setChattingRoom")
	public String setChattingRoomAndGroup(@RequestParam String employee_id) {
		String session_id = (String)session.getAttribute("loginID");
		String chatroom = cgService.getExist1v1ChatRoom(employee_id, session_id);
		if(chatroom != null) {
			return chatroom;
		}
		String newChatroomId = crService.createChatRoom();
		cgService.inviteChatGroup(employee_id, newChatroomId, 0, 0, mService.selectNameById(session_id)+ ", " + mService.selectNameById(employee_id));
		cgService.inviteChatGroup(session_id, newChatroomId, 0, 0, mService.selectNameById(session_id)+ ", " + mService.selectNameById(employee_id));
		return newChatroomId;
		
	}
	
	// 세션에 접속한 사용자에게 채팅그룹 목록을 가져옴
	@ResponseBody
	@GetMapping("/chatListLoad")
	public List<Map<String, Object>> selectByEmpId() {
		String loginID = (String)session.getAttribute("loginID");
		return cgService.selectByEmpId(loginID);
	}
	
	@ResponseBody
	@GetMapping("/getAllOfficerList")
	public List<Map<String,Object>> getAllOfficerList(){
		return cgService.getAllOfficerInfo();
	}
	
	@ResponseBody
	@GetMapping("/getOfficerInfoByEmployeeId")
	public Map<String, Object> getOfficerInfoByEmployeeId(@RequestParam String employee_id) {
	    return cgService.getOfficerInfoByEmployeeId(employee_id);
	}

	
	@ResponseBody
	@RequestMapping("/chatMsgLoad")
	public Map<String,Object> selectByChatId(@RequestParam("emp_id") String emp_id,
			@RequestParam("chat_room_id") String chat_room_id){
		return cmService.selectByChatRoomId(emp_id, chat_room_id);
	}
	
}
