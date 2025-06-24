// package com.myapp.sns.controller;

// public class PostController {
  
// }


package com.myapp.sns.controller;

import com.myapp.sns.entity.Post;
import com.myapp.sns.repository.PostRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable Long id) {
        return postRepository.findById(id).orElseThrow();
    }
}
