package com.springboot.blog_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException (AccessDeniedException e){

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler (IllegalStateException.class)
    public ResponseEntity<?> handleIllegalStateException (IllegalStateException e){

        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }


}
