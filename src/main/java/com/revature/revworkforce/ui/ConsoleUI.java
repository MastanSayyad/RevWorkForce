package com.revature.revworkforce.ui;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.revworkforce.exception.AuthenticationException;
import com.revature.revworkforce.model.Employee;
import com.revature.revworkforce.model.Role;
import com.revature.revworkforce.service.AuthService;
import com.revature.revworkforce.service.AuthServiceImpl;
import com.revature.revworkforce.service.NotificationService;
import com.revature.revworkforce.service.NotificationServiceImpl;
import com.revature.revworkforce.util.SessionManager;

/**
 * Main console UI for the application
 */
public class ConsoleUI {
    
    private static final Logger logger = LogManager.getLogger(ConsoleUI.class);
    private Scanner scanner;
    private AuthService authService;
    private NotificationService notificationService;
    
    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.authService = new AuthServiceImpl();
        this.notificationService = new NotificationServiceImpl();
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
                int choice = MenuHelper.getIntInput(scanner, "Enter your choice: ");
                
                switch (choice) {
                    case 1:
                        handleLogin();
                        break;
                    case 2:
                        MenuHelper.printInfo("Thank you for using RevWorkForce!");
                        running = false;
                        break;
                    default:
                        MenuHelper.printError("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                logger.error("Error in main menu", e);
                MenuHelper.printError("An error occurred. Please try again.");
            }
        }
        
        scanner.close();
        System.out.println("\nGoodbye!");
    }
    
    private void displayWelcome() {
        System.out.println("\n");
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                            ║");
        System.out.println("║            REVWORKFORCE - HR MANAGEMENT SYSTEM             ║");
        System.out.println("║                      Version 1.1.0                         ║");
        System.out.println("║                                                            ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println();
    }
    
    private void displayMainMenu() {
        MenuHelper.printHeader("MAIN MENU");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        MenuHelper.printDivider();
    }
    
    private void handleLogin() {
        MenuHelper.printHeader("LOGIN");
        
        String employeeId = MenuHelper.getStringInput(scanner, "Employee ID: ");
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        try {
            Employee employee = authService.login(employeeId, password);
            
            if (employee != null) {
                MenuHelper.printSuccess("Login successful!");
                
                // Display unread notification count
                int unreadCount = notificationService.getUnreadCount(employee.getEmployeeId());
                if (unreadCount > 0) {
                    MenuHelper.printInfo("You have " + unreadCount + " unread notification(s).");
                }
                
                MenuHelper.pause(scanner);
                
                // Route to appropriate menu based on role
                routeToRoleMenu(employee);
            }
            
        } catch (AuthenticationException e) {
            MenuHelper.printError(e.getMessage());
            MenuHelper.pause(scanner);
        }
    }
    
    private void routeToRoleMenu(Employee employee) {
        // Check roles in priority order: ADMIN > MANAGER > EMPLOYEE
        if (authService.hasRole(employee, Role.ADMIN)) {
            logger.info("Routing to Admin menu");
            AdminMenu adminMenu = new AdminMenu(scanner);
            adminMenu.display();
        } else if (authService.hasRole(employee, Role.MANAGER)) {
            logger.info("Routing to Manager menu");
            ManagerMenu managerMenu = new ManagerMenu(scanner);
            managerMenu.display();
        } else {
            logger.info("Routing to Employee menu");
            EmployeeMenu employeeMenu = new EmployeeMenu(scanner);
            employeeMenu.display();
        }
        
        // Logout after menu exits
        authService.logout();
    }
}