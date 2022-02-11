package com.mastery.java.task.rest;

import ch.qos.logback.classic.Logger;
import com.mastery.java.task.dto.SimpleWebAppExceptionResponse;
import com.mastery.java.task.exceptions.MyServiceIsNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Priority;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class MyServiceExceptionHandler {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(MyServiceExceptionHandler.class);

    @ExceptionHandler(MyServiceIsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public SimpleWebAppExceptionResponse handleNotFoundException(MyServiceIsNotFoundException exception) {
        LOG.info("Handling an exception: {}", MyServiceIsNotFoundException.class.getSimpleName());
        LOG.debug("Exception message: {}", exception.getMessage());
        SimpleWebAppExceptionResponse exceptionResponse = new SimpleWebAppExceptionResponse(HttpStatus.NOT_FOUND, exception);
        LOG.info("Returning an exception response with status: {}", exceptionResponse.getHttpStatus());
        return exceptionResponse;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<SimpleWebAppExceptionResponse> handleConstraintViolations(ConstraintViolationException exception) {
        LOG.info("Handling violation constraints exceptions");
        List<SimpleWebAppExceptionResponse> violationResponses = new ArrayList<>();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            LOG.info("Handling a violation constraint: {}", violation.getMessage());
            SimpleWebAppExceptionResponse exceptionResponse = new SimpleWebAppExceptionResponse(HttpStatus.BAD_REQUEST, violation.getMessage());
            LOG.info("Convert to an exception response with status: {}", exceptionResponse.getHttpStatus());
            violationResponses.add(exceptionResponse);
        }
        LOG.info("Returning a list of the exception responses");
        return violationResponses;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SimpleWebAppExceptionResponse handleAnyUncaughtException(RuntimeException exception) {
        LOG.info("Handling the uncaught exceptions");
        String message = "Uncaught exception. You need to see logs";
        SimpleWebAppExceptionResponse exceptionResponse = new SimpleWebAppExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, message);
        LOG.info("Returning an exception response with status: {}", exceptionResponse.getHttpStatus());
        return exceptionResponse;
    }

}
