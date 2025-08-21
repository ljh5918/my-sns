package com.myapp.sns.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostRequest {
    private String content;
    private String imageUrl; // 이미지 파일 대신 URL/경로 문자열
}
