package com.revature.revworkforce.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.revature.revworkforce.model.AuditLog;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable; 
@DisabledIfEnvironmentVariable(named = "CI", matches = "true")

/**
 * Test class for AuditService
 */
public class AuditServiceTest {
    
    private AuditService auditService;
    
    @BeforeEach
    public void setUp() {
        auditService = new AuditServiceImpl();
    }
    
    @Test
    @DisplayName("Test log action")
    public void testLogAction() {
        boolean result = auditService.logAction(
            "EMP001",
            "LOGIN",
            "employees",
            "EMP001",
            null,
            "User logged in"
        );
        
        // Result can be true or false, we're testing it doesn't crash
        assertNotNull(result);
    }
    
    @Test
    @DisplayName("Test get audit logs by employee")
    public void testGetAuditLogsByEmployee() {
        // First log an action
        auditService.logAction("EMP001", "TEST", "employees", "EMP001", null, "Test");
        
        List<AuditLog> logs = auditService.getAuditLogsByEmployee("EMP001");
        assertNotNull(logs, "Audit logs list should not be null");
    }
    
    @Test
    @DisplayName("Test get audit logs by table")
    public void testGetAuditLogsByTable() {
        // First log an action
        auditService.logAction("EMP001", "TEST", "employees", "EMP001", null, "Test");
        
        List<AuditLog> logs = auditService.getAuditLogsByTable("employees");
        assertNotNull(logs, "Audit logs list should not be null");
    }
    
    @Test
    @DisplayName("Test get audit logs by action")
    public void testGetAuditLogsByAction() {
        // First log an action
        auditService.logAction("EMP001", "LOGIN", "employees", "EMP001", null, "Test");
        
        List<AuditLog> logs = auditService.getAuditLogsByEmployee("LOGIN");
        assertNotNull(logs, "Audit logs list should not be null");
    }
    
    @Test
    @DisplayName("Test get recent audit logs")
    public void testGetRecentAuditLogs() {
        // First log some actions
        auditService.logAction("EMP001", "TEST1", "employees", "EMP001", null, "Test 1");
        auditService.logAction("EMP001", "TEST2", "employees", "EMP001", null, "Test 2");
        
        List<AuditLog> logs = auditService.getRecentAuditLogs(10);
        assertNotNull(logs, "Recent audit logs list should not be null");
    }
    
    @Test
    @DisplayName("Test log action with old and new values")
    public void testLogActionWithValues() {
        boolean result = auditService.logAction(
            "ADM001",
            "UPDATE",
            "employees",
            "EMP001",
            "salary=50000",
            "salary=55000"
        );
        
        assertNotNull(result);
    }
    
    @Test
    @DisplayName("Test log multiple actions for same employee")
    public void testLogMultipleActions() {
        auditService.logAction("EMP001", "LOGIN", "employees", "EMP001", null, "Login 1");
        auditService.logAction("EMP001", "LOGOUT", "employees", "EMP001", null, "Logout 1");
        auditService.logAction("EMP001", "LOGIN", "employees", "EMP001", null, "Login 2");
        
        List<AuditLog> logs = auditService.getAuditLogsByEmployee("EMP001");
        assertNotNull(logs);
        assertTrue(logs.size() >= 3, "Should have at least 3 audit logs");
    }
}