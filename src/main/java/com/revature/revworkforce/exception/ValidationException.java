package com.revature.revworkforce.exception;

/**
 * Exception thrown when input validation fails
 */

public class ValidationException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    public ValidationException(String message) {
        super(message);
    }
    
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}