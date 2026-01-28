package com.revature.revworkforce.dao;

import java.sql.SQLException;
import java.util.List;

import com.revature.revworkforce.model.Holiday;
import com.revature.revworkforce.model.LeaveApplication;
import com.revature.revworkforce.model.LeaveBalance;
import com.revature.revworkforce.model.LeaveType;

/**
 * DAO Interface for Leave Management operations
 */
public interface LeaveDAO {
    
    // Leave Application Operations
    int applyLeave(LeaveApplication leaveApplication) throws SQLException;
    boolean updateLeaveApplication(LeaveApplication leaveApplication) throws SQLException;
    boolean cancelLeaveApplication(int leaveApplicationId) throws SQLException;
    
    // Manager Operations
    boolean approveLeave(int leaveApplicationId, String managerId, String comments) throws SQLException;
    boolean rejectLeave(int leaveApplicationId, String managerId, String comments) throws SQLException;
    
    // Read Operations
    LeaveApplication getLeaveApplicationById(int leaveApplicationId) throws SQLException;
    List<LeaveApplication> getLeaveApplicationsByEmployee(String employeeId) throws SQLException;
    List<LeaveApplication> getPendingLeavesByEmployee(String employeeId) throws SQLException;
    List<LeaveApplication> getLeaveApplicationsByManager(String managerId) throws SQLException;
    List<LeaveApplication> getPendingLeavesByManager(String managerId) throws SQLException;
    
    // Leave Balance Operations
    List<LeaveBalance> getLeaveBalances(String employeeId, int year) throws SQLException;
    LeaveBalance getLeaveBalance(String employeeId, int leaveTypeId, int year) throws SQLException;
    boolean updateLeaveBalance(LeaveBalance leaveBalance) throws SQLException;
    boolean assignLeaveBalance(String employeeId, int leaveTypeId, int totalAllocated, int year) throws SQLException;
    
    // Leave Type Operations
    List<LeaveType> getAllLeaveTypes() throws SQLException;
    LeaveType getLeaveTypeById(int leaveTypeId) throws SQLException;
    
    // Holiday Operations
    List<Holiday> getHolidaysByYear(int year) throws SQLException;
    boolean addHoliday(Holiday holiday) throws SQLException;
    
    // Utility
    int getAvailableLeaves(String employeeId, int leaveTypeId, int year) throws SQLException;
    boolean hasOverlappingLeaves(String employeeId, java.time.LocalDate startDate, 
                                  java.time.LocalDate endDate) throws SQLException;
}