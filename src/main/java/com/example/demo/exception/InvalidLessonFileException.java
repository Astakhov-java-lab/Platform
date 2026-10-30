package com.example.demo.exception;

public class InvalidLessonFileException extends RuntimeException{

    public InvalidLessonFileException(String message){
        super(message);
    }
}
