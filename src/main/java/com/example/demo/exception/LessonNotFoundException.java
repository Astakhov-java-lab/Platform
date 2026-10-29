package com.example.demo.exception;

public class LessonNotFoundException extends RuntimeException{

    public LessonNotFoundException(String message){
        super(message);
    }
}