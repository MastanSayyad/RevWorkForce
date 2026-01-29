package com.revature.revworkforce.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.revworkforce.model.Employee;
import com.revature.revworkforce.model.Goal;
import com.revature.revworkforce.model.Holiday;
import com.revature.revworkforce.model.LeaveApplication;
import com.revature.revworkforce.model.LeaveBalance;
import com.revature.revworkforce.model.LeaveType;
import com.revature.revworkforce.model.Notification;
import com.revature.revworkforce.model.PerformanceReview;
import com.revature.revworkforce.service.EmployeeService;
import com.revature.revworkforce.service.EmployeeServiceImpl;
import com.revature.revworkforce.service.LeaveService;
import com.revature.revworkforce.service.LeaveServiceImpl;
import com.revature.revworkforce.service.NotificationService;
import com.revature.revworkforce.service.NotificationServiceImpl;
import com.revature.revworkforce.service.PerformanceService;
import com.revature.revworkforce.service.PerformanceServiceImpl;
import com.revature.revworkforce.util.SessionManager;

/**
 * Employee Menu UI
 */
public class EmployeeMenu {
    
    private static final Logger logger = LogManager.getLogger(EmployeeMenu.class);
    private Scanner scanner;
    private EmployeeService employeeService;
    private LeaveService leaveService;
    private PerformanceService performanceService;
    private NotificationService notificationService;
    
    public EmployeeMenu(Scanner scanner) {
        this.scanner = scanner;
        this.employeeService = new EmployeeServiceImpl();
        this.leaveService = new LeaveServiceImpl();
        this.performanceService = new PerformanceServiceImpl();
        this.notificationService = new NotificationServiceImpl();
    }
    
    public void display() {
        Employee currentUser = SessionManager.getCurrentUser();
        
        if (currentUser == null) {
            MenuHelper.printError("Session expired. Please login again.");
            return;
        }
        
        boolean running = true;
        while (running) {
            try {
                displayMenu(currentUser);
                int choice = MenuHelper.getIntInput(scanner, "Enter your choice: ");
                
                switch (choice) {
                    case 1:
                        viewProfile();
                        break;
                    case 2:
                        updateProfile();
                        break;
                    case 3:
                        viewLeaveBalance();
                        break;
                    case 4:
                        applyForLeave();
                        break;
                    case 5:
                        viewLeaveApplications();
                        break;
                    case 6:
                        cancelLeave();
                        break;
                    case 7:
                        viewHolidays();
                        break;
                    case 8:
                        viewGoals();
                        break;
                    case 9:
                        addGoal();
                        break;
                    case 10:
                        updateGoalProgress();
                        break;
                    case 11:
                        viewNotifications();
                        break;
                    case 12:
                        changePassword();
                        break;
                    case 0:
                        running = false;
                        MenuHelper.printInfo("Logging out...");
                        break;
                    default:
                        MenuHelper.printError("Invalid choice. Please try again.");
                }
                
                if (running && choice != 0) {
                    MenuHelper.pause(scanner);
                }
                
            } catch (Exception e) {
                logger.error("Error in employee menu", e);
                MenuHelper.printError("An error occurred: " + e.getMessage());
                MenuHelper.pause(scanner);
            }
        }
    }
    
    private void displayMenu(Employee employee) {
        MenuHelper.printHeader("EMPLOYEE MENU");
        System.out.println("Welcome, " + employee.getFullName() + "!");
        System.out.println();
        System.out.println("PROFILE & SETTINGS");
        System.out.println("  1. View My Profile");
        System.out.println("  2. Update Profile");
        System.out.println();
        System.out.println("LEAVE MANAGEMENT");
        System.out.println("  3. View Leave Balance");
        System.out.println("  4. Apply for Leave");
        System.out.println("  5. View My Leave Applications");
        System.out.println("  6. Cancel Pending Leave");
        System.out.println("  7. View Holiday Calendar");
        System.out.println();
        System.out.println("PERFORMANCE & GOALS");
        System.out.println("  8. View My Goals");
        System.out.println("  9. Add New Goal");
        System.out.println(" 10. Update Goal Progress");
        System.out.println();
        System.out.println("NOTIFICATIONS");
        System.out.println(" 11. View Notifications");
        System.out.println();
        System.out.println(" 12. Change Password");
        System.out.println("  0. Logout");
        MenuHelper.printDivider();
    }
    
    private void viewProfile() {
        Employee currentUser = SessionManager.getCurrentUser();
        Employee employee = employeeService.getEmployeeDetails(currentUser.getEmployeeId());
        
        if (employee != null) {
            MenuHelper.displayEmployeeDetails(employee);
        } else {
            MenuHelper.printError("Failed to retrieve profile.");
        }
    }
    
