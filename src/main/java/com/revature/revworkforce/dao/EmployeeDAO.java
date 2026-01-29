package com.revature.revworkforce.dao;

import java.sql.SQLException;
import java.util.List;

import com.revature.revworkforce.model.Department;
import com.revature.revworkforce.model.Designation;
import com.revature.revworkforce.model.Employee;
import com.revature.revworkforce.model.Role;

/**
 * DAO Interface for Employee operations
 */
public interface EmployeeDAO {
    
    // Authentication
    Employee findByEmployeeId(String employeeId) throws SQLException;
    Employee findByEmail(String email) throws SQLException;
    
    // CRUD Operations
    boolean createEmployee(Employee employee) throws SQLException;
    boolean updateEmployee(Employee employee) throws SQLException;
    boolean updateProfile(Employee employee) throws SQLException;
    boolean deactivateEmployee(String employeeId) throws SQLException;
    boolean activateEmployee(String employeeId) throws SQLException;
    
    // Read Operations
    Employee getEmployeeWithDetails(String employeeId) throws SQLException;
    List<Employee> getAllEmployees() throws SQLException;
    List<Employee> getActiveEmployees() throws SQLException;
    List<Employee> getEmployeesByDepartment(int departmentId) throws SQLException;
    List<Employee> getEmployeesByManager(String managerId) throws SQLException;
    
    // Role Management
    List<Role> getEmployeeRoles(String employeeId) throws SQLException;
    boolean assignRole(String employeeId, int roleId) throws SQLException;
    boolean removeRole(String employeeId, int roleId) throws SQLException;
    
    // Lookup Data
    List<Department> getAllDepartments() throws SQLException;
    List<Designation> getAllDesignations() throws SQLException;
    List<Role> getAllRoles() throws SQLException;
    
    // Utility
    boolean isEmailExists(String email) throws SQLException;
    boolean isEmployeeIdExists(String employeeId) throws SQLException;
    int getTotalEmployeeCount() throws SQLException;
    
    boolean updatePassword(String employeeId, String passwordHash) throws SQLException;

}