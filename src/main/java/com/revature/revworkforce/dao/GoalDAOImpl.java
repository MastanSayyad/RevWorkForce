package com.revature.revworkforce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.revworkforce.model.Goal;
import com.revature.revworkforce.util.DatabaseUtil;

/**
 * Implementation of GoalDAO interface
 */
public class GoalDAOImpl implements GoalDAO {
    
    private static final Logger logger = LogManager.getLogger(GoalDAOImpl.class);
    
    @Override
    public int createGoal(Goal goal) throws SQLException {
        logger.debug("Creating goal for employee: {}", goal.getEmployeeId());
        
        String sql = "INSERT INTO goals (goal_id, employee_id, goal_description, deadline, " +
                     "priority, success_metrics, progress_percentage, status) " +
                     "VALUES (seq_goal_id.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"goal_id"})) {
            
            pstmt.setString(1, goal.getEmployeeId());
            pstmt.setString(2, goal.getGoalDescription());
            pstmt.setDate(3, java.sql.Date.valueOf(goal.getDeadline()));
            pstmt.setString(4, goal.getPriority());
            pstmt.setString(5, goal.getSuccessMetrics());
            pstmt.setInt(6, goal.getProgressPercentage());
            pstmt.setString(7, goal.getStatus());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int goalId = rs.getInt(1);
                    logger.info("Goal created successfully: {}", goalId);
                    return goalId;
                }
            }
        } catch (SQLException e) {
            logger.error("Error creating goal", e);
            throw e;
        }
        
        return -1;
    }
    
    @Override
    public boolean updateGoal(Goal goal) throws SQLException {
        logger.debug("Updating goal: {}", goal.getGoalId());
        
        String sql = "UPDATE goals SET goal_description = ?, deadline = ?, priority = ?, " +
                     "success_metrics = ?, progress_percentage = ?, status = ? " +
                     "WHERE goal_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, goal.getGoalDescription());
            pstmt.setDate(2, java.sql.Date.valueOf(goal.getDeadline()));
            pstmt.setString(3, goal.getPriority());
            pstmt.setString(4, goal.getSuccessMetrics());
            pstmt.setInt(5, goal.getProgressPercentage());
            pstmt.setString(6, goal.getStatus());
            pstmt.setInt(7, goal.getGoalId());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                logger.info("Goal updated successfully: {}", goal.getGoalId());
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error updating goal", e);
            throw e;
        }
        
        return false;
    }
    
    @Override
    public boolean updateGoalProgress(int goalId, int progressPercentage) throws SQLException {
        logger.debug("Updating goal progress: {} to {}%", goalId, progressPercentage);
        
        String sql = "UPDATE goals SET progress_percentage = ?, status = ? WHERE goal_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Auto-update status based on progress
            String status = progressPercentage == 0 ? Goal.STATUS_NOT_STARTED :
                           progressPercentage == 100 ? Goal.STATUS_COMPLETED :
                           Goal.STATUS_IN_PROGRESS;
            
            pstmt.setInt(1, progressPercentage);
            pstmt.setString(2, status);
            pstmt.setInt(3, goalId);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                logger.info("Goal progress updated: {}", goalId);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error updating goal progress", e);
            throw e;
        }
        
        return false;
    }
    
    @Override
    public boolean updateGoalStatus(int goalId, String status) throws SQLException {
        logger.debug("Updating goal status: {} to {}", goalId, status);
        
        String sql = "UPDATE goals SET status = ? WHERE goal_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            pstmt.setInt(2, goalId);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                logger.info("Goal status updated: {}", goalId);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error updating goal status", e);
            throw e;
        }
        
        return false;
    }
    
    @Override
    public boolean deleteGoal(int goalId) throws SQLException {
        logger.debug("Deleting goal: {}", goalId);
        
        String sql = "DELETE FROM goals WHERE goal_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, goalId);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                logger.info("Goal deleted successfully: {}", goalId);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error deleting goal", e);
            throw e;
        }
        
        return false;
    }
    
    @Override
    public boolean addManagerComments(int goalId, String comments) throws SQLException {
        logger.debug("Adding manager comments to goal: {}", goalId);
        
        String sql = "UPDATE goals SET manager_comments = ? WHERE goal_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, comments);
            pstmt.setInt(2, goalId);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                logger.info("Manager comments added to goal: {}", goalId);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error adding manager comments", e);
            throw e;
        }
        
        return false;
    }
    
    @Override
    public Goal getGoalById(int goalId) throws SQLException {
        logger.debug("Getting goal by ID: {}", goalId);
        
        String sql = "SELECT * FROM goals WHERE goal_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, goalId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractGoalFromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.error("Error getting goal by ID", e);
            throw e;
        }
        
        return null;
    }
    
    @Override
    public List<Goal> getGoalsByEmployee(String employeeId) throws SQLException {
        logger.debug("Getting goals for employee: {}", employeeId);
        
        List<Goal> goals = new ArrayList<>();
        String sql = "SELECT * FROM goals WHERE employee_id = ? ORDER BY deadline ASC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employeeId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                goals.add(extractGoalFromResultSet(rs));
            }
            
            logger.info("Goals retrieved for employee: {}", goals.size());
        } catch (SQLException e) {
            logger.error("Error getting goals by employee", e);
            throw e;
        }
        
        return goals;
    }
    
    @Override
    public List<Goal> getActiveGoalsByEmployee(String employeeId) throws SQLException {
        logger.debug("Getting active goals for employee: {}", employeeId);
        
        List<Goal> goals = new ArrayList<>();
        String sql = "SELECT * FROM goals WHERE employee_id = ? " +
                     "AND status NOT IN (?, ?) ORDER BY deadline ASC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employeeId);
            pstmt.setString(2, Goal.STATUS_COMPLETED);
            pstmt.setString(3, Goal.STATUS_CANCELLED);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                goals.add(extractGoalFromResultSet(rs));
            }
            
            logger.info("Active goals retrieved: {}", goals.size());
        } catch (SQLException e) {
            logger.error("Error getting active goals", e);
            throw e;
        }
        
        return goals;
    }
    
    @Override
    public List<Goal> getGoalsByStatus(String employeeId, String status) throws SQLException {
        logger.debug("Getting goals by status: {} for employee: {}", status, employeeId);
        
        List<Goal> goals = new ArrayList<>();
        String sql = "SELECT * FROM goals WHERE employee_id = ? AND status = ? " +
                     "ORDER BY deadline ASC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employeeId);
            pstmt.setString(2, status);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                goals.add(extractGoalFromResultSet(rs));
            }
            
            logger.info("Goals by status retrieved: {}", goals.size());
        } catch (SQLException e) {
            logger.error("Error getting goals by status", e);
            throw e;
        }
        
        return goals;
    }
    
    @Override
    public List<Goal> getGoalsByTeam(String managerId) throws SQLException {
        logger.debug("Getting goals for team managed by: {}", managerId);
        
        List<Goal> goals = new ArrayList<>();
        String sql = "SELECT g.* FROM goals g " +
                     "INNER JOIN employees e ON g.employee_id = e.employee_id " +
                     "WHERE e.manager_id = ? " +
                     "ORDER BY g.deadline ASC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, managerId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                goals.add(extractGoalFromResultSet(rs));
            }
            
            logger.info("Team goals retrieved: {}", goals.size());
        } catch (SQLException e) {
            logger.error("Error getting goals by team", e);
            throw e;
        }
        
        return goals;
    }
    
    /**
     * Helper method to extract Goal from ResultSet
     */
    private Goal extractGoalFromResultSet(ResultSet rs) throws SQLException {
        Goal goal = new Goal();
        goal.setGoalId(rs.getInt("goal_id"));
        goal.setEmployeeId(rs.getString("employee_id"));
        goal.setGoalDescription(rs.getString("goal_description"));
        goal.setDeadline(rs.getDate("deadline").toLocalDate());
        goal.setPriority(rs.getString("priority"));
        goal.setSuccessMetrics(rs.getString("success_metrics"));
        goal.setProgressPercentage(rs.getInt("progress_percentage"));
        goal.setStatus(rs.getString("status"));
        goal.setManagerComments(rs.getString("manager_comments"));
        
        if (rs.getTimestamp("created_at") != null) {
            goal.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        if (rs.getTimestamp("updated_at") != null) {
            goal.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        }
        
        return goal;
    }
}