    private void updateProfile() {
        MenuHelper.printHeader("UPDATE PROFILE");
        
        Employee currentUser = SessionManager.getCurrentUser();
        Employee employee = employeeService.getEmployeeDetails(currentUser.getEmployeeId());
        
        if (employee == null) {
            MenuHelper.printError("Failed to retrieve profile.");
            return;
        }
        
        System.out.println("Leave blank to keep current value.");
        System.out.println();
        
        String phone = MenuHelper.getStringInput(scanner, "Phone (" + employee.getPhone() + "): ");
        if (!phone.isEmpty()) {
            employee.setPhone(phone);
        }
        
        String address = MenuHelper.getStringInput(scanner, "Address (" + employee.getAddress() + "): ");
        if (!address.isEmpty()) {
            employee.setAddress(address);
        }
        
        String emergency = MenuHelper.getStringInput(scanner, 
            "Emergency Contact (" + employee.getEmergencyContact() + "): ");
        if (!emergency.isEmpty()) {
            employee.setEmergencyContact(emergency);
        }
        
        try {
            if (employeeService.updateProfile(employee)) {
                MenuHelper.printSuccess("Profile updated successfully!");
            } else {
                MenuHelper.printError("Failed to update profile.");
            }
        } catch (Exception e) {
            MenuHelper.printError(e.getMessage());
        }
    }
    
    private void viewLeaveBalance() {
        Employee currentUser = SessionManager.getCurrentUser();
        int currentYear = LocalDate.now().getYear();
        
        List<LeaveBalance> balances = leaveService.getLeaveBalances(
            currentUser.getEmployeeId(), currentYear);
        
        MenuHelper.displayLeaveBalances(balances);
    }
    
    private void applyForLeave() {
        MenuHelper.printHeader("APPLY FOR LEAVE");
        
        Employee currentUser = SessionManager.getCurrentUser();
        
        // Display leave types
        List<LeaveType> leaveTypes = leaveService.getAllLeaveTypes();
        if (leaveTypes == null || leaveTypes.isEmpty()) {
            MenuHelper.printError("No leave types available.");
            return;
        }
        
        System.out.println("\nAvailable Leave Types:");
        for (LeaveType type : leaveTypes) {
            System.out.printf("%d. %s - %s%n", 
                type.getLeaveTypeId(), 
                type.getLeaveTypeName(), 
                type.getDescription());
        }
        
        int leaveTypeId = MenuHelper.getIntInput(scanner, "\nSelect leave type: ");
        LocalDate startDate = MenuHelper.getDateInput(scanner, "Start date");
        LocalDate endDate = MenuHelper.getDateInput(scanner, "End date");
        String reason = MenuHelper.getStringInput(scanner, "Reason: ");
        
        LeaveApplication application = new LeaveApplication();
        application.setEmployeeId(currentUser.getEmployeeId());
        application.setLeaveTypeId(leaveTypeId);
        application.setStartDate(startDate);
        application.setEndDate(endDate);
        application.setReason(reason);
        
        try {
            int applicationId = leaveService.applyForLeave(application);
            if (applicationId > 0) {
                MenuHelper.printSuccess("Leave application submitted successfully! ID: " + applicationId);
            } else {
                MenuHelper.printError("Failed to apply for leave.");
            }
        } catch (Exception e) {
            MenuHelper.printError(e.getMessage());
        }
    }
    
    private void viewLeaveApplications() {
        Employee currentUser = SessionManager.getCurrentUser();
        
        List<LeaveApplication> applications = leaveService.getEmployeeLeaveApplications(
            currentUser.getEmployeeId());
        
        MenuHelper.displayLeaveApplications(applications);
    }
    
    private void cancelLeave() {
        MenuHelper.printHeader("CANCEL LEAVE");
        
        Employee currentUser = SessionManager.getCurrentUser();
        
        // First show pending leaves
        List<LeaveApplication> applications = leaveService.getEmployeeLeaveApplications(
            currentUser.getEmployeeId());
        
        if (applications == null || applications.isEmpty()) {
            MenuHelper.printInfo("No leave applications found.");
            return;
        }
        
        // Filter and display only pending
        applications = applications.stream()
            .filter(app -> LeaveApplication.STATUS_PENDING.equals(app.getStatus()))
            .toList();
        
        if (applications.isEmpty()) {
            MenuHelper.printInfo("No pending leave applications to cancel.");
            return;
        }
        
        MenuHelper.displayLeaveApplications(applications);
        
        int leaveId = MenuHelper.getIntInput(scanner, "\nEnter Leave Application ID to cancel: ");
        
        if (MenuHelper.confirmAction(scanner, "Are you sure you want to cancel this leave?")) {
            try {
                if (leaveService.cancelLeave(leaveId, currentUser.getEmployeeId())) {
                    MenuHelper.printSuccess("Leave application cancelled successfully!");
                } else {
                    MenuHelper.printError("Failed to cancel leave application.");
                }
            } catch (Exception e) {
                MenuHelper.printError(e.getMessage());
            }
        }
    }
    
