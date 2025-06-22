package com.springboot.blog_api.controller;

import com.springboot.blog_api.dto.like.LikeResponseDto;
import com.springboot.blog_api.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("like")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("create/{postId}")
    public ResponseEntity<String> createLike (@PathVariable Long postId) {

        likeService.createLike(postId);
        return new ResponseEntity<>("Like created successfully" , HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{likeId}")
    public ResponseEntity<String> deleteLike (@PathVariable Long likeId) {

        likeService.deleteLike(likeId);
        return new ResponseEntity<>("Like deleted successfully" , HttpStatus.OK);
    }

    @GetMapping("/post-likes/{postId}")
    public ResponseEntity<List<LikeResponseDto>> getLikesForPost (@PathVariable Long postId) {

        return new ResponseEntity<>(likeService.getLikesForPost (postId) , HttpStatus.OK);
    }


}
