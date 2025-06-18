package com.springboot.blog_api.controller;

import com.springboot.blog_api.dto.post.PostRequestDto;
import com.springboot.blog_api.dto.post.PostResponseDto;
import com.springboot.blog_api.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("post")
public class PostController {

    private final PostService postService;

    @PostMapping("create")
    public ResponseEntity<PostResponseDto> create (@RequestBody PostRequestDto postRequestDto , Authentication auth) {
        return new ResponseEntity<>( postService.create (postRequestDto , auth),  HttpStatus.CREATED);
    }

    @GetMapping("getAll")
    public ResponseEntity<List<PostResponseDto>> getAllPosts (Authentication auth) {
        return new ResponseEntity<>(postService.findAllPosts (auth), HttpStatus.OK);
    }

    @PutMapping("update/{postId}")
    public ResponseEntity<PostResponseDto> updatePost (@PathVariable long postId , @RequestBody PostRequestDto postRequestDto, Authentication auth) {

        return new ResponseEntity<>(postService.updatePost (postId , postRequestDto , auth) , HttpStatus.OK);

    }

    @DeleteMapping("delete/{postId}")
    public ResponseEntity<?> deletePost (@PathVariable long postId, Authentication auth) {

        postService.deletePost (postId, auth);
        return new ResponseEntity<>("Post deleted successfully" , HttpStatus.OK);
    }


}
