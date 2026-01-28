package com.revature.revworkforce.model;

import java.time.LocalDateTime;

/**
 * Model class representing a Designation
 */
public class Designation {
    
    private int designationId;
    private String designationName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors
    public Designation() {
    }
    
    public Designation(int designationId, String designationName) {
        this.designationId = designationId;
        this.designationName = designationName;
    }
    
    public Designation(int designationId, String designationName, 
                      LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.designationId = designationId;
        this.designationName = designationName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public int getDesignationId() {
        return designationId;
    }
    
    public void setDesignationId(int designationId) {
        this.designationId = designationId;
    }
    
    public String getDesignationName() {
        return designationName;
    }
    
    public void setDesignationName(String designationName) {
        this.designationName = designationName;
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
    
    @Override
    public String toString() {
        return "Designation{" +
                "designationId=" + designationId +
                ", designationName='" + designationName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Designation that = (Designation) o;
        return designationId == that.designationId;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(designationId);
    }
}