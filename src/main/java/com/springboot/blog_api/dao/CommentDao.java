package com.springboot.blog_api.dao;

import com.springboot.blog_api.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDao extends JpaRepository<Comment,Long> {


}
