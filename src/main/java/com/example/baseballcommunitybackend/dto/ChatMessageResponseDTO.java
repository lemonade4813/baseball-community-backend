package com.example.baseballcommunitybackend.dto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ChatMessageResponseDTO {
    private String id;
    private String sender;
    private String team;
    private String content;
    private LocalDateTime timestamp;
    private String profileImageUrl;
}
