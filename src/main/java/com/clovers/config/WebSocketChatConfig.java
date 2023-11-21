package com.clovers.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.clovers.interceptors.UserHandShakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketChatConfig implements WebSocketMessageBrokerConfigurer {
	// WebSocket - Stomp 메시지를 관리하는 메시지브로커 클래스
	// 메시지에 헤더에 정보를 나누어서 메시지의 속성마다 메시지를 다르게 처리하는것이 가능해짐. 
	// 채팅창을 만드는 것 - publish
	// 채팅창에 초대되는 것 - subscribe
	
	@Autowired
	private UserHandShakeInterceptor userHandShakeInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
    	registry.addEndpoint("/ws/chat")
    		.setAllowedOriginPatterns("*")
    		.withSockJS()
    		.setInterceptors(userHandShakeInterceptor); // 인터셉터에서 특정 정보를 가져올 수 있음.
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/pub");
        registry.enableSimpleBroker("/app","/sub");   // Enables a simple in-memory broker


        //   Use this for enabling a Full featured broker like RabbitMQ

        /*
        registry.enableStompBrokerRelay("/topic")
                .setRelayHost("localhost")
                .setRelayPort(61613)
                .setClientLogin("guest")
                .setClientPasscode("guest");
        */
    }
}