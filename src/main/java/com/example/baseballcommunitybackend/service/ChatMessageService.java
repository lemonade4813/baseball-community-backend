package com.example.baseballcommunitybackend.service;

import com.example.baseballcommunitybackend.document.ChatMessage;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageService {
    private final MongoTemplate mongoTemplate;

    public ChatMessageService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 메시지를 저장하고 컬렉션 이름을 팀에 따라 다르게 설정
     */
    public ChatMessage saveMessage(String team, ChatMessage message) {
        String collectionName = "message_" + team;
        return mongoTemplate.save(message, collectionName);
    }

    /**
     * 특정 채팅방(roomId)에 저장된 메시지 불러오기
     */
    public List<ChatMessage> getMessagesByTeam(String team) {
        String collectionName = "message_" + team;
        return mongoTemplate.findAll(ChatMessage.class, collectionName);
    }
}