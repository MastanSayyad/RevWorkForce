package com.revature.revworkforce.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.revworkforce.dao.LeaveDAO;
import com.revature.revworkforce.dao.LeaveDAOImpl;
import com.revature.revworkforce.exception.LeaveException;
import com.revature.revworkforce.exception.ValidationException;
import com.revature.revworkforce.model.Holiday;
import com.revature.revworkforce.model.LeaveApplication;
import com.revature.revworkforce.model.LeaveBalance;
import com.revature.revworkforce.model.LeaveType;
import com.revature.revworkforce.util.DateUtil;

/**
 * Implementation of LeaveService interface
 */
public class LeaveServiceImpl implements LeaveService {
    
    private static final Logger logger = LogManager.getLogger(LeaveServiceImpl.class);
    private LeaveDAO leaveDAO;
    private NotificationService notificationService;
    
    public LeaveServiceImpl() {
        this.leaveDAO = new LeaveDAOImpl();
        this.notificationService = new NotificationServiceImpl();
    }
    
    @Override
    public int applyForLeave(LeaveApplication leaveApplication) 
            throws LeaveException, ValidationException {
        logger.info("Processing leave application for employee: {}", 
                   leaveApplication.getEmployeeId());
        
        try {
            // Validate leave application
            validateLeaveApplication(leaveApplication);
            
            // Check for overlapping leaves
            if (leaveDAO.hasOverlappingLeaves(leaveApplication.getEmployeeId(), 
                    leaveApplication.getStartDate(), leaveApplication.getEndDate())) {
                throw new LeaveException("Leave dates overlap with existing leave application");
            }
            
            // Calculate working days
            int totalDays = calculateWorkingDays(leaveApplication.getStartDate(), 
                                                 leaveApplication.getEndDate());
            leaveApplication.setTotalDays(totalDays);
            
            // Check leave balance
            int currentYear = LocalDate.now().getYear();
            int availableLeaves = leaveDAO.getAvailableLeaves(
                leaveApplication.getEmployeeId(), 
                leaveApplication.getLeaveTypeId(), 
                currentYear);
            
            if (totalDays > availableLeaves) {
                throw new LeaveException("Insufficient leave balance. Available: " + 
                                       availableLeaves + " days, Requested: " + totalDays + " days");
            }
            
            // Apply for leave
            int leaveApplicationId = leaveDAO.applyLeave(leaveApplication);
            
            if (leaveApplicationId > 0) {
                // Create notification for manager
                // Note: In a real scenario, you'd get the manager ID from employee data
                notificationService.createNotification(
                    leaveApplication.getEmployeeId(), 
                    "LEAVE", 
                    "Your leave application has been submitted and is pending approval."
                );
                
                logger.info("Leave application created: {}", leaveApplicationId);
                return leaveApplicationId;
            }
            
        } catch (LeaveException | ValidationException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error applying for leave", e);
            throw new LeaveException("Failed to apply for leave: " + e.getMessage());
        }
        
        return -1;
    }
    
