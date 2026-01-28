package com.revature.revworkforce.model;

import java.time.LocalDateTime;

/**
 * Model class representing a Role
 */
public class Role {
    
    private int roleId;
    private String roleName;
    private LocalDateTime createdAt;
    
    // Role constants
    public static final String EMPLOYEE = "EMPLOYEE";
    public static final String MANAGER = "MANAGER";
    public static final String ADMIN = "ADMIN";
    
    // Constructors
    public Role() {
    }
    
    public Role(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }
    
    public Role(int roleId, String roleName, LocalDateTime createdAt) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public int getRoleId() {
        return roleId;
    }
    
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    
    public String getRoleName() {
        return roleName;
    }
    
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return roleId == role.roleId;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(roleId);
    }
}