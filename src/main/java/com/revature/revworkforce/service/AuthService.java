package com.revature.revworkforce.service;

import com.revature.revworkforce.exception.AuthenticationException;
import com.revature.revworkforce.model.Employee;

/**
 * Service Interface for Authentication operations
 */
public interface AuthService {
    
    /**
     * Authenticate user with employee ID and password
     * @param employeeId Employee ID
     * @param password Plain text password
     * @return Authenticated Employee object with roles
     * @throws AuthenticationException if authentication fails
     */
    Employee login(String employeeId, String password) throws AuthenticationException;
    
    /**
     * Change password for an employee
     * @param employeeId Employee ID
     * @param currentPassword Current password
     * @param newPassword New password
     * @return true if password changed successfully
     * @throws AuthenticationException if current password is incorrect
     */
    boolean changePassword(String employeeId, String currentPassword, String newPassword) 
            throws AuthenticationException;
    
    /**
     * Check if user has a specific role
     * @param employee Employee object
     * @param roleName Role name to check
     * @return true if employee has the role
     */
    boolean hasRole(Employee employee, String roleName);
    
    /**
     * Logout user
     */
    void logout();
}