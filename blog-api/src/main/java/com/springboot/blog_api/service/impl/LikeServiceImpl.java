package com.springboot.blog_api.service.impl;

import com.springboot.blog_api.dao.LikeDao;
import com.springboot.blog_api.dao.PostDao;
import com.springboot.blog_api.dto.like.LikeResponseDto;
import com.springboot.blog_api.entity.Like;
import com.springboot.blog_api.entity.Post;
import com.springboot.blog_api.entity.User;
import com.springboot.blog_api.mapper.LikeMapper;
import com.springboot.blog_api.security.SecurityUtil;
import com.springboot.blog_api.service.LikeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final PostDao postDao;
    private final LikeDao likeDao;
    private final LikeMapper likeMapper;
    private final SecurityUtil securityUtil;

    public void createLike(Long postId) {
        User user = securityUtil.getCurrentUser();

        Post post = postDao.findById(postId).orElseThrow(()-> new EntityNotFoundException("Post not found"));

        if (likeDao.existsByUserAndPost(user, post)) {
            throw new IllegalStateException("You already liked this post");
        }

        Like like = new Like();
        like.setPost(post);
        like.setUser(user);
        like.setLikeCreationDate(LocalDateTime.now());

        likeDao.save(like);
    }

    public void deleteLike(Long likeId) {
        User user = securityUtil.getCurrentUser();

        Like like = likeDao.findById(likeId).orElseThrow(()-> new EntityNotFoundException("Like not found"));

        if (!like.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not authorized to delete the like!");
        }
        likeDao.delete(like);
    }

    public List<LikeResponseDto> getLikesForPost(Long postId) {

        Post post = postDao.findById(postId).orElseThrow(()-> new EntityNotFoundException("Post not found"));

        List<LikeResponseDto> allPostLikes = post.getLikes().stream().map(likeMapper::toDto).collect(Collectors.toList());

        return allPostLikes ;
    }

}
