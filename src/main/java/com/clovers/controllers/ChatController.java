package com.clovers.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clovers.services.ChatGroupService;


@Controller
public class ChatController {

	@Autowired
	private ChatGroupService cgService;
	
	
	@RequestMapping("/chat/goMain")
	public String goMain() {
		return "chat/main";
	}
	
	@RequestMapping("/chat/chatList")
	public String goChatList() {
		return "chat/chatList";
	}
	
	@RequestMapping("/chat/fileList")
	public String goFileList() {
		return "chat/fileList";
	}
	
	@RequestMapping("/chat/goChatRoom")
	public String goChatRoom() {
		return "chat/chatRoom";
	}
	
	@ResponseBody
	@RequestMapping("/chat/chatListLoad")
	public List<Map<String, Object>> selectByEmpId() {
		return cgService.selectByEmpId();
	}
	
	@ResponseBody
	@RequestMapping("/chat/chatMsgLoad")
	public Map<String,Object> selectByChatId(){
		return cgService.selectByChatId();
	}
	
}
