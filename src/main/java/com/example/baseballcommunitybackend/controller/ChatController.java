package com.example.baseballcommunitybackend.controller;

import com.example.baseballcommunitybackend.document.ChatMessage;
import com.example.baseballcommunitybackend.service.ChatMessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChatController {
    private final ChatMessageService chatMessageService;

    public ChatController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @MessageMapping("/chat/{team}") // 클라이언트에서 메시지 전송
    @SendTo("/baseballchat/room/{team}")  // 특정 채팅방으로 메시지 브로드캐스트
    public ChatMessage sendMessage(@PathVariable String team, ChatMessage message) {
        System.out.println(team);
        return chatMessageService.saveMessage(team, message); // 메시지 저장
    }

    @GetMapping("/chat/{team}/messages") // 특정 채팅방 메시지 조회
    public List<ChatMessage> getMessages(@PathVariable String team) {
        System.out.println(team);
        return chatMessageService.getMessagesByTeam(team);
    }
}