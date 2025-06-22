package com.springboot.blog_api.dao;

import com.springboot.blog_api.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagDao extends JpaRepository<Tag,Long> {

    Optional<Tag> findByName(String name);

}
