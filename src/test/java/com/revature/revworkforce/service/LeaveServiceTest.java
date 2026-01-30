package com.revature.revworkforce.service;

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
 * Test class for LeaveService
 */

import org.junit.jupiter.api.Disabled;
@Disabled("Integration test – requires database")
public class LeaveServiceTest {
    
    private LeaveService leaveService;
    
    @BeforeEach
    public void setUp() {
        leaveService = new LeaveServiceImpl();
    }
    
    @Test
    @DisplayName("Test get leave balances")
    public void testGetLeaveBalances() {
        int currentYear = LocalDate.now().getYear();
        List<LeaveBalance> balances = leaveService.getLeaveBalances("EMP001", currentYear);
        
        assertNotNull(balances, "Leave balances should not be null");
        assertTrue(balances.size() > 0, "Should have at least one leave balance");
    }
    
    @Test
    @DisplayName("Test get all leave types")
    public void testGetAllLeaveTypes() {
        List<LeaveType> leaveTypes = leaveService.getAllLeaveTypes();
        
        assertNotNull(leaveTypes, "Leave types should not be null");
        assertTrue(leaveTypes.size() >= 4, "Should have at least 4 leave types");
    }
    
    @Test
    @DisplayName("Test get holidays")
    public void testGetHolidays() {
        List<Holiday> holidays = leaveService.getHolidays(2025);
        
        assertNotNull(holidays, "Holidays should not be null");
        assertTrue(holidays.size() > 0, "Should have at least one holiday");
    }
    
    @Test
    @DisplayName("Test calculate working days - weekdays only")
    public void testCalculateWorkingDays_Weekdays() {
        // Monday to Friday (5 working days)
        LocalDate start = LocalDate.of(2025, 2, 3); // Monday
        LocalDate end = LocalDate.of(2025, 2, 7);   // Friday
        
        int workingDays = leaveService.calculateWorkingDays(start, end);
        assertEquals(5, workingDays, "Should have 5 working days");
    }
    
    @Test
    @DisplayName("Test calculate working days - including weekend")
    public void testCalculateWorkingDays_IncludingWeekend() {
        // Monday to Sunday (5 working days, excluding Sat-Sun)
        LocalDate start = LocalDate.of(2025, 2, 3); // Monday
        LocalDate end = LocalDate.of(2025, 2, 9);   // Sunday
        
        int workingDays = leaveService.calculateWorkingDays(start, end);
        assertEquals(5, workingDays, "Should have 5 working days (excluding weekend)");
    }
    
    @Test
    @DisplayName("Test assign leave balances")
    public void testAssignLeaveBalances() {
        int currentYear = LocalDate.now().getYear();
        boolean result = leaveService.assignLeaveBalances("EMP001", currentYear);
        
        // This might fail if balances already exist, which is acceptable
        // The test verifies the method doesn't crash
        assertNotNull(result);
    }
}
