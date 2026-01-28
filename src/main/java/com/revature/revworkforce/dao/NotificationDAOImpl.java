package com.revature.revworkforce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.revworkforce.model.Announcement;
import com.revature.revworkforce.model.Notification;
import com.revature.revworkforce.util.DatabaseUtil;

/**
 * Implementation of NotificationDAO interface
 */
public class NotificationDAOImpl implements NotificationDAO {
    
    private static final Logger logger = LogManager.getLogger(NotificationDAOImpl.class);
    
    @Override
    public boolean createNotification(Notification notification) throws SQLException {
        logger.debug("Creating notification for employee: {}", notification.getEmployeeId());
        
        String sql = "INSERT INTO notifications (notification_id, employee_id, " +
                     "notification_type, message, is_read) " +
                     "VALUES (seq_notification_id.NEXTVAL, ?, ?, ?, 0)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, notification.getEmployeeId());
            pstmt.setString(2, notification.getNotificationType());
            pstmt.setString(3, notification.getMessage());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                logger.info("Notification created successfully");
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error creating notification", e);
            throw e;
        }
        
        return false;
    }
    
    @Override
    public boolean markAsRead(int notificationId) throws SQLException {
        logger.debug("Marking notification as read: {}", notificationId);
        
        String sql = "UPDATE notifications SET is_read = 1, read_at = CURRENT_TIMESTAMP " +
                     "WHERE notification_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, notificationId);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                logger.info("Notification marked as read: {}", notificationId);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error marking notification as read", e);
            throw e;
        }
        
        return false;
    }
    
    @Override
    public boolean markAllAsRead(String employeeId) throws SQLException {
        logger.debug("Marking all notifications as read for employee: {}", employeeId);
        
        String sql = "UPDATE notifications SET is_read = 1, read_at = CURRENT_TIMESTAMP " +
                     "WHERE employee_id = ? AND is_read = 0";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employeeId);
            
            int rowsAffected = pstmt.executeUpdate();
            
            logger.info("Marked {} notifications as read", rowsAffected);
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.error("Error marking all notifications as read", e);
            throw e;
        }
    }
    
    @Override
    public boolean deleteNotification(int notificationId) throws SQLException {
        logger.debug("Deleting notification: {}", notificationId);
        
        String sql = "DELETE FROM notifications WHERE notification_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, notificationId);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                logger.info("Notification deleted: {}", notificationId);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error deleting notification", e);
            throw e;
        }
        
        return false;
    }
    
    @Override
    public List<Notification> getNotificationsByEmployee(String employeeId) throws SQLException {
        logger.debug("Getting notifications for employee: {}", employeeId);
        
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM notifications WHERE employee_id = ? " +
                     "ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employeeId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                notifications.add(extractNotificationFromResultSet(rs));
            }
            
            logger.info("Notifications retrieved: {}", notifications.size());
        } catch (SQLException e) {
            logger.error("Error getting notifications", e);
            throw e;
        }
        
        return notifications;
    }
    
    @Override
    public List<Notification> getUnreadNotifications(String employeeId) throws SQLException {
        logger.debug("Getting unread notifications for employee: {}", employeeId);
        
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM notifications WHERE employee_id = ? AND is_read = 0 " +
                     "ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employeeId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                notifications.add(extractNotificationFromResultSet(rs));
            }
            
            logger.info("Unread notifications retrieved: {}", notifications.size());
        } catch (SQLException e) {
            logger.error("Error getting unread notifications", e);
            throw e;
        }
        
        return notifications;
    }
    
    @Override
    public int getUnreadCount(String employeeId) throws SQLException {
        logger.debug("Getting unread count for employee: {}", employeeId);
        
        String sql = "SELECT COUNT(*) FROM notifications WHERE employee_id = ? AND is_read = 0";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employeeId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                int count = rs.getInt(1);
                logger.info("Unread count: {}", count);
                return count;
            }
        } catch (SQLException e) {
            logger.error("Error getting unread count", e);
            throw e;
        }
        
        return 0;
    }
    
    @Override
    public int createAnnouncement(Announcement announcement) throws SQLException {
        logger.debug("Creating announcement: {}", announcement.getTitle());
        
        String sql = "INSERT INTO announcements (announcement_id, title, content, posted_by) " +
                     "VALUES (seq_announcement_id.NEXTVAL, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"announcement_id"})) {
            
            pstmt.setString(1, announcement.getTitle());
            pstmt.setString(2, announcement.getContent());
            pstmt.setString(3, announcement.getPostedBy());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int announcementId = rs.getInt(1);
                    logger.info("Announcement created: {}", announcementId);
                    return announcementId;
                }
            }
        } catch (SQLException e) {
            logger.error("Error creating announcement", e);
            throw e;
        }
        
        return -1;
    }
    
    @Override
    public List<Announcement> getAllAnnouncements() throws SQLException {
        logger.debug("Getting all announcements");
        
        List<Announcement> announcements = new ArrayList<>();
        String sql = "SELECT a.*, e.first_name || ' ' || e.last_name AS poster_name " +
                     "FROM announcements a " +
                     "LEFT JOIN employees e ON a.posted_by = e.employee_id " +
                     "ORDER BY a.posted_date DESC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                announcements.add(extractAnnouncementFromResultSet(rs));
            }
            
            logger.info("Announcements retrieved: {}", announcements.size());
        } catch (SQLException e) {
            logger.error("Error getting all announcements", e);
            throw e;
        }
        
        return announcements;
    }
    
    @Override
    public List<Announcement> getRecentAnnouncements(int limit) throws SQLException {
        logger.debug("Getting recent {} announcements", limit);
        
        List<Announcement> announcements = new ArrayList<>();
        String sql = "SELECT a.*, e.first_name || ' ' || e.last_name AS poster_name " +
                     "FROM announcements a " +
                     "LEFT JOIN employees e ON a.posted_by = e.employee_id " +
                     "ORDER BY a.posted_date DESC " +
                     "FETCH FIRST ? ROWS ONLY";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                announcements.add(extractAnnouncementFromResultSet(rs));
            }
            
            logger.info("Recent announcements retrieved: {}", announcements.size());
        } catch (SQLException e) {
            logger.error("Error getting recent announcements", e);
            throw e;
        }
        
        return announcements;
    }
    
    @Override
    public Announcement getAnnouncementById(int announcementId) throws SQLException {
        logger.debug("Getting announcement by ID: {}", announcementId);
        
        String sql = "SELECT a.*, e.first_name || ' ' || e.last_name AS poster_name " +
                     "FROM announcements a " +
                     "LEFT JOIN employees e ON a.posted_by = e.employee_id " +
                     "WHERE a.announcement_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, announcementId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractAnnouncementFromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.error("Error getting announcement by ID", e);
            throw e;
        }
        
        return null;
    }
    
    /**
     * Helper method to extract Notification from ResultSet
     */
    private Notification extractNotificationFromResultSet(ResultSet rs) throws SQLException {
        Notification notification = new Notification();
        notification.setNotificationId(rs.getInt("notification_id"));
        notification.setEmployeeId(rs.getString("employee_id"));
        notification.setNotificationType(rs.getString("notification_type"));
        notification.setMessage(rs.getString("message"));
        notification.setRead(rs.getInt("is_read") == 1);
        
        if (rs.getTimestamp("created_at") != null) {
            notification.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        if (rs.getTimestamp("read_at") != null) {
            notification.setReadAt(rs.getTimestamp("read_at").toLocalDateTime());
        }
        
        return notification;
    }
    
    /**
     * Helper method to extract Announcement from ResultSet
     */
    private Announcement extractAnnouncementFromResultSet(ResultSet rs) throws SQLException {
        Announcement announcement = new Announcement();
        announcement.setAnnouncementId(rs.getInt("announcement_id"));
        announcement.setTitle(rs.getString("title"));
        announcement.setContent(rs.getString("content"));
        announcement.setPostedBy(rs.getString("posted_by"));
        
        if (rs.getTimestamp("posted_date") != null) {
            announcement.setPostedDate(rs.getTimestamp("posted_date").toLocalDateTime());
        }
        if (rs.getTimestamp("created_at") != null) {
            announcement.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        
        try {
            announcement.setPosterName(rs.getString("poster_name"));
        } catch (SQLException e) {
            // Column may not exist in all queries
        }
        
        return announcement;
    }
}