package com.springboot.blog_api.controller;

import com.springboot.blog_api.dto.post.PostRequestDto;
import com.springboot.blog_api.dto.post.PostResponseDto;
import com.springboot.blog_api.service.PostService;
import lombok.RequiredArgsConstructor;
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
        return postService.create (postRequestDto , auth);
    }

    @GetMapping("getAll")
    public ResponseEntity<List<PostResponseDto>> getAllPosts (Authentication auth) {
        return postService.findAllPosts (auth);
    }

    @PutMapping("update/{postId}")
    public ResponseEntity<PostResponseDto> updatePost (@PathVariable long postId , @RequestBody PostRequestDto postRequestDto, Authentication auth) {

        return postService.updatePost (postId , postRequestDto , auth);

    }

    @DeleteMapping("delete/{postId}")
    public ResponseEntity<?> deletePost (@PathVariable long postId, Authentication auth) {

        return postService.deletePost(postId , auth);
    }


}
