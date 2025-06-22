package com.springboot.blog_api.service;

import com.springboot.blog_api.dto.comment.CommentRequestDto;
import com.springboot.blog_api.dto.comment.CommentResponseDto;
import com.springboot.blog_api.dto.comment.UpdateCommentRequestDto;

import java.util.List;

public interface CommentService {

    CommentResponseDto createComment(CommentRequestDto commentRequestDto);

    CommentResponseDto getCommentById(Long commentId);

    List<CommentResponseDto> findAllComments();

    CommentResponseDto updateComment(Long commentId, UpdateCommentRequestDto updateDto);

    void deleteComment(Long commentId);

    List<CommentResponseDto> findCommentsByPostId(Long postId );

}
