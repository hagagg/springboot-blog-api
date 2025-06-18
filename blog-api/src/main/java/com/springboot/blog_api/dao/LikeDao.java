package com.springboot.blog_api.dao;

import com.springboot.blog_api.entity.Like;
import com.springboot.blog_api.entity.Post;
import com.springboot.blog_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeDao extends JpaRepository<Like, Long> {

    Boolean existsByUserAndPost (User user, Post post);

}
