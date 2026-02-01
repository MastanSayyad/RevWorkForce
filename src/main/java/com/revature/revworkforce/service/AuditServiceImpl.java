package com.revature.revworkforce.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.revworkforce.dao.AuditLogDAO;
import com.revature.revworkforce.dao.AuditLogDAOImpl;
import com.revature.revworkforce.model.AuditLog;

/**
 * Implementation of AuditService interface
 */
public class AuditServiceImpl implements AuditService {
    
    private static final Logger logger = LogManager.getLogger(AuditServiceImpl.class);
    private AuditLogDAO auditLogDAO;
    
    public AuditServiceImpl() {
        this.auditLogDAO = new AuditLogDAOImpl();
    }
    
    @Override
    public boolean logAction(String employeeId, String action, String tableName, 
                            String recordId, String oldValue, String newValue) {
        logger.debug("Logging action: {} on table: {} by employee: {}", 
                    action, tableName, employeeId);
        
        try {
            AuditLog auditLog = new AuditLog();
            auditLog.setEmployeeId(employeeId);
            auditLog.setAction(action);
            auditLog.setTableName(tableName);
            auditLog.setRecordId(recordId);
            auditLog.setOldValue(oldValue);
            auditLog.setNewValue(newValue);
            auditLog.setIpAddress("localhost"); // In real app, get actual IP
            
            return auditLogDAO.createAuditLog(auditLog);
        } catch (Exception e) {
            logger.error("Error logging action", e);
            return false;
        }
    }
    
    @Override
    public List<AuditLog> getAuditLogsByEmployee(String employeeId) {
        try {
            return auditLogDAO.getAuditLogsByEmployee(employeeId);
        } catch (Exception e) {
            logger.error("Error getting audit logs by employee", e);
            return null;
        }
    }
    
    @Override
    public List<AuditLog> getAuditLogsByTable(String tableName) {
        try {
            return auditLogDAO.getAuditLogsByTable(tableName);
        } catch (Exception e) {
            logger.error("Error getting audit logs by table", e);
            return null;
        }
    }
    
    @Override
    public List<AuditLog> getRecentAuditLogs(int limit) {
        try {
            return auditLogDAO.getRecentAuditLogs(limit);
        } catch (Exception e) {
            logger.error("Error getting recent audit logs", e);
            return null;
        }
    }
    
    @Override
    public List<AuditLog> getAllAuditLogs() {
        try {
            return auditLogDAO.getAllAuditLogs();
        } catch (Exception e) {
            logger.error("Error getting all audit logs", e);
            return null;
        }
    }
}