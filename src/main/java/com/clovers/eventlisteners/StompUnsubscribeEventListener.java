package com.clovers.eventlisteners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import com.clovers.services.SubscriptionService;

@Component
public class StompUnsubscribeEventListener implements ApplicationListener<SessionUnsubscribeEvent> {
	
	@Autowired
	private SubscriptionService subscriptionService;
	
	
    @Override
    public void onApplicationEvent(SessionUnsubscribeEvent event) {
    	
    	
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // 구독 해제 정보 추출
        String sessionId = headerAccessor.getSessionId();
        String subscriptionId = headerAccessor.getSubscriptionId();
        String destination = headerAccessor.getDestination();

        // 로그 출력 또는 필요한 처리 수행
        System.out.println("Unsubscription: sessionId=" + sessionId + ", subscriptionId=" + subscriptionId + ", destination=" + destination);
    }
}
