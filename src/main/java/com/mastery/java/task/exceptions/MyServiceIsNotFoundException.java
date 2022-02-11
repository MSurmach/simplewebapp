package com.mastery.java.task.exceptions;

/**
 * Custom exception, that will be thrown if the client tries to find employee, that is absent inside a database.
 */
public class MyServiceIsNotFoundException extends RuntimeException {

    public MyServiceIsNotFoundException(String message) {
        super(message);
    }
}
