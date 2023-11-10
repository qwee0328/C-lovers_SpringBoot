package com.clovers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.clovers.dto.ChatMessageDTO;
import com.clovers.services.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ChatController {
	
//	private final SimpMessageSendingOperations messagingTemplate;
//	
//	@Autowired
//	private MemberService mService;
//	
//	//Publisher 구현
//	@MessageMapping("/chat/message")
//	public void message(ChatMessageDTO message) {
//		if(ChatMessageDTO.ChatMessageStates.JOIN.equals(message.getState())) {
//			message.setContent(mService.selectNameById(message.getEmp_id()) +" 님이 입장하셨습니다.");
//		}
//		messagingTemplate.convertAndSend("/sub/chat/room/" + message.getChat_room_id(), message);
//	}
}
