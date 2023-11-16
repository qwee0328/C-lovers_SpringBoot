package com.clovers.eventlisteners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.clovers.dto.ChatMessageDTO;
import com.clovers.services.ChatMessageService;


@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    
    @Autowired
    private ChatMessageService cmService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        

    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        
        // 세션에서 Stomp Header로 부터 가져오는 객체(바꿔야됨)
        String username = (String) headerAccessor.getSessionAttributes().get("username"); 
        if(username != null) {
            logger.info(username + "님이 채팅방을 나갔습니다.");

            ChatMessageDTO chatMessage = new ChatMessageDTO();
            chatMessage.setState(ChatMessageDTO.ChatMessageStates.EXIT);
            chatMessage.setContent(username);

            messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getChat_room_id(), chatMessage);
        }
    }
    
    
}
