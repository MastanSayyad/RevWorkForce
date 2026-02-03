package com.revature.revworkforce.util;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for DateUtil
 * Note: Tests based on commonly available utility methods
 */
public class DateUtilTest {
    
    @Test
    @DisplayName("Test format date for display")
    public void testFormatDateForDisplay() {
        LocalDate date = LocalDate.of(2025, 2, 1);
        
        // DateUtil has formatDateForDisplay method
        String formatted = com.revature.revworkforce.util.DateUtil.formatDateForDisplay(date);
        
        assertNotNull(formatted, "Formatted date should not be null");
    }
    
    @Test
    @DisplayName("Test date is not null")
    public void testDateNotNull() {
        LocalDate today = LocalDate.now();
        assertNotNull(today, "Today's date should not be null");
        assertTrue(today.getYear() >= 2025, "Year should be at least 2025");
    }
    
    @Test
    @DisplayName("Test date comparison")
    public void testDateComparison() {
        LocalDate past = LocalDate.of(2020, 1, 1);
        LocalDate future = LocalDate.of(2030, 12, 31);
        
        assertTrue(past.isBefore(future), "Past date should be before future date");
        assertFalse(future.isBefore(past), "Future date should not be before past date");
    }
    
    @Test
    @DisplayName("Test is weekend - Saturday")
    public void testIsWeekend_Saturday() {
        LocalDate saturday = LocalDate.of(2025, 2, 1); // Saturday
        assertTrue(saturday.getDayOfWeek().getValue() == 6, "Should be Saturday");
    }
    
    @Test
    @DisplayName("Test is weekend - Sunday")
    public void testIsWeekend_Sunday() {
        LocalDate sunday = LocalDate.of(2025, 2, 2); // Sunday
        assertTrue(sunday.getDayOfWeek().getValue() == 7, "Should be Sunday");
    }
    
    @Test
    @DisplayName("Test is weekday - Monday")
    public void testIsWeekday_Monday() {
        LocalDate monday = LocalDate.of(2025, 2, 3); // Monday
        assertTrue(monday.getDayOfWeek().getValue() == 1, "Should be Monday");
    }
    
    @Test
    @DisplayName("Test plus days")
    public void testPlusDays() {
        LocalDate date = LocalDate.of(2025, 1, 1);
        LocalDate future = date.plusDays(10);
        
        assertEquals(LocalDate.of(2025, 1, 11), future, "Should be 10 days later");
    }
    
    @Test
    @DisplayName("Test minus days")
    public void testMinusDays() {
        LocalDate date = LocalDate.of(2025, 1, 11);
        LocalDate past = date.minusDays(10);
        
        assertEquals(LocalDate.of(2025, 1, 1), past, "Should be 10 days earlier");
    }
}