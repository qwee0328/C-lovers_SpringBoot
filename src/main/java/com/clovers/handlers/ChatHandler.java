package com.clovers.handlers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.clovers.model.ChatMessageModel;

@Controller
public class ChatHandler {
	
	@MessageMapping("/chat.sendMessage") // 메시지 매핑(-> 채팅을 보낼 때 사용) -> 채팅방 
    @SendTo("/topic/public")			 // 메시지 매핑을 적용한 곳에 SendTo를 하게 된다면 라우팅이 적용
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

}
