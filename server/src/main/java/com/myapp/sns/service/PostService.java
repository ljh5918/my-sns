package com.myapp.sns.service;

import com.myapp.sns.dto.PostRequest;
import com.myapp.sns.dto.PostResponse;
import com.myapp.sns.entity.Post;
import com.myapp.sns.entity.User;
import com.myapp.sns.repository.PostRepository;
import com.myapp.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 전체 게시글
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }

    // 단일 게시글
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
        return PostResponse.from(post);
    }

    // 내 게시글 목록
    public List<PostResponse> getPostsByUser(String email) {
        User me = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
        return postRepository.findByAuthor(me).stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }

    // 내 글 작성
    public PostResponse createMyPost(PostRequest req, String email) {
        User me = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        Post post = Post.builder()
                .content(req.getContent())
                .imageUrl(req.getImageUrl())
                .author(me)
                .createdAt(LocalDateTime.now())
                .build();

        return PostResponse.from(postRepository.save(post));
    }

    // 내 글 수정
    public PostResponse updateMyPost(Long id, PostRequest req, String email) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        if (!post.getAuthor().getEmail().equals(email)) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        post.setContent(req.getContent());
        post.setImageUrl(req.getImageUrl());
        return PostResponse.from(postRepository.save(post));
    }

    // 내 글 삭제
    public void deleteMyPost(Long id, String email) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        if (!post.getAuthor().getEmail().equals(email)) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }
        postRepository.delete(post);
    }
}
