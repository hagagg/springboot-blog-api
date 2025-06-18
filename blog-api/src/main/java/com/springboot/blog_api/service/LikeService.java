package com.springboot.blog_api.service;

import com.springboot.blog_api.dto.like.LikeResponseDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface LikeService {

    void createLike(Long postId , Authentication auth);

    void deleteLike(Long likeId, Authentication auth);

    List<LikeResponseDto> getLikesForPost(Long postId, Authentication auth);

}
