package com.springboot.blog_api.dto.comment;

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
public class CommentResponseDto {

    private Long id;
    private String content;
    private PostSummaryDto post;
    private UserSummaryDto user;

}
