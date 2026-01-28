package com.revature.revworkforce.model;

import java.time.LocalDateTime;

/**
 * Model class representing an Announcement
 */
public class Announcement {
    
    private int announcementId;
    private String title;
    private String content;
    private String postedBy;
    private LocalDateTime postedDate;
    private LocalDateTime createdAt;
    
    // Additional field for display
    private String posterName;
    
    // Constructors
    public Announcement() {
    }
    
    public Announcement(String title, String content, String postedBy) {
        this.title = title;
        this.content = content;
        this.postedBy = postedBy;
    }
    
    // Getters and Setters
    public int getAnnouncementId() {
        return announcementId;
    }
    
    public void setAnnouncementId(int announcementId) {
        this.announcementId = announcementId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getPostedBy() {
        return postedBy;
    }
    
    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }
    
    public LocalDateTime getPostedDate() {
        return postedDate;
    }
    
    public void setPostedDate(LocalDateTime postedDate) {
        this.postedDate = postedDate;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getPosterName() {
        return posterName;
    }
    
    public void setPosterName(String posterName) {
        this.posterName = posterName;
    }
    
    @Override
    public String toString() {
        return "Announcement{" +
                "announcementId=" + announcementId +
                ", title='" + title + '\'' +
                ", postedBy='" + postedBy + '\'' +
                ", postedDate=" + postedDate +
                '}';
    }
}