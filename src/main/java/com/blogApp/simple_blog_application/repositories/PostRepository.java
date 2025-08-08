package com.blogApp.simple_blog_application.repositories;

import com.blogApp.simple_blog_application.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
