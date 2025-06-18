package com.springboot.blog_api.service.impl;

import com.springboot.blog_api.dao.CategoryDao;
import com.springboot.blog_api.dao.UserDao;
import com.springboot.blog_api.dto.category.CategoryRequestDto;
import com.springboot.blog_api.dto.category.CategoryResponseDto;
import com.springboot.blog_api.entity.Category;
import com.springboot.blog_api.entity.User;
import com.springboot.blog_api.enums.Role;
import com.springboot.blog_api.mapper.CategoryMapper;
import com.springboot.blog_api.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;
    private final UserDao userDao;
    private final CategoryMapper categoryMapper;

    public CategoryResponseDto create (CategoryRequestDto categoryRequestDto , Authentication auth) {

        String email = auth.getName();
        User user = userDao.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));

        Category category = categoryMapper.toEntity(categoryRequestDto);
        categoryDao.save(category);

        return categoryMapper.toDto(category);
    }

    public Category findByName (String name) {

        String cleanedName = name.trim().toLowerCase();

        return categoryDao.findByNameIgnoreCaseAndTrimmed(cleanedName).orElseThrow(()-> new EntityNotFoundException("category with name -" + name + "- not found"));
    }

    public CategoryResponseDto update(Long categoryId, CategoryRequestDto categoryRequestDto, Authentication auth) {

        String email = auth.getName();
        User user =  userDao.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));

        Category category = categoryDao.findById(categoryId).orElseThrow(()-> new EntityNotFoundException("Category not found!"));

        BeanUtils.copyProperties(categoryRequestDto,category , "id" , "posts");

        categoryDao.save(category);

        return categoryMapper.toDto(category);
    }

    public List<CategoryResponseDto> findAllCategories(Authentication auth) {

        String email = auth.getName();
        User user = userDao.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));

        if (!user.getRole().equals(Role.ADMIN)) {
            throw new AccessDeniedException("Only Admin Can view all Categories");
        }

        return categoryDao.findAll().stream().map(categoryMapper::toDto).collect(Collectors.toList());
    }

    public void delete(Long categoryId, Authentication auth) {

        String email = auth.getName();
        User user =  userDao.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));

        Category category = categoryDao.findById(categoryId).orElseThrow(()-> new EntityNotFoundException("Category not found!"));

        if (!category.getPosts().isEmpty()) {
            throw new IllegalStateException("You can't delete any category that has posts");
        }

        categoryDao.delete(category);
    }

}
