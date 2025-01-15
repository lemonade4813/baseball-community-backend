package com.example.baseballcommunitybackend.service;

import com.example.baseballcommunitybackend.document.Post;
import com.example.baseballcommunitybackend.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getPosts(String title, String content) {
        if (title != null) {
            return postRepository.findByTitleContainingIgnoreCase(title);
        } else if (content != null) {
            return postRepository.findByContentContainingIgnoreCase(content);
        } else {
            return postRepository.findAll(); // 아무 조건도 없으면 전체 조회
        }
    }

    public Optional<Post> getPostById(String id) {
        return postRepository.findById(id);
    }

    public Post createPost(Post board) {
        board.setCreatedAt(LocalDateTime.now().toString());
        return postRepository.save(board);
    }

    public Post updatePost(String id, Post updatedPost) {
        return postRepository.findById(id)
                .map(post -> {
                    post.setTitle(updatedPost.getTitle());
                    post.setContent(updatedPost.getContent());
                    post.setAuthor(updatedPost.getAuthor());
                    return postRepository.save(post);
                })
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
    }

    public void deletePost(String id) {
        postRepository.deleteById(id);
    }
}