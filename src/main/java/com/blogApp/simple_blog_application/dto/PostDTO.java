package com.blogApp.simple_blog_application.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostDTO {
    private Long id;
    private String img_url;
    private String title;
    private Long likes;
    private String description;
    private List<CommentDTO> comments;
}
