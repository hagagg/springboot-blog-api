package com.springboot.blog_api.controller;

import com.springboot.blog_api.dto.comment.CommentRequestDto;
import com.springboot.blog_api.dto.comment.CommentResponseDto;
import com.springboot.blog_api.dto.comment.UpdateCommentRequestDto;
import com.springboot.blog_api.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

        return new ResponseEntity<>(commentService.createComment(commentRequestDto , auth) , HttpStatus.CREATED);
    }

    @GetMapping("get/{commentId}")
    public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable Long commentId , Authentication auth) {

        return new ResponseEntity<>(commentService.getCommentById (commentId , auth) , HttpStatus.OK);
    }

    @GetMapping("getAll")
    public  ResponseEntity<List<CommentResponseDto>> getAllComments(Authentication auth) {

        return new ResponseEntity<>(commentService.findAllComments (auth) , HttpStatus.OK);
    }

    @PutMapping("update/{commentId}")
    public ResponseEntity<?> updateComment (@PathVariable Long commentId, @RequestBody UpdateCommentRequestDto updateDto, Authentication auth) {

        return new ResponseEntity<>(commentService.updateComment(commentId , updateDto , auth) , HttpStatus.OK);
    }

    @DeleteMapping("delete/{commentId}")
    public ResponseEntity<String> deleteComment (@PathVariable Long commentId, Authentication auth) {

        commentService.deleteComment (commentId , auth);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }

    @GetMapping("post-comments/{postId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPostId(@PathVariable Long postId ) {

        return new ResponseEntity<>(commentService.findCommentsByPostId (postId) , HttpStatus.OK);
    }


}
