package com.revature.revworkforce.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

/**
 * Utility class for input validation
 */
public class InputValidator {
    
    private static final Pattern EMAIL_PATTERN = 
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    private static final Pattern PHONE_PATTERN = 
            Pattern.compile("^\\d{10}$");
    
    private static final Pattern EMPLOYEE_ID_PATTERN = 
            Pattern.compile("^[A-Z]{3}\\d{3}$"); // e.g., EMP001
    
    /**
     * Validate email address
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    /**
     * Validate phone number (10 digits)
     */
    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }
    
    /**
     * Validate employee ID format
     */
    public static boolean isValidEmployeeId(String employeeId) {
        return employeeId != null && EMPLOYEE_ID_PATTERN.matcher(employeeId).matches();
    }
    
    /**
     * Validate date in yyyy-MM-dd format
     */
    public static boolean isValidDate(String date) {
        if (date == null) return false;
        
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    /**
     * Sanitize input to prevent SQL injection
     */
    public static String sanitizeInput(String input) {
        if (input == null) return "";
        return input.trim().replaceAll("[<>\"']", "");
    }
    
    /**
     * Validate string is not null or empty
     */
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
    
    /**
     * Validate number is positive
     */
    public static boolean isPositiveNumber(int number) {
        return number > 0;
    }
    
    /**
     * Validate rating (1-5)
     */
    public static boolean isValidRating(double rating) {
        return rating >= 1.0 && rating <= 5.0;
    }
}