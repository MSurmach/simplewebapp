package com.mastery.java.task.rest;

import com.mastery.java.task.dto.ExceptionResponse;
import com.mastery.java.task.exceptions.ResourceIsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionAdviceController {

    @ExceptionHandler(ResourceIsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFoundException(ResourceIsNotFoundException exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(exception.getMessage());
        exceptionResponse.setTime(LocalDateTime.now());
        exceptionResponse.setCode(HttpStatus.NOT_FOUND.value());
        exceptionResponse.setCodeStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
        return exceptionResponse;
    }
}
