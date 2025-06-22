package com.springboot.blog_api.controller;

import com.springboot.blog_api.dto.authentication.AuthRequestDto;
import com.springboot.blog_api.dto.authentication.AuthResponseDto;
import com.springboot.blog_api.dto.authentication.RegisterRequestDto;
import com.springboot.blog_api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<AuthResponseDto> register (@RequestBody RegisterRequestDto request) {
        return new ResponseEntity<>(authService.register (request) , HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity <AuthResponseDto> login (@RequestBody AuthRequestDto request) {
        return new ResponseEntity<>(authService.login (request) , HttpStatus.OK);
    }


}
