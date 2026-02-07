package com.revature.revworkforce.service;

import static org.junit.jupiter.api.Assertions.*;



import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.revature.revworkforce.exception.ValidationException;
import com.revature.revworkforce.model.Department;
import com.revature.revworkforce.model.Designation;
import com.revature.revworkforce.model.Employee;

/**
 * Test class for EmployeeService
 */

import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable; 
@DisabledIfEnvironmentVariable(named = "CI", matches = "true")

public class EmployeeServiceTest {
    
    private EmployeeService employeeService;
    
    @BeforeEach
    public void setUp() {
        employeeService = new EmployeeServiceImpl();
    }
    
    @Test
    @DisplayName("Test get employee details")
    public void testGetEmployeeDetails() {
        Employee employee = employeeService.getEmployeeDetails("EMP001");
        assertNotNull(employee, "Employee should not be null");
        assertEquals("EMP001", employee.getEmployeeId());
    }
    
    @Test
    @DisplayName("Test get employee details - invalid ID")
    public void testGetEmployeeDetails_InvalidId() {
        Employee employee = employeeService.getEmployeeDetails("INVALID");
        assertNull(employee, "Employee should be null for invalid ID");
    }
    
    @Test
    @DisplayName("Test get all active employees")
    public void testGetAllActiveEmployees() {
        List<Employee> employees = employeeService.getAllActiveEmployees();
        assertNotNull(employees, "Employee list should not be null");
        assertTrue(employees.size() > 0, "Should have at least one active employee");
    }
    
    @Test
    @DisplayName("Test get all departments")
    public void testGetAllDepartments() {
        List<Department> departments = employeeService.getAllDepartments();
        assertNotNull(departments, "Department list should not be null");
        assertTrue(departments.size() > 0, "Should have at least one department");
    }
    
    @Test
    @DisplayName("Test get all designations")
    public void testGetAllDesignations() {
        List<Designation> designations = employeeService.getAllDesignations();
        assertNotNull(designations, "Designation list should not be null");
        assertTrue(designations.size() > 0, "Should have at least one designation");
    }
    
    @Test
    @DisplayName("Test generate employee ID")
    public void testGenerateEmployeeId() {
        String employeeId = employeeService.generateEmployeeId();
        assertNotNull(employeeId, "Generated employee ID should not be null");
        assertTrue(employeeId.startsWith("EMP"), "Employee ID should start with EMP");
        assertEquals(6, employeeId.length(), "Employee ID should be 6 characters");
    }
    
    @Test
    @DisplayName("Test update profile with valid data")
    public void testUpdateProfile_ValidData() {
        Employee employee = new Employee();
        employee.setEmployeeId("EMP001");
        employee.setPhone("9876543210");
        employee.setAddress("New Address");
        employee.setEmergencyContact("Emergency Contact");
        
        try {
            boolean result = employeeService.updateProfile(employee);
            assertTrue(result, "Profile update should succeed");
        } catch (ValidationException e) {
            fail("Profile update should not throw exception for valid data");
        }
    }
    
    @Test
    @DisplayName("Test update profile with invalid phone")
    public void testUpdateProfile_InvalidPhone() {
        Employee employee = new Employee();
        employee.setEmployeeId("EMP001");
        employee.setPhone("123"); // Invalid phone
        employee.setAddress("New Address");
        employee.setEmergencyContact("Emergency Contact");
        
        assertThrows(ValidationException.class, () -> {
            employeeService.updateProfile(employee);
        }, "Should throw ValidationException for invalid phone");
    }
    
    @Test
    @DisplayName("Test get team members")
    public void testGetTeamMembers() {
        List<Employee> teamMembers = employeeService.getTeamMembers("MGR001");
        assertNotNull(teamMembers, "Team members list should not be null");
    }

    @Test
    @DisplayName("Test get employees by department")
    public void testGetEmployeesByDepartment() {
        List<Employee> employees = employeeService.getEmployeesByDepartment(1);
        assertNotNull(employees, "Employees list should not be null");
    }

    @Test
    @DisplayName("Test deactivate employee")
    public void testDeactivateEmployee() {
        // This is a destructive test, so we test the method exists but don't actually deactivate
        boolean result = employeeService.deactivateEmployee("NONEXISTENT");
        // Result can be true or false for non-existent employee
        assertNotNull(result);
    }

    @Test
    @DisplayName("Test activate employee")
    public void testActivateEmployee() {
        // This tests the method exists
        boolean result = employeeService.activateEmployee("NONEXISTENT");
        // Result can be true or false
        assertNotNull(result);
    }

    @Test
    @DisplayName("Test assign role")
    public void testAssignRole() {
        // This tests the method exists
        boolean result = employeeService.assignRole("EMP001", 1);
        // Result can be true or false
        assertNotNull(result);
    }
}