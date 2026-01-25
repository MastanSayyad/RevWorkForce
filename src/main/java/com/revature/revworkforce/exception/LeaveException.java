package com.revature.revworkforce.exception;

/**
 * Exception thrown for leave-related errors
 */

public class LeaveException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    public LeaveException(String message) {
        super(message);
    }
    
    public LeaveException(String message, Throwable cause) {
        super(message, cause);
    }
}