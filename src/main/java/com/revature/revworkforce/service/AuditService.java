package com.revature.revworkforce.service;

import java.util.List;

import com.revature.revworkforce.model.AuditLog;

/**
 * Service Interface for Audit Log operations
 */
public interface AuditService {
    
    /**
     * Log an action
     * @param employeeId Employee who performed the action
     * @param action Action performed (INSERT, UPDATE, DELETE, LOGIN, LOGOUT)
     * @param tableName Table affected
     * @param recordId Record ID affected
     * @param oldValue Old value (for UPDATE)
     * @param newValue New value (for INSERT/UPDATE)
     * @return true if logged successfully
     */
    boolean logAction(String employeeId, String action, String tableName, 
                     String recordId, String oldValue, String newValue);
    
    /**
     * Get audit logs by employee
     * @param employeeId Employee ID
     * @return List of audit logs
     */
    List<AuditLog> getAuditLogsByEmployee(String employeeId);
    
    /**
     * Get audit logs by table
     * @param tableName Table name
     * @return List of audit logs
     */
    List<AuditLog> getAuditLogsByTable(String tableName);
    
    /**
     * Get recent audit logs
     * @param limit Number of logs to retrieve
     * @return List of recent audit logs
     */
    List<AuditLog> getRecentAuditLogs(int limit);
    
    /**
     * Get all audit logs (Admin only)
     * @return List of all audit logs
     */
    List<AuditLog> getAllAuditLogs();
}