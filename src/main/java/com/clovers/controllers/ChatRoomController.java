package com.clovers.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clovers.services.ChatGroupService;


@Controller
@RequestMapping("/chat")
public class ChatRoomController {

	@Autowired
	private ChatGroupService cgService;
	
	
	@RequestMapping("/goMain")
	public String goMain() {
		return "chat/main";
	}
	
	@RequestMapping("/chatList")
	public String goChatList() {
		return "chat/chatList";
	}
	
	
	@RequestMapping("/fileList")
	public String goFileList() {
		return "chat/fileList";
	}
	
	@RequestMapping("/goChatRoom")
	public String goChatRoom() {
		return "chat/chatRoom";
	}
	
	@ResponseBody
	@GetMapping("/chatListLoad")
	public List<Map<String, Object>> selectByEmpId() {
		return cgService.selectByEmpId();
	}
	
	@ResponseBody
	@RequestMapping("/chatMsgLoad")
	public Map<String,Object> selectByChatId(){
		return cgService.selectByChatId();
	}
	
}
