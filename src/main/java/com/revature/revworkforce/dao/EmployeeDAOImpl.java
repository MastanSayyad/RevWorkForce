package com.revature.revworkforce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.revworkforce.model.Department;
import com.revature.revworkforce.model.Designation;
import com.revature.revworkforce.model.Employee;
import com.revature.revworkforce.model.Role;
import com.revature.revworkforce.util.DatabaseUtil;

/**
 * Implementation of EmployeeDAO interface
 */
public class EmployeeDAOImpl implements EmployeeDAO {
    
    private static final Logger logger = LogManager.getLogger(EmployeeDAOImpl.class);
    
    @Override
    public Employee findByEmployeeId(String employeeId) throws SQLException {
        logger.debug("Finding employee by ID: {}", employeeId);
        
        String sql = "SELECT * FROM employees WHERE employee_id = ? AND is_active = 1";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employeeId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Employee employee = extractEmployeeFromResultSet(rs);
                logger.info("Employee found: {}", employeeId);
                return employee;
            }
        } catch (SQLException e) {
            logger.error("Error finding employee by ID: {}", employeeId, e);
            throw e;
        }
        
        logger.warn("Employee not found: {}", employeeId);
        return null;
    }
    
    @Override
    public Employee findByEmail(String email) throws SQLException {
        logger.debug("Finding employee by email: {}", email);
        
        String sql = "SELECT * FROM employees WHERE email = ? AND is_active = 1";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Employee employee = extractEmployeeFromResultSet(rs);
                logger.info("Employee found by email: {}", email);
                return employee;
            }
        } catch (SQLException e) {
            logger.error("Error finding employee by email: {}", email, e);
            throw e;
        }
        
        return null;
    }
    
    @Override
    public boolean createEmployee(Employee employee) throws SQLException {
        logger.debug("Creating employee: {}", employee.getEmployeeId());
        
        String sql = "INSERT INTO employees (employee_id, first_name, last_name, email, phone, " +
                     "address, emergency_contact, date_of_birth, department_id, designation_id, " +
                     "manager_id, joining_date, salary, password_hash, is_active) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employee.getEmployeeId());
            pstmt.setString(2, employee.getFirstName());
            pstmt.setString(3, employee.getLastName());
            pstmt.setString(4, employee.getEmail());
            pstmt.setString(5, employee.getPhone());
            pstmt.setString(6, employee.getAddress());
            pstmt.setString(7, employee.getEmergencyContact());
            pstmt.setDate(8, java.sql.Date.valueOf(employee.getDateOfBirth()));
            pstmt.setInt(9, employee.getDepartmentId());
            pstmt.setInt(10, employee.getDesignationId());
            pstmt.setString(11, employee.getManagerId());
            pstmt.setDate(12, java.sql.Date.valueOf(employee.getJoiningDate()));
            pstmt.setDouble(13, employee.getSalary());
            pstmt.setString(14, employee.getPasswordHash());
            pstmt.setInt(15, employee.isActive() ? 1 : 0);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                logger.info("Employee created successfully: {}", employee.getEmployeeId());
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error creating employee: {}", employee.getEmployeeId(), e);
            throw e;
        }
        
        return false;
    }
    
    @Override
    public boolean updateEmployee(Employee employee) throws SQLException {
        logger.debug("Updating employee: {}", employee.getEmployeeId());
        
        String sql = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, " +
                     "phone = ?, address = ?, emergency_contact = ?, date_of_birth = ?, " +
                     "department_id = ?, designation_id = ?, manager_id = ?, salary = ? " +
                     "WHERE employee_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setString(3, employee.getEmail());
            pstmt.setString(4, employee.getPhone());
            pstmt.setString(5, employee.getAddress());
            pstmt.setString(6, employee.getEmergencyContact());
            pstmt.setDate(7, java.sql.Date.valueOf(employee.getDateOfBirth()));
            pstmt.setInt(8, employee.getDepartmentId());
            pstmt.setInt(9, employee.getDesignationId());
            pstmt.setString(10, employee.getManagerId());
            pstmt.setDouble(11, employee.getSalary());
            pstmt.setString(12, employee.getEmployeeId());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                logger.info("Employee updated successfully: {}", employee.getEmployeeId());
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error updating employee: {}", employee.getEmployeeId(), e);
            throw e;
        }
        
        return false;
    }
    
    @Override
    public boolean updateProfile(Employee employee) throws SQLException {
        logger.debug("Updating employee profile: {}", employee.getEmployeeId());
        
        String sql = "UPDATE employees SET phone = ?, address = ?, emergency_contact = ? " +
                     "WHERE employee_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employee.getPhone());
            pstmt.setString(2, employee.getAddress());
            pstmt.setString(3, employee.getEmergencyContact());
            pstmt.setString(4, employee.getEmployeeId());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                logger.info("Employee profile updated: {}", employee.getEmployeeId());
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error updating profile: {}", employee.getEmployeeId(), e);
            throw e;
        }
        
        return false;
    }
    
    @Override
    public boolean deactivateEmployee(String employeeId) throws SQLException {
        logger.debug("Deactivating employee: {}", employeeId);
        
        String sql = "UPDATE employees SET is_active = 0 WHERE employee_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employeeId);
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                logger.info("Employee deactivated: {}", employeeId);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error deactivating employee: {}", employeeId, e);
            throw e;
        }
        
        return false;
    }
    
    @Override
    public boolean activateEmployee(String employeeId) throws SQLException {
        logger.debug("Activating employee: {}", employeeId);
        
        String sql = "UPDATE employees SET is_active = 1 WHERE employee_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employeeId);
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                logger.info("Employee activated: {}", employeeId);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error activating employee: {}", employeeId, e);
            throw e;
        }
        
        return false;
    }
    
    @Override
    public Employee getEmployeeWithDetails(String employeeId) throws SQLException {
        logger.debug("Getting employee with details: {}", employeeId);
        
        String sql = "SELECT e.*, d.department_name, dg.designation_name, " +
                     "m.first_name || ' ' || m.last_name AS manager_name " +
                     "FROM employees e " +
                     "LEFT JOIN departments d ON e.department_id = d.department_id " +
                     "LEFT JOIN designations dg ON e.designation_id = dg.designation_id " +
                     "LEFT JOIN employees m ON e.manager_id = m.employee_id " +
                     "WHERE e.employee_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employeeId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Employee employee = extractEmployeeFromResultSet(rs);
                employee.setDepartmentName(rs.getString("department_name"));
                employee.setDesignationName(rs.getString("designation_name"));
                employee.setManagerName(rs.getString("manager_name"));
                
                // Load roles
                employee.setRoles(getEmployeeRoles(employeeId));
                
                logger.info("Employee with details retrieved: {}", employeeId);
                return employee;
            }
        } catch (SQLException e) {
            logger.error("Error getting employee details: {}", employeeId, e);
            throw e;
        }
        
        return null;
    }
    
    @Override
    public List<Employee> getAllEmployees() throws SQLException {
        logger.debug("Getting all employees");
        
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT e.*, d.department_name, dg.designation_name " +
                     "FROM employees e " +
                     "LEFT JOIN departments d ON e.department_id = d.department_id " +
                     "LEFT JOIN designations dg ON e.designation_id = dg.designation_id " +
                     "ORDER BY e.employee_id";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Employee employee = extractEmployeeFromResultSet(rs);
                employee.setDepartmentName(rs.getString("department_name"));
                employee.setDesignationName(rs.getString("designation_name"));
                employees.add(employee);
            }
            
            logger.info("Total employees retrieved: {}", employees.size());
        } catch (SQLException e) {
            logger.error("Error getting all employees", e);
            throw e;
        }
        
        return employees;
    }
    
    @Override
    public List<Employee> getActiveEmployees() throws SQLException {
        logger.debug("Getting active employees");
        
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT e.*, d.department_name, dg.designation_name " +
                     "FROM employees e " +
                     "LEFT JOIN departments d ON e.department_id = d.department_id " +
                     "LEFT JOIN designations dg ON e.designation_id = dg.designation_id " +
                     "WHERE e.is_active = 1 " +
                     "ORDER BY e.employee_id";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Employee employee = extractEmployeeFromResultSet(rs);
                employee.setDepartmentName(rs.getString("department_name"));
                employee.setDesignationName(rs.getString("designation_name"));
                employees.add(employee);
            }
            
            logger.info("Active employees retrieved: {}", employees.size());
        } catch (SQLException e) {
            logger.error("Error getting active employees", e);
            throw e;
        }
        
        return employees;
    }
    
    @Override
    public List<Employee> getEmployeesByDepartment(int departmentId) throws SQLException {
        logger.debug("Getting employees by department: {}", departmentId);
        
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT e.*, d.department_name, dg.designation_name " +
                     "FROM employees e " +
                     "LEFT JOIN departments d ON e.department_id = d.department_id " +
                     "LEFT JOIN designations dg ON e.designation_id = dg.designation_id " +
                     "WHERE e.department_id = ? AND e.is_active = 1 " +
                     "ORDER BY e.employee_id";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, departmentId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Employee employee = extractEmployeeFromResultSet(rs);
                employee.setDepartmentName(rs.getString("department_name"));
                employee.setDesignationName(rs.getString("designation_name"));
                employees.add(employee);
            }
            
            logger.info("Employees by department retrieved: {}", employees.size());
        } catch (SQLException e) {
            logger.error("Error getting employees by department", e);
            throw e;
        }
        
        return employees;
    }
    
    @Override
    public List<Employee> getEmployeesByManager(String managerId) throws SQLException {
        logger.debug("Getting employees by manager: {}", managerId);
        
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT e.*, d.department_name, dg.designation_name " +
                     "FROM employees e " +
                     "LEFT JOIN departments d ON e.department_id = d.department_id " +
                     "LEFT JOIN designations dg ON e.designation_id = dg.designation_id " +
                     "WHERE e.manager_id = ? AND e.is_active = 1 " +
                     "ORDER BY e.employee_id";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, managerId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Employee employee = extractEmployeeFromResultSet(rs);
                employee.setDepartmentName(rs.getString("department_name"));
                employee.setDesignationName(rs.getString("designation_name"));
                employees.add(employee);
            }
            
            logger.info("Employees by manager retrieved: {}", employees.size());
        } catch (SQLException e) {
            logger.error("Error getting employees by manager", e);
            throw e;
        }
        
        return employees;
    }
    
    @Override
    public List<Role> getEmployeeRoles(String employeeId) throws SQLException {
        logger.debug("Getting roles for employee: {}", employeeId);
        
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT r.* FROM roles r " +
                     "INNER JOIN employee_roles er ON r.role_id = er.role_id " +
                     "WHERE er.employee_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employeeId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Role role = new Role();
                role.setRoleId(rs.getInt("role_id"));
                role.setRoleName(rs.getString("role_name"));
                role.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                roles.add(role);
            }
            
            logger.info("Roles retrieved for employee {}: {}", employeeId, roles.size());
        } catch (SQLException e) {
            logger.error("Error getting employee roles", e);
            throw e;
        }
        
        return roles;
    }
    
    @Override
    public boolean assignRole(String employeeId, int roleId) throws SQLException {
        logger.debug("Assigning role {} to employee {}", roleId, employeeId);
        
        String sql = "INSERT INTO employee_roles (employee_role_id, employee_id, role_id) " +
                     "VALUES (seq_employee_role_id.NEXTVAL, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employeeId);
            pstmt.setInt(2, roleId);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                logger.info("Role assigned successfully");
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error assigning role", e);
            throw e;
        }
        
        return false;
    }
    
    @Override
    public boolean removeRole(String employeeId, int roleId) throws SQLException {
        logger.debug("Removing role {} from employee {}", roleId, employeeId);
        
        String sql = "DELETE FROM employee_roles WHERE employee_id = ? AND role_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employeeId);
            pstmt.setInt(2, roleId);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                logger.info("Role removed successfully");
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error removing role", e);
            throw e;
        }
        
        return false;
    }
    
    @Override
    public List<Department> getAllDepartments() throws SQLException {
        logger.debug("Getting all departments");
        
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM departments ORDER BY department_name";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Department dept = new Department();
                dept.setDepartmentId(rs.getInt("department_id"));
                dept.setDepartmentName(rs.getString("department_name"));
                dept.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                departments.add(dept);
            }
            
            logger.info("Departments retrieved: {}", departments.size());
        } catch (SQLException e) {
            logger.error("Error getting departments", e);
            throw e;
        }
        
        return departments;
    }
    
    @Override
    public List<Designation> getAllDesignations() throws SQLException {
        logger.debug("Getting all designations");
        
        List<Designation> designations = new ArrayList<>();
        String sql = "SELECT * FROM designations ORDER BY designation_name";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Designation desig = new Designation();
                desig.setDesignationId(rs.getInt("designation_id"));
                desig.setDesignationName(rs.getString("designation_name"));
                desig.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                designations.add(desig);
            }
            
            logger.info("Designations retrieved: {}", designations.size());
        } catch (SQLException e) {
            logger.error("Error getting designations", e);
            throw e;
        }
        
        return designations;
    }
    
    @Override
    public List<Role> getAllRoles() throws SQLException {
        logger.debug("Getting all roles");
        
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT * FROM roles ORDER BY role_id";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Role role = new Role();
                role.setRoleId(rs.getInt("role_id"));
                role.setRoleName(rs.getString("role_name"));
                role.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                roles.add(role);
            }
            
            logger.info("Roles retrieved: {}", roles.size());
        } catch (SQLException e) {
            logger.error("Error getting roles", e);
            throw e;
        }
        
        return roles;
    }

    @Override
    public boolean isEmailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM employees WHERE email = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            logger.error("Error checking email existence", e);
            throw e;
        }
        
        return false;
    }

    @Override
    public boolean isEmployeeIdExists(String employeeId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM employees WHERE employee_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employeeId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            logger.error("Error checking employee ID existence", e);
            throw e;
        }
        
        return false;
    }

    @Override
    public int getTotalEmployeeCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM employees WHERE is_active = 1";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Error getting employee count", e);
            throw e;
        }
        
        return 0;
    }

    /**
     * Helper method to extract Employee object from ResultSet
     */
    private Employee extractEmployeeFromResultSet(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setEmployeeId(rs.getString("employee_id"));
        employee.setFirstName(rs.getString("first_name"));
        employee.setLastName(rs.getString("last_name"));
        employee.setEmail(rs.getString("email"));
        employee.setPhone(rs.getString("phone"));
        employee.setAddress(rs.getString("address"));
        employee.setEmergencyContact(rs.getString("emergency_contact"));
        
        if (rs.getDate("date_of_birth") != null) {
            employee.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
        }
        
        employee.setDepartmentId(rs.getInt("department_id"));
        employee.setDesignationId(rs.getInt("designation_id"));
        employee.setManagerId(rs.getString("manager_id"));
        
        if (rs.getDate("joining_date") != null) {
            employee.setJoiningDate(rs.getDate("joining_date").toLocalDate());
        }
        
        employee.setSalary(rs.getDouble("salary"));
        employee.setPasswordHash(rs.getString("password_hash"));
        employee.setActive(rs.getInt("is_active") == 1);
        
        if (rs.getTimestamp("created_at") != null) {
            employee.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        
        if (rs.getTimestamp("updated_at") != null) {
            employee.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        }
        
        return employee;
    }
}