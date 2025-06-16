package com.springboot.blog_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table (name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime likedDate;

    @ManyToOne
    @JoinColumn (name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;


}
