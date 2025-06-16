package com.springboot.blog_api.dao;

import com.springboot.blog_api.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeDao extends JpaRepository<Like, Long> {


}
