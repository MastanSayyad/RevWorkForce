package com.revature.revworkforce.dao;

import java.sql.SQLException;
import java.util.List;

import com.revature.revworkforce.model.AuditLog;

/**
 * DAO Interface for Audit Log operations
 */
public interface AuditLogDAO {
    
    /**
     * Create an audit log entry
     * @param auditLog AuditLog object
     * @return true if created successfully
     * @throws SQLException if database error occurs
     */
    boolean createAuditLog(AuditLog auditLog) throws SQLException;
    
    /**
     * Get audit logs by employee
     * @param employeeId Employee ID
     * @return List of audit logs
     * @throws SQLException if database error occurs
     */
    List<AuditLog> getAuditLogsByEmployee(String employeeId) throws SQLException;
    
    /**
     * Get audit logs by table name
     * @param tableName Table name
     * @return List of audit logs
     * @throws SQLException if database error occurs
     */
    List<AuditLog> getAuditLogsByTable(String tableName) throws SQLException;
    
    /**
     * Get audit logs by action
     * @param action Action type (INSERT, UPDATE, DELETE)
     * @return List of audit logs
     * @throws SQLException if database error occurs
     */
    List<AuditLog> getAuditLogsByAction(String action) throws SQLException;
    
    /**
     * Get recent audit logs
     * @param limit Maximum number of logs to retrieve
     * @return List of recent audit logs
     * @throws SQLException if database error occurs
     */
    List<AuditLog> getRecentAuditLogs(int limit) throws SQLException;
    
    /**
     * Get all audit logs
     * @return List of all audit logs
     * @throws SQLException if database error occurs
     */
    List<AuditLog> getAllAuditLogs() throws SQLException;
}