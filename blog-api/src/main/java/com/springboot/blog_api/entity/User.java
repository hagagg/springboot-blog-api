package com.springboot.blog_api.entity;

import com.springboot.blog_api.enums.Role;
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
@Table (name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false , unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDateTime registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany (mappedBy = "creator" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Post> posts;

    @OneToMany (mappedBy = "user" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Like> likes;

    @OneToMany (mappedBy = "user" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Comment> comments;

    public String getFullName() {
        return  firstName + " " + lastName;
    }

}
