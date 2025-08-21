package com.myapp.sns.dto;

import com.myapp.sns.entity.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponse {
    private Long id;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;
    private String authorEmail;
    private String authorUsername;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
        this.imageUrl = post.getImageUrl();
        this.createdAt = post.getCreatedAt();
        this.authorEmail = post.getAuthor().getEmail();
        this.authorUsername = post.getAuthor().getUsername();
    }

    public static PostResponse from(Post post) {
        return new PostResponse(post);
    }
}
