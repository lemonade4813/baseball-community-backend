package com.example.baseballcommunitybackend.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDTO<T> {
    private String message;
    private T data;
}
