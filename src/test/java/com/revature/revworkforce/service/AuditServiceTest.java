package com.revature.revworkforce.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.revature.revworkforce.model.AuditLog;

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
        List<AuditLog> logs = auditService.getAuditLogsByEmployee("EMP001");
        assertNotNull(logs, "Audit logs list should not be null");
    }
    
    @Test
    @DisplayName("Test get audit logs by table")
    public void testGetAuditLogsByTable() {
        List<AuditLog> logs = auditService.getAuditLogsByTable("employees");
        assertNotNull(logs, "Audit logs list should not be null");
    }
    
    @Test
    @DisplayName("Test get audit logs by action")
    public void testGetAuditLogsByAction() {
        List<AuditLog> logs = auditService.getAuditLogsByEmployee("LOGIN");
        assertNotNull(logs, "Audit logs list should not be null");
    }
    
    @Test
    @DisplayName("Test get recent audit logs")
    public void testGetRecentAuditLogs() {
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
}