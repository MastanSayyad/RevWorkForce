package com.revature.revworkforce.service;

import java.util.List;

import com.revature.revworkforce.exception.ValidationException;
import com.revature.revworkforce.model.Department;
import com.revature.revworkforce.model.Designation;
import com.revature.revworkforce.model.Employee;

/**
 * Service Interface for Employee operations
 */
public interface EmployeeService {
    
    /**
     * Create a new employee
     * @param employee Employee object
     * @param plainPassword Plain text password
     * @return true if employee created successfully
     * @throws ValidationException if validation fails
     */
    boolean createEmployee(Employee employee, String plainPassword) throws ValidationException;
    
    /**
     * Update employee information (Admin only)
     * @param employee Employee object
     * @return true if updated successfully
     * @throws ValidationException if validation fails
     */
    boolean updateEmployee(Employee employee) throws ValidationException;
    
    /**
     * Update employee profile (Employee can update own profile)
     * @param employee Employee object
     * @return true if updated successfully
     * @throws ValidationException if validation fails
     */
    boolean updateProfile(Employee employee) throws ValidationException;
    
    /**
     * Deactivate an employee
     * @param employeeId Employee ID
     * @return true if deactivated successfully
     */
    boolean deactivateEmployee(String employeeId);
    
    /**
     * Activate an employee
     * @param employeeId Employee ID
     * @return true if activated successfully
     */
    boolean activateEmployee(String employeeId);
    
    /**
     * Get employee with complete details
     * @param employeeId Employee ID
     * @return Employee object with all details
     */
    Employee getEmployeeDetails(String employeeId);
    
    /**
     * Get all active employees
     * @return List of active employees
     */
    List<Employee> getAllActiveEmployees();
    
    /**
     * Get employees by department
     * @param departmentId Department ID
     * @return List of employees in the department
     */
    List<Employee> getEmployeesByDepartment(int departmentId);
    
    /**
     * Get direct reportees of a manager
     * @param managerId Manager's employee ID
     * @return List of employees reporting to the manager
     */
    List<Employee> getTeamMembers(String managerId);
    
    /**
     * Assign role to employee
     * @param employeeId Employee ID
     * @param roleId Role ID
     * @return true if role assigned successfully
     */
    boolean assignRole(String employeeId, int roleId);
    
    /**
     * Get all departments
     * @return List of departments
     */
    List<Department> getAllDepartments();
    
    /**
     * Get all designations
     * @return List of designations
     */
    List<Designation> getAllDesignations();
    
    /**
     * Generate next employee ID
     * @return Next available employee ID
     */
    String generateEmployeeId();
}