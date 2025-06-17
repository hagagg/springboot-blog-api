package com.springboot.blog_api.service;

import com.springboot.blog_api.dao.CategoryDao;
import com.springboot.blog_api.dao.UserDao;
import com.springboot.blog_api.dto.category.CategoryRequestDto;
import com.springboot.blog_api.dto.category.CategoryResponseDto;
import com.springboot.blog_api.entity.Category;
import com.springboot.blog_api.entity.User;
import com.springboot.blog_api.mapper.CategoryMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryDao categoryDao;
    private final UserDao userDao;
    private final CategoryMapper categoryMapper;

    public ResponseEntity<CategoryResponseDto> create (CategoryRequestDto categoryRequestDto , Authentication auth) {

        String email = auth.getName();
        User user = userDao.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));

        Category category = categoryMapper.toEntity(categoryRequestDto);
        categoryDao.save(category);

        return new ResponseEntity<>(categoryMapper.toDto(category), HttpStatus.CREATED);
    }

    public Category findByName (String name) {

        String cleanedName = name.trim().toLowerCase();

        return categoryDao.findByNameIgnoreCaseAndTrimmed(cleanedName).orElseThrow(()-> new EntityNotFoundException("category with name -" + name + "- not found"));
    }

    public ResponseEntity<CategoryResponseDto> update(Long categoryId, CategoryRequestDto categoryRequestDto, Authentication auth) {

        String email = auth.getName();
        User user =  userDao.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));

        Category category = categoryDao.findById(categoryId).orElseThrow(()-> new EntityNotFoundException("Category not found!"));

        BeanUtils.copyProperties(categoryRequestDto,category , "id" , "posts");

        categoryDao.save(category);

        return new ResponseEntity<>(categoryMapper.toDto(category), HttpStatus.OK);
    }

    public ResponseEntity<List<CategoryResponseDto>> findAllCategories(Authentication auth) {

        String email = auth.getName();
        User user = userDao.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));

        List<CategoryResponseDto> allCategories = categoryDao.findAll().stream().map(categoryMapper::toDto).collect(Collectors.toList());

        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    public ResponseEntity<String> delete(Long categoryId, Authentication auth) {

        String email = auth.getName();
        User user =  userDao.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));

        Category category = categoryDao.findById(categoryId).orElseThrow(()-> new EntityNotFoundException("Category not found!"));

        if (!category.getPosts().isEmpty()) {
            return new ResponseEntity<>("You can't delete this category, It has posts.", HttpStatus.BAD_REQUEST);
        }

        categoryDao.delete(category);

        return new ResponseEntity<>("Category deleted.", HttpStatus.OK);
    }



}
