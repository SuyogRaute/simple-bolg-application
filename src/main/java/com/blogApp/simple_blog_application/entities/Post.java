package com.blogApp.simple_blog_application.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String img_url;
    private String title;
    private Long likes;
    private String description;

    @OneToMany(cascade = CascadeType.ALL,mappedBy ="post")
    private List<Comment> comments;


}
