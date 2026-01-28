package com.revature.revworkforce.dao;

import java.sql.SQLException;
import java.util.List;

import com.revature.revworkforce.model.Announcement;
import com.revature.revworkforce.model.Notification;

/**
 * DAO Interface for Notification operations
 */
public interface NotificationDAO {
    
    // Notification Operations
    boolean createNotification(Notification notification) throws SQLException;
    boolean markAsRead(int notificationId) throws SQLException;
    boolean markAllAsRead(String employeeId) throws SQLException;
    boolean deleteNotification(int notificationId) throws SQLException;
    
    // Read Operations
    List<Notification> getNotificationsByEmployee(String employeeId) throws SQLException;
    List<Notification> getUnreadNotifications(String employeeId) throws SQLException;
    int getUnreadCount(String employeeId) throws SQLException;
    
    // Announcement Operations
    int createAnnouncement(Announcement announcement) throws SQLException;
    List<Announcement> getAllAnnouncements() throws SQLException;
    List<Announcement> getRecentAnnouncements(int limit) throws SQLException;
    Announcement getAnnouncementById(int announcementId) throws SQLException;
}