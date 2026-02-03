package com.revature.revworkforce.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.revature.revworkforce.model.Holiday;
import com.revature.revworkforce.model.LeaveBalance;
import com.revature.revworkforce.model.LeaveType;

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
    @DisplayName("Test has overlapping leaves")
    public void testHasOverlappingLeaves() {
        try {
            LocalDate start = LocalDate.now().plusDays(5);
            LocalDate end = LocalDate.now().plusDays(7);
            
            boolean hasOverlap = leaveDAO.hasOverlappingLeaves("EMP001", start, end);
            // Result can be true or false, we're testing it doesn't crash
            assertNotNull(hasOverlap);
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
}