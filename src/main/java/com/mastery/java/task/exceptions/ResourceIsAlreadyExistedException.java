package com.mastery.java.task.exceptions;

/**
 * Custom exception, that will be thrown if the client tries to save employee, that has already been saved.
 */
public class ResourceIsAlreadyExistedException extends RuntimeException {
    public ResourceIsAlreadyExistedException(String message) {
        super(message);
    }
}
