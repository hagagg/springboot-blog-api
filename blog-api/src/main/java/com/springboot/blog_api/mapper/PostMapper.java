package com.springboot.blog_api.mapper;

import com.springboot.blog_api.dto.post.PostRequestDto;
import com.springboot.blog_api.dto.post.PostResponseDto;
import com.springboot.blog_api.dto.post.PostSummaryDto;
import com.springboot.blog_api.entity.Category;
import com.springboot.blog_api.entity.Comment;
import com.springboot.blog_api.entity.Post;
import com.springboot.blog_api.entity.Tag;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class PostMapper {

    public PostResponseDto toDto(Post post) {

        return PostResponseDto.builder()
                .id(post.getId())
                .content(post.getContent())
                .publishDate(post.getPublishDate())
                .updateDate(post.getUpdateDate())
                .creatorName(post.getCreator().getFirstName() + " " + post.getCreator().getLastName())
                .likesCount(post.getLikes().size())
                .comments(post.getComments().stream().map(Comment::getContent).collect(Collectors.toList()))
                .tags(post.getTags().stream().map(Tag::getName).collect(Collectors.toList()))
                .categories(post.getCategories().stream().map(Category::getName).collect(Collectors.toList()))
                .build();
    }

    public Post toEntity(PostRequestDto dto) {

        return Post.builder()
                .content(dto.getContent())
                .publishDate(LocalDateTime.now())
                .likes(new ArrayList<>())
                .categories(new ArrayList<>())
                .comments(new ArrayList<>())
                .tags(new ArrayList<>())
                .build();
    }

    public PostSummaryDto toSummaryDto(Post post) {
        return PostSummaryDto.builder()
                .id(post.getId())
                .content(post.getContent())
                .build();
    }
}
