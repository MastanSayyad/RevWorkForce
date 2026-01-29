package com.revature.revworkforce.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.revworkforce.dao.GoalDAO;
import com.revature.revworkforce.dao.GoalDAOImpl;
import com.revature.revworkforce.dao.PerformanceDAO;
import com.revature.revworkforce.dao.PerformanceDAOImpl;
import com.revature.revworkforce.exception.ValidationException;
import com.revature.revworkforce.model.Goal;
import com.revature.revworkforce.model.PerformanceReview;
import com.revature.revworkforce.util.InputValidator;

/**
 * Implementation of PerformanceService interface
 */
public class PerformanceServiceImpl implements PerformanceService {
    
    private static final Logger logger = LogManager.getLogger(PerformanceServiceImpl.class);
    private PerformanceDAO performanceDAO;
    private GoalDAO goalDAO;
    private NotificationService notificationService;
    
    public PerformanceServiceImpl() {
        this.performanceDAO = new PerformanceDAOImpl();
        this.goalDAO = new GoalDAOImpl();
        this.notificationService = new NotificationServiceImpl();
    }
    
    // ========== Performance Review Methods ==========
    
    @Override
    public int createPerformanceReview(PerformanceReview review) throws ValidationException {
        logger.info("Creating performance review for employee: {}", review.getEmployeeId());
        
        try {
            // Validate review data
            validatePerformanceReview(review);
            
            // Check if review already exists for the year
            PerformanceReview existing = performanceDAO.getReviewByEmployeeAndYear(
                review.getEmployeeId(), review.getReviewYear());
            
            if (existing != null) {
                throw new ValidationException("Performance review already exists for year " + 
                                            review.getReviewYear());
            }
            
            // Create review
            int reviewId = performanceDAO.createPerformanceReview(review);
            
            if (reviewId > 0) {
                logger.info("Performance review created: {}", reviewId);
                return reviewId;
            }
            
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error creating performance review", e);
            throw new ValidationException("Failed to create performance review: " + e.getMessage());
        }
        
        return -1;
    }
    
