package com.revature.revworkforce.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.revworkforce.dao.NotificationDAO;
import com.revature.revworkforce.dao.NotificationDAOImpl;
import com.revature.revworkforce.model.Announcement;
import com.revature.revworkforce.model.Notification;

/**
 * Implementation of NotificationService interface
 */
public class NotificationServiceImpl implements NotificationService {
    
    private static final Logger logger = LogManager.getLogger(NotificationServiceImpl.class);
    private NotificationDAO notificationDAO;
    
    public NotificationServiceImpl() {
        this.notificationDAO = new NotificationDAOImpl();
    }
    
    @Override
    public boolean createNotification(String employeeId, String type, String message) {
        logger.debug("Creating notification for employee: {}", employeeId);
        
        try {
            Notification notification = new Notification(employeeId, type, message);
            return notificationDAO.createNotification(notification);
        } catch (Exception e) {
            logger.error("Error creating notification", e);
            return false;
        }
    }
    
    @Override
    public boolean markAsRead(int notificationId) {
        logger.debug("Marking notification as read: {}", notificationId);
        
        try {
            return notificationDAO.markAsRead(notificationId);
        } catch (Exception e) {
            logger.error("Error marking notification as read", e);
            return false;
        }
    }
    
    @Override
    public boolean markAllAsRead(String employeeId) {
        logger.debug("Marking all notifications as read for employee: {}", employeeId);
        
        try {
            return notificationDAO.markAllAsRead(employeeId);
        } catch (Exception e) {
            logger.error("Error marking all notifications as read", e);
            return false;
        }
    }
    
    @Override
    public List<Notification> getNotifications(String employeeId) {
        try {
            return notificationDAO.getNotificationsByEmployee(employeeId);
        } catch (Exception e) {
            logger.error("Error getting notifications", e);
            return null;
        }
    }
    
    @Override
    public List<Notification> getUnreadNotifications(String employeeId) {
        try {
            return notificationDAO.getUnreadNotifications(employeeId);
        } catch (Exception e) {
            logger.error("Error getting unread notifications", e);
            return null;
        }
    }
    
    @Override
    public int getUnreadCount(String employeeId) {
        try {
            return notificationDAO.getUnreadCount(employeeId);
        } catch (Exception e) {
            logger.error("Error getting unread count", e);
            return 0;
        }
    }
    
    @Override
    public int createAnnouncement(Announcement announcement) {
        logger.info("Creating announcement: {}", announcement.getTitle());
        
        try {
            return notificationDAO.createAnnouncement(announcement);
        } catch (Exception e) {
            logger.error("Error creating announcement", e);
            return -1;
        }
    }

    @Override
    public List<Announcement> getAllAnnouncements() {
        try {
            return notificationDAO.getAllAnnouncements();
        } catch (Exception e) {
            logger.error("Error getting all announcements", e);
            return null;
        }
    }

    @Override
    public List<Announcement> getRecentAnnouncements(int limit) {
        try {
            return notificationDAO.getRecentAnnouncements(limit);
        } catch (Exception e) {
            logger.error("Error getting recent announcements", e);
            return null;
        }
    }
    }