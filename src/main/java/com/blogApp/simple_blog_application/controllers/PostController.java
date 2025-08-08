package com.blogApp.simple_blog_application.controllers;

import com.blogApp.simple_blog_application.dto.*;
import com.blogApp.simple_blog_application.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        return postService.getPostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PostDTO createPost(@RequestBody PostRequestDTO postRequestDTO) {
        return postService.createPost(postRequestDTO);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDTO> addComment(@PathVariable Long postId, @RequestBody CommentRequestDTO commentRequestDTO) {
        return postService.addComment(postId, commentRequestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        postService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{postId}/like")
    public ResponseEntity<String> likePost(@PathVariable Long postId) {
        boolean success = postService.like(postId);
        if (success) {
            return ResponseEntity.ok("Post liked successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