    @Override
    public boolean updatePerformanceReview(PerformanceReview review) throws ValidationException {
        logger.info("Updating performance review: {}", review.getReviewId());
        
        try {
            // Validate review data
            validatePerformanceReview(review);
            
            // Get existing review
            PerformanceReview existing = performanceDAO.getPerformanceReviewById(review.getReviewId());
            
            if (existing == null) {
                throw new ValidationException("Performance review not found");
            }
            
            // Check if review is in DRAFT status
            if (!PerformanceReview.STATUS_DRAFT.equals(existing.getStatus())) {
                throw new ValidationException("Only draft reviews can be updated");
            }
            
            // Update review
            boolean updated = performanceDAO.updatePerformanceReview(review);
            
            if (updated) {
                logger.info("Performance review updated: {}", review.getReviewId());
                return true;
            }
            
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error updating performance review", e);
            throw new ValidationException("Failed to update performance review: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean submitPerformanceReview(int reviewId, String employeeId) 
            throws ValidationException {
        logger.info("Submitting performance review: {}", reviewId);
        
        try {
            // Get review
            PerformanceReview review = performanceDAO.getPerformanceReviewById(reviewId);
            
            if (review == null) {
                throw new ValidationException("Performance review not found");
            }
            
            // Check authorization
            if (!review.getEmployeeId().equals(employeeId)) {
                throw new ValidationException("Unauthorized to submit this review");
            }
            
            // Check if review is in DRAFT status
            if (!PerformanceReview.STATUS_DRAFT.equals(review.getStatus())) {
                throw new ValidationException("Review has already been submitted");
            }
            
            // Validate review is complete
            if (review.getSelfAssessmentRating() == 0) {
                throw new ValidationException("Self-assessment rating is required");
            }
            
            // Submit review
            boolean submitted = performanceDAO.submitPerformanceReview(reviewId);
            
            if (submitted) {
                // Create notification for manager
                notificationService.createNotification(
                    employeeId, 
                    "PERFORMANCE", 
                    "Your performance review has been submitted for manager approval."
                );
                
                logger.info("Performance review submitted: {}", reviewId);
                return true;
            }
            
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error submitting performance review", e);
            throw new ValidationException("Failed to submit performance review: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean addManagerFeedback(int reviewId, String managerId, String feedback, double rating) 
            throws ValidationException {
        logger.info("Adding manager feedback to review: {}", reviewId);
        
        try {
            // Get review
            PerformanceReview review = performanceDAO.getPerformanceReviewById(reviewId);
            
            if (review == null) {
                throw new ValidationException("Performance review not found");
            }
            
            // Check if review is submitted
            if (!PerformanceReview.STATUS_SUBMITTED.equals(review.getStatus())) {
                throw new ValidationException("Can only provide feedback on submitted reviews");
            }
            
            // Validate rating
            if (!InputValidator.isValidRating(rating)) {
                throw new ValidationException("Rating must be between 1.0 and 5.0");
            }
            
            // Validate feedback
            if (feedback == null || feedback.trim().isEmpty()) {
                throw new ValidationException("Feedback cannot be empty");
            }
            
            // Add feedback
            boolean added = performanceDAO.addManagerFeedback(reviewId, managerId, feedback, rating);
            
            if (added) {
                // Create notification for employee
                notificationService.createNotification(
                    review.getEmployeeId(), 
                    "PERFORMANCE", 
                    "Your manager has reviewed your performance review for year " + 
                    review.getReviewYear()
                );
                
                logger.info("Manager feedback added to review: {}", reviewId);
                return true;
            }
            
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error adding manager feedback", e);
            throw new ValidationException("Failed to add manager feedback: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public List<PerformanceReview> getEmployeeReviews(String employeeId) {
        try {
            return performanceDAO.getReviewsByEmployee(employeeId);
        } catch (Exception e) {
            logger.error("Error getting employee reviews", e);
            return null;
        }
    }
    
    @Override
    public List<PerformanceReview> getPendingReviewsForManager(String managerId) {
        try {
            return performanceDAO.getPendingReviewsByManager(managerId);
        } catch (Exception e) {
            logger.error("Error getting pending reviews for manager", e);
            return null;
        }
    }
    
    // ========== Goal Management Methods ==========
    
    @Override
    public int createGoal(Goal goal) throws ValidationException {
        logger.info("Creating goal for employee: {}", goal.getEmployeeId());
        
        try {
            // Validate goal
            validateGoal(goal);
            
            // Create goal
            int goalId = goalDAO.createGoal(goal);
            
            if (goalId > 0) {
                logger.info("Goal created: {}", goalId);
                return goalId;
            }
            
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error creating goal", e);
            throw new ValidationException("Failed to create goal: " + e.getMessage());
        }
        
        return -1;
    }
    
    @Override
    public boolean updateGoal(Goal goal) throws ValidationException {
        logger.info("Updating goal: {}", goal.getGoalId());
        
        try {
            // Validate goal
            validateGoal(goal);
            
            // Get existing goal
            Goal existing = goalDAO.getGoalById(goal.getGoalId());
            
            if (existing == null) {
                throw new ValidationException("Goal not found");
            }
            
            // Update goal
            boolean updated = goalDAO.updateGoal(goal);
            
            if (updated) {
                logger.info("Goal updated: {}", goal.getGoalId());
                return true;
            }
            
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error updating goal", e);
            throw new ValidationException("Failed to update goal: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean updateGoalProgress(int goalId, int progressPercentage) throws ValidationException {
        logger.info("Updating goal progress: {} to {}%", goalId, progressPercentage);
        
        try {
            // Validate progress
            if (progressPercentage < 0 || progressPercentage > 100) {
                throw new ValidationException("Progress must be between 0 and 100");
            }
            
            // Update progress
            boolean updated = goalDAO.updateGoalProgress(goalId, progressPercentage);
            
            if (updated) {
                logger.info("Goal progress updated: {}", goalId);
                return true;
            }
            
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error updating goal progress", e);
            throw new ValidationException("Failed to update goal progress: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean deleteGoal(int goalId, String employeeId) {
        logger.info("Deleting goal: {}", goalId);
        
        try {
            // Get goal
            Goal goal = goalDAO.getGoalById(goalId);
            
            if (goal == null) {
                logger.warn("Goal not found: {}", goalId);
                return false;
            }
            
            // Check authorization
            if (!goal.getEmployeeId().equals(employeeId)) {
                logger.warn("Unauthorized delete attempt for goal: {}", goalId);
                return false;
            }
            
            // Delete goal
            return goalDAO.deleteGoal(goalId);
            
        } catch (Exception e) {
            logger.error("Error deleting goal", e);
            return false;
        }
    }
    
    @Override
    public List<Goal> getEmployeeGoals(String employeeId) {
        try {
            return goalDAO.getGoalsByEmployee(employeeId);
        } catch (Exception e) {
            logger.error("Error getting employee goals", e);
            return null;
        }
    }
    
    @Override
    public List<Goal> getActiveGoals(String employeeId) {
        try {
            return goalDAO.getActiveGoalsByEmployee(employeeId);
        } catch (Exception e) {
            logger.error("Error getting active goals", e);
            return null;
        }
    }
    
    @Override
    public List<Goal> getTeamGoals(String managerId) {
        try {
            return goalDAO.getGoalsByTeam(managerId);
        } catch (Exception e) {
            logger.error("Error getting team goals", e);
            return null;
        }
    }
    
    /**
     * Validate performance review data
     */
    private void validatePerformanceReview(PerformanceReview review) throws ValidationException {
        if (review.getEmployeeId() == null || review.getEmployeeId().isEmpty()) {
            throw new ValidationException("Employee ID cannot be empty");
        }
        
        if (review.getReviewYear() <= 0) {
            throw new ValidationException("Review year is required");
        }
        
        if (review.getSelfAssessmentRating() > 0 && 
            !InputValidator.isValidRating(review.getSelfAssessmentRating())) {
            throw new ValidationException("Self-assessment rating must be between 1.0 and 5.0");
        }
    }
    
    /**
     * Validate goal data
     */
    private void validateGoal(Goal goal) throws ValidationException {
        if (goal.getEmployeeId() == null || goal.getEmployeeId().isEmpty()) {
            throw new ValidationException("Employee ID cannot be empty");
        }
        
        if (goal.getGoalDescription() == null || goal.getGoalDescription().trim().isEmpty()) {
            throw new ValidationException("Goal description cannot be empty");
        }
        
        if (goal.getGoalDescription().length() > 1000) {
            throw new ValidationException("Goal description is too long (max 1000 characters)");
        }
        
        if (goal.getDeadline() == null) {
            throw new ValidationException("Goal deadline cannot be empty");
        }
        
        if (goal.getPriority() == null || goal.getPriority().isEmpty()) {
            throw new ValidationException("Goal priority must be selected");
        }
        
        if (!goal.getPriority().equals(Goal.PRIORITY_HIGH) && 
            !goal.getPriority().equals(Goal.PRIORITY_MEDIUM) && 
            !goal.getPriority().equals(Goal.PRIORITY_LOW)) {
            throw new ValidationException("Invalid priority value");
        }
    }
}