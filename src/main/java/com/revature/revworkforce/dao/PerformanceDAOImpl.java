package com.revature.revworkforce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.revworkforce.model.PerformanceReview;
import com.revature.revworkforce.util.DatabaseUtil;

/**
 * Implementation of PerformanceDAO interface
 */
public class PerformanceDAOImpl implements PerformanceDAO {
    
    private static final Logger logger = LogManager.getLogger(PerformanceDAOImpl.class);
    
    @Override
    public int createPerformanceReview(PerformanceReview review) throws SQLException {
        logger.debug("Creating performance review for employee: {}", review.getEmployeeId());
        
        String sql = "INSERT INTO performance_reviews (review_id, employee_id, review_year, " +
                     "key_deliverables, major_accomplishments, areas_of_improvement, " +
                     "self_assessment_rating, status) " +
                     "VALUES (seq_review_id.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"review_id"})) {
            
            pstmt.setString(1, review.getEmployeeId());
            pstmt.setInt(2, review.getReviewYear());
            pstmt.setString(3, review.getKeyDeliverables());
            pstmt.setString(4, review.getMajorAccomplishments());
            pstmt.setString(5, review.getAreasOfImprovement());
            
            if (review.getSelfAssessmentRating() > 0) {
                pstmt.setDouble(6, review.getSelfAssessmentRating());
            } else {
                pstmt.setNull(6, java.sql.Types.NUMERIC);
            }
            
            pstmt.setString(7, PerformanceReview.STATUS_DRAFT);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int reviewId = rs.getInt(1);
                    logger.info("Performance review created: {}", reviewId);
                    return reviewId;
                }
            }
        } catch (SQLException e) {
            logger.error("Error creating performance review", e);
            throw e;
        }
        
        return -1;
    }
    
    @Override
    public boolean updatePerformanceReview(PerformanceReview review) throws SQLException {
        logger.debug("Updating performance review: {}", review.getReviewId());
        
        String sql = "UPDATE performance_reviews SET key_deliverables = ?, " +
                     "major_accomplishments = ?, areas_of_improvement = ?, " +
                     "self_assessment_rating = ? WHERE review_id = ? AND status = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, review.getKeyDeliverables());
            pstmt.setString(2, review.getMajorAccomplishments());
            pstmt.setString(3, review.getAreasOfImprovement());
            
            if (review.getSelfAssessmentRating() > 0) {
                pstmt.setDouble(4, review.getSelfAssessmentRating());
            } else {
                pstmt.setNull(4, java.sql.Types.NUMERIC);
            }
            
            pstmt.setInt(5, review.getReviewId());
            pstmt.setString(6, PerformanceReview.STATUS_DRAFT);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                logger.info("Performance review updated: {}", review.getReviewId());
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error updating performance review", e);
            throw e;
        }
        
        return false;
    }
    
    @Override
    public boolean submitPerformanceReview(int reviewId) throws SQLException {
        logger.debug("Submitting performance review: {}", reviewId);
        
        String sql = "UPDATE performance_reviews SET status = ?, submitted_date = CURRENT_TIMESTAMP " +
                     "WHERE review_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, PerformanceReview.STATUS_SUBMITTED);
            pstmt.setInt(2, reviewId);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                logger.info("Performance review submitted: {}", reviewId);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error submitting performance review", e);
            throw e;
        }
        
        return false;
    }
    
    @Override
    public boolean addManagerFeedback(int reviewId, String managerId, String feedback, 
                                     double rating) throws SQLException {
        logger.debug("Adding manager feedback to review: {}", reviewId);
        
        String sql = "UPDATE performance_reviews SET manager_feedback = ?, manager_rating = ?, " +
                     "reviewed_by = ?, reviewed_date = CURRENT_TIMESTAMP, status = ? " +
                     "WHERE review_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, feedback);
            pstmt.setDouble(2, rating);
            pstmt.setString(3, managerId);
            pstmt.setString(4, PerformanceReview.STATUS_REVIEWED);
            pstmt.setInt(5, reviewId);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                logger.info("Manager feedback added to review: {}", reviewId);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error adding manager feedback", e);
            throw e;
        }
        
        return false;
    }
    
    @Override
    public PerformanceReview getPerformanceReviewById(int reviewId) throws SQLException {
        logger.debug("Getting performance review by ID: {}", reviewId);
        
        String sql = "SELECT pr.*, e.first_name || ' ' || e.last_name AS employee_name, " +
                     "m.first_name || ' ' || m.last_name AS reviewer_name " +
                     "FROM performance_reviews pr " +
                     "INNER JOIN employees e ON pr.employee_id = e.employee_id " +
                     "LEFT JOIN employees m ON pr.reviewed_by = m.employee_id " +
                     "WHERE pr.review_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, reviewId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractPerformanceReviewFromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.error("Error getting performance review", e);
            throw e;
        }
        
        return null;
    }
    
    @Override
    public List<PerformanceReview> getReviewsByEmployee(String employeeId) throws SQLException {
        logger.debug("Getting performance reviews for employee: {}", employeeId);
        
        List<PerformanceReview> reviews = new ArrayList<>();
        String sql = "SELECT pr.*, m.first_name || ' ' || m.last_name AS reviewer_name " +
                     "FROM performance_reviews pr " +
                     "LEFT JOIN employees m ON pr.reviewed_by = m.employee_id " +
                     "WHERE pr.employee_id = ? " +
                     "ORDER BY pr.review_year DESC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employeeId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                reviews.add(extractPerformanceReviewFromResultSet(rs));
            }
            
            logger.info("Performance reviews retrieved: {}", reviews.size());
        } catch (SQLException e) {
            logger.error("Error getting reviews by employee", e);
            throw e;
        }
        
        return reviews;
    }
    
    @Override
    public PerformanceReview getReviewByEmployeeAndYear(String employeeId, int year) 
            throws SQLException {
        logger.debug("Getting review for employee: {} year: {}", employeeId, year);
        
        String sql = "SELECT pr.*, m.first_name || ' ' || m.last_name AS reviewer_name " +
                     "FROM performance_reviews pr " +
                     "LEFT JOIN employees m ON pr.reviewed_by = m.employee_id " +
                     "WHERE pr.employee_id = ? AND pr.review_year = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employeeId);
            pstmt.setInt(2, year);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractPerformanceReviewFromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.error("Error getting review by employee and year", e);
            throw e;
        }
        
        return null;
    }
    
    @Override
    public List<PerformanceReview> getPendingReviewsByManager(String managerId) 
            throws SQLException {
        logger.debug("Getting pending reviews for manager: {}", managerId);
        
        List<PerformanceReview> reviews = new ArrayList<>();
        String sql = "SELECT pr.*, e.first_name || ' ' || e.last_name AS employee_name " +
                     "FROM performance_reviews pr " +
                     "INNER JOIN employees e ON pr.employee_id = e.employee_id " +
                     "WHERE e.manager_id = ? AND pr.status = ? " +
                     "ORDER BY pr.submitted_date DESC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, managerId);
            pstmt.setString(2, PerformanceReview.STATUS_SUBMITTED);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                reviews.add(extractPerformanceReviewFromResultSet(rs));
            }
            
            logger.info("Pending reviews for manager retrieved: {}", reviews.size());
        } catch (SQLException e) {
            logger.error("Error getting pending reviews by manager", e);
            throw e;
        }
        
        return reviews;
    }
    
    @Override
    public List<PerformanceReview> getReviewedByManager(String managerId) throws SQLException {
        logger.debug("Getting reviewed performance reviews by manager: {}", managerId);
        
        List<PerformanceReview> reviews = new ArrayList<>();
        String sql = "SELECT pr.*, e.first_name || ' ' || e.last_name AS employee_name " +
                     "FROM performance_reviews pr " +
                     "INNER JOIN employees e ON pr.employee_id = e.employee_id " +
                     "WHERE pr.reviewed_by = ? " +
                     "ORDER BY pr.reviewed_date DESC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, managerId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                reviews.add(extractPerformanceReviewFromResultSet(rs));
            }
            
            logger.info("Reviewed by manager retrieved: {}", reviews.size());
        } catch (SQLException e) {
            logger.error("Error getting reviewed by manager", e);
            throw e;
        }
        
        return reviews;
    }
    
    @Override
    public boolean deletePerformanceReview(int reviewId) throws SQLException {
        logger.debug("Deleting performance review: {}", reviewId);
        
        String sql = "DELETE FROM performance_reviews WHERE review_id = ? AND status = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, reviewId);
            pstmt.setString(2, PerformanceReview.STATUS_DRAFT);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                logger.info("Performance review deleted: {}", reviewId);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error deleting performance review", e);
            throw e;
        }
        
        return false;
    }
    
    /**
     * Helper method to extract PerformanceReview from ResultSet
     */
    private PerformanceReview extractPerformanceReviewFromResultSet(ResultSet rs) 
            throws SQLException {
        PerformanceReview review = new PerformanceReview();
        review.setReviewId(rs.getInt("review_id"));
        review.setEmployeeId(rs.getString("employee_id"));
        review.setReviewYear(rs.getInt("review_year"));
        review.setKeyDeliverables(rs.getString("key_deliverables"));
        review.setMajorAccomplishments(rs.getString("major_accomplishments"));
        review.setAreasOfImprovement(rs.getString("areas_of_improvement"));
        
        double selfRating = rs.getDouble("self_assessment_rating");
        if (!rs.wasNull()) {
            review.setSelfAssessmentRating(selfRating);
        }
        
        review.setManagerFeedback(rs.getString("manager_feedback"));
        
        double managerRating = rs.getDouble("manager_rating");
        if (!rs.wasNull()) {
            review.setManagerRating(managerRating);
        }
        
        review.setStatus(rs.getString("status"));
        
        if (rs.getTimestamp("submitted_date") != null) {
            review.setSubmittedDate(rs.getTimestamp("submitted_date").toLocalDateTime());
        }
        if (rs.getTimestamp("reviewed_date") != null) {
            review.setReviewedDate(rs.getTimestamp("reviewed_date").toLocalDateTime());
        }
        
        review.setReviewedBy(rs.getString("reviewed_by"));
        
        if (rs.getTimestamp("created_at") != null) {
            review.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        if (rs.getTimestamp("updated_at") != null) {
            review.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        }
        
        // Additional fields
        try {
            review.setEmployeeName(rs.getString("employee_name"));
        } catch (SQLException e) {
            // Column may not exist
        }
        try {
            review.setReviewerName(rs.getString("reviewer_name"));
        } catch (SQLException e) {
            // Column may not exist
        }
        
        return review;
    }
}