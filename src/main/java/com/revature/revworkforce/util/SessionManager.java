package com.revature.revworkforce.util;

import com.revature.revworkforce.model.Employee;

/**
 * Session manager to store current logged-in user
 */
public class SessionManager {
    
    private static Employee currentUser;
    private static long lastActivityTime;
    private static final long SESSION_TIMEOUT = 30 * 60 * 1000; // 30 minutes
    
    /**
     * Set current logged-in user
     */
    public static void setCurrentUser(Employee employee) {
        currentUser = employee;
        lastActivityTime = System.currentTimeMillis();
    }
    
    /**
     * Get current logged-in user
     */
    public static Employee getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Update last activity time
     */
    public static void updateActivity() {
        lastActivityTime = System.currentTimeMillis();
    }
    
    /**
     * Check if session is expired
     */
    public static boolean isSessionExpired() {
        if (currentUser == null) return true;
        
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastActivityTime) > SESSION_TIMEOUT;
    }
    
    /**
     * Logout current user
     */
    public static void logout() {
        currentUser = null;
        lastActivityTime = 0;
    }
    
    /**
     * Check if user is logged in
     */
    public static boolean isLoggedIn() {
        return currentUser != null && !isSessionExpired();
    }
}