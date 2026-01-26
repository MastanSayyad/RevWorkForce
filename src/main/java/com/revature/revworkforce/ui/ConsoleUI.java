package com.revature.revworkforce.ui;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main console UI for the application
 */
public class ConsoleUI {
    
    private static final Logger logger = LogManager.getLogger(ConsoleUI.class);
    private Scanner scanner;
    
    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Start the application
     */
    public void start() {
        displayWelcome();
        
        boolean running = true;
        while (running) {
            try {
                displayMainMenu();
                int choice = getIntInput();
                
                switch (choice) {
                    case 1:
                        System.out.println("Login feature - Coming soon!");
                        break;
                    case 2:
                        System.out.println("Exit application");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                logger.error("Error in main menu", e);
                System.out.println("An error occurred. Please try again.");
            }
        }
        
        scanner.close();
        System.out.println("Thank you for using RevWorkForce!");
    }
    
    private void displayWelcome() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║     REVWORKFORCE - HR MANAGEMENT       ║");
        System.out.println("║          SYSTEM v1.0                   ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();
    }
    
    private void displayMainMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("Enter your choice: ");
    }
    
    private int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}