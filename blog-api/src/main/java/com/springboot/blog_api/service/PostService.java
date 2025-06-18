package com.springboot.blog_api.service;

import com.springboot.blog_api.dto.post.PostRequestDto;
import com.springboot.blog_api.dto.post.PostResponseDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface PostService {

    PostResponseDto create(PostRequestDto postRequestDto , Authentication auth);

    List<PostResponseDto> findAllPosts(Authentication auth);

    PostResponseDto updatePost(long postId, PostRequestDto postRequestDto, Authentication auth);

    void deletePost(long postId, Authentication auth);

}
