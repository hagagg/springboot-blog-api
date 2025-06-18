package com.springboot.blog_api.service;

import com.springboot.blog_api.dao.LikeDao;
import com.springboot.blog_api.dao.PostDao;
import com.springboot.blog_api.dao.UserDao;
import com.springboot.blog_api.dto.like.LikeResponseDto;
import com.springboot.blog_api.entity.Like;
import com.springboot.blog_api.entity.Post;
import com.springboot.blog_api.entity.User;
import com.springboot.blog_api.mapper.LikeMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final UserDao userDao;
    private final PostDao postDao;
    private final LikeDao likeDao;
    private final LikeMapper likeMapper;

    public ResponseEntity<String> createLike(Long postId , Authentication auth) {

        String email = auth.getName();
        User user = userDao.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not  found"));

        Post post = postDao.findById(postId).orElseThrow(()-> new EntityNotFoundException("Post not found"));

        if (likeDao.existsByUserAndPost(user, post)) {
            return new ResponseEntity<>("You have already liked this post", HttpStatus.CONFLICT);
        }

        Like like = new Like();
        like.setPost(post);
        like.setUser(user);
        like.setLikeCreationDate(LocalDateTime.now());

        likeDao.save(like);

        return new ResponseEntity<>("Like created successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteLike(Long likeId, Authentication auth) {

        String email = auth.getName();
        User user = userDao.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not  found"));

        Like like = likeDao.findById(likeId).orElseThrow(()-> new EntityNotFoundException("Like not found"));

        if (!like.getUser().getId().equals(user.getId())) {
            return new ResponseEntity<>("You are not authorized to delete the like!", HttpStatus.FORBIDDEN);
        }
        likeDao.delete(like);

        return new ResponseEntity<>("Like deleted successfully", HttpStatus.OK);
    }

    public ResponseEntity<List<LikeResponseDto>> getLikesForPost(Long postId, Authentication auth) {

        String email = auth.getName();
        User user = userDao.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not  found"));

        Post post = postDao.findById(postId).orElseThrow(()-> new EntityNotFoundException("Post not found"));

        List<LikeResponseDto> allPostLikes = post.getLikes().stream().map(likeMapper::toDto).collect(Collectors.toList());

        return new ResponseEntity<>(allPostLikes, HttpStatus.OK);
    }


}
