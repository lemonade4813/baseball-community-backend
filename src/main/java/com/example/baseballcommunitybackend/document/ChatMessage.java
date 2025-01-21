package com.example.baseballcommunitybackend.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "messages")
public class ChatMessage {
    @Id
    private String id;
    private String sender;
    private String content;
    private LocalDateTime timestamp;

}