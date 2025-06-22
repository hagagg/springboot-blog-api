package com.springboot.blog_api.mapper;

import com.springboot.blog_api.dto.comment.CommentRequestDto;
import com.springboot.blog_api.dto.comment.CommentResponseDto;
import com.springboot.blog_api.dto.comment.UpdateCommentRequestDto;
import com.springboot.blog_api.dto.post.PostSummaryDto;
import com.springboot.blog_api.dto.user.UserSummaryDto;
import com.springboot.blog_api.entity.Comment;
import com.springboot.blog_api.entity.Post;
import com.springboot.blog_api.entity.User;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentResponseDto toDto(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .post(PostSummaryDto.builder()
                        .id(comment.getPost().getId())
                        .content(comment.getPost().getContent())
                        .build())
                .user(UserSummaryDto.builder()
                        .id(comment.getUser().getId())
                        .fullName(comment.getUser().getFullName())
                        .build()
                ).build();
    }

    public Comment toEntity(CommentRequestDto commentRequestDto , Post post , User user) {
        return Comment.builder()
                .content(commentRequestDto.getContent())
                .post(post)
                .user(user)
                .build();
    }


    public Comment updateDto(UpdateCommentRequestDto updateCommentRequestDto) {
        return Comment.builder()
                .content(updateCommentRequestDto.getContent())
                .build();
    }


}
