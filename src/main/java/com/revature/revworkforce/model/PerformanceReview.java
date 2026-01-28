package com.revature.revworkforce.model;

import java.time.LocalDateTime;

/**
 * Model class representing a Performance Review
 */

public class PerformanceReview {
    
    private int reviewId;
    private String employeeId;
    private int reviewYear;
    private String keyDeliverables;
    private String majorAccomplishments;
    private String areasOfImprovement;
    private double selfAssessmentRating;
    private String managerFeedback;
    private double managerRating;
    private String status;
    private LocalDateTime submittedDate;
    private LocalDateTime reviewedDate;
    private String reviewedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Additional fields for display
    private String employeeName;
    private String reviewerName;
    
    // Status constants
    public static final String STATUS_DRAFT = "DRAFT";
    public static final String STATUS_SUBMITTED = "SUBMITTED";
    public static final String STATUS_REVIEWED = "REVIEWED";
    
    // Constructors
    public PerformanceReview() {
    }
    
    public PerformanceReview(String employeeId, int reviewYear) {
        this.employeeId = employeeId;
        this.reviewYear = reviewYear;
        this.status = STATUS_DRAFT;
    }
    
    // Getters and Setters
    public int getReviewId() {
        return reviewId;
    }
    
    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }
    
    public String getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    
    public int getReviewYear() {
        return reviewYear;
    }
    
    public void setReviewYear(int reviewYear) {
        this.reviewYear = reviewYear;
    }
    
    public String getKeyDeliverables() {
        return keyDeliverables;
    }
    
    public void setKeyDeliverables(String keyDeliverables) {
        this.keyDeliverables = keyDeliverables;
    }
    
    public String getMajorAccomplishments() {
        return majorAccomplishments;
    }
    
    public void setMajorAccomplishments(String majorAccomplishments) {
        this.majorAccomplishments = majorAccomplishments;
    }
    
    public String getAreasOfImprovement() {
        return areasOfImprovement;
    }
    
    public void setAreasOfImprovement(String areasOfImprovement) {
        this.areasOfImprovement = areasOfImprovement;
    }
    
    public double getSelfAssessmentRating() {
        return selfAssessmentRating;
    }
    
    public void setSelfAssessmentRating(double selfAssessmentRating) {
        this.selfAssessmentRating = selfAssessmentRating;
    }
    
    public String getManagerFeedback() {
        return managerFeedback;
    }
    
    public void setManagerFeedback(String managerFeedback) {
        this.managerFeedback = managerFeedback;
    }
    
    public double getManagerRating() {
        return managerRating;
    }
    
    public void setManagerRating(double managerRating) {
        this.managerRating = managerRating;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getSubmittedDate() {
        return submittedDate;
    }
    
    public void setSubmittedDate(LocalDateTime submittedDate) {
        this.submittedDate = submittedDate;
    }
    
    public LocalDateTime getReviewedDate() {
        return reviewedDate;
    }
    
    public void setReviewedDate(LocalDateTime reviewedDate) {
        this.reviewedDate = reviewedDate;
    }
    
    public String getReviewedBy() {
        return reviewedBy;
    }
    
    public void setReviewedBy(String reviewedBy) {
        this.reviewedBy = reviewedBy;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public String getEmployeeName() {
        return employeeName;
    }
    
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    
    public String getReviewerName() {
        return reviewerName;
    }
    
    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }
    
    @Override
    public String toString() {
        return "PerformanceReview{" +
                "reviewId=" + reviewId +
                ", employeeId='" + employeeId + '\'' +
                ", reviewYear=" + reviewYear +
                ", selfAssessmentRating=" + selfAssessmentRating +
                ", managerRating=" + managerRating +
                ", status='" + status + '\'' +
                '}';
    }
}