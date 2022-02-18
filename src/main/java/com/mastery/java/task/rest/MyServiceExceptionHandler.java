package com.mastery.java.task.rest;

import ch.qos.logback.classic.Logger;
import com.mastery.java.task.dto.SimpleWebAppExceptionResponse;
import com.mastery.java.task.exceptions.MyServiceNotFoundException;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @ApiResponse(
            responseCode = "404",
            description = "Employee not found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SimpleWebAppExceptionResponse.class)))

    @ExceptionHandler(MyServiceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public SimpleWebAppExceptionResponse handleNotFoundException(HttpServletRequest request, MyServiceNotFoundException exception) {
        logExceptionWithStackTrace(request, exception);
        return new SimpleWebAppExceptionResponse(HttpStatus.NOT_FOUND, exception);
    }

    @ApiResponse(
            responseCode = "400",
            description = "Bad request. Violating the constraints",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SimpleWebAppExceptionResponse.class)))

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

    @Hidden
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SimpleWebAppExceptionResponse handleAnyUncaughtException(HttpServletRequest request, Throwable exception) {
        String message = "Uncaught exception. You need to see logs";
        SimpleWebAppExceptionResponse exceptionResponse = new SimpleWebAppExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, message);
        logExceptionWithStackTrace(request, exception);
        return exceptionResponse;
    }

    private void logExceptionWithStackTrace(HttpServletRequest request, Throwable exception) {
        LOG.error("Failed to request \"{}\", {} is thrown", request.getRequestURL(), exception.getClass().getSimpleName(), exception);
    }
}
