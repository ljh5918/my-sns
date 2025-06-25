// // package com.myapp.sns.controller;

// // public class PostController {
  
// // }


// package com.myapp.sns.controller;

// import com.myapp.sns.entity.Post;
// import com.myapp.sns.repository.PostRepository;
// import org.springframework.web.bind.annotation.*;

// import java.time.LocalDateTime;
// import java.util.List;

// @CrossOrigin(origins = "http://localhost:3000")
// @RestController
// @RequestMapping("/api/posts")
// public class PostController {

//     private final PostRepository postRepository;

//     public PostController(PostRepository postRepository) {
//         this.postRepository = postRepository;
//     }

//     @GetMapping
//     public List<Post> getAllPosts() {
//         return postRepository.findAll();
//     }

//     @PostMapping
//     public Post createPost(@RequestBody Post post) {
//         post.setCreatedAt(LocalDateTime.now());
//         return postRepository.save(post);
//     }

//     @GetMapping("/{id}")
//     public Post getPost(@PathVariable Long id) {
//         return postRepository.findById(id).orElseThrow();
//     }
// }


package com.myapp.sns.controller;

import com.myapp.sns.dto.PostRequest;
import com.myapp.sns.dto.PostResponse;
import com.myapp.sns.entity.Post;
import com.myapp.sns.entity.User;
import com.myapp.sns.repository.PostRepository;
import com.myapp.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostRequest request, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow();

        Post post = Post.builder()
                .content(request.getContent())
                .imageUrl(request.getImageUrl())
                .author(user)
                .build();

        postRepository.save(post);

        return ResponseEntity.ok("게시글 작성 완료");
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        List<PostResponse> result = posts.stream()
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .content(post.getContent())
                        .imageUrl(post.getImageUrl())
                        .createdAt(post.getCreatedAt())
                        .authorEmail(post.getAuthor().getEmail())
                        .authorUsername(post.getAuthor().getUsername())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }
}
