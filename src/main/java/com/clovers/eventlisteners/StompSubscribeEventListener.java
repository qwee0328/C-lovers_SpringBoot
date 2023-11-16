package com.clovers.eventlisteners;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import com.clovers.dto.ChatMessageDTO;
import com.clovers.services.MemberService;

import jakarta.servlet.http.HttpSession;

@Component
public class StompSubscribeEventListener implements ApplicationListener<SessionSubscribeEvent> {

	
	
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	
	
	@Autowired
	private MemberService mService;
	
    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        
        String userid = (String) headerAccessor.getSessionAttributes().get("loginID"); 
        String username = mService.selectNameById(userid);
        // 구독 정보 추출
        String sessionId = headerAccessor.getSessionId();
        String subscriptionId = headerAccessor.getSubscriptionId();
        String destination = headerAccessor.getDestination();
        ChatMessageDTO sysChat = new ChatMessageDTO();
        sysChat.setChat_room_id("e06547e1-27a3-42fb-8dda-784c497b5417");
        sysChat.setContent(username + " 님이 입장하셨습니다.");
        sysChat.setEmp_id("system");
        sysChat.setState(ChatMessageDTO.ChatMessageStates.JOIN);
        sysChat.setWrite_date(new Timestamp(System.currentTimeMillis()));

        // 로그 출력 또는 필요한 처리 수행
        System.out.println("New subscription: sessionId=" + sessionId + ", subscriptionId=" + subscriptionId + ", destination=" + destination);
        messagingTemplate.convertAndSend(destination,sysChat);
    }
}