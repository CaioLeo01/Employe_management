package com.example.employeemanagement.exception;

/**
 * Custom exception thrown when a requested resource is not found in the system.
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
     * Constructs a new exception with the specified detail message.
     * @param message the detail message explaining which resource wasn't found
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}