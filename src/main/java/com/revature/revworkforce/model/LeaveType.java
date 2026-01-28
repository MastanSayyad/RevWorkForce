package com.revature.revworkforce.model;

import java.time.LocalDateTime;

/**
 * Model class representing a Leave Type
 */
public class LeaveType {
    
    private int leaveTypeId;
    private String leaveTypeName;
    private String description;
    private LocalDateTime createdAt;
    
    // Leave type constants
    public static final String CASUAL = "CASUAL";
    public static final String SICK = "SICK";
    public static final String PAID = "PAID";
    public static final String PRIVILEGE = "PRIVILEGE";
    
    // Constructors
    public LeaveType() {
    }
    
    public LeaveType(int leaveTypeId, String leaveTypeName) {
        this.leaveTypeId = leaveTypeId;
        this.leaveTypeName = leaveTypeName;
    }
    
    public LeaveType(int leaveTypeId, String leaveTypeName, String description) {
        this.leaveTypeId = leaveTypeId;
        this.leaveTypeName = leaveTypeName;
        this.description = description;
    }
    
    public LeaveType(int leaveTypeId, String leaveTypeName, 
                    String description, LocalDateTime createdAt) {
        this.leaveTypeId = leaveTypeId;
        this.leaveTypeName = leaveTypeName;
        this.description = description;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public int getLeaveTypeId() {
        return leaveTypeId;
    }
    
    public void setLeaveTypeId(int leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }
    
    public String getLeaveTypeName() {
        return leaveTypeName;
    }
    
    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "LeaveType{" +
                "leaveTypeId=" + leaveTypeId +
                ", leaveTypeName='" + leaveTypeName + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeaveType leaveType = (LeaveType) o;
        return leaveTypeId == leaveType.leaveTypeId;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(leaveTypeId);
    }
}