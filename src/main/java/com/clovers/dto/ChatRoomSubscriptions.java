package com.clovers.dto;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatRoomSubscriptions {
	// 웹소켓 구독정보와 관련된 DTO. DB에 연결하지 않는다. 채팅방마다 다르게 처리할 생
	private final Map<String, Map<String,String>> sessionSubscriptions = new ConcurrentHashMap<>();
	
	
	public void addSubscription(String sessionId, String SubscriptionId, String loginId) {
		sessionSubscriptions.computeIfAbsent(sessionId, k -> new HashMap<>()).put(SubscriptionId, loginId);
	}
	
	public void removeSubscription(String sessionId, String subscriptionId) {
		Map<String,String> subscriptions = sessionSubscriptions.get(sessionId);
		if(subscriptions != null) {
			subscriptions.remove(subscriptionId);
			if(subscriptions.isEmpty()) {
				sessionSubscriptions.remove(sessionId);
			}
		}
	}
	
	// 특정 세션의 구독을 조회 : 특정 세션 Id에 대한 모든 구독 Id와 로그인 Id 조회
	public Map<String,String> getSubscriptions(String sessionId){
		return sessionSubscriptions.getOrDefault(sessionId, Collections.emptyMap());
	}
	
	public void clearSubscriptions(String sessionId) {
		sessionSubscriptions.remove(sessionId);
	}
	
    public boolean isEmpty() {
        return sessionSubscriptions.isEmpty();
    }

}
