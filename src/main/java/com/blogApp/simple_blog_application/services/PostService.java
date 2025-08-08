package com.blogApp.simple_blog_application.services;

import com.blogApp.simple_blog_application.dto.*;
import com.blogApp.simple_blog_application.entities.Comment;
import com.blogApp.simple_blog_application.entities.Post;
import com.blogApp.simple_blog_application.repositories.CommentsRepository;
import com.blogApp.simple_blog_application.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    // Get all posts with comments
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream().map(this::convertToPostDTO).collect(Collectors.toList());
    }

    // Get a single post by ID
    public Optional<PostDTO> getPostById(Long id) {
        return postRepository.findById(id).map(this::convertToPostDTO);
    }

    // Create a post
    public PostDTO createPost(PostRequestDTO requestDTO) {
        Post post = new Post();
        post.setTitle(requestDTO.getTitle());
        post.setDescription(requestDTO.getDescription());
        post.setLikes(requestDTO.getLikes());
        post.setImg_url(requestDTO.getImg_url());

        Post saved = postRepository.save(post);
        return convertToPostDTO(saved);
    }

    // Add a comment to a post
    public Optional<CommentDTO> addComment(Long postId, CommentRequestDTO commentRequestDTO) {
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isPresent()) {
            Comment comment = new Comment();
            comment.setComment(commentRequestDTO.getComment());
            comment.setPost(postOpt.get());

            Comment saved = commentsRepository.save(comment);
            return Optional.of(convertToCommentDTO(saved));
        }
        return Optional.empty();
    }

    // Delete a post
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    // Delete a comment
    public void deleteComment(Long commentId) {
        commentsRepository.deleteById(commentId);
    }

    public boolean like(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (post.getLikes() == null) {
                post.setLikes(1L); // if null, start from 1
            } else {
                post.setLikes(post.getLikes() + 1);
            }

            postRepository.save(post);
            return true;
        } else {
            return false;
        }
    }


    // ===================== CONVERTERS =====================

    private PostDTO convertToPostDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setImg_url(post.getImg_url());
        dto.setTitle(post.getTitle());
        dto.setLikes(post.getLikes());
        dto.setDescription(post.getDescription());

        List<CommentDTO> comments = post.getComments() != null
                ? post.getComments().stream().map(this::convertToCommentDTO).collect(Collectors.toList())
                : null;

        dto.setComments(comments);
        return dto;
    }

    private CommentDTO convertToCommentDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setComment(comment.getComment());
        return dto;
    }
}
