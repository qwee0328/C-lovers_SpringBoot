package com.clovers.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.clovers.dto.ChatRoomSubscriptions;

@Service
public class SubscriptionService {
    private final Map<String, ChatRoomSubscriptions> chatRoomSubscriptions = new ConcurrentHashMap<>();

    // 채팅방과 연관된 구독정보 추가 
    public void addSubscription(String chatRoomId, String sessionId, String subscriptionId, String loginId) {
        chatRoomSubscriptions.computeIfAbsent(chatRoomId, k -> new ChatRoomSubscriptions()).addSubscription(sessionId, subscriptionId, loginId);
    }
    
    // 채팅방과 연관된 구독정보 제거
    public void removeSubscription(String chatRoomId, String sessionId, String subscriptionId) {
        ChatRoomSubscriptions subscriptions = chatRoomSubscriptions.get(chatRoomId);
        if (subscriptions != null) {
            subscriptions.removeSubscription(sessionId, subscriptionId);
            if (subscriptions.isEmpty()) {
                chatRoomSubscriptions.remove(chatRoomId);
            }
        }
    }
    
    // 채팅방과 연관된 모든 구독정보를 가져옴
    public ChatRoomSubscriptions getSubscriptions(String chatRoomId) {
        return chatRoomSubscriptions.getOrDefault(chatRoomId, new ChatRoomSubscriptions());
    }

    public void clearSubscriptions(String chatRoomId) {
        chatRoomSubscriptions.remove(chatRoomId);
    }
}