    @Override
    public boolean cancelLeave(int leaveApplicationId, String employeeId) throws LeaveException {
        logger.info("Cancelling leave application: {}", leaveApplicationId);
        
        try {
            // Get leave application
            LeaveApplication leave = leaveDAO.getLeaveApplicationById(leaveApplicationId);
            
            if (leave == null) {
                throw new LeaveException("Leave application not found");
            }
            
            // Check authorization
            if (!leave.getEmployeeId().equals(employeeId)) {
                throw new LeaveException("Unauthorized to cancel this leave application");
            }
            
            // Check if leave is pending
            if (!LeaveApplication.STATUS_PENDING.equals(leave.getStatus())) {
                throw new LeaveException("Only pending leave applications can be cancelled");
            }
            
            // Cancel leave
            boolean cancelled = leaveDAO.cancelLeaveApplication(leaveApplicationId);
            
            if (cancelled) {
                logger.info("Leave application cancelled: {}", leaveApplicationId);
                return true;
            }
            
        } catch (LeaveException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error cancelling leave", e);
            throw new LeaveException("Failed to cancel leave: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean approveLeave(int leaveApplicationId, String managerId, String comments) 
            throws LeaveException {
        logger.info("Approving leave application: {}", leaveApplicationId);
        
        try {
            // Get leave application
            LeaveApplication leave = leaveDAO.getLeaveApplicationById(leaveApplicationId);
            
            if (leave == null) {
                throw new LeaveException("Leave application not found");
            }
            
            // Check if leave is pending
            if (!LeaveApplication.STATUS_PENDING.equals(leave.getStatus())) {
                throw new LeaveException("Only pending leave applications can be approved");
            }
            
            // Approve leave (this also updates leave balance)
            boolean approved = leaveDAO.approveLeave(leaveApplicationId, managerId, comments);
            
            if (approved) {
                // Create notification for employee
                notificationService.createNotification(
                    leave.getEmployeeId(), 
                    "LEAVE", 
                    "Your leave application from " + 
                    DateUtil.formatDateForDisplay(leave.getStartDate()) + " to " + 
                    DateUtil.formatDateForDisplay(leave.getEndDate()) + 
                    " has been APPROVED."
                );
                
                logger.info("Leave application approved: {}", leaveApplicationId);
                return true;
            }
            
        } catch (LeaveException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error approving leave", e);
            throw new LeaveException("Failed to approve leave: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean rejectLeave(int leaveApplicationId, String managerId, String comments) 
            throws LeaveException {
        logger.info("Rejecting leave application: {}", leaveApplicationId);
        
        try {
            // Get leave application
            LeaveApplication leave = leaveDAO.getLeaveApplicationById(leaveApplicationId);
            
            if (leave == null) {
                throw new LeaveException("Leave application not found");
            }
            
            // Check if leave is pending
            if (!LeaveApplication.STATUS_PENDING.equals(leave.getStatus())) {
                throw new LeaveException("Only pending leave applications can be rejected");
            }
            
            // Validate comments
            if (comments == null || comments.trim().isEmpty()) {
                throw new LeaveException("Rejection reason is required");
            }
            
            // Reject leave
            boolean rejected = leaveDAO.rejectLeave(leaveApplicationId, managerId, comments);
            
            if (rejected) {
                // Create notification for employee
                notificationService.createNotification(
                    leave.getEmployeeId(), 
                    "LEAVE", 
                    "Your leave application from " + 
                    DateUtil.formatDateForDisplay(leave.getStartDate()) + " to " + 
                    DateUtil.formatDateForDisplay(leave.getEndDate()) + 
                    " has been REJECTED. Reason: " + comments
                );
                
                logger.info("Leave application rejected: {}", leaveApplicationId);
                return true;
            }
            
        } catch (LeaveException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error rejecting leave", e);
            throw new LeaveException("Failed to reject leave: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public List<LeaveApplication> getEmployeeLeaveApplications(String employeeId) {
        try {
            return leaveDAO.getLeaveApplicationsByEmployee(employeeId);
        } catch (Exception e) {
            logger.error("Error getting employee leave applications", e);
            return null;
        }
    }
    
    @Override
    public List<LeaveApplication> getPendingLeavesForManager(String managerId) {
        try {
            return leaveDAO.getPendingLeavesByManager(managerId);
        } catch (Exception e) {
            logger.error("Error getting pending leaves for manager", e);
            return null;
        }
    }
    
    @Override
    public List<LeaveBalance> getLeaveBalances(String employeeId, int year) {
        try {
            return leaveDAO.getLeaveBalances(employeeId, year);
        } catch (Exception e) {
            logger.error("Error getting leave balances", e);
            return null;
        }
    }
    
    @Override
    public List<LeaveType> getAllLeaveTypes() {
        try {
            return leaveDAO.getAllLeaveTypes();
        } catch (Exception e) {
            logger.error("Error getting leave types", e);
            return null;
        }
    }
    
    @Override
    public List<Holiday> getHolidays(int year) {
        try {
            return leaveDAO.getHolidaysByYear(year);
        } catch (Exception e) {
            logger.error("Error getting holidays", e);
            return null;
        }
    }
    
    @Override
    public boolean assignLeaveBalances(String employeeId, int year) {
        logger.info("Assigning leave balances for employee: {} year: {}", employeeId, year);
        
        try {
            // Get all leave types
            List<LeaveType> leaveTypes = leaveDAO.getAllLeaveTypes();
            
            // Default leave allocations
            for (LeaveType type : leaveTypes) {
                int allocation = 0;
                
                switch (type.getLeaveTypeName()) {
                    case "CASUAL":
                        allocation = 12;
                        break;
                    case "SICK":
                        allocation = 10;
                        break;
                    case "PAID":
                        allocation = 15;
                        break;
                    case "PRIVILEGE":
                        allocation = 5;
                        break;
                    default:
                        allocation = 10;
                }
                
                leaveDAO.assignLeaveBalance(employeeId, type.getLeaveTypeId(), allocation, year);
            }
            
            logger.info("Leave balances assigned successfully");
            return true;
            
        } catch (Exception e) {
            logger.error("Error assigning leave balances", e);
            return false;
        }
    }
    
    @Override
    public int calculateWorkingDays(LocalDate startDate, LocalDate endDate) {
        int workingDays = 0;
        LocalDate date = startDate;
        
        try {
            // Get holidays for the year
            List<Holiday> holidays = leaveDAO.getHolidaysByYear(startDate.getYear());
            
            while (!date.isAfter(endDate)) {
                // Check if it's a weekday
                DayOfWeek dayOfWeek = date.getDayOfWeek();
                boolean isWeekend = (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY);
                
                // Check if it's a holiday
                final LocalDate currentDate = date;
                boolean isHoliday = holidays.stream()
                    .anyMatch(h -> h.getHolidayDate().equals(currentDate));
                
                // Count only if it's not weekend and not holiday
                if (!isWeekend && !isHoliday) {
                    workingDays++;
                }
                
                date = date.plusDays(1);
            }
            
        } catch (Exception e) {
            logger.error("Error calculating working days", e);
            // Fallback to simple calculation
            workingDays = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
        }
        
        return workingDays;
    }
    
    /**
     * Validate leave application
     */
    private void validateLeaveApplication(LeaveApplication leaveApplication) 
            throws ValidationException {
        
        if (leaveApplication.getEmployeeId() == null || leaveApplication.getEmployeeId().isEmpty()) {
            throw new ValidationException("Employee ID cannot be empty");
        }
        
        if (leaveApplication.getLeaveTypeId() <= 0) {
            throw new ValidationException("Leave type must be selected");
        }
        
        if (leaveApplication.getStartDate() == null) {
            throw new ValidationException("Start date cannot be empty");
        }
        
        if (leaveApplication.getEndDate() == null) {
            throw new ValidationException("End date cannot be empty");
        }
        
        if (leaveApplication.getStartDate().isAfter(leaveApplication.getEndDate())) {
            throw new ValidationException("Start date cannot be after end date");
        }
        
        if (leaveApplication.getStartDate().isBefore(LocalDate.now())) {
            throw new ValidationException("Cannot apply for leave in the past");
        }
        
        if (leaveApplication.getReason() == null || leaveApplication.getReason().trim().isEmpty()) {
            throw new ValidationException("Leave reason cannot be empty");
        }
        
        if (leaveApplication.getReason().length() > 500) {
            throw new ValidationException("Leave reason is too long (max 500 characters)");
        }
    }
}