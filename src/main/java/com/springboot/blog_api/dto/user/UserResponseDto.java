package com.springboot.blog_api.dto.user;

import com.springboot.blog_api.entity.Comment;
import com.springboot.blog_api.entity.Like;
import com.springboot.blog_api.entity.Post;
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
public class UserResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;

}
