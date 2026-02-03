package com.revature.revworkforce.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for Model classes - Getters/Setters
 */
public class ModelTest {
    
    @Test
    @DisplayName("Test Employee model")
    public void testEmployeeModel() {
        Employee employee = new Employee();
        employee.setEmployeeId("EMP001");
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@test.com");
        employee.setPhone("1234567890");
        employee.setActive(true);
        
        assertEquals("EMP001", employee.getEmployeeId());
        assertEquals("John", employee.getFirstName());
        assertEquals("Doe", employee.getLastName());
        assertEquals("john.doe@test.com", employee.getEmail());
        assertEquals("1234567890", employee.getPhone());
        assertTrue(employee.isActive());
        assertEquals("John Doe", employee.getFullName());
    }
    
    @Test
    @DisplayName("Test Goal model")
    public void testGoalModel() {
        Goal goal = new Goal();
        goal.setGoalId(1);
        goal.setEmployeeId("EMP001");
        goal.setGoalDescription("Test goal");
        goal.setDeadline(LocalDate.now().plusMonths(3));
        goal.setPriority(Goal.PRIORITY_HIGH);
        goal.setProgressPercentage(50);
        goal.setStatus(Goal.STATUS_IN_PROGRESS);
        
        assertEquals(1, goal.getGoalId());
        assertEquals("EMP001", goal.getEmployeeId());
        assertEquals("Test goal", goal.getGoalDescription());
        assertEquals(Goal.PRIORITY_HIGH, goal.getPriority());
        assertEquals(50, goal.getProgressPercentage());
        assertEquals(Goal.STATUS_IN_PROGRESS, goal.getStatus());
    }
    
    @Test
    @DisplayName("Test LeaveApplication model")
    public void testLeaveApplicationModel() {
        LeaveApplication leave = new LeaveApplication();
        leave.setLeaveApplicationId(1);
        leave.setEmployeeId("EMP001");
        leave.setLeaveTypeId(1);
        leave.setStartDate(LocalDate.now());
        leave.setEndDate(LocalDate.now().plusDays(3));
        leave.setTotalDays(3);
        leave.setReason("Personal");
        leave.setStatus(LeaveApplication.STATUS_PENDING);
        
        assertEquals(1, leave.getLeaveApplicationId());
        assertEquals("EMP001", leave.getEmployeeId());
        assertEquals(3, leave.getTotalDays());
        assertEquals("Personal", leave.getReason());
        assertEquals(LeaveApplication.STATUS_PENDING, leave.getStatus());
    }
    
    @Test
    @DisplayName("Test PerformanceReview model")
    public void testPerformanceReviewModel() {
        PerformanceReview review = new PerformanceReview();
        review.setReviewId(1);
        review.setEmployeeId("EMP001");
        review.setReviewYear(2025);
        review.setSelfAssessmentRating(4.0);
        review.setManagerRating(4.5);
        review.setStatus(PerformanceReview.STATUS_REVIEWED);
        
        assertEquals(1, review.getReviewId());
        assertEquals("EMP001", review.getEmployeeId());
        assertEquals(2025, review.getReviewYear());
        assertEquals(4.0, review.getSelfAssessmentRating());
        assertEquals(4.5, review.getManagerRating());
        assertEquals(PerformanceReview.STATUS_REVIEWED, review.getStatus());
    }
    
    @Test
    @DisplayName("Test Notification model")
    public void testNotificationModel() {
        Notification notification = new Notification("EMP001", "LEAVE", "Test message");
        
        assertEquals("EMP001", notification.getEmployeeId());
        assertEquals("LEAVE", notification.getNotificationType());
        assertEquals("Test message", notification.getMessage());
        assertFalse(notification.isRead());
        
        notification.setRead(true);
        assertTrue(notification.isRead());
    }
    
    @Test
    @DisplayName("Test Department model")
    public void testDepartmentModel() {
        Department dept = new Department();
        dept.setDepartmentId(1);
        dept.setDepartmentName("IT");
        
        assertEquals(1, dept.getDepartmentId());
        assertEquals("IT", dept.getDepartmentName());
    }
    
    @Test
    @DisplayName("Test LeaveBalance model")
    public void testLeaveBalanceModel() {
        LeaveBalance balance = new LeaveBalance();
        balance.setEmployeeId("EMP001");
        balance.setLeaveTypeId(1);
        balance.setTotalAllocated(12);
        balance.setUsedLeaves(3);
        balance.setAvailableLeaves(9);
        
        assertEquals("EMP001", balance.getEmployeeId());
        assertEquals(12, balance.getTotalAllocated());
        assertEquals(3, balance.getUsedLeaves());
        assertEquals(9, balance.getAvailableLeaves());
    }
}