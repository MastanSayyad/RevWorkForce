package com.revature.revworkforce.service;

import java.util.List;

import com.revature.revworkforce.model.Announcement;
import com.revature.revworkforce.model.Notification;

/**
 * Service Interface for Notification operations
 */
public interface NotificationService {
    
    /**
     * Create a notification
     * @param employeeId Employee ID
     * @param type Notification type
     * @param message Message content
     * @return true if notification created successfully
     */
    boolean createNotification(String employeeId, String type, String message);
    
    /**
     * Mark notification as read
     * @param notificationId Notification ID
     * @return true if marked as read successfully
     */
    boolean markAsRead(int notificationId);
    
    /**
     * Mark all notifications as read for an employee
     * @param employeeId Employee ID
     * @return true if all marked as read successfully
     */
    boolean markAllAsRead(String employeeId);
    
    /**
     * Get all notifications for an employee
     * @param employeeId Employee ID
     * @return List of notifications
     */
    List<Notification> getNotifications(String employeeId);
    
    /**
     * Get unread notifications for an employee
     * @param employeeId Employee ID
     * @return List of unread notifications
     */
    List<Notification> getUnreadNotifications(String employeeId);
    
    /**
     * Get unread notification count
     * @param employeeId Employee ID
     * @return Count of unread notifications
     */
    int getUnreadCount(String employeeId);
    
    /**
     * Create an announcement (Admin only)
     * @param announcement Announcement object
     * @return Announcement ID if successful
     */
    int createAnnouncement(Announcement announcement);
    
    /**
     * Get all announcements
     * @return List of announcements
     */
    List<Announcement> getAllAnnouncements();
    
    /**
     * Get recent announcements
     * @param limit Maximum number of announcements
     * @return List of recent announcements
     */
    List<Announcement> getRecentAnnouncements(int limit);
}