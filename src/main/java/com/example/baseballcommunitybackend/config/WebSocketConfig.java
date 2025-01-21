package com.example.baseballcommunitybackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/baseballchat"); // 클라이언트가 구독하는 주제
        config.setApplicationDestinationPrefixes("/app"); // 클라이언트가 메시지를 보낼 때 사용할 접두사
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // WebSocket 연결 엔드포인트
                .setAllowedOrigins("*") // CORS 설정
                .withSockJS(); // SockJS 지원
    }
}