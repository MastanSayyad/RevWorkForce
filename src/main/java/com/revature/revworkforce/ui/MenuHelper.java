package com.revature.revworkforce.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import com.revature.revworkforce.model.Employee;
import com.revature.revworkforce.model.LeaveApplication;
import com.revature.revworkforce.model.LeaveBalance;
import com.revature.revworkforce.model.Goal;
import com.revature.revworkforce.model.Notification;

/**
 * Helper class for UI operations
 */
public class MenuHelper {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    
    /**
     * Print a header
     */
    public static void printHeader(String title) {
        int width = 60;
        System.out.println("\n" + "=".repeat(width));
        System.out.println(centerText(title, width));
        System.out.println("=".repeat(width));
    }
    
    /**
     * Print a sub-header
     */
    public static void printSubHeader(String title) {
        System.out.println("\n" + "-".repeat(60));
        System.out.println(title);
        System.out.println("-".repeat(60));
    }
    
    /**
     * Center text within a width
     */
    private static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text;
    }
    
    /**
     * Print a divider
     */
    public static void printDivider() {
        System.out.println("-".repeat(60));
    }
    
    /**
     * Print success message
     */
    public static void printSuccess(String message) {
        System.out.println("\n✓ SUCCESS: " + message);
    }
    
    /**
     * Print error message
     */
    public static void printError(String message) {
        System.out.println("\n✗ ERROR: " + message);
    }
    
    /**
     * Print warning message
     */
    public static void printWarning(String message) {
        System.out.println("\n⚠ WARNING: " + message);
    }
    
    /**
     * Print info message
     */
    public static void printInfo(String message) {
        System.out.println("\nℹ INFO: " + message);
    }
    
    /**
     * Get integer input with validation
     */
    public static int getIntInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                printError("Invalid number. Please try again.");
            }
        }
    }
    
    /**
     * Get double input with validation
     */
    public static double getDoubleInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                printError("Invalid number. Please try again.");
            }
        }
    }
    
    /**
     * Get string input
     */
    public static String getStringInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    /**
     * Get date input with validation
     */
    public static LocalDate getDateInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt + " (yyyy-MM-dd): ");
                String input = scanner.nextLine().trim();
                return LocalDate.parse(input, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                printError("Invalid date format. Please use yyyy-MM-dd (e.g., 2025-03-15)");
            }
        }
    }
    
    /**
     * Format date for display
     */
    public static String formatDate(LocalDate date) {
        return date != null ? date.format(DISPLAY_FORMATTER) : "N/A";
    }
    
    /**
     * Confirm action
     */
    public static boolean confirmAction(Scanner scanner, String message) {
        System.out.print(message + " (y/n): ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("y") || input.equals("yes");
    }
    
    /**
     * Pause for user to press Enter
     */
    public static void pause(Scanner scanner) {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Display employee details in a formatted box
     */
    public static void displayEmployeeDetails(Employee employee) {
        printSubHeader("EMPLOYEE DETAILS");
        System.out.printf("Employee ID      : %s%n", employee.getEmployeeId());
        System.out.printf("Name             : %s %s%n", employee.getFirstName(), employee.getLastName());
        System.out.printf("Email            : %s%n", employee.getEmail());
        System.out.printf("Phone            : %s%n", employee.getPhone());
        System.out.printf("Department       : %s%n", employee.getDepartmentName() != null ? employee.getDepartmentName() : "N/A");
        System.out.printf("Designation      : %s%n", employee.getDesignationName() != null ? employee.getDesignationName() : "N/A");
        System.out.printf("Manager          : %s%n", employee.getManagerName() != null ? employee.getManagerName() : "N/A");
        System.out.printf("Joining Date     : %s%n", formatDate(employee.getJoiningDate()));
        
        if (employee.getRoles() != null && !employee.getRoles().isEmpty()) {
            System.out.print("Roles            : ");
            employee.getRoles().forEach(role -> System.out.print(role.getRoleName() + " "));
            System.out.println();
        }
        printDivider();
    }
    
    /**
     * Display leave balances in table format
     */
    public static void displayLeaveBalances(List<LeaveBalance> balances) {
        if (balances == null || balances.isEmpty()) {
            printInfo("No leave balances found.");
            return;
        }
        
        printSubHeader("LEAVE BALANCES");
        System.out.printf("%-20s %-15s %-15s %-15s%n", 
            "Leave Type", "Total", "Used", "Available");
        printDivider();
        
        for (LeaveBalance balance : balances) {
            System.out.printf("%-20s %-15d %-15d %-15d%n",
                balance.getLeaveTypeName(),
                balance.getTotalAllocated(),
                balance.getUsedLeaves(),
                balance.getAvailableLeaves());
        }
        printDivider();
    }
    
    /**
     * Display leave applications in table format
     */
    public static void displayLeaveApplications(List<LeaveApplication> applications) {
        if (applications == null || applications.isEmpty()) {
            printInfo("No leave applications found.");
            return;
        }
        
        printSubHeader("LEAVE APPLICATIONS");
        System.out.printf("%-5s %-12s %-12s %-12s %-5s %-15s%n", 
            "ID", "Type", "Start", "End", "Days", "Status");
        printDivider();
        
        for (LeaveApplication app : applications) {
            System.out.printf("%-5d %-12s %-12s %-12s %-5d %-15s%n",
                app.getLeaveApplicationId(),
                app.getLeaveTypeName() != null ? app.getLeaveTypeName() : "N/A",
                formatDate(app.getStartDate()),
                formatDate(app.getEndDate()),
                app.getTotalDays(),
                app.getStatus());
        }
        printDivider();
    }
    
    /**
     * Display goals in table format
     */
    public static void displayGoals(List<Goal> goals) {
        if (goals == null || goals.isEmpty()) {
            printInfo("No goals found.");
            return;
        }
        
        printSubHeader("GOALS");
        System.out.printf("%-5s %-40s %-12s %-10s %-10s%n", 
            "ID", "Description", "Deadline", "Priority", "Progress");
        printDivider();
        
        for (Goal goal : goals) {
            String description = goal.getGoalDescription();
            if (description.length() > 37) {
                description = description.substring(0, 37) + "...";
            }
            System.out.printf("%-5d %-40s %-12s %-10s %-10s%n",
                goal.getGoalId(),
                description,
                formatDate(goal.getDeadline()),
                goal.getPriority(),
                goal.getProgressPercentage() + "%");
        }
        printDivider();
    }
    
    /**
     * Display notifications
     */
    public static void displayNotifications(List<Notification> notifications) {
        if (notifications == null || notifications.isEmpty()) {
            printInfo("No notifications.");
            return;
        }
        
        printSubHeader("NOTIFICATIONS");
        
        for (Notification notif : notifications) {
            String status = notif.isRead() ? "[READ]" : "[UNREAD]";
            System.out.printf("%s [%s] %s%n", 
                status,
                notif.getNotificationType(),
                notif.getMessage());
            printDivider();
        }
    }
    
    /**
     * Display employees in table format
     */
    public static void displayEmployeeList(List<Employee> employees) {
        if (employees == null || employees.isEmpty()) {
            printInfo("No employees found.");
            return;
        }
        
        printSubHeader("EMPLOYEE LIST");
        System.out.printf("%-10s %-25s %-30s %-20s%n", 
            "Emp ID", "Name", "Email", "Department");
        printDivider();
        
        for (Employee emp : employees) {
            System.out.printf("%-10s %-25s %-30s %-20s%n",
                emp.getEmployeeId(),
                emp.getFullName(),
                emp.getEmail(),
                emp.getDepartmentName() != null ? emp.getDepartmentName() : "N/A");
        }
        printDivider();
    }
}