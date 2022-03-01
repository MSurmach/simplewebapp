package com.mastery.java.task.rest;

import ch.qos.logback.classic.Logger;
import com.mastery.java.task.dto.SimpleWebAppExceptionResponse;
import com.mastery.java.task.exceptions.MyServiceNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class MyServiceExceptionHandler {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(MyServiceExceptionHandler.class);

    @ExceptionHandler(MyServiceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public SimpleWebAppExceptionResponse handleNotFoundException(HttpServletRequest request, MyServiceNotFoundException exception) {
        logExceptionWithStackTrace(request, exception);
        return new SimpleWebAppExceptionResponse(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<SimpleWebAppExceptionResponse> handleConstraintViolations(HttpServletRequest request, ConstraintViolationException exception) {
        logExceptionWithStackTrace(request, exception);
        List<SimpleWebAppExceptionResponse> violationResponses = new ArrayList<>();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            SimpleWebAppExceptionResponse exceptionResponse = new SimpleWebAppExceptionResponse(HttpStatus.BAD_REQUEST, violation.getMessage());
            violationResponses.add(exceptionResponse);
        }
        return violationResponses;
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SimpleWebAppExceptionResponse handleAnyUncaughtException(HttpServletRequest request, Throwable exception) {
        String message = "Internal server error";
        SimpleWebAppExceptionResponse exceptionResponse = new SimpleWebAppExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, message);
        logExceptionWithStackTrace(request, exception);
        return exceptionResponse;
    }

    private void logExceptionWithStackTrace(HttpServletRequest request, Throwable exception) {
        LOG.error("Failed to request \"{}\", {} is thrown", request.getRequestURL(), exception.getClass().getSimpleName(), exception);
    }
}
