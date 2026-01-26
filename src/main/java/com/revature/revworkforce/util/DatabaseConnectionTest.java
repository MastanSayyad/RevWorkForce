package com.revature.revworkforce.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnectionTest {
    
    public static void main(String[] args){
        System.out.println("Testing database connection...");
        
        try (Connection conn = DatabaseUtil.getConnection()) {
            System.out.println("Connection successful!");
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS emp_count FROM employees");
            
            if (rs.next()) {
                System.out.println("Total employees in database: " + rs.getInt("emp_count"));
            }
            
            rs.close();
            stmt.close();
            
            System.out.println("Database test completed successfully! ^o^");
            
        } catch (Exception e) {
            System.err.println("Database connection failed! T_T");
            e.printStackTrace();
        }
    }
}