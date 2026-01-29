package com.revature.revworkforce.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.revworkforce.dao.EmployeeDAO;
import com.revature.revworkforce.dao.EmployeeDAOImpl;
import com.revature.revworkforce.exception.ValidationException;
import com.revature.revworkforce.model.Department;
import com.revature.revworkforce.model.Designation;
import com.revature.revworkforce.model.Employee;
import com.revature.revworkforce.util.InputValidator;
import com.revature.revworkforce.util.PasswordUtil;

/**
 * Implementation of EmployeeService interface
 */
public class EmployeeServiceImpl implements EmployeeService {
    
    private static final Logger logger = LogManager.getLogger(EmployeeServiceImpl.class);
    private EmployeeDAO employeeDAO;
    
    public EmployeeServiceImpl() {
        this.employeeDAO = new EmployeeDAOImpl();
    }
    
    @Override
    public boolean createEmployee(Employee employee, String plainPassword) throws ValidationException {
        logger.info("Creating employee: {}", employee.getEmployeeId());
        
        try {
            // Validate employee data
            validateEmployeeData(employee, true);
            
            // Validate password
            if (!InputValidator.isNotEmpty(plainPassword)) {
                throw new ValidationException("Password cannot be empty");
            }
            
            if (plainPassword.length() < 8) {
                throw new ValidationException("Password must be at least 8 characters long");
            }
            
            // Check if employee ID already exists
            if (employeeDAO.isEmployeeIdExists(employee.getEmployeeId())) {
                throw new ValidationException("Employee ID already exists");
            }
            
            // Check if email already exists
            if (employeeDAO.isEmailExists(employee.getEmail())) {
                throw new ValidationException("Email already exists");
            }
            
            // Hash password
            String passwordHash = PasswordUtil.hashPassword(plainPassword);
            employee.setPasswordHash(passwordHash);
            
            // Set as active by default
            employee.setActive(true);
            
            // Create employee
            boolean created = employeeDAO.createEmployee(employee);
            
            if (created) {
                // Assign default EMPLOYEE role
                employeeDAO.assignRole(employee.getEmployeeId(), 1); // Role ID 1 = EMPLOYEE
                
                logger.info("Employee created successfully: {}", employee.getEmployeeId());
                return true;
            }
            
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error creating employee", e);
            throw new ValidationException("Failed to create employee: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean updateEmployee(Employee employee) throws ValidationException {
        logger.info("Updating employee: {}", employee.getEmployeeId());
        
        try {
            // Validate employee data
            validateEmployeeData(employee, false);
            
            // Check if employee exists
            Employee existing = employeeDAO.findByEmployeeId(employee.getEmployeeId());
            if (existing == null) {
                throw new ValidationException("Employee not found");
            }
            
            // Check if email is being changed and if it's already taken
            if (!existing.getEmail().equals(employee.getEmail())) {
                if (employeeDAO.isEmailExists(employee.getEmail())) {
                    throw new ValidationException("Email already exists");
                }
            }
            
            // Update employee
            boolean updated = employeeDAO.updateEmployee(employee);
            
            if (updated) {
                logger.info("Employee updated successfully: {}", employee.getEmployeeId());
                return true;
            }
            
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error updating employee", e);
            throw new ValidationException("Failed to update employee: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean updateProfile(Employee employee) throws ValidationException {
        logger.info("Updating profile: {}", employee.getEmployeeId());
        
        try {
            // Validate profile data
            if (!InputValidator.isValidPhone(employee.getPhone())) {
                throw new ValidationException("Invalid phone number. Must be 10 digits.");
            }
            
            if (!InputValidator.isNotEmpty(employee.getAddress())) {
                throw new ValidationException("Address cannot be empty");
            }
            
            // Update profile
            boolean updated = employeeDAO.updateProfile(employee);
            
            if (updated) {
                logger.info("Profile updated successfully: {}", employee.getEmployeeId());
                return true;
            }
            
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error updating profile", e);
            throw new ValidationException("Failed to update profile: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean deactivateEmployee(String employeeId) {
        logger.info("Deactivating employee: {}", employeeId);
        
        try {
            return employeeDAO.deactivateEmployee(employeeId);
        } catch (Exception e) {
            logger.error("Error deactivating employee", e);
            return false;
        }
    }
    
    @Override
    public boolean activateEmployee(String employeeId) {
        logger.info("Activating employee: {}", employeeId);
        
        try {
            return employeeDAO.activateEmployee(employeeId);
        } catch (Exception e) {
            logger.error("Error activating employee", e);
            return false;
        }
    }
    
    @Override
    public Employee getEmployeeDetails(String employeeId) {
        try {
            return employeeDAO.getEmployeeWithDetails(employeeId);
        } catch (Exception e) {
            logger.error("Error getting employee details", e);
            return null;
        }
    }
    
    @Override
    public List<Employee> getAllActiveEmployees() {
        try {
            return employeeDAO.getActiveEmployees();
        } catch (Exception e) {
            logger.error("Error getting all active employees", e);
            return null;
        }
    }
    
    @Override
    public List<Employee> getEmployeesByDepartment(int departmentId) {
        try {
            return employeeDAO.getEmployeesByDepartment(departmentId);
        } catch (Exception e) {
            logger.error("Error getting employees by department", e);
            return null;
        }
    }
    
    @Override
    public List<Employee> getTeamMembers(String managerId) {
        try {
            return employeeDAO.getEmployeesByManager(managerId);
        } catch (Exception e) {
            logger.error("Error getting team members", e);
            return null;
        }
    }
    
    @Override
    public boolean assignRole(String employeeId, int roleId) {
        logger.info("Assigning role {} to employee {}", roleId, employeeId);
        
        try {
            return employeeDAO.assignRole(employeeId, roleId);
        } catch (Exception e) {
            logger.error("Error assigning role", e);
            return false;
        }
    }
    
    @Override
    public List<Department> getAllDepartments() {
        try {
            return employeeDAO.getAllDepartments();
        } catch (Exception e) {
            logger.error("Error getting departments", e);
            return null;
        }
    }
    
    @Override
    public List<Designation> getAllDesignations() {
        try {
            return employeeDAO.getAllDesignations();
        } catch (Exception e) {
            logger.error("Error getting designations", e);
            return null;
        }
    }
    
    @Override
    public String generateEmployeeId() {
        try {
            int count = employeeDAO.getTotalEmployeeCount();
            return String.format("EMP%03d", count + 1);
        } catch (Exception e) {
            logger.error("Error generating employee ID", e);
            return "EMP001";
        }
    }
    
    /**
     * Validate employee data
     */
    private void validateEmployeeData(Employee employee, boolean isNew) throws ValidationException {
        // Validate employee ID
        if (isNew && !InputValidator.isValidEmployeeId(employee.getEmployeeId())) {
            throw new ValidationException("Invalid employee ID format. Expected: EMP001");
        }
        
        // Validate names
        if (!InputValidator.isNotEmpty(employee.getFirstName())) {
            throw new ValidationException("First name cannot be empty");
        }
        
        if (!InputValidator.isNotEmpty(employee.getLastName())) {
            throw new ValidationException("Last name cannot be empty");
        }
        
        // Validate email
        if (!InputValidator.isValidEmail(employee.getEmail())) {
            throw new ValidationException("Invalid email format");
        }
        
        // Validate phone
        if (!InputValidator.isValidPhone(employee.getPhone())) {
            throw new ValidationException("Invalid phone number. Must be 10 digits.");
        }
        
        // Validate department and designation
        if (!InputValidator.isPositiveNumber(employee.getDepartmentId())) {
            throw new ValidationException("Department must be selected");
        }
        
        if (!InputValidator.isPositiveNumber(employee.getDesignationId())) {
            throw new ValidationException("Designation must be selected");
        }
        
        // Validate dates
        if (employee.getDateOfBirth() == null) {
            throw new ValidationException("Date of birth cannot be empty");
        }
        
        if (employee.getJoiningDate() == null) {
            throw new ValidationException("Joining date cannot be empty");
        }
        
        // Validate salary
        if (employee.getSalary() <= 0) {
            throw new ValidationException("Salary must be greater than zero");
        }
    }
}