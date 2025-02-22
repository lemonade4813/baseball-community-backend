package com.example.baseballcommunitybackend.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostRecommendRequestDTO {

    private String id;
    private Boolean isRecommend;

}
