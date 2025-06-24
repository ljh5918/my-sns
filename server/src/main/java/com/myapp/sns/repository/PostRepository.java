// package com.myapp.sns.Repository;

// public class PostRepository {
  
// }


package com.myapp.sns.repository;

import com.myapp.sns.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
