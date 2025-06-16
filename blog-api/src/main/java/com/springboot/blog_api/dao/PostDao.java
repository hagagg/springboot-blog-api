package com.springboot.blog_api.dao;

import com.springboot.blog_api.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostDao extends JpaRepository<Post,Long> {

}
