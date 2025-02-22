package com.example.baseballcommunitybackend.service;

import com.example.baseballcommunitybackend.document.ChatMessage;
import com.example.baseballcommunitybackend.document.User;
import com.example.baseballcommunitybackend.dto.ChatMessageResponseDTO;
import com.example.baseballcommunitybackend.repository.UserRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatMessageService {
    private final MongoTemplate mongoTemplate;
    private final UserRepository userRepository;

    public ChatMessageService(MongoTemplate mongoTemplate, UserRepository userRepository) {
        this.mongoTemplate = mongoTemplate;
        this.userRepository = userRepository;
    }

    /**
     * 메시지를 저장하고 컬렉션 이름을 팀에 따라 다르게 설정
     */
    public ChatMessage saveMessage(ChatMessage message) {
        String team = message.getTeam();
        System.out.println(message);
        String collectionName = "message_" + team;
        return mongoTemplate.save(message, collectionName);
    }

    /**
     * 특정 채팅방(roomId)에 저장된 메시지 불러오기
     */
    public List<ChatMessageResponseDTO> getMessagesByTeam(String team) {
        String collectionName = "message_" + team;

        List<ChatMessage> chatMessageList = mongoTemplate.findAll(ChatMessage.class, collectionName);

        return chatMessageList.stream().map(chatMessage -> {
            List<User> users = userRepository.findAllByNickname(chatMessage.getSender());
            String profileImagePath = users.isEmpty() ? null : users.get(0).getProfileImagePath();

            return ChatMessageResponseDTO.builder()
                    .id(chatMessage.getId())
                    .sender(chatMessage.getSender())
                    .team(chatMessage.getTeam())
                    .content(chatMessage.getContent())
                    .timestamp(chatMessage.getTimestamp())
                    .profileImageUrl(profileImagePath)
                    .build();
        }).collect(Collectors.toList());
    }
}