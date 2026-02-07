package com.revature.revworkforce.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for Model classes - Getters/Setters and basic functionality
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
        employee.setAddress("123 Test St");
        employee.setEmergencyContact("Emergency Contact");
        employee.setDateOfBirth(LocalDate.of(1990, 1, 1));
        employee.setDepartmentId(1);
        employee.setDesignationId(1);
        employee.setManagerId("MGR001");
        employee.setJoiningDate(LocalDate.of(2020, 1, 1));
        employee.setSalary(50000.0);
        employee.setActive(true);
        employee.setPasswordHash("hashedpassword");
        
        assertEquals("EMP001", employee.getEmployeeId());
        assertEquals("John", employee.getFirstName());
        assertEquals("Doe", employee.getLastName());
        assertEquals("john.doe@test.com", employee.getEmail());
        assertEquals("1234567890", employee.getPhone());
        assertEquals("123 Test St", employee.getAddress());
        assertEquals("Emergency Contact", employee.getEmergencyContact());
        assertEquals(LocalDate.of(1990, 1, 1), employee.getDateOfBirth());
        assertEquals(1, employee.getDepartmentId());
        assertEquals(1, employee.getDesignationId());
        assertEquals("MGR001", employee.getManagerId());
        assertEquals(LocalDate.of(2020, 1, 1), employee.getJoiningDate());
        assertEquals(50000.0, employee.getSalary());
        assertTrue(employee.isActive());
        assertEquals("hashedpassword", employee.getPasswordHash());
        assertEquals("John Doe", employee.getFullName());
    }
    
    @Test
    @DisplayName("Test Goal model")
    public void testGoalModel() {
        Goal goal = new Goal();
        goal.setGoalId(1);
        goal.setEmployeeId("EMP001");
        goal.setGoalDescription("Test goal description");
        goal.setDeadline(LocalDate.now().plusMonths(3));
        goal.setPriority(Goal.PRIORITY_HIGH);
        goal.setSuccessMetrics("Success metrics");
        goal.setProgressPercentage(50);
        goal.setStatus(Goal.STATUS_IN_PROGRESS);
        goal.setManagerComments("Manager comments");
        goal.setCreatedAt(LocalDateTime.now());
        goal.setUpdatedAt(LocalDateTime.now());
        
        assertEquals(1, goal.getGoalId());
        assertEquals("EMP001", goal.getEmployeeId());
        assertEquals("Test goal description", goal.getGoalDescription());
        assertNotNull(goal.getDeadline());
        assertEquals(Goal.PRIORITY_HIGH, goal.getPriority());
        assertEquals("Success metrics", goal.getSuccessMetrics());
        assertEquals(50, goal.getProgressPercentage());
        assertEquals(Goal.STATUS_IN_PROGRESS, goal.getStatus());
        assertEquals("Manager comments", goal.getManagerComments());
        assertNotNull(goal.getCreatedAt());
        assertNotNull(goal.getUpdatedAt());
        
        // Test constructor
        Goal goal2 = new Goal("EMP002", "Another goal", LocalDate.now(), Goal.PRIORITY_LOW);
        assertEquals("EMP002", goal2.getEmployeeId());
        assertEquals("Another goal", goal2.getGoalDescription());
        assertEquals(Goal.PRIORITY_LOW, goal2.getPriority());
        assertEquals(0, goal2.getProgressPercentage());
        assertEquals(Goal.STATUS_NOT_STARTED, goal2.getStatus());
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
        leave.setReason("Personal reasons");
        leave.setStatus(LeaveApplication.STATUS_PENDING);
        leave.setReviewedBy("MGR001");
        leave.setReviewedBy("Review comments");
        leave.setCreatedAt(LocalDateTime.now());
        leave.setUpdatedAt(LocalDateTime.now());
        
        assertEquals(1, leave.getLeaveApplicationId());
        assertEquals("EMP001", leave.getEmployeeId());
        assertEquals(1, leave.getLeaveTypeId());
        assertNotNull(leave.getStartDate());
        assertNotNull(leave.getEndDate());
        assertEquals(3, leave.getTotalDays());
        assertEquals("Personal reasons", leave.getReason());
        assertEquals(LeaveApplication.STATUS_PENDING, leave.getStatus());
        assertEquals("Review comments", leave.getReviewedBy());
        assertNotNull(leave.getCreatedAt());
        assertNotNull(leave.getUpdatedAt());
    }
    
    @Test
    @DisplayName("Test PerformanceReview model")
    public void testPerformanceReviewModel() {
        PerformanceReview review = new PerformanceReview();
        review.setReviewId(1);
        review.setEmployeeId("EMP001");
        review.setReviewYear(2025);
        review.setKeyDeliverables("Key deliverables");
        review.setMajorAccomplishments("Major accomplishments");
        review.setAreasOfImprovement("Areas of improvement");
        review.setSelfAssessmentRating(4.0);
        review.setManagerFeedback("Manager feedback");
        review.setManagerRating(4.5);
        review.setStatus(PerformanceReview.STATUS_REVIEWED);
        review.setSubmittedDate(LocalDateTime.now());
        review.setReviewedDate(LocalDateTime.now());
        review.setReviewedBy("MGR001");
        review.setCreatedAt(LocalDateTime.now());
        review.setUpdatedAt(LocalDateTime.now());
        
        assertEquals(1, review.getReviewId());
        assertEquals("EMP001", review.getEmployeeId());
        assertEquals(2025, review.getReviewYear());
        assertEquals("Key deliverables", review.getKeyDeliverables());
        assertEquals("Major accomplishments", review.getMajorAccomplishments());
        assertEquals("Areas of improvement", review.getAreasOfImprovement());
        assertEquals(4.0, review.getSelfAssessmentRating());
        assertEquals("Manager feedback", review.getManagerFeedback());
        assertEquals(4.5, review.getManagerRating());
        assertEquals(PerformanceReview.STATUS_REVIEWED, review.getStatus());
        assertNotNull(review.getSubmittedDate());
        assertNotNull(review.getReviewedDate());
        assertEquals("MGR001", review.getReviewedBy());
        assertNotNull(review.getCreatedAt());
        assertNotNull(review.getUpdatedAt());
        
        // Test constructor
        PerformanceReview review2 = new PerformanceReview("EMP002", 2024);
        assertEquals("EMP002", review2.getEmployeeId());
        assertEquals(2024, review2.getReviewYear());
        assertEquals(PerformanceReview.STATUS_DRAFT, review2.getStatus());
    }
    
    @Test
    @DisplayName("Test Notification model")
    public void testNotificationModel() {
        Notification notification = new Notification("EMP001", Notification.TYPE_LEAVE, "Test message");
        
        assertEquals("EMP001", notification.getEmployeeId());
        assertEquals(Notification.TYPE_LEAVE, notification.getNotificationType());
        assertEquals("Test message", notification.getMessage());
        assertFalse(notification.isRead());
        
        notification.setNotificationId(1);
        notification.setRead(true);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setReadAt(LocalDateTime.now());
        
        assertEquals(1, notification.getNotificationId());
        assertTrue(notification.isRead());
        assertNotNull(notification.getCreatedAt());
        assertNotNull(notification.getReadAt());
    }
    
    @Test
    @DisplayName("Test Department model")
    public void testDepartmentModel() {
        Department dept = new Department();
        dept.setDepartmentId(1);
        dept.setDepartmentName("IT");
        dept.setCreatedAt(LocalDateTime.now());
        
        assertEquals(1, dept.getDepartmentId());
        assertEquals("IT", dept.getDepartmentName());
        assertNotNull(dept.getCreatedAt());
    }
    
    @Test
    @DisplayName("Test Designation model")
    public void testDesignationModel() {
        Designation desig = new Designation();
        desig.setDesignationId(1);
        desig.setDesignationName("Software Engineer");
        desig.setCreatedAt(LocalDateTime.now());
        
        assertEquals(1, desig.getDesignationId());
        assertEquals("Software Engineer", desig.getDesignationName());
        assertNotNull(desig.getCreatedAt());
    }
    
    @Test
    @DisplayName("Test LeaveBalance model")
    public void testLeaveBalanceModel() {
        LeaveBalance balance = new LeaveBalance();
        balance.setEmployeeId("EMP001");
        balance.setLeaveTypeId(1);
        balance.setYear(2025);
        balance.setTotalAllocated(12);
        balance.setUsedLeaves(3);
        balance.setAvailableLeaves(9);
        balance.setCreatedAt(LocalDateTime.now());
        balance.setUpdatedAt(LocalDateTime.now());
        
        assertEquals("EMP001", balance.getEmployeeId());
        assertEquals(1, balance.getLeaveTypeId());
        assertEquals(2025, balance.getYear());
        assertEquals(12, balance.getTotalAllocated());
        assertEquals(3, balance.getUsedLeaves());
        assertEquals(9, balance.getAvailableLeaves());
        assertNotNull(balance.getCreatedAt());
        assertNotNull(balance.getUpdatedAt());
    }
    
    @Test
    @DisplayName("Test LeaveType model")
    public void testLeaveTypeModel() {
        LeaveType leaveType = new LeaveType();
        leaveType.setLeaveTypeId(1);
        leaveType.setLeaveTypeName("CASUAL");
        leaveType.setDescription("Casual Leave");
        leaveType.setCreatedAt(LocalDateTime.now());
        
        assertEquals(1, leaveType.getLeaveTypeId());
        assertEquals("CASUAL", leaveType.getLeaveTypeName());
        assertEquals("Casual Leave", leaveType.getDescription());
        assertNotNull(leaveType.getCreatedAt());
    }
    
    @Test
    @DisplayName("Test Holiday model")
    public void testHolidayModel() {
        Holiday holiday = new Holiday();
        holiday.setHolidayId(1);
        holiday.setHolidayName("New Year");
        holiday.setHolidayDate(LocalDate.of(2025, 1, 1));
        holiday.setYear(2025);
        holiday.setCreatedAt(LocalDateTime.now());
        
        assertEquals(1, holiday.getHolidayId());
        assertEquals("New Year", holiday.getHolidayName());
        assertEquals(LocalDate.of(2025, 1, 1), holiday.getHolidayDate());
        assertEquals(2025, holiday.getYear());
        assertNotNull(holiday.getCreatedAt());
    }
    
    @Test
    @DisplayName("Test Role model")
    public void testRoleModel() {
        Role role = new Role();
        role.setRoleId(1);
        role.setRoleName("EMPLOYEE");
        role.setCreatedAt(LocalDateTime.now());
        
        assertEquals(1, role.getRoleId());
        assertEquals("EMPLOYEE", role.getRoleName());
        assertNotNull(role.getCreatedAt());
    }
    
    @Test
    @DisplayName("Test Announcement model")
    public void testAnnouncementModel() {
        Announcement announcement = new Announcement("Test Title", "Test Content", "ADM001");
        
        assertEquals("Test Title", announcement.getTitle());
        assertEquals("Test Content", announcement.getContent());
        assertEquals("ADM001", announcement.getPostedBy());
        
        announcement.setAnnouncementId(1);
        announcement.setPostedDate(LocalDateTime.now());
        announcement.setCreatedAt(LocalDateTime.now());
        announcement.setPosterName("Admin User");
        
        assertEquals(1, announcement.getAnnouncementId());
        assertNotNull(announcement.getPostedDate());
        assertNotNull(announcement.getCreatedAt());
        assertEquals("Admin User", announcement.getPosterName());
    }
    
    @Test
    @DisplayName("Test AuditLog model")
    public void testAuditLogModel() {
        AuditLog log = new AuditLog("EMP001", "LOGIN", "employees", "EMP001");
        
        assertEquals("EMP001", log.getEmployeeId());
        assertEquals("LOGIN", log.getAction());
        assertEquals("employees", log.getTableName());
        assertEquals("EMP001", log.getRecordId());
        
        log.setLogId(1);
        log.setOldValue("old");
        log.setNewValue("new");
        log.setIpAddress("127.0.0.1");
        log.setCreatedAt(LocalDateTime.now());
        
        assertEquals(1, log.getLogId());
        assertEquals("old", log.getOldValue());
        assertEquals("new", log.getNewValue());
        assertEquals("127.0.0.1", log.getIpAddress());
        assertNotNull(log.getCreatedAt());
    }
}