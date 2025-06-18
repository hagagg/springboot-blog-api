package com.springboot.blog_api.service.impl;

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
import com.springboot.blog_api.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserDao userDao;
    private final PostDao postDao;
    private final CommentDao commentDao;
    private final CommentMapper commentMapper;

    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, Authentication auth) {

        String email = auth.getName();
        User user = userDao.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));

        Post post = postDao.findById(commentRequestDto.getPostId()).orElseThrow(()-> new EntityNotFoundException("Post not found"));

        Comment comment = commentMapper.toEntity(commentRequestDto, post, user);

        comment = commentDao.save(comment);

        return commentMapper.toDto(comment);
    }

    public CommentResponseDto getCommentById(Long commentId, Authentication auth) {

        String email = auth.getName();
        User user = userDao.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Comment comment =  commentDao.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        return commentMapper.toDto(comment);
    }

    public List<CommentResponseDto> findAllComments(Authentication auth) {

        String email = auth.getName();
        User user = userDao.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!user.getRole().equals(Role.ADMIN)) {
            throw new  AccessDeniedException("Only admins can view all comments");
        }
        List<Comment> comments = commentDao.findAll();

        return comments.stream().map(commentMapper::toDto).collect(Collectors.toList());
    }

    public CommentResponseDto updateComment(Long commentId, UpdateCommentRequestDto updateDto, Authentication auth) {

        String email = auth.getName();
        User user = userDao.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Comment comment = commentDao.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        if (!user.getId().equals(comment.getUser().getId())) {
            throw new AccessDeniedException("You are not authorized to update this comment");
        }

        BeanUtils.copyProperties(updateDto, comment);
        comment.setUpdatedDate(LocalDateTime.now());

        commentDao.save(comment);

        return commentMapper.toDto(comment);
    }

    public void deleteComment(Long commentId, Authentication auth) {

        String email = auth.getName();
        User user = userDao.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Comment comment = commentDao.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        if (!user.getId().equals(comment.getUser().getId()) &&  !user.getRole().equals(Role.ADMIN)) {
            throw new AccessDeniedException("You are not authorized to delete this comment!");
        }

        commentDao.delete(comment);
    }

    public List<CommentResponseDto> findCommentsByPostId(Long postId ) {

        Post post = postDao.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found"));

        List<Comment> postComments = post.getComments();

        return postComments.stream().map(commentMapper::toDto).collect(Collectors.toList());
    }


}
