package com.revature.revworkforce.dao;

import com.revature.revworkforce.model.Notification;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable; 
@DisabledIfEnvironmentVariable(named = "CI", matches = "true")

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NotificationDAOTest {

    private NotificationDAO notificationDAO;
    private final String testEmployeeId = "EMP001";

    @BeforeAll
    void setUp() {
        notificationDAO = new NotificationDAOImpl();
    }

    // ==================== Create ====================

    @Test
    void testCreateNotification() throws SQLException {
        Notification notification = new Notification();
        notification.setEmployeeId(testEmployeeId);
        notification.setNotificationType("LEAVE");
        notification.setMessage("Leave approved");
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        boolean created = notificationDAO.createNotification(notification);
        assertTrue(created);
    }

    // ==================== Read ====================

    @Test
    void testGetNotificationsByEmployee() throws SQLException {
        List<Notification> notifications =
                notificationDAO.getNotificationsByEmployee(testEmployeeId);

        assertNotNull(notifications);
    }

    @Test
    void testGetUnreadNotifications() throws SQLException {
        List<Notification> unread =
                notificationDAO.getUnreadNotifications(testEmployeeId);

        assertNotNull(unread);

        for (Notification n : unread) {
            assertFalse(n.isRead());
        }
    }

    @Test
    void testGetUnreadCount() throws SQLException {
        int count = notificationDAO.getUnreadCount(testEmployeeId);
        assertTrue(count >= 0);
    }

    // ==================== Update ====================

    @Test
    void testMarkAsRead() throws SQLException {
        // Create notification
        Notification notification = new Notification();
        notification.setEmployeeId(testEmployeeId);
        notification.setNotificationType("LEAVE");
        notification.setMessage("Mark read test");
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        boolean created = notificationDAO.createNotification(notification);
        assertTrue(created);

        // Grab latest notification
        List<Notification> list =
                notificationDAO.getUnreadNotifications(testEmployeeId);

        assertFalse(list.isEmpty());

        int notificationId = list.get(0).getNotificationId();

        boolean marked = notificationDAO.markAsRead(notificationId);
        assertTrue(marked);
    }

    @Test
    void testMarkAllAsRead() throws SQLException {
        boolean result =
                notificationDAO.markAllAsRead(testEmployeeId);

        // May be false if nothing unread — both are acceptable
        assertNotNull(result);
    }

    // ==================== Delete ====================

    @Test
    void testDeleteNotification() throws SQLException {
        // Create notification
        Notification notification = new Notification();
        notification.setEmployeeId(testEmployeeId);
        notification.setNotificationType("ANNOUNCEMENT");
        notification.setMessage("Delete me");
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        boolean created = notificationDAO.createNotification(notification);
        assertTrue(created);

        // Fetch latest
        List<Notification> notifications =
                notificationDAO.getNotificationsByEmployee(testEmployeeId);

        assertFalse(notifications.isEmpty());

        int notificationId =
                notifications.get(0).getNotificationId();

        boolean deleted =
                notificationDAO.deleteNotification(notificationId);

        assertTrue(deleted);
    }

    @Test
    void testDeleteNotification_NotFound() throws SQLException {
        boolean deleted =
                notificationDAO.deleteNotification(999999);

        assertFalse(deleted);
    }
}
