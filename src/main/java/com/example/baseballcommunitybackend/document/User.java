package com.example.baseballcommunitybackend.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String userId;
    private String password;
    private String nickname;
    private String team;
    private String profileImagePath;
}