package com.clovers.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.clovers.services.ChatGroupService;
import com.clovers.services.ChatMessageService;

@Controller
@RequestMapping("/chat")
public class ChatController {

	@Autowired
	private ChatGroupService cgService;

	@Autowired
	private ChatMessageService cmService;

	// ------------------------------ 뷰 리졸버 관련
	// -----------------------------------------------------

	// 채팅 메인화면
	@GetMapping("/goMain")
	public String goMain() {
		return "chat/main";
	}

	// 채팅 그룹 리스트 화면
	@RequestMapping("/chatList")
	public String goChatList() {
		return "chat/chatList";
	}

	// 파일 모아보기
	@RequestMapping("/fileList")
	public String goFileList() {
		return "chat/fileList";
	}

	// 채팅방으로 이동
	@RequestMapping("/goChatRoom/{id}")
	public String goChatRoom(@PathVariable("id") String chatRoomId, @SessionAttribute("loginID") String loginID,
			Model model) {
		// chatRoomId를 사용하여 필요한 작업 수행
		Map<String, Object> personalChatRoomInfo = cgService.selectByEmpIdNChatRoomID(loginID, chatRoomId);
		model.addAttribute("chatRoomId", chatRoomId);
		model.addAttribute("personalChatRoomInfo", personalChatRoomInfo);
		return "chat/chatRoom";
	}

	// ---------------------------------- RequestBody 관련
	// ------------------------------------------------

	// 1대 1 채팅방을 최초로 개설하거나 1대1채팅을 할 때 실행되는 함수. 해당 채팅방이 이미 존재 할 경우 이미 존재하는 채팅방 id를
	// 반환,
	// 그렇지 않다면 새로운 채팅방을 개설하고 채팅 그룹에 연관 관계를 작성한 다음 새로 만든 채팅방 id를 반환
	@ResponseBody
	@PostMapping("/setChattingRoom")
	public String setChattingRoomAndGroup(@RequestParam String employee_id,
			@SessionAttribute("loginID") String loginID) {
		return cgService.setInitChattingRoomAndGroup(employee_id, loginID);
	}

	// 그룹채팅방을 개설하고 채팅 그룹에 연관관계를 작성한 다음 새로 만든 채팅방 id를 반환
	@ResponseBody
	@PostMapping("/setGroupChattingRoom")
	public String setGroupChattingRoomAndGroup(@RequestBody Map<String, Object> payload,
			@SessionAttribute("loginID") String loginID) {
		@SuppressWarnings("unchecked")
		List<String> selectedEmployees = (List<String>) payload.get("selectedEmployees");
		return cgService.setGroupChattingRoomAndGroup(selectedEmployees, loginID);
	}

	// 이미 개설된 채팅방의 채팅 그룹에 새로운 사용자를 추가할 때 사용하는 함수.
	@ResponseBody
	@PostMapping("/inviteChatGroup")
	public String inviteChatGroup(@RequestBody Map<String, Object> payload, 
	                              @SessionAttribute("loginID") String loginID) {

	    @SuppressWarnings("unchecked")
	    List<String> selectedEmployees = (List<String>) payload.get("selectedEmployees");
	    String chatRoomId = (String) payload.get("chatRoomId");
	    
	    System.out.println(selectedEmployees);
	    System.out.println(chatRoomId);
	    
	    
	    return cgService.setAlreadyExistChatGroupInvite(selectedEmployees, chatRoomId, loginID);
	}


	// 채팅그룹의 이름을 변경(본인에게만 적용)
	@ResponseBody
	@PostMapping("/updateChatGroupName")
	public int updateChatGroupName(@RequestBody Map<String, String> payload,
			@SessionAttribute("loginID") String loginID) {

		Map<String, Object> param = new HashMap<>();
		param.put("name", (String) payload.get("chatRoomName"));
		param.put("chat_room_id", (String) payload.get("chatRoomId"));
		param.put("emp_id", loginID);

		return cgService.updateChatGroupName(param);
	}
	
	//채팅그룹에서 나감.
	@ResponseBody
	@PostMapping("/deleteByEmpIdNChatId")
	public int deleteByEmpIdNChatId(@RequestBody Map<String,String> payload,
			@SessionAttribute("loginID") String loginID) {
		String extractRoomId = (String) payload.get("chatRoomId");
		return cgService.deleteByEmpIdNChatId(loginID, extractRoomId);
	}

	// 메인 페이지 관련 정보 넘기기
	@ResponseBody
	@GetMapping("/getMainData")
	public Map<String, Object> getMainData(@SessionAttribute("loginID") String loginID) {
		return cgService.getMainData(loginID);
	}

	@ResponseBody
	@GetMapping("/getMyInfo")
	public Map<String, Object> getMyInfo(@SessionAttribute("loginID") String loginID) {
		return cgService.getOfficerInfoByEmployeeId(loginID);
	}

	@ResponseBody
	@GetMapping("/getChatRoomIdBySearch")
	public List<Map<String,Object>> getChatRoomIdBySearch(@RequestParam String search) {
		return cmService.getChatRoomIdBySearch(search);
	}

	// 세션에 접속한 사용자에게 채팅그룹 목록을 가져옴
	@ResponseBody
	@GetMapping("/chatListLoad")
	public List<Map<String, Object>> selectByEmpId(@SessionAttribute("loginID") String loginID) {
		return cgService.selectByEmpId(loginID);
	}

	@ResponseBody
	@GetMapping("/getAllOfficerList")
	public List<Map<String, Object>> getAllOfficerList() {
		return cgService.getAllOfficerInfo();
	}

	@ResponseBody
	@GetMapping("/getOfficerInfoByEmployeeId")
	public Map<String, Object> getOfficerInfoByEmployeeId(@RequestParam String employee_id) {
		return cgService.getOfficerInfoByEmployeeId(employee_id);
	}

	@ResponseBody
	@RequestMapping("/chatMsgLoad")
	public Map<String, Object> selectByChatId(@RequestParam("emp_id") String emp_id,
			@RequestParam("chat_room_id") String chat_room_id) {
		return cmService.selectByChatRoomId(emp_id, chat_room_id);
	}

}
