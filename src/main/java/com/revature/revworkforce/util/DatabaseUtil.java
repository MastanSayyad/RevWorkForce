package com.revature.revworkforce.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for database connection management
 */

public class DatabaseUtil {
    
    private static final Logger logger = LogManager.getLogger(DatabaseUtil.class);
    private static Properties properties = new Properties();
    
    static {
        try (InputStream input = DatabaseUtil.class.getClassLoader()
                .getResourceAsStream("db.properties")) {
            
            if (input == null) {
                logger.error("Unable to find db.properties");
                throw new RuntimeException("db.properties file not found");
            }
            
            properties.load(input);
            Class.forName(properties.getProperty("db.driver"));
            logger.info("Database driver loaded successfully");
            
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Failed to load database configuration", e);
            throw new RuntimeException("Database initialization failed", e);
        }
    }
    
    /**
     * Get database connection
     * @return Connection object
     * @throws SQLException if connection fails
     */
    
    public static Connection getConnection() throws SQLException {
        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");
        
        logger.debug("Attempting database connection");
        return DriverManager.getConnection(url, username, password);
    }
    
    /**
     * Close connection safely
     * @param conn Connection to close
     */
    
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                logger.debug("Database connection closed");
            } catch (SQLException e) {
                logger.error("Error closing connection", e);
            }
        }
    }
}