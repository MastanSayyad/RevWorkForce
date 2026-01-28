package com.revature.revworkforce.dao;

import java.sql.SQLException;
import java.util.List;

import com.revature.revworkforce.model.Goal;

/**
 * DAO Interface for Goal Management operations
 */
public interface GoalDAO {
    
    // CRUD Operations
    int createGoal(Goal goal) throws SQLException;
    boolean updateGoal(Goal goal) throws SQLException;
    boolean updateGoalProgress(int goalId, int progressPercentage) throws SQLException;
    boolean updateGoalStatus(int goalId, String status) throws SQLException;
    boolean deleteGoal(int goalId) throws SQLException;
    
    // Manager Operations
    boolean addManagerComments(int goalId, String comments) throws SQLException;
    
    // Read Operations
    Goal getGoalById(int goalId) throws SQLException;
    List<Goal> getGoalsByEmployee(String employeeId) throws SQLException;
    List<Goal> getActiveGoalsByEmployee(String employeeId) throws SQLException;
    List<Goal> getGoalsByStatus(String employeeId, String status) throws SQLException;
    List<Goal> getGoalsByTeam(String managerId) throws SQLException;
}