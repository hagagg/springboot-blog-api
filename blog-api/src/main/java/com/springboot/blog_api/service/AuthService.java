package com.springboot.blog_api.service;

import com.springboot.blog_api.dto.authentication.AuthRequestDto;
import com.springboot.blog_api.dto.authentication.AuthResponseDto;
import com.springboot.blog_api.dto.authentication.RegisterRequestDto;

public interface AuthService {

    AuthResponseDto register(RegisterRequestDto request);

    AuthResponseDto login(AuthRequestDto request);

}
