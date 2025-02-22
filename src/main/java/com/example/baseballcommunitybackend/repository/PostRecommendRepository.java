package com.example.baseballcommunitybackend.repository;

import com.example.baseballcommunitybackend.document.PostRecommend;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRecommendRepository extends MongoRepository<PostRecommend, String> {
    Integer countByPostIdAndIsRecommend(String postId, boolean isRecommend);

}
