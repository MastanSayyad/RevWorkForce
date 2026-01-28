package com.revature.revworkforce.model;

import java.time.LocalDateTime;

/**
 * Model class representing an Audit Log entry
 */
public class AuditLog {
    
    private int logId;
    private String employeeId;
    private String action;
    private String tableName;
    private String recordId;
    private String oldValue;
    private String newValue;
    private String ipAddress;
    private LocalDateTime createdAt;
    
    // Constructors
    public AuditLog() {
    }
    
    public AuditLog(String employeeId, String action, String tableName, String recordId) {
        this.employeeId = employeeId;
        this.action = action;
        this.tableName = tableName;
        this.recordId = recordId;
    }
    
    // Getters and Setters
    public int getLogId() {
        return logId;
    }
    
    public void setLogId(int logId) {
        this.logId = logId;
    }
    
    public String getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getAction() {
        return action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }
    
    public String getTableName() {
        return tableName;
    }
    
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    public String getRecordId() {
        return recordId;
    }
    
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
    
    public String getOldValue() {
        return oldValue;
    }
    
    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }
    
    public String getNewValue() {
        return newValue;
    }
    
    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "AuditLog{" +
                "logId=" + logId +
                ", employeeId='" + employeeId + '\'' +
                ", action='" + action + '\'' +
                ", tableName='" + tableName + '\'' +
                ", recordId='" + recordId + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}