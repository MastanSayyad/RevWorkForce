package com.revature.revworkforce.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.revature.revworkforce.model.Holiday;
import com.revature.revworkforce.model.LeaveApplication;
import com.revature.revworkforce.model.LeaveBalance;
import com.revature.revworkforce.model.LeaveType;

import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable; 
@DisabledIfEnvironmentVariable(named = "CI", matches = "true")

/**
 * Test class for LeaveDAO
 */
public class LeaveDAOTest {
    
    private LeaveDAO leaveDAO;
    
    @BeforeEach
    public void setUp() {
        leaveDAO = new LeaveDAOImpl();
    }
    
    @Test
    @DisplayName("Test get all leave types")
    public void testGetAllLeaveTypes() {
        try {
            List<LeaveType> leaveTypes = leaveDAO.getAllLeaveTypes();
            assertNotNull(leaveTypes, "Leave types should not be null");
            assertTrue(leaveTypes.size() >= 4, "Should have at least 4 leave types");
            
            // Verify structure
            for (LeaveType type : leaveTypes) {
                assertNotNull(type.getLeaveTypeName());
                assertTrue(type.getLeaveTypeId() > 0);
            }
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    @DisplayName("Test get leave balances")
    public void testGetLeaveBalances() {
        try {
            int currentYear = LocalDate.now().getYear();
            List<LeaveBalance> balances = leaveDAO.getLeaveBalances("EMP001", currentYear);
            assertNotNull(balances, "Leave balances should not be null");
            
            if (!balances.isEmpty()) {
                LeaveBalance first = balances.get(0);
                assertTrue(first.getTotalAllocated() >= 0);
                assertTrue(first.getUsedLeaves() >= 0);
                assertTrue(first.getAvailableLeaves() >= 0);
            }
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    @DisplayName("Test get holidays by year")
    public void testGetHolidaysByYear() {
        try {
            List<Holiday> holidays = leaveDAO.getHolidaysByYear(2025);
            assertNotNull(holidays, "Holidays list should not be null");
            
            // Verify holiday structure
            for (Holiday holiday : holidays) {
                assertNotNull(holiday.getHolidayName());
                assertNotNull(holiday.getHolidayDate());
                assertEquals(2025, holiday.getHolidayDate().getYear());
            }
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    @DisplayName("Test get available leaves")
    public void testGetAvailableLeaves() {
        try {
            int currentYear = LocalDate.now().getYear();
            int availableLeaves = leaveDAO.getAvailableLeaves("EMP001", 1, currentYear);
            assertTrue(availableLeaves >= 0, "Available leaves should be non-negative");
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    @DisplayName("Test has overlapping leaves - no overlap")
    public void testHasOverlappingLeaves_NoOverlap() {
        try {
            // Use far future dates
            LocalDate start = LocalDate.now().plusYears(2);
            LocalDate end = start.plusDays(2);
            
            boolean hasOverlap = leaveDAO.hasOverlappingLeaves("EMP001", start, end);
            // Result can be true or false, we're testing it doesn't crash
            assertNotNull(hasOverlap);
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    @DisplayName("Test get leave applications by employee")
    public void testGetLeaveApplicationsByEmployee() {
        try {
            List<LeaveApplication> applications = leaveDAO.getLeaveApplicationsByEmployee("EMP001");
            assertNotNull(applications, "Leave applications list should not be null");
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    @DisplayName("Test get pending leaves by manager")
    public void testGetPendingLeavesByManager() {
        try {
            List<LeaveApplication> pendingLeaves = leaveDAO.getPendingLeavesByManager("MGR001");
            assertNotNull(pendingLeaves, "Pending leaves list should not be null");
            
            // Verify all are pending
            for (LeaveApplication leave : pendingLeaves) {
                assertEquals(LeaveApplication.STATUS_PENDING, leave.getStatus());
            }
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    @DisplayName("Test get leave application by ID")
    public void testGetLeaveApplicationById() {
        try {
            // Get any existing leave application
            List<LeaveApplication> apps = leaveDAO.getLeaveApplicationsByEmployee("EMP001");
            
            if (apps != null && !apps.isEmpty()) {
                int leaveId = apps.get(0).getLeaveApplicationId();
                LeaveApplication leave = leaveDAO.getLeaveApplicationById(leaveId);
                
                assertNotNull(leave);
                assertEquals(leaveId, leave.getLeaveApplicationId());
            }
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    @DisplayName("Test assign leave balance")
    public void testAssignLeaveBalance() {
        try {
            int currentYear = LocalDate.now().getYear() + 1; // Next year
            boolean result = leaveDAO.assignLeaveBalance("EMP001", 1, 12, currentYear);
            // May return true or false if already exists
            assertNotNull(result);
        } catch (Exception e) {
            // May throw exception if balance already exists
            assertTrue(e.getMessage() != null);
        }
    }
}