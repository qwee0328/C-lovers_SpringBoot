package com.clovers.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clovers.dto.ChatGroupDTO;
import com.clovers.model.ChatMessageModel;
import com.clovers.services.ChatGroupService;



@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessageModel sendMessage(@Payload ChatMessageModel chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessageModel addUser(@Payload ChatMessageModel chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    
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
	public List<ChatGroupDTO> selectByEmpId() {
		return cgService.selectByEmpId();
	}
	
	@ResponseBody
	@RequestMapping("/chat/chatMsgLoad")
	public Map<String,Object> selectByChatId(){
		return cgService.selectByChatId();
	}
	
}
