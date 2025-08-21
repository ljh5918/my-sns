package com.myapp.sns.repository;

import com.myapp.sns.entity.Post;
import com.myapp.sns.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // Post 엔티티에 작성자 필드명이 author 라고 가정
    List<Post> findByAuthor(User author);
}
