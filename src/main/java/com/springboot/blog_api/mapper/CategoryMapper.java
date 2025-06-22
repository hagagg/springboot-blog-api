package com.springboot.blog_api.mapper;

import com.springboot.blog_api.dto.category.CategoryRequestDto;
import com.springboot.blog_api.dto.category.CategoryResponseDto;
import com.springboot.blog_api.dto.post.PostSummaryDto;
import com.springboot.blog_api.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    private final PostMapper postMapper;

    public CategoryResponseDto toDto(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .posts(category.getPosts().stream().map (postMapper:: toSummaryDto).collect(Collectors.toList()))
                .build();
    }

    public Category toEntity (CategoryRequestDto categoryRequestDto) {
        return Category.builder()
                .name(categoryRequestDto.getName())
                .description(categoryRequestDto.getDescription())
                .posts(new ArrayList<>())
                .build();
    }
}
