package com.myapp.sns.dto;

import lombok.Data;

@Data
public class PostRequest {
    private String content;
    private String imageUrl; // 선택사항
}
