package com.example.baseballcommunitybackend.controller;


import com.example.baseballcommunitybackend.document.Post;
import com.example.baseballcommunitybackend.document.PostRecommend;
import com.example.baseballcommunitybackend.dto.PostRecommendRequestDTO;
import com.example.baseballcommunitybackend.service.CustomUserDetails;
import com.example.baseballcommunitybackend.service.PostRecommendService;
import com.example.baseballcommunitybackend.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final PostRecommendService  postRecommendService;

    public PostController(PostService postService, PostRecommendService postRecommendService) {
        this.postService = postService;
        this.postRecommendService = postRecommendService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getPosts(@RequestParam(required = false) String title,
                                               @RequestParam(required = false) String content) {
        List<Post> posts = postService.getPosts(title, content);

        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build(); // 데이터가 없는 경우 204 No Content
        }
        return ResponseEntity.ok(posts); // 데이터가 있는 경우 200 OK

    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable String id) {
        return postService.getPostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post,  @AuthenticationPrincipal CustomUserDetails userDetails) {
        Post createdPost = postService.createPost(post, userDetails.getNickname());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost); // 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable String id, @RequestBody Post post) {
        try {
            return ResponseEntity.ok(postService.updatePost(id, post));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/is-author")
    public ResponseEntity<Boolean> isAuthor(
            @PathVariable String id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        boolean isAuthor = postService.isAuthorOfPost(id, userDetails.getNickname());
        return ResponseEntity.ok(isAuthor);
    }

    @PostMapping("/recommend")
    public ResponseEntity<String> recommend(
            @RequestBody PostRecommendRequestDTO postRecommendRequestDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        System.out.println(postRecommendRequestDTO);

        PostRecommend postRecommend = new PostRecommend();
        postRecommend.setPostId(postRecommendRequestDTO.getId());
        postRecommend.setIsRecommend(postRecommendRequestDTO.getIsRecommend());
        postRecommend.setNickname(userDetails.getNickname());

        postRecommendService.postRecommendSave(postRecommend);

        return ResponseEntity.ok("처리 되었습니다.");
    }


    @GetMapping("/{id}/recommend/count")
    public ResponseEntity<?> recommendCount(
            @PathVariable String id) {

        PostRecommendService.PostRecommendCount count = postRecommendService.getPostRecommendCount(id);

        return ResponseEntity.ok(count);
    }




}