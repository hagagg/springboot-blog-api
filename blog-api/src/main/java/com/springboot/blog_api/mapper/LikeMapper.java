package com.springboot.blog_api.mapper;

import com.springboot.blog_api.dto.like.LikeResponseDto;
import com.springboot.blog_api.dto.post.PostSummaryDto;
import com.springboot.blog_api.dto.user.UserSummaryDto;
import com.springboot.blog_api.entity.Like;

import org.springframework.stereotype.Component;

@Component
public class LikeMapper {

    public LikeResponseDto toDto(Like like) {
        return LikeResponseDto.builder()
                .id(like.getId())
                .post(PostSummaryDto.builder()
                        .id(like.getPost().getId())
                        .content(like.getPost().getContent())
                        .build())
                .user(UserSummaryDto.builder()
                        .id(like.getUser().getId())
                        .fullName(like.getUser().getFullName())
                        .build())
                .build();
    }

}
