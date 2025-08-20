package com.myapp.sns.service;

import com.myapp.sns.dto.PostRequest;
import com.myapp.sns.dto.PostResponse;
import com.myapp.sns.entity.Post;
import com.myapp.sns.entity.User;
import com.myapp.sns.repository.PostRepository;
import com.myapp.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 게시글 전체 조회 (로그인 여부 상관 없음)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // 단일 게시글 조회
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
        return toResponse(post);
    }

    


    // 게시글 작성 (로그인 사용자만)
    public PostResponse createPost(PostRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));

        Post post = Post.builder()
                .content(request.getContent())
                .imageUrl(request.getImageUrl())
                .author(user)
                .build();

        return toResponse(postRepository.save(post));
    }

    // 게시글 수정 (작성자 본인만 가능)
    public PostResponse updatePost(Long id, PostRequest request, String userEmail) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        if (!post.getAuthor().getEmail().equals(userEmail)) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        post.setContent(request.getContent());
        post.setImageUrl(request.getImageUrl());

        return toResponse(postRepository.save(post));
    }

    // 게시글 삭제 (작성자 본인만 가능)
    public void deletePost(Long id, String userEmail) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        if (!post.getAuthor().getEmail().equals(userEmail)) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

        postRepository.delete(post);
    }

    // Entity → DTO 변환
    private PostResponse toResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .createdAt(post.getCreatedAt())
                .authorEmail(post.getAuthor().getEmail())
                .authorUsername(post.getAuthor().getUsername())
                .build();
    }
}
