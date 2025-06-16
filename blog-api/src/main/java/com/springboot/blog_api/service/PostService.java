package com.springboot.blog_api.service;

import com.springboot.blog_api.dao.PostDao;
import com.springboot.blog_api.dao.UserDao;
import com.springboot.blog_api.dto.post.PostRequestDto;
import com.springboot.blog_api.dto.post.PostResponseDto;
import com.springboot.blog_api.entity.Category;
import com.springboot.blog_api.entity.Post;
import com.springboot.blog_api.entity.Tag;
import com.springboot.blog_api.entity.User;
import com.springboot.blog_api.enums.Role;
import com.springboot.blog_api.mapper.PostMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;
    private final PostDao  postDao;
    private final UserDao userDao;
    private final TagService tagService;
    private final CategoryService categoryService;

    public ResponseEntity<PostResponseDto> create(PostRequestDto postRequestDto , Authentication auth) {

        String email = auth.getName();
        User creator = userDao.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " +auth.getName()));

        Post post = postMapper.toEntity(postRequestDto);
        post.setCreator(creator);

        List<Tag> postTags = postRequestDto.getTags().stream().map(tagService :: findOrCreateByName).collect(Collectors.toList());
        post.setTags(postTags);

        List<Category> postCategories = new ArrayList<>();
        for (String categoryName : postRequestDto.getCategories()) {
            Category category = categoryService.findByName(categoryName);
            postCategories.add(category);
        }
        post.setCategories(postCategories);

        postDao.save(post);

        return new ResponseEntity<>(postMapper.toDto(post), HttpStatus.CREATED);

    }

    public ResponseEntity<List<PostResponseDto>> findAllPosts(Authentication auth) {

        List<PostResponseDto> allPosts = postDao.findAll().stream().map(postMapper::toDto).collect(Collectors.toList());

        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

    public ResponseEntity<PostResponseDto> updatePost(long postId, PostRequestDto postRequestDto, Authentication auth) {
        try {
            String email = auth.getName();
            User user = userDao.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

            Post post = postDao.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));

            if (post.getCreator().getId() != user.getId()) {
                throw new AccessDeniedException("You are not authorized to update this post");
            }

            BeanUtils.copyProperties(postRequestDto, post, "tags", "categories");
            post.setUpdateDate(LocalDateTime.now());

            if (postRequestDto.getTags() != null) {
                List<Tag> postTags = postRequestDto.getTags().stream().map(tagService::findOrCreateByName).collect(Collectors.toList());
                post.setTags(postTags);
            }

            if (postRequestDto.getCategories() != null) {
                List<Category> postCategories = postRequestDto.getCategories().stream().map(categoryService::findByName).collect(Collectors.toList());
                post.setCategories(postCategories);
            }

            postDao.save(post);

            return new ResponseEntity<>(postMapper.toDto(post), HttpStatus.OK);

        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deletePost(long postId, Authentication auth) {

        try {
            String email = auth.getName();
            User user = userDao.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

            Post post = postDao.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));

            if (post.getCreator().getId() != user.getId() && !user.getRole().equals(Role.ADMIN)) {
                throw new AccessDeniedException("You are not authorized to delete this post");
            }

            postDao.delete(post);
            return new ResponseEntity<>("Post deleted.", HttpStatus.OK);

        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
