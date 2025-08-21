// package com.myapp.sns.controller;

// import com.myapp.sns.dto.PostResponse;
// import com.myapp.sns.service.PostService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.Authentication;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @CrossOrigin(origins = "http://localhost:3000")
// @RestController
// @RequestMapping("/api/mypage")
// @RequiredArgsConstructor
// public class MyPageController {

//     private final PostService postService;

//     // 로그인한 사용자가 작성한 게시글만 조회
//     @GetMapping("/posts")
//     public ResponseEntity<List<PostResponse>> getMyPosts(Authentication authentication) {
//         String email = authentication.getName(); // JWT에서 추출된 사용자 이메일
//         return ResponseEntity.ok(postService.getPostsByUser(email));
//     }
// }














package com.myapp.sns.controller;

import com.myapp.sns.dto.PostRequest;
import com.myapp.sns.dto.PostResponse;
import com.myapp.sns.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final PostService postService;

    // 내 글 목록
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> myPosts(Authentication auth) {
        String email = auth.getName();
        return ResponseEntity.ok(postService.getPostsByUser(email));
    }

    // 내 글 작성
    @PostMapping("/posts")
    public ResponseEntity<PostResponse> create(Authentication auth, @RequestBody PostRequest req) {
        String email = auth.getName();
        return ResponseEntity.ok(postService.createMyPost(req, email));
    }

    // 내 글 수정
    @PutMapping("/posts/{id}")
    public ResponseEntity<PostResponse> update(@PathVariable Long id,
                                               Authentication auth,
                                               @RequestBody PostRequest req) {
        String email = auth.getName();
        return ResponseEntity.ok(postService.updateMyPost(id, req, email));
    }

    // 내 글 삭제
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication auth) {
        String email = auth.getName();
        postService.deleteMyPost(id, email);
        return ResponseEntity.noContent().build();
    }
}
