package com.revature.revworkforce.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.revworkforce.dao.EmployeeDAO;
import com.revature.revworkforce.dao.EmployeeDAOImpl;
import com.revature.revworkforce.exception.AuthenticationException;
import com.revature.revworkforce.model.Employee;
import com.revature.revworkforce.model.Role;
import com.revature.revworkforce.util.PasswordUtil;
import com.revature.revworkforce.util.SessionManager;

/**
 * Implementation of AuthService interface
 */
public class AuthServiceImpl implements AuthService {
    
    private static final Logger logger = LogManager.getLogger(AuthServiceImpl.class);
    private EmployeeDAO employeeDAO;
    private AuditService auditService; // for logger service
    
    public AuthServiceImpl() {
        this.employeeDAO = new EmployeeDAOImpl();
        this.auditService = new AuditServiceImpl(); // for logger
    }
    
    @Override
    public Employee login(String employeeId, String password) throws AuthenticationException {
        logger.info("Login attempt for employee: {}", employeeId);
        
        try {
            // Validate inputs
            if (employeeId == null || employeeId.trim().isEmpty()) {
                throw new AuthenticationException("Employee ID cannot be empty");
            }
            
            if (password == null || password.trim().isEmpty()) {
                throw new AuthenticationException("Password cannot be empty");
            }
            
            // Find employee
            Employee employee = employeeDAO.getEmployeeWithDetails(employeeId);
            
            if (employee == null) {
                logger.warn("Login failed: Employee not found - {}", employeeId);
                // Log failed login attempt - logger
                auditService.logAction(employeeId, "LOGIN_FAILED", "employees", 
                    employeeId, null, "Employee not found");
                throw new AuthenticationException("Invalid employee ID or password");
            }
            
            // Check if employee is active
            if (!employee.isActive()) {
                logger.warn("Login failed: Inactive account - {}", employeeId);
                // Log failed login attempt 
                auditService.logAction(employeeId, "LOGIN_FAILED", "employees", 
                    employeeId, null, "Account inactive");
                throw new AuthenticationException("Account is inactive. Please contact administrator.");
            }
            
            // Verify password
            if (!PasswordUtil.verifyPassword(password, employee.getPasswordHash())) {
                logger.warn("Login failed: Invalid password - {}", employeeId);
                // Log failed login attempt 
                auditService.logAction(employeeId, "LOGIN_FAILED", "employees", 
                    employeeId, null, "Invalid password");
                throw new AuthenticationException("Invalid employee ID or password");
            }
            
            // Set session
            SessionManager.setCurrentUser(employee);
            
            // Log successful login 
            auditService.logAction(employeeId, "LOGIN_SUCCESS", "employees", 
                employeeId, null, "Successful login");
            
            logger.info("Login successful for employee: {}", employeeId);
            return employee;
            
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Login error for employee: {}", employeeId, e);
            throw new AuthenticationException("Authentication service unavailable");
        }
    }
    
    @Override
    public boolean changePassword(String employeeId, String currentPassword, String newPassword) 
            throws AuthenticationException {
        logger.info("Password change attempt for employee: {}", employeeId);
        
        try {
            // Validate inputs
            if (currentPassword == null || currentPassword.trim().isEmpty()) {
                throw new AuthenticationException("Current password cannot be empty");
            }
            
            if (newPassword == null || newPassword.trim().isEmpty()) {
                throw new AuthenticationException("New password cannot be empty");
            }
            
            if (newPassword.length() < 8) {
                throw new AuthenticationException("New password must be at least 8 characters long");
            }
            
            if (currentPassword.equals(newPassword)) {
                throw new AuthenticationException("New password must be different from current password");
            }
            
            // Find employee
            Employee employee = employeeDAO.findByEmployeeId(employeeId);
            
            if (employee == null) {
                throw new AuthenticationException("Employee not found");
            }
            
            // Verify current password
            if (!PasswordUtil.verifyPassword(currentPassword, employee.getPasswordHash())) {
                logger.warn("Password change failed: Invalid current password - {}", employeeId);
                // Log failed password change 
                auditService.logAction(employeeId, "PASSWORD_CHANGE_FAILED", "employees", 
                    employeeId, null, "Invalid current password");
                throw new AuthenticationException("Current password is incorrect");
            }
            
            // Hash new password
            String newPasswordHash = PasswordUtil.hashPassword(newPassword);
            
            // Update password in database
            employee.setPasswordHash(newPasswordHash);
            boolean updated = employeeDAO.updateEmployee(employee);
            
            if (updated) {
                // Log successful password change 
                auditService.logAction(employeeId, "PASSWORD_CHANGED", "employees", 
                    employeeId, "***", "***");
                logger.info("Password changed successfully for employee: {}", employeeId);
                return true;
            }
            
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Password change error for employee: {}", employeeId, e);
            throw new AuthenticationException("Password change service unavailable");
        }
        
        return false;
    }
    
    @Override
    public boolean hasRole(Employee employee, String roleName) {
        if (employee == null || employee.getRoles() == null) {
            return false;
        }
        
        return employee.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(roleName));
    }
    
    @Override
    public void logout() {
        Employee currentUser = SessionManager.getCurrentUser();
        if (currentUser != null) {
            // Log logout 
            auditService.logAction(currentUser.getEmployeeId(), "LOGOUT", "employees", 
                currentUser.getEmployeeId(), null, "User logged out");
            logger.info("Logout: {}", currentUser.getEmployeeId());
            SessionManager.logout();
        }
    }
}