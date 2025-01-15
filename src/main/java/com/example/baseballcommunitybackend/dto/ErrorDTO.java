package com.example.baseballcommunitybackend.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTO {
    private String error;
}
