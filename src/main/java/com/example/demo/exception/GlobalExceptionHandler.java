package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Это класс для того чтобы мои созданные ошибки нормально читались ( так как в Spring если не обработать через @ExceptionHandler то будет просто возращаться ответ запроса)
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LessonNotFoundException.class)
    public ProblemDetail handleLessonNotFound(LessonNotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(InvalidLessonFileException.class)
    public ProblemDetail handleInvalidLessonFile(InvalidLessonFileException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
