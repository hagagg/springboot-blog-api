package com.springboot.blog_api.controller;

import com.springboot.blog_api.dto.comment.CommentRequestDto;
import com.springboot.blog_api.dto.comment.CommentResponseDto;
import com.springboot.blog_api.dto.comment.UpdateCommentRequestDto;
import com.springboot.blog_api.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("create")
    public ResponseEntity<CommentResponseDto> createComment (@RequestBody CommentRequestDto commentRequestDto, Authentication auth) {

        return commentService.createComment(commentRequestDto , auth);
    }

    @GetMapping("get/{commentId}")
    public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable Long commentId , Authentication auth) {

        return commentService.getCommentById (commentId , auth);
    }

    @GetMapping("getAll")
    public  ResponseEntity<?> getAllComments(Authentication auth) {

        return commentService.findAllComments (auth);
    }

    @PutMapping("update/{commentId}")
    public ResponseEntity<?> updateComment (@PathVariable Long commentId, @RequestBody UpdateCommentRequestDto updateDto, Authentication auth) {

        return commentService.updateComment(commentId , updateDto , auth);
    }

    @DeleteMapping("delete/{commentId}")
    public ResponseEntity<String> deleteComment (@PathVariable Long commentId, Authentication auth) {

        return commentService.deleteComment(commentId , auth);
    }

    @GetMapping("post-comments/{postId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPostId(@PathVariable Long postId ) {

        return commentService.findCommentsByPostId (postId );
    }


}
