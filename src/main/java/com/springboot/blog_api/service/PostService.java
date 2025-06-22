package com.springboot.blog_api.service;

import com.springboot.blog_api.dto.post.PostRequestDto;
import com.springboot.blog_api.dto.post.PostResponseDto;

import java.util.List;

public interface PostService {

    PostResponseDto create(PostRequestDto postRequestDto);

    List<PostResponseDto> findAllPosts();

    PostResponseDto updatePost(long postId, PostRequestDto postRequestDto);

    void deletePost(long postId);

}
