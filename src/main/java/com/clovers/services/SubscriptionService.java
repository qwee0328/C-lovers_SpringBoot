package com.clovers.services;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
import com.clovers.dto.ChatRoomSubscriptions;

@Service
public class SubscriptionService {
	private final ConcurrentHashMap<String, ChatRoomSubscriptions> chatRoomSubscriptions = new ConcurrentHashMap<>();

	// 채팅방과 연관된 구독정보 추가
	public void addSubscription(String chatRoomId, String sessionId, String loginId) {
		chatRoomSubscriptions.computeIfAbsent(chatRoomId, k -> new ChatRoomSubscriptions()).addSubscription(chatRoomId,
				sessionId, loginId);
	}

	// 채팅방과 연관된 구독정보 제거
	public void removeSubscription(String chatRoomId, String sessionId) {
		ChatRoomSubscriptions subscriptions = chatRoomSubscriptions.get(chatRoomId);
		if (subscriptions != null) {
			subscriptions.removeSubscription(chatRoomId, sessionId);
			if (subscriptions.isEmpty(chatRoomId)) {
				chatRoomSubscriptions.remove(chatRoomId);
			}
		}
	}

	// 채팅방과 연관된 모든 구독정보를 가져옴
	public ChatRoomSubscriptions getSubscriptions(String chatRoomId) {
		return chatRoomSubscriptions.getOrDefault(chatRoomId, new ChatRoomSubscriptions());
	}

	// 특정 채팅방의 구독 정보를 모두 제거
	public void clearSubscriptions(String chatRoomId) {
		chatRoomSubscriptions.remove(chatRoomId);
	}

	// 특정 세션 ID와 연관된 모든 구독 정보를 제거
    public void clearSubscriptionsForSession(String sessionId) {
        for (ChatRoomSubscriptions subscriptions : chatRoomSubscriptions.values()) {
            subscriptions.removeSubscriptionForSession(sessionId);
        }
    }

}
