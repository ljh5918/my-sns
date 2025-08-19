

// package com.myapp.sns.controller;

// import com.myapp.sns.dto.PostRequest;
// import com.myapp.sns.dto.PostResponse;
// import com.myapp.sns.service.PostService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.Authentication;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;




// @RestController
// @RequestMapping("/api/posts")
// @RequiredArgsConstructor
// public class PostController {

//     private final PostService postService;

//     // 전체 게시글 조회 (로그인 필요 없음)
//     @GetMapping
//     public ResponseEntity<List<PostResponse>> getAllPosts() {
//         return ResponseEntity.ok(postService.getAllPosts());
//     }

//     // 특정 게시글 조회 (로그인 필요 없음)
//     @GetMapping("/{id}")
//     public ResponseEntity<Object> getPost(@PathVariable Long id) {
//         return ResponseEntity.ok(postService.getPost(id));
//     }

//     // 게시글 작성 (로그인 필요)
//     @PostMapping
//     public ResponseEntity<?> createPost(@RequestBody PostRequest request, Authentication authentication) {
//         postService.createPost(request, authentication);
//         return ResponseEntity.ok("게시글 작성 완료");
//     }

//     // 게시글 수정 (작성자만 가능)
//     @PutMapping("/{id}")
//     public ResponseEntity<?> updatePost(@PathVariable Long id,
//                                         @RequestBody PostRequest request,
//                                         Authentication authentication) {
//         postService.updatePost(id, request, authentication);
//         return ResponseEntity.ok("게시글 수정 완료");
//     }

//     // 게시글 삭제 (작성자만 가능)
//     @DeleteMapping("/{id}")
//     public ResponseEntity<?> deletePost(@PathVariable Long id, Authentication authentication) {
//         postService.deletePost(id, authentication);
//         return ResponseEntity.ok("게시글 삭제 완료");
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
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 전체 게시글 조회 (로그인 안 해도 가능)
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    // id값으로 사용자가 작성한 게시글만 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    // 게시글 작성 (로그인 필요)
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest request,
                                                   Authentication authentication) {
        String email = authentication.getName(); // 토큰에서 꺼낸 사용자 이메일
        return ResponseEntity.ok(postService.createPost(request, email));
    }

    // 게시글 수정 (작성자 본인만)
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long id,
                                                   @RequestBody PostRequest request,
                                                   Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(postService.updatePost(id, request, email));
    }

    // 게시글 삭제 (작성자 본인만)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id,
                                           Authentication authentication) {
        String email = authentication.getName();
        postService.deletePost(id, email);
        return ResponseEntity.noContent().build();
    }
}
