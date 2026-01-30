package com.revature.revworkforce.util;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for DatabaseUtil
 */

import org.junit.jupiter.api.Disabled;

@Disabled("Requires real database connection")
public class DatabaseUtilTest {
    
    @Test
    @DisplayName("Test database connection")
    public void testGetConnection() {
        try (Connection conn = DatabaseUtil.getConnection()) {
            assertNotNull(conn, "Connection should not be null");
            assertFalse(conn.isClosed(), "Connection should be open");
        } catch (Exception e) {
            fail("Database connection failed: " + e.getMessage());
        }
    }
    
    @Test
    @DisplayName("Test database query execution")
    public void testDatabaseQuery() {
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM employees")) {
            
            assertTrue(rs.next(), "Result set should have data");
            int count = rs.getInt(1);
            assertTrue(count >= 0, "Employee count should be non-negative");
            
        } catch (Exception e) {
            fail("Database query failed: " + e.getMessage());
        }
    }
}