package com.clovers.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.clovers.dto.ChatMessageDTO;
import com.clovers.services.MemberService;

@Controller
public class ChatHandler {
	
	private final SimpMessageSendingOperations messagingTemplate = null;
	
	@Autowired
	private MemberService mService;
	
	@MessageMapping("/chat/message")
	public void message(ChatMessageDTO message) {
		if(ChatMessageDTO.ChatMessageStates.JOIN.equals(message.getState())) {
			message.setContent(mService.selectNameById(message.getEmp_id()) +" 님이 입장하셨습니다.");
		}
		messagingTemplate.convertAndSend("/sub/chat/room/" + message.getChat_room_id(), message);
	}
	
	@MessageMapping("/chat.sendMessage") // 메시지 매핑(-> 채팅을 보낼 때 사용) -> 채팅방 
    @SendTo("/topic/public")			 // 메시지 매핑을 적용한 곳에 SendTo를 하게 된다면 라우팅이 적용
    public ChatMessageDTO sendMessage(@Payload ChatMessageDTO chatMessage) {
        return chatMessage;
    }
	
	

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessageDTO addUser(@Payload ChatMessageDTO chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", mService.selectNameById(chatMessage.getEmp_id()));
        return chatMessage;
    }

}
