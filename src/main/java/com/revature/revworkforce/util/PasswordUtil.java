package com.revature.revworkforce.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for password hashing and verification using BCrypt
 */
public class PasswordUtil {
    
    private static final Logger logger = LogManager.getLogger(PasswordUtil.class);
    private static final int SALT_ROUNDS = 12;
    
    /**
     * Hash a plain text password
     * @param plainPassword Password to hash
     * @return Hashed password
     */
    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        
        logger.debug("Hashing password");
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(SALT_ROUNDS));
    }
    
    /**
     * 
     * Verify a plain password against a hashed password
     * @param plainPassword Plain text password
     * @param hashedPassword Hashed password from database
     * @return true if passwords match, false otherwise
     * 
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        
        logger.debug("Verifying password");
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}