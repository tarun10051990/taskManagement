package com.taskmanagement.demo.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

public class TaskNotFoundException extends RuntimeException{

    public TaskNotFoundException(String s){
        super(s);
    }

}
