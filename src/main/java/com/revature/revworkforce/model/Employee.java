package com.revature.revworkforce.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Model class representing an Employee
 * Central entity in the system
 */
public class Employee {
    
    private String employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String emergencyContact;
    private LocalDate dateOfBirth;
    private int departmentId;
    private int designationId;
    private String managerId;
    private LocalDate joiningDate;
    private double salary;
    private String passwordHash;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Additional fields for display purposes (not in DB)
    private String departmentName;
    private String designationName;
    private String managerName;
    private List<Role> roles;
    
    // Constructors
    public Employee() {
    }
    
    public Employee(String employeeId, String firstName, String lastName, String email) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    
    // Full constructor
    public Employee(String employeeId, String firstName, String lastName, String email,
                   String phone, String address, String emergencyContact, LocalDate dateOfBirth,
                   int departmentId, int designationId, String managerId, LocalDate joiningDate,
                   double salary, String passwordHash, boolean isActive) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.emergencyContact = emergencyContact;
        this.dateOfBirth = dateOfBirth;
        this.departmentId = departmentId;
        this.designationId = designationId;
        this.managerId = managerId;
        this.joiningDate = joiningDate;
        this.salary = salary;
        this.passwordHash = passwordHash;
        this.isActive = isActive;
    }
    
    // Getters and Setters
    public String getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getEmergencyContact() {
        return emergencyContact;
    }
    
    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
    
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public int getDepartmentId() {
        return departmentId;
    }
    
    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
    
    public int getDesignationId() {
        return designationId;
    }
    
    public void setDesignationId(int designationId) {
        this.designationId = designationId;
    }
    
    public String getManagerId() {
        return managerId;
    }
    
    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }
    
    public LocalDate getJoiningDate() {
        return joiningDate;
    }
    
    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }
    
    public double getSalary() {
        return salary;
    }
    
    public void setSalary(double salary) {
        this.salary = salary;
    }
    
    public String getPasswordHash() {
        return passwordHash;
    }
    
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
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
    
    // Additional getters and setters
    public String getDepartmentName() {
        return departmentName;
    }
    
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    
    public String getDesignationName() {
        return designationName;
    }
    
    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }
    
    public String getManagerName() {
        return managerName;
    }
    
    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
    
    public List<Role> getRoles() {
        return roles;
    }
    
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
    // Helper methods
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public boolean hasRole(String roleName) {
        if (roles == null) return false;
        return roles.stream().anyMatch(role -> role.getRoleName().equals(roleName));
    }
    
    @Override
    public String toString() {
        return "Employee{" +
                "employeeId='" + employeeId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", departmentId=" + departmentId +
                ", designationId=" + designationId +
                ", managerId='" + managerId + '\'' +
                ", isActive=" + isActive +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return employeeId.equals(employee.employeeId);
    }
    
    @Override
    public int hashCode() {
        return employeeId.hashCode();
    }
}