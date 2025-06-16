package com.springboot.blog_api.service;

import com.springboot.blog_api.dao.UserDao;
import com.springboot.blog_api.dto.authentication.AuthRequestDto;
import com.springboot.blog_api.dto.authentication.AuthResponseDto;
import com.springboot.blog_api.dto.authentication.RegisterRequestDto;
import com.springboot.blog_api.entity.User;
import com.springboot.blog_api.enums.Role;
import com.springboot.blog_api.mapper.UserMapper;
import com.springboot.blog_api.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public ResponseEntity<AuthResponseDto> register(RegisterRequestDto request) {

        if (userDao.findByEmail(request.getEmail()).isPresent()){
            return new ResponseEntity<>(new AuthResponseDto("User already exists"), HttpStatus.NOT_FOUND);
        }

        request.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));

        User user = userMapper.toEntity(request);

        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }
        userDao.save(user);

        String token = jwtService.generateToken(user.getEmail());
        return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.CREATED);

    }


    public ResponseEntity<AuthResponseDto> login(AuthRequestDto request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail() , request.getPassword()));

        User user = userDao.findByEmail(request.getEmail()).orElseThrow(()->new UsernameNotFoundException("User not found"));

        String token = jwtService.generateToken(user.getEmail());

        return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);

    }



}

