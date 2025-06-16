package com.springboot.blog_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table (name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime publishDate;

    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn (name = "creator_id")
    private User creator;

    @OneToMany (mappedBy = "post" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Like> likes;

    @OneToMany (mappedBy = "post" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Comment> comments;

    @ManyToMany
    @JoinTable (
            name = "post_tags",
            joinColumns = @JoinColumn (name = "post_id"),
            inverseJoinColumns = @JoinColumn (name = "tag_id")
    )
    private List<Tag> tags;

    @ManyToMany
    @JoinTable (
            name = "post_categories",
            joinColumns = @JoinColumn (name = "post_id"),
            inverseJoinColumns = @JoinColumn (name = "category_id")
    )
    private List<Category> categories;



}
