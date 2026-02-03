package com.revature.revworkforce.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.revature.revworkforce.model.Department;
import com.revature.revworkforce.model.Designation;
import com.revature.revworkforce.model.Employee;

/**
 * Test class for EmployeeDAO
 */
public class EmployeeDAOTest {
    
    private EmployeeDAO employeeDAO;
    
    @BeforeEach
    public void setUp() {
        employeeDAO = new EmployeeDAOImpl();
    }
    
    @Test
    @DisplayName("Test find employee by ID")
    public void testFindByEmployeeId() {
        try {
            Employee employee = employeeDAO.findByEmployeeId("EMP001");
            assertNotNull(employee, "Employee should not be null");
            assertEquals("EMP001", employee.getEmployeeId());
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    @DisplayName("Test find employee by ID - not found")
    public void testFindByEmployeeId_NotFound() {
        try {
            Employee employee = employeeDAO.findByEmployeeId("INVALID999");
            assertNull(employee, "Employee should be null for invalid ID");
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    @DisplayName("Test get employee with details")
    public void testGetEmployeeWithDetails() {
        try {
            Employee employee = employeeDAO.getEmployeeWithDetails("EMP001");
            assertNotNull(employee, "Employee should not be null");
            assertNotNull(employee.getDepartmentName(), "Department name should be populated");
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    @DisplayName("Test get active employees")
    public void testGetActiveEmployees() {
        try {
            List<Employee> employees = employeeDAO.getActiveEmployees();
            assertNotNull(employees, "Employees list should not be null");
            assertTrue(employees.size() > 0, "Should have at least one active employee");
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    @DisplayName("Test get all departments")
    public void testGetAllDepartments() {
        try {
            List<Department> departments = employeeDAO.getAllDepartments();
            assertNotNull(departments, "Departments list should not be null");
            assertTrue(departments.size() > 0, "Should have at least one department");
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    @DisplayName("Test get all designations")
    public void testGetAllDesignations() {
        try {
            List<Designation> designations = employeeDAO.getAllDesignations();
            assertNotNull(designations, "Designations list should not be null");
            assertTrue(designations.size() > 0, "Should have at least one designation");
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    @DisplayName("Test get employees by department")
    public void testGetEmployeesByDepartment() {
        try {
            List<Employee> employees = employeeDAO.getEmployeesByDepartment(1);
            assertNotNull(employees, "Employees list should not be null");
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    @DisplayName("Test get employees by manager")
    public void testGetEmployeesByManager() {
        try {
            List<Employee> employees = employeeDAO.getEmployeesByManager("MGR001");
            assertNotNull(employees, "Employees list should not be null");
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    @DisplayName("Test is email exists")
    public void testIsEmailExists() {
        try {
            boolean exists = employeeDAO.isEmailExists("amit.patel@revworkforce.com");
            assertTrue(exists, "Email should exist for EMP001");
            
            boolean notExists = employeeDAO.isEmailExists("nonexistent@test.com");
            assertFalse(notExists, "Non-existent email should return false");
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    @DisplayName("Test is employee ID exists")
    public void testIsEmployeeIdExists() {
        try {
            boolean exists = employeeDAO.isEmployeeIdExists("EMP001");
            assertTrue(exists, "Employee ID should exist");
            
            boolean notExists = employeeDAO.isEmployeeIdExists("INVALID999");
            assertFalse(notExists, "Non-existent ID should return false");
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
}