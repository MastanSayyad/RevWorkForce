package com.revature.revworkforce.dao;

import java.sql.SQLException;
import java.util.List;

import com.revature.revworkforce.model.PerformanceReview;

/**
 * DAO Interface for Performance Review operations
 */
public interface PerformanceDAO {
    
    // Performance Review Operations
    int createPerformanceReview(PerformanceReview review) throws SQLException;
    boolean updatePerformanceReview(PerformanceReview review) throws SQLException;
    boolean submitPerformanceReview(int reviewId) throws SQLException;
    boolean addManagerFeedback(int reviewId, String managerId, String feedback, 
                               double rating) throws SQLException;
    
    // Read Operations
    PerformanceReview getPerformanceReviewById(int reviewId) throws SQLException;
    List<PerformanceReview> getReviewsByEmployee(String employeeId) throws SQLException;
    PerformanceReview getReviewByEmployeeAndYear(String employeeId, int year) throws SQLException;
    List<PerformanceReview> getPendingReviewsByManager(String managerId) throws SQLException;
    List<PerformanceReview> getReviewedByManager(String managerId) throws SQLException;
    
    // Delete Operation
    boolean deletePerformanceReview(int reviewId) throws SQLException;
}