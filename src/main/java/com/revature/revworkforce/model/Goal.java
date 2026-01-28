package com.revature.revworkforce.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Model class representing an Employee Goal
 */
public class Goal {
    
    private int goalId;
    private String employeeId;
    private String goalDescription;
    private LocalDate deadline;
    private String priority;
    private String successMetrics;
    private int progressPercentage;
    private String status;
    private String managerComments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Priority constants
    public static final String PRIORITY_HIGH = "HIGH";
    public static final String PRIORITY_MEDIUM = "MEDIUM";
    public static final String PRIORITY_LOW = "LOW";
    
    // Status constants
    public static final String STATUS_NOT_STARTED = "NOT_STARTED";
    public static final String STATUS_IN_PROGRESS = "IN_PROGRESS";
    public static final String STATUS_COMPLETED = "COMPLETED";
    public static final String STATUS_CANCELLED = "CANCELLED";
    
    // Constructors
    public Goal() {
    }
    
    public Goal(String employeeId, String goalDescription, LocalDate deadline, String priority) {
        this.employeeId = employeeId;
        this.goalDescription = goalDescription;
        this.deadline = deadline;
        this.priority = priority;
        this.progressPercentage = 0;
        this.status = STATUS_NOT_STARTED;
    }
    
    // Getters and Setters
    public int getGoalId() {
        return goalId;
    }
    
    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }
    
    public String getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getGoalDescription() {
        return goalDescription;
    }
    
    public void setGoalDescription(String goalDescription) {
        this.goalDescription = goalDescription;
    }
    
    public LocalDate getDeadline() {
        return deadline;
    }
    
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
    
    public String getPriority() {
        return priority;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
    }
    
    public String getSuccessMetrics() {
        return successMetrics;
    }
    
    public void setSuccessMetrics(String successMetrics) {
        this.successMetrics = successMetrics;
    }
    
    public int getProgressPercentage() {
        return progressPercentage;
    }
    
    public void setProgressPercentage(int progressPercentage) {
        this.progressPercentage = progressPercentage;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getManagerComments() {
        return managerComments;
    }
    
    public void setManagerComments(String managerComments) {
        this.managerComments = managerComments;
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
        return "Goal{" +
                "goalId=" + goalId +
                ", employeeId='" + employeeId + '\'' +
                ", goalDescription='" + goalDescription + '\'' +
                ", deadline=" + deadline +
                ", priority='" + priority + '\'' +
                ", progressPercentage=" + progressPercentage +
                ", status='" + status + '\'' +
                '}';
    }
}