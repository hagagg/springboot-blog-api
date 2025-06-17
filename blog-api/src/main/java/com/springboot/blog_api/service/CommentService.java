package com.springboot.blog_api.service;

import com.springboot.blog_api.dao.CommentDao;
import com.springboot.blog_api.dao.PostDao;
import com.springboot.blog_api.dao.UserDao;
import com.springboot.blog_api.dto.comment.CommentRequestDto;
import com.springboot.blog_api.dto.comment.CommentResponseDto;
import com.springboot.blog_api.dto.comment.UpdateCommentRequestDto;
import com.springboot.blog_api.entity.Comment;
import com.springboot.blog_api.entity.Post;
import com.springboot.blog_api.entity.User;
import com.springboot.blog_api.enums.Role;
import com.springboot.blog_api.mapper.CommentMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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
public class CommentService {

    private final UserDao userDao;
    private final PostDao postDao;
    private final CommentDao commentDao;
    private final CommentMapper commentMapper;

    public ResponseEntity<CommentResponseDto> createComment(CommentRequestDto commentRequestDto, Authentication auth) {

        String email = auth.getName();
        User user = userDao.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));

        Post post = postDao.findById(commentRequestDto.getPostId()).orElseThrow(()-> new EntityNotFoundException("Post not found"));

        Comment comment = commentMapper.toEntity(commentRequestDto, post, user);

        comment = commentDao.save(comment);

        return new ResponseEntity<>(commentMapper.toDto(comment), HttpStatus.CREATED);
    }

    public ResponseEntity<CommentResponseDto> getCommentById(Long commentId, Authentication auth) {

        String email = auth.getName();
        User user = userDao.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Comment comment =  commentDao.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        return new ResponseEntity<>(commentMapper.toDto(comment), HttpStatus.OK);
    }

    public ResponseEntity<?> findAllComments(Authentication auth) {

        String email = auth.getName();
        User user = userDao.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!user.getRole().equals(Role.ADMIN)) {
            return new ResponseEntity<>( "Only admins can get all comments!",HttpStatus.FORBIDDEN);
        }
        List<Comment> comments = commentDao.findAll();

        List<CommentResponseDto> dtos = comments.stream().map(commentMapper::toDto).collect(Collectors.toList());

        return new ResponseEntity<>(dtos, HttpStatus.OK);

    }

    public ResponseEntity<?> updateComment(Long commentId, UpdateCommentRequestDto updateDto, Authentication auth) {

        String email = auth.getName();
        User user = userDao.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Comment comment = commentDao.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        if (!user.getId().equals(comment.getUser().getId())) {
            return new ResponseEntity<>("You are not authorized to update this comment!",HttpStatus.FORBIDDEN);
        }

        BeanUtils.copyProperties(updateDto, comment);
        comment.setUpdatedDate(LocalDateTime.now());

        commentDao.save(comment);

        return new ResponseEntity<>(commentMapper.toDto(comment), HttpStatus.OK);
    }

    public ResponseEntity<String> deleteComment(Long commentId, Authentication auth) {

        String email = auth.getName();
        User user = userDao.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Comment comment = commentDao.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        if (!user.getId().equals(comment.getUser().getId()) &&  !user.getRole().equals(Role.ADMIN)) {
            return new ResponseEntity<>("You are not authorized to delete this comment!",HttpStatus.FORBIDDEN);
        }

        commentDao.delete(comment);

        return new ResponseEntity<>("Comment deleted. " ,HttpStatus.OK);
    }

    public ResponseEntity<List<CommentResponseDto>> findCommentsByPostId(Long postId ) {

        Post post = postDao.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found"));

        List<Comment> postComments = post.getComments();

        List<CommentResponseDto> dtos = postComments.stream().map(commentMapper::toDto).collect(Collectors.toList());

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }


}
