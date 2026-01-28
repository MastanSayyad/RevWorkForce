package com.revature.revworkforce.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Model class representing a Holiday
 */
public class Holiday {
    
    private int holidayId;
    private String holidayName;
    private LocalDate holidayDate;
    private int year;
    private LocalDateTime createdAt;
    
    // Constructors
    public Holiday() {
    }
    
    public Holiday(int holidayId, String holidayName, LocalDate holidayDate, int year) {
        this.holidayId = holidayId;
        this.holidayName = holidayName;
        this.holidayDate = holidayDate;
        this.year = year;
    }
    
    public Holiday(int holidayId, String holidayName, LocalDate holidayDate, 
                  int year, LocalDateTime createdAt) {
        this.holidayId = holidayId;
        this.holidayName = holidayName;
        this.holidayDate = holidayDate;
        this.year = year;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public int getHolidayId() {
        return holidayId;
    }
    
    public void setHolidayId(int holidayId) {
        this.holidayId = holidayId;
    }
    
    public String getHolidayName() {
        return holidayName;
    }
    
    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }
    
    public LocalDate getHolidayDate() {
        return holidayDate;
    }
    
    public void setHolidayDate(LocalDate holidayDate) {
        this.holidayDate = holidayDate;
    }
    
    public int getYear() {
        return year;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "Holiday{" +
                "holidayId=" + holidayId +
                ", holidayName='" + holidayName + '\'' +
                ", holidayDate=" + holidayDate +
                ", year=" + year +
                ", createdAt=" + createdAt +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Holiday holiday = (Holiday) o;
        return holidayId == holiday.holidayId;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(holidayId);
    }
}