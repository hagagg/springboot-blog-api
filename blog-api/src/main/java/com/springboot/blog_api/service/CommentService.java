package com.springboot.blog_api.service;

import com.springboot.blog_api.dto.comment.CommentRequestDto;
import com.springboot.blog_api.dto.comment.CommentResponseDto;
import com.springboot.blog_api.dto.comment.UpdateCommentRequestDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CommentService {

    CommentResponseDto createComment(CommentRequestDto commentRequestDto, Authentication auth);

    CommentResponseDto getCommentById(Long commentId, Authentication auth);

    List<CommentResponseDto> findAllComments(Authentication auth);

    CommentResponseDto updateComment(Long commentId, UpdateCommentRequestDto updateDto, Authentication auth);

    void deleteComment(Long commentId, Authentication auth);

    List<CommentResponseDto> findCommentsByPostId(Long postId );

}
