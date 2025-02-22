package com.example.baseballcommunitybackend.service;


import com.example.baseballcommunitybackend.document.PostRecommend;
import com.example.baseballcommunitybackend.repository.PostRecommendRepository;
import org.springframework.stereotype.Service;

@Service
public class PostRecommendService {
    private final PostRecommendRepository postRecommendRepository;

    PostRecommendService (PostRecommendRepository postRecommendRepository) {
        this.postRecommendRepository = postRecommendRepository;
    }

    public PostRecommendCount getPostRecommendCount(String postId) {
        int recommendedCount = postRecommendRepository.countByPostIdAndIsRecommend(postId, true);
        int notRecommendedCount = postRecommendRepository.countByPostIdAndIsRecommend(postId, false);

        return new PostRecommendCount(recommendedCount, notRecommendedCount);
    }

    public record PostRecommendCount(int recommendedCount, int notRecommendedCount) {}

    public void postRecommendSave(PostRecommend postRecommend) {
        postRecommendRepository.save(postRecommend);
    }

}
