package com.springboot.blog_api.service;

import com.springboot.blog_api.dto.category.CategoryRequestDto;
import com.springboot.blog_api.dto.category.CategoryResponseDto;
import com.springboot.blog_api.entity.Category;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CategoryService {


    CategoryResponseDto create (CategoryRequestDto categoryRequestDto , Authentication auth);

    Category findByName (String name);

    CategoryResponseDto update(Long categoryId, CategoryRequestDto categoryRequestDto, Authentication auth);

    List<CategoryResponseDto> findAllCategories(Authentication auth);

    void delete(Long categoryId, Authentication auth);

}
