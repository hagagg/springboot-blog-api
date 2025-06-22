package com.springboot.blog_api.service;

import com.springboot.blog_api.dto.category.CategoryRequestDto;
import com.springboot.blog_api.dto.category.CategoryResponseDto;
import com.springboot.blog_api.entity.Category;

import java.util.List;

public interface CategoryService {


    CategoryResponseDto create (CategoryRequestDto categoryRequestDto);

    Category findByName (String name);

    CategoryResponseDto update(Long categoryId, CategoryRequestDto categoryRequestDto);

    List<CategoryResponseDto> findAllCategories();

    void delete(Long categoryId);

}
