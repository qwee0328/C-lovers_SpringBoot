package com.clovers.dto;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatRoomSubscriptions {
	// 각 채팅방의 구독 정보를 관리하는 Map
	// Key: 채팅방 ID, Value: 해당 채팅방의 구독 정보 Map (Key: 세션 ID, Value: 로그인 ID)
	private final Map<String, Map<String, String>> chatRoomSubscriptions = new ConcurrentHashMap<>();

	// 채팅방에 새로운 구독 정보 추가
	public void addSubscription(String chatRoomId, String sessionId, String loginId) {
		chatRoomSubscriptions.computeIfAbsent(chatRoomId, k -> new ConcurrentHashMap<>()).put(sessionId, loginId);
	}

	// 특정 채팅방에서 구독 정보 제거
	public void removeSubscription(String chatRoomId, String sessionId) {
		Map<String, String> subscriptions = chatRoomSubscriptions.get(chatRoomId);
		if (subscriptions != null) {
			subscriptions.remove(sessionId);
			if (subscriptions.isEmpty()) {
				chatRoomSubscriptions.remove(chatRoomId);
			}
		}
	}

	// 특정 채팅방의 모든 구독 정보 조회
	public Map<String, String> getSubscriptions(String chatRoomId) {
		return chatRoomSubscriptions.getOrDefault(chatRoomId, Collections.emptyMap());
	}

	// 특정 채팅방의 구독 정보를 모두 제거
	public void clearSubscriptions(String chatRoomId) {
		chatRoomSubscriptions.remove(chatRoomId);
	}

	// 특정 채팅방에 구독 정보가 비어 있는지 확인
	public boolean isEmpty(String chatRoomId) {
		return chatRoomSubscriptions.getOrDefault(chatRoomId, Collections.emptyMap()).isEmpty();
	}

	// 특정 세션 ID와 연관된 모든 구독 정보를 제거
    public void removeSubscriptionForSession(String sessionId) {
        // 모든 채팅방을 순회하면서 특정 세션 ID와 연결된 구독 정보만 제거
        for (Map<String, String> subscriptions : chatRoomSubscriptions.values()) {
            subscriptions.remove(sessionId);
        }
    }
}
