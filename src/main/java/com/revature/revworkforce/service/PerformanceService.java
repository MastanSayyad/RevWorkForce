package com.revature.revworkforce.service;

import java.util.List;

import com.revature.revworkforce.exception.ValidationException;
import com.revature.revworkforce.model.Goal;
import com.revature.revworkforce.model.PerformanceReview;

/**
 * Service Interface for Performance Management operations
 */
public interface PerformanceService {
    
    // Performance Review Operations
    
    /**
     * Create a new performance review
     * @param review Performance review object
     * @return Review ID if successful
     * @throws ValidationException if validation fails
     */
    int createPerformanceReview(PerformanceReview review) throws ValidationException;
    
    /**
     * Update performance review (only in DRAFT status)
     * @param review Performance review object
     * @return true if updated successfully
     * @throws ValidationException if validation fails
     */
    boolean updatePerformanceReview(PerformanceReview review) throws ValidationException;
    
    /**
     * Submit performance review for manager approval
     * @param reviewId Review ID
     * @param employeeId Employee ID (for authorization)
     * @return true if submitted successfully
     * @throws ValidationException if validation fails
     */
    boolean submitPerformanceReview(int reviewId, String employeeId) throws ValidationException;
    
    /**
     * Add manager feedback to a submitted review
     * @param reviewId Review ID
     * @param managerId Manager's employee ID
     * @param feedback Manager's feedback
     * @param rating Manager's rating (1-5)
     * @return true if feedback added successfully
     * @throws ValidationException if validation fails
     */
    boolean addManagerFeedback(int reviewId, String managerId, String feedback, double rating) 
            throws ValidationException;
    
    /**
     * Get performance reviews for an employee
     * @param employeeId Employee ID
     * @return List of performance reviews
     */
    List<PerformanceReview> getEmployeeReviews(String employeeId);
    
    /**
     * Get pending reviews for manager's team
     * @param managerId Manager's employee ID
     * @return List of pending performance reviews
     */
    List<PerformanceReview> getPendingReviewsForManager(String managerId);
    
    // Goal Management Operations
    
    /**
     * Create a new goal
     * @param goal Goal object
     * @return Goal ID if successful
     * @throws ValidationException if validation fails
     */
    int createGoal(Goal goal) throws ValidationException;
    
    /**
     * Update goal
     * @param goal Goal object
     * @return true if updated successfully
     * @throws ValidationException if validation fails
     */
    boolean updateGoal(Goal goal) throws ValidationException;
    
    /**
     * Update goal progress
     * @param goalId Goal ID
     * @param progressPercentage Progress percentage (0-100)
     * @return true if updated successfully
     * @throws ValidationException if validation fails
     */
    boolean updateGoalProgress(int goalId, int progressPercentage) throws ValidationException;
    
    /**
     * Delete a goal
     * @param goalId Goal ID
     * @param employeeId Employee ID (for authorization)
     * @return true if deleted successfully
     */
    boolean deleteGoal(int goalId, String employeeId);
    
    /**
     * Get goals for an employee
     * @param employeeId Employee ID
     * @return List of goals
     */
    List<Goal> getEmployeeGoals(String employeeId);
    
    /**
     * Get active goals for an employee
     * @param employeeId Employee ID
     * @return List of active goals
     */
    List<Goal> getActiveGoals(String employeeId);
    
    /**
     * Get team goals (for managers)
     * @param managerId Manager's employee ID
     * @return List of team goals
     */
    List<Goal> getTeamGoals(String managerId);
}