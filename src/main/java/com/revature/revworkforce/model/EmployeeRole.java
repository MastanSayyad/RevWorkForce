package com.revature.revworkforce.model;

import java.time.LocalDateTime;

/**
 * Model class representing Employee-Role relationship
 * Junction table between Employee and Role
 */
public class EmployeeRole {
    
    private int employeeRoleId;
    private String employeeId;
    private int roleId;
    private LocalDateTime createdAt;
    
    // Constructors
    public EmployeeRole() {
    }
    
    public EmployeeRole(String employeeId, int roleId) {
        this.employeeId = employeeId;
        this.roleId = roleId;
    }
    
    public EmployeeRole(int employeeRoleId, String employeeId, int roleId, LocalDateTime createdAt) {
        this.employeeRoleId = employeeRoleId;
        this.employeeId = employeeId;
        this.roleId = roleId;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public int getEmployeeRoleId() {
        return employeeRoleId;
    }
    
    public void setEmployeeRoleId(int employeeRoleId) {
        this.employeeRoleId = employeeRoleId;
    }
    
    public String getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    
    public int getRoleId() {
        return roleId;
    }
    
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "EmployeeRole{" +
                "employeeRoleId=" + employeeRoleId +
                ", employeeId='" + employeeId + '\'' +
                ", roleId=" + roleId +
                ", createdAt=" + createdAt +
                '}';
    }
}