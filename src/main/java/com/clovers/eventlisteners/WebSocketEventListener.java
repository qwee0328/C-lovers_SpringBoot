package com.clovers.eventlisteners;

import java.sql.Timestamp;

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
import com.clovers.services.MemberService;


@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    
    @Autowired
    private ChatMessageService cmService;
    
    @Autowired
    private MemberService mService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
        

    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        
        
        String userid = (String) headerAccessor.getSessionAttributes().get("loginID"); 
        String username = mService.selectNameById(userid);
        // 구독 정보 추출
        String sessionId = headerAccessor.getSessionId();
        // 로그 출력 또는 필요한 처리 수행
        System.out.println("New subscription: sessionId=" + sessionId + ", loginedID : " + userid );
        
        
        
    }
    
    
}
