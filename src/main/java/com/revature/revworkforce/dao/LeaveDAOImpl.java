package com.revature.revworkforce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.revworkforce.model.Holiday;
import com.revature.revworkforce.model.LeaveApplication;
import com.revature.revworkforce.model.LeaveBalance;
import com.revature.revworkforce.model.LeaveType;
import com.revature.revworkforce.util.DatabaseUtil;

/**
 * Implementation of LeaveDAO interface
 */
public class LeaveDAOImpl implements LeaveDAO {
    
    private static final Logger logger = LogManager.getLogger(LeaveDAOImpl.class);
    
    @Override
    public int applyLeave(LeaveApplication leaveApplication) throws SQLException {
        logger.debug("Applying leave for employee: {}", leaveApplication.getEmployeeId());
        
        String sql = "INSERT INTO leave_applications (leave_application_id, employee_id, " +
                     "leave_type_id, start_date, end_date, total_days, reason, status) " +
                     "VALUES (seq_leave_application_id.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, 
                     new String[]{"leave_application_id"})) {
            
            pstmt.setString(1, leaveApplication.getEmployeeId());
            pstmt.setInt(2, leaveApplication.getLeaveTypeId());
            pstmt.setDate(3, java.sql.Date.valueOf(leaveApplication.getStartDate()));
            pstmt.setDate(4, java.sql.Date.valueOf(leaveApplication.getEndDate()));
            pstmt.setInt(5, leaveApplication.getTotalDays());
            pstmt.setString(6, leaveApplication.getReason());
            pstmt.setString(7, LeaveApplication.STATUS_PENDING);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int leaveApplicationId = rs.getInt(1);
                    logger.info("Leave application created: {}", leaveApplicationId);
                    return leaveApplicationId;
                }
            }
        } catch (SQLException e) {
            logger.error("Error applying leave", e);
            throw e;
        }
        
        return -1;
    }
    
    @Override
    public boolean updateLeaveApplication(LeaveApplication leaveApplication) throws SQLException {
        logger.debug("Updating leave application: {}", leaveApplication.getLeaveApplicationId());
        
        String sql = "UPDATE leave_applications SET start_date = ?, end_date = ?, " +
                     "total_days = ?, reason = ? WHERE leave_application_id = ? AND status = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDate(1, java.sql.Date.valueOf(leaveApplication.getStartDate()));
            pstmt.setDate(2, java.sql.Date.valueOf(leaveApplication.getEndDate()));
            pstmt.setInt(3, leaveApplication.getTotalDays());
            pstmt.setString(4, leaveApplication.getReason());
            pstmt.setInt(5, leaveApplication.getLeaveApplicationId());
            pstmt.setString(6, LeaveApplication.STATUS_PENDING);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                logger.info("Leave application updated: {}", leaveApplication.getLeaveApplicationId());
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error updating leave application", e);
            throw e;
        }
        
        return false;
    }
    
    @Override
    public boolean cancelLeaveApplication(int leaveApplicationId) throws SQLException {
        logger.debug("Cancelling leave application: {}", leaveApplicationId);
        
        String sql = "UPDATE leave_applications SET status = ? " +
                     "WHERE leave_application_id = ? AND status = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, LeaveApplication.STATUS_CANCELLED);
            pstmt.setInt(2, leaveApplicationId);
            pstmt.setString(3, LeaveApplication.STATUS_PENDING);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                logger.info("Leave application cancelled: {}", leaveApplicationId);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error cancelling leave application", e);
            throw e;
        }
        
        return false;
    }
    
    @Override
    public boolean approveLeave(int leaveApplicationId, String managerId, String comments) 
            throws SQLException {
        logger.debug("Approving leave application: {}", leaveApplicationId);
        
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);
            
            // Update leave application status
            String updateSql = "UPDATE leave_applications SET status = ?, reviewed_by = ?, " +
                              "manager_comments = ?, reviewed_date = CURRENT_TIMESTAMP " +
                              "WHERE leave_application_id = ?";
            
            try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                pstmt.setString(1, LeaveApplication.STATUS_APPROVED);
                pstmt.setString(2, managerId);
                pstmt.setString(3, comments);
                pstmt.setInt(4, leaveApplicationId);
                pstmt.executeUpdate();
            }
            
            // Update leave balance
            String balanceSql = "UPDATE leave_balances lb SET used_leaves = used_leaves + ?, " +
                               "available_leaves = available_leaves - ? " +
                               "WHERE employee_id = (SELECT employee_id FROM leave_applications " +
                               "WHERE leave_application_id = ?) " +
                               "AND leave_type_id = (SELECT leave_type_id FROM leave_applications " +
                               "WHERE leave_application_id = ?) " +
                               "AND year = EXTRACT(YEAR FROM SYSDATE)";
            
            try (PreparedStatement pstmt = conn.prepareStatement(balanceSql)) {
                // Get total days from leave application
                LeaveApplication app = getLeaveApplicationById(leaveApplicationId);
                pstmt.setInt(1, app.getTotalDays());
                pstmt.setInt(2, app.getTotalDays());
                pstmt.setInt(3, leaveApplicationId);
                pstmt.setInt(4, leaveApplicationId);
                pstmt.executeUpdate();
            }
            
            conn.commit();
            logger.info("Leave approved and balance updated: {}", leaveApplicationId);
            return true;
            
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            logger.error("Error approving leave", e);
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
    
    @Override
    public boolean rejectLeave(int leaveApplicationId, String managerId, String comments) 
            throws SQLException {
        logger.debug("Rejecting leave application: {}", leaveApplicationId);
        
        String sql = "UPDATE leave_applications SET status = ?, reviewed_by = ?, " +
                     "manager_comments = ?, reviewed_date = CURRENT_TIMESTAMP " +
                     "WHERE leave_application_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, LeaveApplication.STATUS_REJECTED);
            pstmt.setString(2, managerId);
            pstmt.setString(3, comments);
            pstmt.setInt(4, leaveApplicationId);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                logger.info("Leave rejected: {}", leaveApplicationId);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error rejecting leave", e);
            throw e;
        }
        
        return false;
    }
    
    @Override
    public LeaveApplication getLeaveApplicationById(int leaveApplicationId) throws SQLException {
        logger.debug("Getting leave application by ID: {}", leaveApplicationId);
        
        String sql = "SELECT la.*, lt.leave_type_name, " +
                     "e.first_name || ' ' || e.last_name AS employee_name, " +
                     "m.first_name || ' ' || m.last_name AS reviewer_name " +
                     "FROM leave_applications la " +
                     "INNER JOIN leave_types lt ON la.leave_type_id = lt.leave_type_id " +
                     "INNER JOIN employees e ON la.employee_id = e.employee_id " +
                     "LEFT JOIN employees m ON la.reviewed_by = m.employee_id " +
                     "WHERE la.leave_application_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, leaveApplicationId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractLeaveApplicationFromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.error("Error getting leave application", e);
            throw e;
        }
        
        return null;
    }
    
    @Override
    public List<LeaveApplication> getLeaveApplicationsByEmployee(String employeeId) 
            throws SQLException {
        logger.debug("Getting leave applications for employee: {}", employeeId);
        
        List<LeaveApplication> applications = new ArrayList<>();
        String sql = "SELECT la.*, lt.leave_type_name, " +
                     "m.first_name || ' ' || m.last_name AS reviewer_name " +
                     "FROM leave_applications la " +
                     "INNER JOIN leave_types lt ON la.leave_type_id = lt.leave_type_id " +
                     "LEFT JOIN employees m ON la.reviewed_by = m.employee_id " +
                     "WHERE la.employee_id = ? " +
                     "ORDER BY la.applied_date DESC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employeeId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                applications.add(extractLeaveApplicationFromResultSet(rs));
            }
            
            logger.info("Leave applications retrieved: {}", applications.size());
        } catch (SQLException e) {
            logger.error("Error getting leave applications", e);
            throw e;
        }
        
        return applications;
    }
    
    @Override
    public List<LeaveApplication> getPendingLeavesByEmployee(String employeeId) 
            throws SQLException {
        logger.debug("Getting pending leaves for employee: {}", employeeId);
        
        List<LeaveApplication> applications = new ArrayList<>();
        String sql = "SELECT la.*, lt.leave_type_name " +
                     "FROM leave_applications la " +
                     "INNER JOIN leave_types lt ON la.leave_type_id = lt.leave_type_id " +
                     "WHERE la.employee_id = ? AND la.status = ? " +
                     "ORDER BY la.applied_date DESC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employeeId);
            pstmt.setString(2, LeaveApplication.STATUS_PENDING);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                applications.add(extractLeaveApplicationFromResultSet(rs));
            }
            
            logger.info("Pending leaves retrieved: {}", applications.size());
        } catch (SQLException e) {
            logger.error("Error getting pending leaves", e);
            throw e;
        }
        
        return applications;
    }
    
    @Override
    public List<LeaveApplication> getLeaveApplicationsByManager(String managerId) 
            throws SQLException {
        logger.debug("Getting leave applications for manager: {}", managerId);
        
        List<LeaveApplication> applications = new ArrayList<>();
        String sql = "SELECT la.*, lt.leave_type_name, " +
                     "e.first_name || ' ' || e.last_name AS employee_name " +
                     "FROM leave_applications la " +
                     "INNER JOIN leave_types lt ON la.leave_type_id = lt.leave_type_id " +
                     "INNER JOIN employees e ON la.employee_id = e.employee_id " +
                     "WHERE e.manager_id = ? " +
                     "ORDER BY la.applied_date DESC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, managerId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                applications.add(extractLeaveApplicationFromResultSet(rs));
            }
            
            logger.info("Leave applications for manager retrieved: {}", applications.size());
        } catch (SQLException e) {
            logger.error("Error getting leave applications by manager", e);
            throw e;
        }
        
        return applications;
    }
    
    @Override
    public List<LeaveApplication> getPendingLeavesByManager(String managerId) 
            throws SQLException {
        logger.debug("Getting pending leaves for manager: {}", managerId);
        
        List<LeaveApplication> applications = new ArrayList<>();
        String sql = "SELECT la.*, lt.leave_type_name, " +
                     "e.first_name || ' ' || e.last_name AS employee_name " +
                     "FROM leave_applications la " +
                     "INNER JOIN leave_types lt ON la.leave_type_id = lt.leave_type_id " +
                     "INNER JOIN employees e ON la.employee_id = e.employee_id " +
                     "WHERE e.manager_id = ? AND la.status = ? " +
                     "ORDER BY la.applied_date DESC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, managerId);
            pstmt.setString(2, LeaveApplication.STATUS_PENDING);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                applications.add(extractLeaveApplicationFromResultSet(rs));
            }
            
            logger.info("Pending leaves for manager retrieved: {}", applications.size());
        } catch (SQLException e) {
            logger.error("Error getting pending leaves by manager", e);
            throw e;
        }
        
        return applications;
    }
    
    @Override
    public List<LeaveBalance> getLeaveBalances(String employeeId, int year) throws SQLException {
        logger.debug("Getting leave balances for employee: {} year: {}", employeeId, year);
        
        List<LeaveBalance> balances = new ArrayList<>();
        String sql = "SELECT lb.*, lt.leave_type_name " +
                     "FROM leave_balances lb " +
                     "INNER JOIN leave_types lt ON lb.leave_type_id = lt.leave_type_id " +
                     "WHERE lb.employee_id = ? AND lb.year = ? " +
                     "ORDER BY lt.leave_type_id";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employeeId);
            pstmt.setInt(2, year);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                LeaveBalance balance = extractLeaveBalanceFromResultSet(rs);
                balance.setLeaveTypeName(rs.getString("leave_type_name"));
                balances.add(balance);
            }
            
            logger.info("Leave balances retrieved: {}", balances.size());
        } catch (SQLException e) {
            logger.error("Error getting leave balances", e);
            throw e;
        }
        
        return balances;
    }
    
    @Override
    public LeaveBalance getLeaveBalance(String employeeId, int leaveTypeId, int year) 
            throws SQLException {
        logger.debug("Getting leave balance: emp={}, type={}, year={}", 
                    employeeId, leaveTypeId, year);
        
        String sql = "SELECT lb.*, lt.leave_type_name " +
                     "FROM leave_balances lb " +
                     "INNER JOIN leave_types lt ON lb.leave_type_id = lt.leave_type_id " +
                     "WHERE lb.employee_id = ? AND lb.leave_type_id = ? AND lb.year = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employeeId);
            pstmt.setInt(2, leaveTypeId);
            pstmt.setInt(3, year);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                LeaveBalance balance = extractLeaveBalanceFromResultSet(rs);
                balance.setLeaveTypeName(rs.getString("leave_type_name"));
                return balance;
            }
        } catch (SQLException e) {
            logger.error("Error getting leave balance", e);
            throw e;
        }
        
        return null;
    }
    
    @Override
    public boolean updateLeaveBalance(LeaveBalance leaveBalance) throws SQLException {
        logger.debug("Updating leave balance: {}", leaveBalance.getLeaveBalanceId());
        
        String sql = "UPDATE leave_balances SET total_allocated = ?, used_leaves = ?, " +
                     "available_leaves = ? WHERE leave_balance_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, leaveBalance.getTotalAllocated());
            pstmt.setInt(2, leaveBalance.getUsedLeaves());
            pstmt.setInt(3, leaveBalance.getAvailableLeaves());
            pstmt.setInt(4, leaveBalance.getLeaveBalanceId());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                logger.info("Leave balance updated: {}", leaveBalance.getLeaveBalanceId());
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error updating leave balance", e);
            throw e;
        }
        
        return false;
    }
    
    @Override
    public boolean assignLeaveBalance(String employeeId, int leaveTypeId, 
                                     int totalAllocated, int year) throws SQLException {
        logger.debug("Assigning leave balance: emp={}, type={}, allocated={}, year={}", 
                    employeeId, leaveTypeId, totalAllocated, year);
        
        String sql = "INSERT INTO leave_balances (leave_balance_id, employee_id, leave_type_id, " +
                     "total_allocated, used_leaves, available_leaves, year) " +
                     "VALUES (seq_leave_balance_id.NEXTVAL, ?, ?, ?, 0, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employeeId);
            pstmt.setInt(2, leaveTypeId);
            pstmt.setInt(3, totalAllocated);
            pstmt.setInt(4, totalAllocated);
            pstmt.setInt(5, year);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                logger.info("Leave balance assigned successfully");
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error assigning leave balance", e);
            throw e;
        }
        
        return false;
    }
    
    @Override
    public List<LeaveType> getAllLeaveTypes() throws SQLException {
        logger.debug("Getting all leave types");
        
        List<LeaveType> leaveTypes = new ArrayList<>();
        String sql = "SELECT * FROM leave_types ORDER BY leave_type_id";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                LeaveType leaveType = new LeaveType();
                leaveType.setLeaveTypeId(rs.getInt("leave_type_id"));
                leaveType.setLeaveTypeName(rs.getString("leave_type_name"));
                leaveType.setDescription(rs.getString("description"));
                leaveType.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                leaveTypes.add(leaveType);
            }
            
            logger.info("Leave types retrieved: {}", leaveTypes.size());
        } catch (SQLException e) {
            logger.error("Error getting leave types", e);
            throw e;
        }
        
        return leaveTypes;
    }
    
    @Override
    public LeaveType getLeaveTypeById(int leaveTypeId) throws SQLException {
        logger.debug("Getting leave type by ID: {}", leaveTypeId);
        
        String sql = "SELECT * FROM leave_types WHERE leave_type_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, leaveTypeId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                LeaveType leaveType = new LeaveType();
                leaveType.setLeaveTypeId(rs.getInt("leave_type_id"));
                leaveType.setLeaveTypeName(rs.getString("leave_type_name"));
                leaveType.setDescription(rs.getString("description"));
                leaveType.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                return leaveType;
            }
        } catch (SQLException e) {
            logger.error("Error getting leave type", e);
            throw e;
        }
        
        return null;
    }
    
    @Override
    public List<Holiday> getHolidaysByYear(int year) throws SQLException {
        logger.debug("Getting holidays for year: {}", year);
        
        List<Holiday> holidays = new ArrayList<>();
        String sql = "SELECT * FROM holidays WHERE year = ? ORDER BY holiday_date";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, year);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Holiday holiday = new Holiday();
                holiday.setHolidayId(rs.getInt("holiday_id"));
                holiday.setHolidayName(rs.getString("holiday_name"));
                holiday.setHolidayDate(rs.getDate("holiday_date").toLocalDate());
                holiday.setYear(rs.getInt("year"));
                holiday.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                holidays.add(holiday);
            }
            
            logger.info("Holidays retrieved: {}", holidays.size());
        } catch (SQLException e) {
            logger.error("Error getting holidays", e);
            throw e;
        }
        
        return holidays;
    }
    
    @Override
    public boolean addHoliday(Holiday holiday) throws SQLException {
        logger.debug("Adding holiday: {}", holiday.getHolidayName());
        
        String sql = "INSERT INTO holidays (holiday_id, holiday_name, holiday_date, year) " +
                     "VALUES (seq_holiday_id.NEXTVAL, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, holiday.getHolidayName());
            pstmt.setDate(2, java.sql.Date.valueOf(holiday.getHolidayDate()));
            pstmt.setInt(3, holiday.getYear());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                logger.info("Holiday added successfully");
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error adding holiday", e);
            throw e;
        }
        
        return false;
    }
    
    @Override
    public int getAvailableLeaves(String employeeId, int leaveTypeId, int year) 
            throws SQLException {
        String sql = "SELECT available_leaves FROM leave_balances " +
                     "WHERE employee_id = ? AND leave_type_id = ? AND year = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employeeId);
            pstmt.setInt(2, leaveTypeId);
            pstmt.setInt(3, year);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("available_leaves");
            }
        } catch (SQLException e) {
            logger.error("Error getting available leaves", e);
            throw e;
        }
        
        return 0;
    }
    
    @Override
    public boolean hasOverlappingLeaves(String employeeId, LocalDate startDate, 
                                       LocalDate endDate) throws SQLException {
        String sql = "SELECT COUNT(*) FROM leave_applications " +
                     "WHERE employee_id = ? AND status IN (?, ?) " +
                     "AND ((start_date BETWEEN ? AND ?) OR (end_date BETWEEN ? AND ?) " +
                     "OR (start_date <= ? AND end_date >= ?))";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employeeId);
            pstmt.setString(2, LeaveApplication.STATUS_PENDING);
            pstmt.setString(3, LeaveApplication.STATUS_APPROVED);
            pstmt.setDate(4, java.sql.Date.valueOf(startDate));
            pstmt.setDate(5, java.sql.Date.valueOf(endDate));
            pstmt.setDate(6, java.sql.Date.valueOf(startDate));
            pstmt.setDate(7, java.sql.Date.valueOf(endDate));
            pstmt.setDate(8, java.sql.Date.valueOf(startDate));
            pstmt.setDate(9, java.sql.Date.valueOf(endDate));
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            logger.error("Error checking overlapping leaves", e);
            throw e;
        }
        
        return false;
    }
    
    /**
     * Helper method to extract LeaveApplication from ResultSet
     */
    private LeaveApplication extractLeaveApplicationFromResultSet(ResultSet rs) 
            throws SQLException {
        LeaveApplication app = new LeaveApplication();
        app.setLeaveApplicationId(rs.getInt("leave_application_id"));
        app.setEmployeeId(rs.getString("employee_id"));
        app.setLeaveTypeId(rs.getInt("leave_type_id"));
        app.setStartDate(rs.getDate("start_date").toLocalDate());
        app.setEndDate(rs.getDate("end_date").toLocalDate());
        app.setTotalDays(rs.getInt("total_days"));
        app.setReason(rs.getString("reason"));
        app.setStatus(rs.getString("status"));
        app.setManagerComments(rs.getString("manager_comments"));
        
        if (rs.getTimestamp("applied_date") != null) {
            app.setAppliedDate(rs.getTimestamp("applied_date").toLocalDateTime());
        }
        if (rs.getTimestamp("reviewed_date") != null) {
            app.setReviewedDate(rs.getTimestamp("reviewed_date").toLocalDateTime());
        }
        
        app.setReviewedBy(rs.getString("reviewed_by"));
        
        // Additional fields if present
        try {
            app.setLeaveTypeName(rs.getString("leave_type_name"));
        } catch (SQLException e) {
            // Column may not exist in all queries
        }
        try {
            app.setEmployeeName(rs.getString("employee_name"));
        } catch (SQLException e) {
            // Column may not exist in all queries
        }
        try {
            app.setReviewerName(rs.getString("reviewer_name"));
        } catch (SQLException e) {
            // Column may not exist in all queries
        }
        
        return app;
    }
    
    /**
     * Helper method to extract LeaveBalance from ResultSet
     */
    private LeaveBalance extractLeaveBalanceFromResultSet(ResultSet rs) throws SQLException {
        LeaveBalance balance = new LeaveBalance();
        balance.setLeaveBalanceId(rs.getInt("leave_balance_id"));
        balance.setEmployeeId(rs.getString("employee_id"));
        balance.setLeaveTypeId(rs.getInt("leave_type_id"));
        balance.setTotalAllocated(rs.getInt("total_allocated"));
        balance.setUsedLeaves(rs.getInt("used_leaves"));
        balance.setAvailableLeaves(rs.getInt("available_leaves"));
        balance.setYear(rs.getInt("year"));
        
        if (rs.getTimestamp("created_at") != null) {
            balance.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        if (rs.getTimestamp("updated_at") != null) {
            balance.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        }
        return balance;
    }
 }