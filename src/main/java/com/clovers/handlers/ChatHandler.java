package com.clovers.handlers;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.clovers.dto.ChatMessageDTO;
import com.clovers.services.ChatMessageService;
import com.clovers.services.MemberService;

@Controller
public class ChatHandler {

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	@Autowired
	private MemberService mService;
	
	@Autowired
	private ChatMessageService cmService;

	@MessageMapping("/chat/message")
	public void message(ChatMessageDTO chatMessage) {
		if (ChatMessageDTO.ChatMessageStates.JOIN.equals(chatMessage.getState())) {
			chatMessage.setContent(mService.selectNameById(chatMessage.getEmp_id()) + " 님이 채팅방에 입장하셨습니다.");
		}
		if (ChatMessageDTO.ChatMessageStates.EXIT.equals(chatMessage.getState())) {
			chatMessage.setContent(mService.selectNameById(chatMessage.getEmp_id()) + " 님이 채팅방을 나갔습니다.");
		}
		chatMessage.setWrite_date(new Timestamp(System.currentTimeMillis()));
		cmService.recordChat(chatMessage);
		messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getChat_room_id(), chatMessage);
	}

	@MessageMapping("/chat/addUser")
	public void addUser(@Payload ChatMessageDTO chatMessage, SimpMessageHeaderAccessor headerAccessor) {
		// 사용자 이름을 웹소켓 세션에 추가
		String username = mService.selectNameById(chatMessage.getEmp_id());
		headerAccessor.getSessionAttributes().put("username", username);

		// 헤더에 사용자 이름 추가
		headerAccessor.setLeaveMutable(true);
		headerAccessor.setHeader("username", username);
		headerAccessor.setHeader("state", ChatMessageDTO.ChatMessageStates.JOIN.toString());

		// 특정 채팅방에 메시지 전송
		messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getChat_room_id(), chatMessage,
				headerAccessor.getMessageHeaders());
	}

}
