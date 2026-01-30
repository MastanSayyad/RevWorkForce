package com.revature.revworkforce.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for PasswordUtil
 */
public class PasswordUtilTest {
    
    @Test
    @DisplayName("Test password hashing")
    public void testHashPassword() {
        String plainPassword = "password123";
        String hashedPassword = PasswordUtil.hashPassword(plainPassword);
        
        assertNotNull(hashedPassword, "Hashed password should not be null");
        assertNotEquals(plainPassword, hashedPassword, "Hashed password should be different from plain password");
        assertTrue(hashedPassword.startsWith("$2a$"), "BCrypt hash should start with $2a$");
    }
    
    @Test
    @DisplayName("Test password verification - correct password")
    public void testVerifyPassword_Success() {
        String plainPassword = "password123";
        String hashedPassword = PasswordUtil.hashPassword(plainPassword);
        
        boolean result = PasswordUtil.verifyPassword(plainPassword, hashedPassword);
        assertTrue(result, "Password verification should succeed for correct password");
    }
    
    @Test
    @DisplayName("Test password verification - incorrect password")
    public void testVerifyPassword_Failure() {
        String plainPassword = "password123";
        String wrongPassword = "wrongpassword";
        String hashedPassword = PasswordUtil.hashPassword(plainPassword);
        
        boolean result = PasswordUtil.verifyPassword(wrongPassword, hashedPassword);
        assertFalse(result, "Password verification should fail for incorrect password");
    }
    
    @Test
    @DisplayName("Test password hashing produces different hashes")
    public void testHashPassword_DifferentHashes() {
        String plainPassword = "password123";
        String hash1 = PasswordUtil.hashPassword(plainPassword);
        String hash2 = PasswordUtil.hashPassword(plainPassword);
        
        assertNotEquals(hash1, hash2, "Same password should produce different hashes (due to salt)");
        
        // But both should verify correctly
        assertTrue(PasswordUtil.verifyPassword(plainPassword, hash1));
        assertTrue(PasswordUtil.verifyPassword(plainPassword, hash2));
    }
}