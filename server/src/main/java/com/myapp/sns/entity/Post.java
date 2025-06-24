// package com.myapp.sns.Entity;

// public class Post {
  
// }


package com.myapp.sns.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String imageUrl;

    private Double latitude;
    private Double longitude;

    private LocalDateTime createdAt;
}
