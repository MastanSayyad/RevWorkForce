package com.revature.revworkforce.model;

import java.time.LocalDateTime;

/**
 * Model class representing Leave Balance for an employee
 */
public class LeaveBalance {
    
    private int leaveBalanceId;
    private String employeeId;
    private int leaveTypeId;
    private int totalAllocated;
    private int usedLeaves;
    private int availableLeaves;
    private int year;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Additional fields for display
    private String leaveTypeName;
    
    // Constructors
    public LeaveBalance() {
    }
    
    public LeaveBalance(String employeeId, int leaveTypeId, int totalAllocated, int year) {
        this.employeeId = employeeId;
        this.leaveTypeId = leaveTypeId;
        this.totalAllocated = totalAllocated;
        this.usedLeaves = 0;
        this.availableLeaves = totalAllocated;
        this.year = year;
    }
    
    public LeaveBalance(int leaveBalanceId, String employeeId, int leaveTypeId,
                       int totalAllocated, int usedLeaves, int availableLeaves, int year) {
        this.leaveBalanceId = leaveBalanceId;
        this.employeeId = employeeId;
        this.leaveTypeId = leaveTypeId;
        this.totalAllocated = totalAllocated;
        this.usedLeaves = usedLeaves;
        this.availableLeaves = availableLeaves;
        this.year = year;
    }
    
    // Getters and Setters
    public int getLeaveBalanceId() {
        return leaveBalanceId;
    }
    
    public void setLeaveBalanceId(int leaveBalanceId) {
        this.leaveBalanceId = leaveBalanceId;
    }
    
    public String getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    
    public int getLeaveTypeId() {
        return leaveTypeId;
    }
    
    public void setLeaveTypeId(int leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }
    
    public int getTotalAllocated() {
        return totalAllocated;
    }
    
    public void setTotalAllocated(int totalAllocated) {
        this.totalAllocated = totalAllocated;
    }
    
    public int getUsedLeaves() {
        return usedLeaves;
    }
    
    public void setUsedLeaves(int usedLeaves) {
        this.usedLeaves = usedLeaves;
    }
    
    public int getAvailableLeaves() {
        return availableLeaves;
    }
    
    public void setAvailableLeaves(int availableLeaves) {
        this.availableLeaves = availableLeaves;
    }
    
    public int getYear() {
        return year;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public String getLeaveTypeName() {
        return leaveTypeName;
    }
    
    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
    }
    
    @Override
    public String toString() {
        return "LeaveBalance{" +
                "leaveBalanceId=" + leaveBalanceId +
                ", employeeId='" + employeeId + '\'' +
                ", leaveTypeId=" + leaveTypeId +
                ", totalAllocated=" + totalAllocated +
                ", usedLeaves=" + usedLeaves +
                ", availableLeaves=" + availableLeaves +
                ", year=" + year +
                '}';
    }
}