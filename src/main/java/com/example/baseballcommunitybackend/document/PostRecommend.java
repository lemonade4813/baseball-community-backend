package com.example.baseballcommunitybackend.document;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "post_recommend")
public class PostRecommend {

    @Id
    private String id;
    private String postId;
    private String nickname;
    private Boolean isRecommend;
}
