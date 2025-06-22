package com.springboot.blog_api.utils;

import com.springboot.blog_api.dao.UserDao;
import com.springboot.blog_api.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

    private final UserDao userDao;
    public String getCurrentUserEmail (){

        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public User getCurrentUser (){

        String email = getCurrentUserEmail();

        return userDao.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
}
