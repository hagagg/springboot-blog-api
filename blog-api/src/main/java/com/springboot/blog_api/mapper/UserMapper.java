package com.springboot.blog_api.mapper;

import com.springboot.blog_api.dto.authentication.RegisterRequestDto;
import com.springboot.blog_api.dto.user.UserResponseDto;
import com.springboot.blog_api.dto.user.UserSummaryDto;
import com.springboot.blog_api.entity.User;
import com.springboot.blog_api.enums.Role;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public User toEntity (RegisterRequestDto registerRequestDto) {

        return User.builder()
                .firstName(registerRequestDto.getFirstName())
                .lastName(registerRequestDto.getLastName())
                .email(registerRequestDto.getEmail())
                .password(registerRequestDto.getPassword())
                .role(registerRequestDto.getRole())
                .registrationDate(LocalDateTime.now())
                .build();
    }

    public UserResponseDto toDto (User user) {

        return UserResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    public UserSummaryDto toSummaryDto (User user) {
        return UserSummaryDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .build();
    }

}
