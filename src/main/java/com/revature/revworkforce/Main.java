package com.revature.revworkforce;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.revature.revworkforce.ui.ConsoleUI;

/**
 * Main entry point for RevWorkForce Application
 * 
 * @author Mastan-Sayyad
 * @version v1.2.0
 */
public class Main {
    
    private static final Logger logger = LogManager.getLogger(Main.class);
    
    public static void main(String[] args) {
        logger.info("========================================");
        logger.info("RevWorkForce Application Started");
        logger.info("========================================");
        
        try {
            ConsoleUI ui = new ConsoleUI();
            ui.start();
        } catch (Exception e) {
            logger.error("Application error occurred", e);
            System.err.println("An unexpected error occurred. Please contact support.");
        } finally {
            logger.info("========================================");
            logger.info("RevWorkForce Application Ended");
            logger.info("========================================");
        }
    }
}
