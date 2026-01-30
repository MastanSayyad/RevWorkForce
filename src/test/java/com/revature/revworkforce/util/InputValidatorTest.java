package com.revature.revworkforce.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for InputValidator
 */
public class InputValidatorTest {
    
    @Test
    @DisplayName("Test valid email addresses")
    public void testIsValidEmail_Valid() {
        assertTrue(InputValidator.isValidEmail("test@example.com"));
        assertTrue(InputValidator.isValidEmail("user.name@company.co.in"));
        assertTrue(InputValidator.isValidEmail("admin@revworkforce.com"));
    }
    
    @Test
    @DisplayName("Test invalid email addresses")
    public void testIsValidEmail_Invalid() {
        assertFalse(InputValidator.isValidEmail("invalid"));
        assertFalse(InputValidator.isValidEmail("@example.com"));
        assertFalse(InputValidator.isValidEmail("test@"));
        assertFalse(InputValidator.isValidEmail(null));
    }
    
    @Test
    @DisplayName("Test valid phone numbers")
    public void testIsValidPhone_Valid() {
        assertTrue(InputValidator.isValidPhone("9876543210"));
        assertTrue(InputValidator.isValidPhone("1234567890"));
    }
    
    @Test
    @DisplayName("Test invalid phone numbers")
    public void testIsValidPhone_Invalid() {
        assertFalse(InputValidator.isValidPhone("123"));
        assertFalse(InputValidator.isValidPhone("12345678901")); // 11 digits
        assertFalse(InputValidator.isValidPhone("abcdefghij"));
        assertFalse(InputValidator.isValidPhone(null));
    }
    
    @Test
    @DisplayName("Test valid employee IDs")
    public void testIsValidEmployeeId_Valid() {
        assertTrue(InputValidator.isValidEmployeeId("EMP001"));
        assertTrue(InputValidator.isValidEmployeeId("MGR123"));
        assertTrue(InputValidator.isValidEmployeeId("ADM999"));
    }
    
    @Test
    @DisplayName("Test invalid employee IDs")
    public void testIsValidEmployeeId_Invalid() {
        assertFalse(InputValidator.isValidEmployeeId("E001")); // Only 1 letter
        assertFalse(InputValidator.isValidEmployeeId("EMPA01")); // Letter in number part
        assertFalse(InputValidator.isValidEmployeeId("emp001")); // Lowercase
        assertFalse(InputValidator.isValidEmployeeId(null));
    }
    
    @Test
    @DisplayName("Test valid dates")
    public void testIsValidDate_Valid() {
        assertTrue(InputValidator.isValidDate("2025-01-30"));
        assertTrue(InputValidator.isValidDate("2024-12-31"));
    }
    
    @Test
    @DisplayName("Test invalid dates")
    public void testIsValidDate_Invalid() {
        assertFalse(InputValidator.isValidDate("2025-13-01")); // Invalid month
        assertFalse(InputValidator.isValidDate("30-01-2025")); // Wrong format
        assertFalse(InputValidator.isValidDate("invalid"));
        assertFalse(InputValidator.isValidDate(null));
    }
    
    @Test
    @DisplayName("Test sanitize input")
    public void testSanitizeInput() {
        assertEquals("test", InputValidator.sanitizeInput("test"));
        assertEquals("test", InputValidator.sanitizeInput("<test>"));
        assertEquals("test", InputValidator.sanitizeInput("\"test\""));
        assertEquals("test", InputValidator.sanitizeInput("  test  "));
        assertEquals("", InputValidator.sanitizeInput(null));
    }
    
    @Test
    @DisplayName("Test is not empty")
    public void testIsNotEmpty() {
        assertTrue(InputValidator.isNotEmpty("test"));
        assertFalse(InputValidator.isNotEmpty(""));
        assertFalse(InputValidator.isNotEmpty("   "));
        assertFalse(InputValidator.isNotEmpty(null));
    }
    
    @Test
    @DisplayName("Test is positive number")
    public void testIsPositiveNumber() {
        assertTrue(InputValidator.isPositiveNumber(1));
        assertTrue(InputValidator.isPositiveNumber(100));
        assertFalse(InputValidator.isPositiveNumber(0));
        assertFalse(InputValidator.isPositiveNumber(-1));
    }
    
    @Test
    @DisplayName("Test is valid rating")
    public void testIsValidRating() {
        assertTrue(InputValidator.isValidRating(1.0));
        assertTrue(InputValidator.isValidRating(3.5));
        assertTrue(InputValidator.isValidRating(5.0));
        assertFalse(InputValidator.isValidRating(0.5));
        assertFalse(InputValidator.isValidRating(5.5));
    }
}