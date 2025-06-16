package com.springboot.blog_api.dao;

import com.springboot.blog_api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryDao extends JpaRepository<Category,Long> {


    @Query("SELECT c FROM Category c WHERE LOWER(TRIM(c.name)) = LOWER(TRIM(:name))")
    Optional<Category> findByNameIgnoreCaseAndTrimmed(@Param("name") String name);
}
