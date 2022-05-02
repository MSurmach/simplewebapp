package com.mastery.java.task.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Objects;

public class SimpleWebAppExceptionResponse {
    private String message;
    private int code;
    private HttpStatus httpStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime time;

    private SimpleWebAppExceptionResponse() {
        time = LocalDateTime.now();
    }

    public SimpleWebAppExceptionResponse(HttpStatus httpStatus) {
        this();
        this.httpStatus = httpStatus;
        code = httpStatus.value();
        message = "Unexpected exception";
    }

    public SimpleWebAppExceptionResponse(HttpStatus httpStatus, String message) {
        this(httpStatus);
        this.message = message;
    }

    public SimpleWebAppExceptionResponse(HttpStatus httpStatus, Exception exception) {
        this(httpStatus, exception.getMessage());
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }


    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleWebAppExceptionResponse that = (SimpleWebAppExceptionResponse) o;
        return code == that.code && Objects.equals(message, that.message) && httpStatus == that.httpStatus && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, code, httpStatus, time);
    }
}
