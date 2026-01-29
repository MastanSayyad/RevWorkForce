package com.revature.revworkforce.service;

import java.time.LocalDate;
import java.util.List;

import com.revature.revworkforce.exception.LeaveException;
import com.revature.revworkforce.exception.ValidationException;
import com.revature.revworkforce.model.Holiday;
import com.revature.revworkforce.model.LeaveApplication;
import com.revature.revworkforce.model.LeaveBalance;
import com.revature.revworkforce.model.LeaveType;

/**
 * Service Interface for Leave Management operations
 */
public interface LeaveService {
    
    /**
     * Apply for leave
     * @param leaveApplication Leave application object
     * @return Leave application ID if successful
     * @throws LeaveException if business rules are violated
     * @throws ValidationException if validation fails
     */
    int applyForLeave(LeaveApplication leaveApplication) throws LeaveException, ValidationException;
    
    /**
     * Cancel a pending leave application
     * @param leaveApplicationId Leave application ID
     * @param employeeId Employee ID (for authorization)
     * @return true if cancelled successfully
     * @throws LeaveException if leave cannot be cancelled
     */
    boolean cancelLeave(int leaveApplicationId, String employeeId) throws LeaveException;
    
    /**
     * Approve leave application (Manager)
     * @param leaveApplicationId Leave application ID
     * @param managerId Manager's employee ID
     * @param comments Manager's comments
     * @return true if approved successfully
     * @throws LeaveException if approval fails
     */
    boolean approveLeave(int leaveApplicationId, String managerId, String comments) 
            throws LeaveException;
    
    /**
     * Reject leave application (Manager)
     * @param leaveApplicationId Leave application ID
     * @param managerId Manager's employee ID
     * @param comments Rejection reason
     * @return true if rejected successfully
     * @throws LeaveException if rejection fails
     */
    boolean rejectLeave(int leaveApplicationId, String managerId, String comments) 
            throws LeaveException;
    
    /**
     * Get leave applications for an employee
     * @param employeeId Employee ID
     * @return List of leave applications
     */
    List<LeaveApplication> getEmployeeLeaveApplications(String employeeId);
    
    /**
     * Get pending leave applications for manager's team
     * @param managerId Manager's employee ID
     * @return List of pending leave applications
     */
    List<LeaveApplication> getPendingLeavesForManager(String managerId);
    
    /**
     * Get leave balances for an employee
     * @param employeeId Employee ID
     * @param year Year
     * @return List of leave balances
     */
    List<LeaveBalance> getLeaveBalances(String employeeId, int year);
    
    /**
     * Get all leave types
     * @return List of leave types
     */
    List<LeaveType> getAllLeaveTypes();
    
    /**
     * Get holidays for a year
     * @param year Year
     * @return List of holidays
     */
    List<Holiday> getHolidays(int year);
    
    /**
     * Assign leave balance to employee (Admin)
     * @param employeeId Employee ID
     * @param year Year
     * @return true if balances assigned successfully
     */
    boolean assignLeaveBalances(String employeeId, int year);
    
    /**
     * Calculate working days between two dates (excluding weekends and holidays)
     * @param startDate Start date
     * @param endDate End date
     * @return Number of working days
     */
    int calculateWorkingDays(LocalDate startDate, LocalDate endDate);
}