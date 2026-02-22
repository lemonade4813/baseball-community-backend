package com.example.baseballcommunitybackend.repository;

import com.example.baseballcommunitybackend.document.Post;
import com.example.baseballcommunitybackend.dto.PostCountDTO;
import com.example.baseballcommunitybackend.dto.TeamCountDTO;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByTitleContainingIgnoreCase(String title);
    List<Post> findByContentContainingIgnoreCase(String content);

    @Aggregation(pipeline = {
            "{ '$group': { '_id': '$author', 'totalCount': { '$sum': 1 } } }"
    })
    List<PostCountDTO> groupByNickname();

    @Aggregation(pipeline = {
            "{ '$group': { '_id': '$team', 'totalCount': { '$sum': 1 } } }"
    })
    List<PostCountDTO> groupByTeam();

}
