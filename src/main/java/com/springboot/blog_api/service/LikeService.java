package com.springboot.blog_api.service;

import com.springboot.blog_api.dto.like.LikeResponseDto;

import java.util.List;

public interface LikeService {

    void createLike(Long postId);

    void deleteLike(Long likeId);

    List<LikeResponseDto> getLikesForPost(Long postId);

}
