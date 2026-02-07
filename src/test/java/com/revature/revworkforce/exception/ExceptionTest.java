package com.revature.revworkforce.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for custom exceptions
 */
public class ExceptionTest {

    // ==================== ValidationException Tests ====================

    @Test
    void testValidationException_WithMessage() {
        String message = "Invalid input data";

        ValidationException exception = new ValidationException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testValidationException_WithMessageAndCause() {
        String message = "Validation failed";
        Exception cause = new Exception("Root cause");

        ValidationException exception =
                new ValidationException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testThrowValidationException() {
        assertThrows(ValidationException.class, () -> {
            throw new ValidationException("Invalid request");
        });
    }

    // ==================== AuthenticationException Tests ====================

    @Test
    void testAuthenticationException_WithMessage() {
        String message = "Authentication failed";

        AuthenticationException exception =
                new AuthenticationException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testAuthenticationException_WithMessageAndCause() {
        String message = "Login failed";
        Exception cause = new Exception("Database unavailable");

        AuthenticationException exception =
                new AuthenticationException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testThrowAuthenticationException() {
        assertThrows(AuthenticationException.class, () -> {
            throw new AuthenticationException("Invalid credentials");
        });
    }

    // ==================== LeaveException Tests ====================

    @Test
    void testLeaveException_WithMessage() {
        String message = "Leave request failed";

        LeaveException exception = new LeaveException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testLeaveException_WithMessageAndCause() {
        String message = "Insufficient leave balance";
        Exception cause = new Exception("Balance too low");

        LeaveException exception =
                new LeaveException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testThrowLeaveException() {
        assertThrows(LeaveException.class, () -> {
            throw new LeaveException("Leave denied");
        });
    }

    // ==================== Exception Hierarchy Tests ====================

    @Test
    void testExceptions_AreCheckedExceptions() {
        assertTrue(new ValidationException("x") instanceof Exception);
        assertTrue(new AuthenticationException("x") instanceof Exception);
        assertTrue(new LeaveException("x") instanceof Exception);
    }


    // ==================== toString Tests ====================

    @Test
    void testValidationException_ToString() {
        ValidationException exception =
                new ValidationException("Invalid field");

        String toString = exception.toString();

        assertTrue(toString.contains("ValidationException"));
        assertTrue(toString.contains("Invalid field"));
    }

    @Test
    void testAuthenticationException_ToString() {
        AuthenticationException exception =
                new AuthenticationException("Auth error");

        String toString = exception.toString();

        assertTrue(toString.contains("AuthenticationException"));
        assertTrue(toString.contains("Auth error"));
    }

    @Test
    void testLeaveException_ToString() {
        LeaveException exception =
                new LeaveException("Leave error");

        String toString = exception.toString();

        assertTrue(toString.contains("LeaveException"));
        assertTrue(toString.contains("Leave error"));
    }

    // ==================== Null and Empty Message Tests ====================

    @Test
    void testExceptions_WithNullMessage() {
        ValidationException ex1 = new ValidationException(null);
        AuthenticationException ex2 = new AuthenticationException(null);
        LeaveException ex3 = new LeaveException(null);

        assertNull(ex1.getMessage());
        assertNull(ex2.getMessage());
        assertNull(ex3.getMessage());
    }

    @Test
    void testExceptions_WithEmptyMessage() {
        ValidationException ex1 = new ValidationException("");
        AuthenticationException ex2 = new AuthenticationException("");
        LeaveException ex3 = new LeaveException("");

        assertEquals("", ex1.getMessage());
        assertEquals("", ex2.getMessage());
        assertEquals("", ex3.getMessage());
    }

    // ==================== Cause Chain Tests ====================

    @Test
    void testExceptionCauseChain() {
        Exception root = new Exception("Root cause");
        ValidationException validation =
                new ValidationException("Validation failed", root);
        AuthenticationException auth =
                new AuthenticationException("Auth failed", validation);

        assertEquals(validation, auth.getCause());
        assertEquals(root, auth.getCause().getCause());
    }
}
