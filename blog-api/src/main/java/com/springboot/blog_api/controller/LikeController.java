package com.springboot.blog_api.controller;

import com.springboot.blog_api.dto.like.LikeResponseDto;
import com.springboot.blog_api.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("like")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("create/{postId}")
    public ResponseEntity<String> createLike (@PathVariable Long postId , Authentication auth) {

        return likeService.createLike (postId , auth);
    }

    @DeleteMapping("delete/{likeId}")
    public ResponseEntity<String> deleteLike (@PathVariable Long likeId , Authentication auth) {

        return likeService.deleteLike(likeId , auth);
    }

    @GetMapping("/post-likes/{postId}")
    public ResponseEntity<List<LikeResponseDto>> getLikesForPost (@PathVariable Long postId, Authentication auth) {

        return likeService.getLikesForPost (postId , auth);
    }


}
