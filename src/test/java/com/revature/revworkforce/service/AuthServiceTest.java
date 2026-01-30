package com.revature.revworkforce.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.revature.revworkforce.exception.AuthenticationException;
import com.revature.revworkforce.model.Employee;
import org.junit.jupiter.api.Disabled;


/**
 * Test class for AuthService
 */

@Disabled("Integration test – requires database")

public class AuthServiceTest {
    
    private AuthService authService;
    
    @BeforeEach
    public void setUp() {
        authService = new AuthServiceImpl();
    }
    
    @Test
    @DisplayName("Test login with valid credentials")
    public void testLogin_ValidCredentials() {
        try {
            Employee employee = authService.login("EMP001", "password123");
            assertNotNull(employee, "Employee should not be null for valid credentials");
            assertEquals("EMP001", employee.getEmployeeId());
        } catch (AuthenticationException e) {
            fail("Login should succeed with valid credentials");
        }
    }
    
    @Test
    @DisplayName("Test login with invalid password")
    public void testLogin_InvalidPassword() {
        assertThrows(AuthenticationException.class, () -> {
            authService.login("EMP001", "wrongpassword");
        }, "Should throw AuthenticationException for invalid password");
    }
    
    @Test
    @DisplayName("Test login with invalid employee ID")
    public void testLogin_InvalidEmployeeId() {
        assertThrows(AuthenticationException.class, () -> {
            authService.login("INVALID", "password123");
        }, "Should throw AuthenticationException for invalid employee ID");
    }
    
    @Test
    @DisplayName("Test login with empty credentials")
    public void testLogin_EmptyCredentials() {
        assertThrows(AuthenticationException.class, () -> {
            authService.login("", "");
        }, "Should throw AuthenticationException for empty credentials");
    }
    
    @Test
    @DisplayName("Test has role - employee with role")
    public void testHasRole_Success() {
        try {
            Employee employee = authService.login("EMP001", "password123");
            boolean hasRole = authService.hasRole(employee, "EMPLOYEE");
            assertTrue(hasRole, "Employee should have EMPLOYEE role");
        } catch (AuthenticationException e) {
            fail("Login failed");
        }
    }
    
    @Test
    @DisplayName("Test has role - employee without role")
    public void testHasRole_Failure() {
        try {
            Employee employee = authService.login("EMP001", "password123");
            boolean hasRole = authService.hasRole(employee, "ADMIN");
            assertFalse(hasRole, "Regular employee should not have ADMIN role");
        } catch (AuthenticationException e) {
            fail("Login failed");
        }
    }
}