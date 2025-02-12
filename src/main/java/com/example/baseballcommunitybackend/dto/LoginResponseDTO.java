package com.example.baseballcommunitybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDTO {
    private String accessToken;
    private String nickname;
    private String profileImagePath;
    private String team;
}