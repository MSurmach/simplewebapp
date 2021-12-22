package com.mastery.java.task.exceptions;

/**
 * Custom exception, that will be thrown if the client tries to find employee, that is absent inside a database.
 */
public class EmployeeIsNotFoundException extends Exception {

    public EmployeeIsNotFoundException(String message) {
        super(message);
    }
}
