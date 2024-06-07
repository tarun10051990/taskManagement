package com.taskmanagement.demo.exception;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Map<String,Object>> notFoundHandler(TaskNotFoundException ex){
        Map<String, Object> mp = new HashMap<>();
        mp.put("message",ex.getMessage());
        mp.put("success",false);
        mp.put("status", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(mp,HttpStatus.NOT_FOUND);
    }
}
