package com.blog.myblog5.repository;

import com.blog.myblog5.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
