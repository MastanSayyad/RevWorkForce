package com.revature.revworkforce.model;

import java.time.LocalDateTime;

/**
 * Model class representing a Notification
 */
public class Notification {
    
    private int notificationId;
    private String employeeId;
    private String notificationType;
    private String message;
    private boolean isRead;
    private LocalDateTime createdAt;
    private LocalDateTime readAt;
    
    // Notification type constants
    public static final String TYPE_LEAVE = "LEAVE";
    public static final String TYPE_PERFORMANCE = "PERFORMANCE";
    public static final String TYPE_BIRTHDAY = "BIRTHDAY";
    public static final String TYPE_ANNIVERSARY = "ANNIVERSARY";
    public static final String TYPE_ANNOUNCEMENT = "ANNOUNCEMENT";
    public static final String TYPE_SYSTEM = "SYSTEM";
    
    // Constructors
    public Notification() {
    }
    
    public Notification(String employeeId, String notificationType, String message) {
        this.employeeId = employeeId;
        this.notificationType = notificationType;
        this.message = message;
        this.isRead = false;
    }
    
    // Getters and Setters
    public int getNotificationId() {
        return notificationId;
    }
    
    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }
    
    public String getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getNotificationType() {
        return notificationType;
    }
    
    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public boolean isRead() {
        return isRead;
    }
    
    public void setRead(boolean read) {
        isRead = read;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getReadAt() {
        return readAt;
    }
    
    public void setReadAt(LocalDateTime readAt) {
        this.readAt = readAt;
    }
    
    @Override
    public String toString() {
        return "Notification{" +
                "notificationId=" + notificationId +
                ", employeeId='" + employeeId + '\'' +
                ", notificationType='" + notificationType + '\'' +
                ", message='" + message + '\'' +
                ", isRead=" + isRead +
                ", createdAt=" + createdAt +
                '}';
    }
}