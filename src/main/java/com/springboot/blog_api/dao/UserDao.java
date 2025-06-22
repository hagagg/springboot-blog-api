package com.springboot.blog_api.dao;

import com.springboot.blog_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

}
