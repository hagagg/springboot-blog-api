package com.springboot.blog_api.controller;

import com.springboot.blog_api.dto.category.CategoryRequestDto;
import com.springboot.blog_api.dto.category.CategoryResponseDto;
import com.springboot.blog_api.entity.Category;
import com.springboot.blog_api.mapper.CategoryMapper;
import com.springboot.blog_api.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("category")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @PostMapping("create")
    public ResponseEntity<CategoryResponseDto> create (@RequestBody CategoryRequestDto categoryRequestDto , Authentication auth) {

        return categoryService.create(categoryRequestDto, auth);
    }

    @PostMapping ("find")
    public ResponseEntity<CategoryResponseDto> getCategoryByName(@RequestParam String name) {

        Category category = categoryService.findByName(name);

        return new ResponseEntity<>(categoryMapper.toDto(category), HttpStatus.OK);
    }

    @PutMapping("update/{categoryId}")
    public ResponseEntity<CategoryResponseDto> update(@PathVariable Long categoryId ,@RequestBody CategoryRequestDto categoryRequestDto, Authentication auth) {

        return categoryService.update (categoryId , categoryRequestDto , auth);
    }

    @GetMapping("all")
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories (Authentication auth) {

        return categoryService.findAllCategories (auth);
    }

    @DeleteMapping("delete/{categoryId}")
    public ResponseEntity<String> delete (@PathVariable Long categoryId ,  Authentication auth) {

        return categoryService.delete (categoryId , auth);
    }
}
