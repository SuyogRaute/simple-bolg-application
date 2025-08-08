package com.blogApp.simple_blog_application.repositories;

import com.blogApp.simple_blog_application.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comment,Long> {
}
