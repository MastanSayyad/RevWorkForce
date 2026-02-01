package com.revature.revworkforce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.revworkforce.model.AuditLog;
import com.revature.revworkforce.util.DatabaseUtil;

/**
 * Implementation of AuditLogDAO interface
 */
public class AuditLogDAOImpl implements AuditLogDAO {
    
    private static final Logger logger = LogManager.getLogger(AuditLogDAOImpl.class);
    
    @Override
    public boolean createAuditLog(AuditLog auditLog) throws SQLException {
        logger.debug("Creating audit log: {}", auditLog.getAction());
        
        String sql = "INSERT INTO audit_logs (log_id, employee_id, action, table_name, " +
                     "record_id, old_value, new_value, ip_address) " +
                     "VALUES (seq_audit_log_id.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, auditLog.getEmployeeId());
            pstmt.setString(2, auditLog.getAction());
            pstmt.setString(3, auditLog.getTableName());
            pstmt.setString(4, auditLog.getRecordId());
            pstmt.setString(5, auditLog.getOldValue());
            pstmt.setString(6, auditLog.getNewValue());
            pstmt.setString(7, auditLog.getIpAddress());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                logger.info("Audit log created: {}", auditLog.getAction());
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error creating audit log", e);
            throw e;
        }
        
        return false;
    }
    
    @Override
    public List<AuditLog> getAuditLogsByEmployee(String employeeId) throws SQLException {
        logger.debug("Getting audit logs for employee: {}", employeeId);
        
        List<AuditLog> auditLogs = new ArrayList<>();
        String sql = "SELECT * FROM audit_logs WHERE employee_id = ? ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employeeId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                auditLogs.add(extractAuditLogFromResultSet(rs));
            }
            
            logger.info("Audit logs retrieved: {}", auditLogs.size());
        } catch (SQLException e) {
            logger.error("Error getting audit logs by employee", e);
            throw e;
        }
        
        return auditLogs;
    }
    
    @Override
    public List<AuditLog> getAuditLogsByTable(String tableName) throws SQLException {
        logger.debug("Getting audit logs for table: {}", tableName);
        
        List<AuditLog> auditLogs = new ArrayList<>();
        String sql = "SELECT * FROM audit_logs WHERE table_name = ? ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, tableName);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                auditLogs.add(extractAuditLogFromResultSet(rs));
            }
            
            logger.info("Audit logs retrieved: {}", auditLogs.size());
        } catch (SQLException e) {
            logger.error("Error getting audit logs by table", e);
            throw e;
        }
        
        return auditLogs;
    }
    
    @Override
    public List<AuditLog> getAuditLogsByAction(String action) throws SQLException {
        logger.debug("Getting audit logs for action: {}", action);
        
        List<AuditLog> auditLogs = new ArrayList<>();
        String sql = "SELECT * FROM audit_logs WHERE action = ? ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, action);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                auditLogs.add(extractAuditLogFromResultSet(rs));
            }
            
            logger.info("Audit logs retrieved: {}", auditLogs.size());
        } catch (SQLException e) {
            logger.error("Error getting audit logs by action", e);
            throw e;
        }
        
        return auditLogs;
    }
    
    @Override
    public List<AuditLog> getRecentAuditLogs(int limit) throws SQLException {
        logger.debug("Getting recent {} audit logs", limit);
        
        List<AuditLog> auditLogs = new ArrayList<>();
        String sql = "SELECT * FROM audit_logs ORDER BY created_at DESC FETCH FIRST ? ROWS ONLY";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                auditLogs.add(extractAuditLogFromResultSet(rs));
            }
            
            logger.info("Recent audit logs retrieved: {}", auditLogs.size());
        } catch (SQLException e) {
            logger.error("Error getting recent audit logs", e);
            throw e;
        }
        
        return auditLogs;
    }
    
    @Override
    public List<AuditLog> getAllAuditLogs() throws SQLException {
        logger.debug("Getting all audit logs");
        
        List<AuditLog> auditLogs = new ArrayList<>();
        String sql = "SELECT * FROM audit_logs ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                auditLogs.add(extractAuditLogFromResultSet(rs));
            }
            
            logger.info("All audit logs retrieved: {}", auditLogs.size());
        } catch (SQLException e) {
            logger.error("Error getting all audit logs", e);
            throw e;
        }
        
        return auditLogs;
    }
    
    /**
     * Helper method to extract AuditLog from ResultSet
     */
    private AuditLog extractAuditLogFromResultSet(ResultSet rs) throws SQLException {
        AuditLog auditLog = new AuditLog();
        auditLog.setLogId(rs.getInt("log_id"));
        auditLog.setEmployeeId(rs.getString("employee_id"));
        auditLog.setAction(rs.getString("action"));
        auditLog.setTableName(rs.getString("table_name"));
        auditLog.setRecordId(rs.getString("record_id"));
        auditLog.setOldValue(rs.getString("old_value"));
        auditLog.setNewValue(rs.getString("new_value"));
        auditLog.setIpAddress(rs.getString("ip_address"));
        
        if (rs.getTimestamp("created_at") != null) {
            auditLog.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        
        return auditLog;
    }
}