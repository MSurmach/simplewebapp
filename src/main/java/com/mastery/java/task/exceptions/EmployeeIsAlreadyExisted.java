package com.mastery.java.task.exceptions;

/**
 * Custom exception, that will be thrown if the client tries to save employee, that has already been saved.
 */
public class EmployeeIsAlreadyExisted extends Exception {
    public EmployeeIsAlreadyExisted(String message) {
        super(message);
    }
}
