package com.example.baseballcommunitybackend.service;

import com.example.baseballcommunitybackend.document.Post;
import com.example.baseballcommunitybackend.document.User;
import com.example.baseballcommunitybackend.repository.PostRepository;
import com.example.baseballcommunitybackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
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

    public Post createPost(Post post, String nickname) {
        post.setAuthor(nickname);
        post.setCreatedAt(LocalDateTime.now().toString());
        return postRepository.save(post);
    }

    public Post updatePost(String id, Post updatedPost) {

        return postRepository.findById(id)
                .map(post -> {
                    post.setTitle(updatedPost.getTitle());
                    post.setContent(updatedPost.getContent());
                    return postRepository.save(post);
                })
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다.: " + id));
    }

    public void deletePost(String id) {
        postRepository.deleteById(id);
    }

    public boolean isAuthorOfPost(String postId, String author) {

        return postRepository.findById(postId)
                .map(post -> post.getAuthor().equals(author))
                .orElse(false); // 게시물이 없으면 false 반환
    }
}