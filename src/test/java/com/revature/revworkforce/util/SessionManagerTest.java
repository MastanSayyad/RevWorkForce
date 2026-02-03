package com.revature.revworkforce.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.revature.revworkforce.model.Employee;

/**
 * Test class for SessionManager
 */
public class SessionManagerTest {
    
    @BeforeEach
    public void setUp() {
        // Clear session before each test using logout()
        SessionManager.logout();
    }
    
    @Test
    @DisplayName("Test set current user")
    public void testSetCurrentUser() {
        Employee employee = new Employee();
        employee.setEmployeeId("EMP001");
        employee.setFirstName("Test");
        employee.setLastName("User");
        
        SessionManager.setCurrentUser(employee);
        
        Employee currentUser = SessionManager.getCurrentUser();
        assertNotNull(currentUser, "Current user should not be null");
        assertEquals("EMP001", currentUser.getEmployeeId());
    }
    
    @Test
    @DisplayName("Test get current user - no user set")
    public void testGetCurrentUser_NoUser() {
        Employee currentUser = SessionManager.getCurrentUser();
        assertNull(currentUser, "Current user should be null when not set");
    }
    
    @Test
    @DisplayName("Test logout clears session")
    public void testLogout() {
        Employee employee = new Employee();
        employee.setEmployeeId("EMP001");
        
        SessionManager.setCurrentUser(employee);
        assertNotNull(SessionManager.getCurrentUser(), "User should be set");
        
        SessionManager.logout();
        assertNull(SessionManager.getCurrentUser(), "User should be null after logout");
    }
    
    @Test
    @DisplayName("Test is logged in - true")
    public void testIsLoggedIn_True() {
        Employee employee = new Employee();
        employee.setEmployeeId("EMP001");
        
        SessionManager.setCurrentUser(employee);
        assertTrue(SessionManager.isLoggedIn(), "Should be logged in");
    }
    
    @Test
    @DisplayName("Test is logged in - false")
    public void testIsLoggedIn_False() {
        assertFalse(SessionManager.isLoggedIn(), "Should not be logged in");
    }
    
    @Test
    @DisplayName("Test update activity")
    public void testUpdateActivity() {
        Employee employee = new Employee();
        employee.setEmployeeId("EMP001");
        
        SessionManager.setCurrentUser(employee);
        
        // Update activity - should not throw exception
        SessionManager.updateActivity();
        
        // Should still be logged in
        assertTrue(SessionManager.isLoggedIn(), "Should still be logged in after activity update");
    }
    
    @Test
    @DisplayName("Test is session expired - fresh session")
    public void testIsSessionExpired_FreshSession() {
        Employee employee = new Employee();
        employee.setEmployeeId("EMP001");
        
        SessionManager.setCurrentUser(employee);
        
        // Fresh session should not be expired
        assertFalse(SessionManager.isSessionExpired(), "Fresh session should not be expired");
    }
    
    @Test
    @DisplayName("Test is session expired - no user")
    public void testIsSessionExpired_NoUser() {
        // No user set, session should be expired
        assertTrue(SessionManager.isSessionExpired(), "Session should be expired when no user is set");
    }
    
    @Test
    @DisplayName("Test multiple users - only one session at a time")
    public void testMultipleUsers() {
        Employee employee1 = new Employee();
        employee1.setEmployeeId("EMP001");
        
        SessionManager.setCurrentUser(employee1);
        assertEquals("EMP001", SessionManager.getCurrentUser().getEmployeeId());
        
        // Set another user - should replace the first
        Employee employee2 = new Employee();
        employee2.setEmployeeId("EMP002");
        
        SessionManager.setCurrentUser(employee2);
        assertEquals("EMP002", SessionManager.getCurrentUser().getEmployeeId(), 
            "New user should replace old user");
    }
}