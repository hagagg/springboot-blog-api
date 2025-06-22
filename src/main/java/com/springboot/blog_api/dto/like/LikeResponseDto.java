package com.springboot.blog_api.dto.like;

import com.springboot.blog_api.dto.post.PostSummaryDto;
import com.springboot.blog_api.dto.user.UserSummaryDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeResponseDto {

    private Long id;
    private PostSummaryDto post;
    private UserSummaryDto user;

}