    private void viewHolidays() {
        int currentYear = LocalDate.now().getYear();
        
        List<Holiday> holidays = leaveService.getHolidays(currentYear);
        
        if (holidays == null || holidays.isEmpty()) {
            MenuHelper.printInfo("No holidays found for " + currentYear);
            return;
        }
        
        MenuHelper.printSubHeader("HOLIDAY CALENDAR " + currentYear);
        System.out.printf("%-30s %-15s%n", "Holiday", "Date");
        MenuHelper.printDivider();
        
        for (Holiday holiday : holidays) {
            System.out.printf("%-30s %-15s%n",
                holiday.getHolidayName(),
                MenuHelper.formatDate(holiday.getHolidayDate()));
        }
        MenuHelper.printDivider();
    }
    
    private void viewGoals() {
        Employee currentUser = SessionManager.getCurrentUser();
        
        List<Goal> goals = performanceService.getEmployeeGoals(currentUser.getEmployeeId());
        
        MenuHelper.displayGoals(goals);
    }
    
    private void addGoal() {
        MenuHelper.printHeader("ADD NEW GOAL");
        
        Employee currentUser = SessionManager.getCurrentUser();
        
        String description = MenuHelper.getStringInput(scanner, "Goal Description: ");
        LocalDate deadline = MenuHelper.getDateInput(scanner, "Deadline");
        
        System.out.println("\nPriority:");
        System.out.println("1. HIGH");
        System.out.println("2. MEDIUM");
        System.out.println("3. LOW");
        int priorityChoice = MenuHelper.getIntInput(scanner, "Select priority: ");
        
        String priority;
        switch (priorityChoice) {
            case 1:
                priority = Goal.PRIORITY_HIGH;
                break;
            case 2:
                priority = Goal.PRIORITY_MEDIUM;
                break;
            case 3:
                priority = Goal.PRIORITY_LOW;
                break;
            default:
                priority = Goal.PRIORITY_MEDIUM;
                break;
        }

        
        String metrics = MenuHelper.getStringInput(scanner, "Success Metrics: ");
        
        Goal goal = new Goal();
        goal.setEmployeeId(currentUser.getEmployeeId());
        goal.setGoalDescription(description);
        goal.setDeadline(deadline);
        goal.setPriority(priority);
        goal.setSuccessMetrics(metrics);
        goal.setProgressPercentage(0);
        goal.setStatus(Goal.STATUS_NOT_STARTED);
        
        try {
            int goalId = performanceService.createGoal(goal);
            if (goalId > 0) {
                MenuHelper.printSuccess("Goal created successfully! ID: " + goalId);
            } else {
                MenuHelper.printError("Failed to create goal.");
            }
        } catch (Exception e) {
            MenuHelper.printError(e.getMessage());
        }
    }
    
    private void updateGoalProgress() {
        MenuHelper.printHeader("UPDATE GOAL PROGRESS");
        
        Employee currentUser = SessionManager.getCurrentUser();
        
        // Display active goals
        List<Goal> goals = performanceService.getActiveGoals(currentUser.getEmployeeId());
        
        if (goals == null || goals.isEmpty()) {
            MenuHelper.printInfo("No active goals found.");
            return;
        }
        
        MenuHelper.displayGoals(goals);
        
        int goalId = MenuHelper.getIntInput(scanner, "\nEnter Goal ID: ");
        int progress = MenuHelper.getIntInput(scanner, "Enter Progress (0-100): ");
        
        try {
            if (performanceService.updateGoalProgress(goalId, progress)) {
                MenuHelper.printSuccess("Goal progress updated successfully!");
            } else {
                MenuHelper.printError("Failed to update goal progress.");
            }
        } catch (Exception e) {
            MenuHelper.printError(e.getMessage());
        }
    }
    
    private void viewNotifications() {
        Employee currentUser = SessionManager.getCurrentUser();
        
        List<Notification> notifications = notificationService.getUnreadNotifications(
            currentUser.getEmployeeId());
        
        MenuHelper.displayNotifications(notifications);
        
        if (notifications != null && !notifications.isEmpty()) {
            if (MenuHelper.confirmAction(scanner, "\nMark all as read?")) {
                notificationService.markAllAsRead(currentUser.getEmployeeId());
                MenuHelper.printSuccess("All notifications marked as read.");
            }
            }
    }
    
    private void changePassword() {
        MenuHelper.printHeader("CHANGE PASSWORD");
        
        Employee currentUser = SessionManager.getCurrentUser();
        
        System.out.print("Current Password: ");
        String currentPassword = scanner.nextLine();
        
        System.out.print("New Password: ");
        String newPassword = scanner.nextLine();
        
        System.out.print("Confirm New Password: ");
        String confirmPassword = scanner.nextLine();
        
        if (!newPassword.equals(confirmPassword)) {
            MenuHelper.printError("Passwords do not match!");
            return;
        }
        
        try {
            com.revature.revworkforce.service.AuthService authService = 
                new com.revature.revworkforce.service.AuthServiceImpl();
            
            if (authService.changePassword(currentUser.getEmployeeId(), currentPassword, newPassword)) {
                MenuHelper.printSuccess("Password changed successfully!");
            } else {
                MenuHelper.printError("Failed to change password.");
            }
        } catch (Exception e) {
            MenuHelper.printError(e.getMessage());
        }
    }
    }
