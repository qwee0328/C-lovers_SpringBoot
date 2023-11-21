package com.clovers.eventlisteners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.clovers.services.SubscriptionService;

@Component
public class StompDisconnectEventListener implements ApplicationListener<SessionDisconnectEvent> {

    @Autowired
    private SubscriptionService subscriptionService;

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        
        // 세션 ID 추출
        String sessionId = headerAccessor.getSessionId();
        String loginId = (String)headerAccessor.getSessionAttributes().get("loginID");

        // 세션 ID와 연관된 모든 채팅방 구독 정보 제거
        subscriptionService.clearSubscriptionsForSession(sessionId);

        // 로그 출력 또는 추가 처리
        System.out.println("WebSocket session disconnected: " + sessionId + " : " + loginId);
    }
}
