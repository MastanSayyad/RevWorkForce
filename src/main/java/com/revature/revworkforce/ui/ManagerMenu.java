package com.revature.revworkforce.ui;

import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.revworkforce.model.Employee;
import com.revature.revworkforce.model.Goal;
import com.revature.revworkforce.model.LeaveApplication;
import com.revature.revworkforce.model.PerformanceReview;
import com.revature.revworkforce.service.EmployeeService;
import com.revature.revworkforce.service.EmployeeServiceImpl;
import com.revature.revworkforce.service.LeaveService;
import com.revature.revworkforce.service.LeaveServiceImpl;
import com.revature.revworkforce.service.PerformanceService;
import com.revature.revworkforce.service.PerformanceServiceImpl;
import com.revature.revworkforce.util.SessionManager;

/**
 * Manager Menu UI
 * Managers have all employee features plus additional management features
 */
public class ManagerMenu {
    
    private static final Logger logger = LogManager.getLogger(ManagerMenu.class);
    private Scanner scanner;
    private EmployeeService employeeService;
    private LeaveService leaveService;
    private PerformanceService performanceService;
    private EmployeeMenu employeeMenu;
    
    public ManagerMenu(Scanner scanner) {
        this.scanner = scanner;
        this.employeeService = new EmployeeServiceImpl();
        this.leaveService = new LeaveServiceImpl();
        this.performanceService = new PerformanceServiceImpl();
        this.employeeMenu = new EmployeeMenu(scanner);
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
                        // Delegate to employee menu
                        employeeMenu.display();
                        running = false; // Return to main menu after employee menu
                        break;
                    case 2:
                        viewTeamMembers();
                        break;
                    case 3:
                        viewTeamLeaveBalances();
                        break;
                    case 4:                               
                        viewPendingLeaves();
                        break;
                    case 5:
                        approveRejectLeave();
                        break;
                    case 6:
                        viewTeamLeaveCalendar();
                        break;
                    case 7:
                        viewPendingPerformanceReviews();
                        break;
                    case 8:
                        reviewPerformance();
                        break;
                    case 9:
                        viewTeamGoals();
                        break;
                    case 0:
                        running = false;
                        MenuHelper.printInfo("Logging out...");
                        break;
                    default:
                        MenuHelper.printError("Invalid choice. Please try again.");
                }
                
                if (running && choice != 0 && choice != 1) {
                    MenuHelper.pause(scanner);
                }
                
            } catch (Exception e) {
                logger.error("Error in manager menu", e);
                MenuHelper.printError("An error occurred: " + e.getMessage());
                MenuHelper.pause(scanner);
            }
        }
    }
    
    private void displayMenu(Employee employee) {
        MenuHelper.printHeader("MANAGER MENU");
        System.out.println("Welcome, " + employee.getFullName() + " (Manager)");
        System.out.println();
        System.out.println("EMPLOYEE FEATURES");
        System.out.println("  1. Access Employee Menu");
        System.out.println();
        System.out.println("TEAM MANAGEMENT");
        System.out.println("  2. View Team Members");
        System.out.println("  3. View Team Leave Balances");        
        System.out.println();
        System.out.println("LEAVE APPROVALS");
        System.out.println("  4. View Pending Leave Requests");     
        System.out.println("  5. Approve/Reject Leave");
        System.out.println("  6. View Team Leave Calendar");
        System.out.println();
        System.out.println("PERFORMANCE MANAGEMENT");
        System.out.println("  7. View Pending Performance Reviews");
        System.out.println("  8. Review Team Member Performance");
        System.out.println("  9. View Team Goals");
        System.out.println();
        System.out.println("  0. Logout");
        MenuHelper.printDivider();
    }
    
    private void viewTeamMembers() {
        Employee currentUser = SessionManager.getCurrentUser();
        
        List<Employee> teamMembers = employeeService.getTeamMembers(currentUser.getEmployeeId());
        
        if (teamMembers == null || teamMembers.isEmpty()) {
            MenuHelper.printInfo("No team members found.");
            return;
        }
        
        MenuHelper.printSubHeader("TEAM MEMBERS");
        System.out.printf("%-10s %-25s %-30s %-20s%n", 
            "Emp ID", "Name", "Email", "Designation");
        MenuHelper.printDivider();
        
        for (Employee emp : teamMembers) {
            System.out.printf("%-10s %-25s %-30s %-20s%n",
                emp.getEmployeeId(),
                emp.getFullName(),
                emp.getEmail(),
                emp.getDesignationName() != null ? emp.getDesignationName() : "N/A");
        }
        MenuHelper.printDivider();
        System.out.println("Total Team Members: " + teamMembers.size());
    }
    
    private void viewPendingLeaves() {
        Employee currentUser = SessionManager.getCurrentUser();
        
        List<LeaveApplication> pendingLeaves = leaveService.getPendingLeavesForManager(
            currentUser.getEmployeeId());
        
        if (pendingLeaves == null || pendingLeaves.isEmpty()) {
            MenuHelper.printInfo("No pending leave requests.");
            return;
        }
        
        MenuHelper.printSubHeader("PENDING LEAVE REQUESTS");
        System.out.printf("%-5s %-15s %-12s %-12s %-12s %-5s %-20s%n", 
            "ID", "Employee", "Type", "Start", "End", "Days", "Reason");
        MenuHelper.printDivider();
        
        for (LeaveApplication app : pendingLeaves) {
            String reason = app.getReason();
            if (reason != null && reason.length() > 17) {
                reason = reason.substring(0, 17) + "...";
            }
            System.out.printf("%-5d %-15s %-12s %-12s %-12s %-5d %-20s%n",
                app.getLeaveApplicationId(),
                app.getEmployeeName() != null ? app.getEmployeeName() : "N/A",
                app.getLeaveTypeName() != null ? app.getLeaveTypeName() : "N/A",
                MenuHelper.formatDate(app.getStartDate()),
                MenuHelper.formatDate(app.getEndDate()),
                app.getTotalDays(),
                reason);
        }
        MenuHelper.printDivider();
    }
    
    private void approveRejectLeave() {
        MenuHelper.printHeader("APPROVE/REJECT LEAVE");
        
        Employee currentUser = SessionManager.getCurrentUser();
        
        // First show pending leaves
        List<LeaveApplication> pendingLeaves = leaveService.getPendingLeavesForManager(
            currentUser.getEmployeeId());
        
        if (pendingLeaves == null || pendingLeaves.isEmpty()) {
            MenuHelper.printInfo("No pending leave requests.");
            return;
        }
        
        // Display pending leaves
        viewPendingLeaves();
        
        int leaveId = MenuHelper.getIntInput(scanner, "\nEnter Leave Application ID: ");
        
        // Find the application
        LeaveApplication application = null;
        for (LeaveApplication app : pendingLeaves) {
            if (app.getLeaveApplicationId() == leaveId) {
                application = app;
                break;
            }
        }
        
        if (application == null) {
            MenuHelper.printError("Invalid Leave Application ID.");
            return;
        }
        
        // Show details
        System.out.println("\n--- Leave Details ---");
        System.out.println("Employee: " + application.getEmployeeName());
        System.out.println("Leave Type: " + application.getLeaveTypeName());
        System.out.println("Start Date: " + MenuHelper.formatDate(application.getStartDate()));
        System.out.println("End Date: " + MenuHelper.formatDate(application.getEndDate()));
        System.out.println("Total Days: " + application.getTotalDays());
        System.out.println("Reason: " + application.getReason());
        MenuHelper.printDivider();
        
        System.out.println("\n1. Approve");
        System.out.println("2. Reject");
        System.out.println("0. Cancel");
        
        int action = MenuHelper.getIntInput(scanner, "Select action: ");
        
        if (action == 0) {
            return;
        }
        
        String comments = MenuHelper.getStringInput(scanner, "Comments: ");
        
        try {
            boolean success = false;
            
            if (action == 1) {
                success = leaveService.approveLeave(leaveId, currentUser.getEmployeeId(), comments);
                if (success) {
                    MenuHelper.printSuccess("Leave application approved successfully!");
                }
            } else if (action == 2) {
                success = leaveService.rejectLeave(leaveId, currentUser.getEmployeeId(), comments);
                if (success) {
                    MenuHelper.printSuccess("Leave application rejected successfully!");
                }
            } else {
                MenuHelper.printError("Invalid action.");
            }
            
            if (!success && (action == 1 || action == 2)) {
                MenuHelper.printError("Failed to process leave application.");
            }
            
        } catch (Exception e) {
            MenuHelper.printError(e.getMessage());
        }
    }
    
    
    private void viewTeamLeaveBalances() {
        Employee currentUser = SessionManager.getCurrentUser();
        
        List<Employee> teamMembers = employeeService.getTeamMembers(currentUser.getEmployeeId());
        
        if (teamMembers == null || teamMembers.isEmpty()) {
            MenuHelper.printInfo("No team members found.");
            return;
        }
        
        MenuHelper.printHeader("TEAM LEAVE BALANCES");
        
        int currentYear = java.time.LocalDate.now().getYear();
        
        for (Employee emp : teamMembers) {
            System.out.println("\n" + emp.getFullName() + " (" + emp.getEmployeeId() + ")");
            MenuHelper.printDivider();
            
            List<com.revature.revworkforce.model.LeaveBalance> balances = 
                leaveService.getLeaveBalances(emp.getEmployeeId(), currentYear);
            
            if (balances != null && !balances.isEmpty()) {
                System.out.printf("  %-15s %-10s %-10s %-10s%n", 
                    "Leave Type", "Total", "Used", "Available");
                System.out.println("  " + "-".repeat(45));
                
                for (com.revature.revworkforce.model.LeaveBalance balance : balances) {
                    System.out.printf("  %-15s %-10d %-10d %-10d%n",
                        balance.getLeaveTypeName(),
                        balance.getTotalAllocated(),
                        balance.getUsedLeaves(),
                        balance.getAvailableLeaves());
                }
            } else {
                System.out.println("  No leave balance information available.");
            }
        }
        MenuHelper.printDivider();
    }
    
    private void viewTeamLeaveCalendar() {
        Employee currentUser = SessionManager.getCurrentUser();
        
        List<LeaveApplication> teamLeaves = leaveService.getPendingLeavesForManager(
            currentUser.getEmployeeId());
        
        MenuHelper.printSubHeader("TEAM LEAVE CALENDAR");
        
        if (teamLeaves == null || teamLeaves.isEmpty()) {
            MenuHelper.printInfo("No upcoming team leaves.");
            return;
        }
        
        System.out.printf("%-15s %-12s %-12s %-15s%n", 
            "Employee", "Start", "End", "Status");
        MenuHelper.printDivider();
        
        for (LeaveApplication app : teamLeaves) {
            System.out.printf("%-15s %-12s %-12s %-15s%n",
                app.getEmployeeName(),
                MenuHelper.formatDate(app.getStartDate()),
                MenuHelper.formatDate(app.getEndDate()),
                app.getStatus());
        }
        MenuHelper.printDivider();
    }
    
    private void viewPendingPerformanceReviews() {
        Employee currentUser = SessionManager.getCurrentUser();
        
        List<PerformanceReview> pendingReviews = performanceService.getPendingReviewsForManager(
            currentUser.getEmployeeId());
        
        if (pendingReviews == null || pendingReviews.isEmpty()) {
            MenuHelper.printInfo("No pending performance reviews.");
            return;
        }
        
        MenuHelper.printSubHeader("PENDING PERFORMANCE REVIEWS");
        System.out.printf("%-5s %-20s %-10s %-15s %-10s%n", 
            "ID", "Employee", "Year", "Submitted", "Self-Rating");
        MenuHelper.printDivider();
        
        for (PerformanceReview review : pendingReviews) {
            System.out.printf("%-5d %-20s %-10d %-15s %-10.1f%n",
                review.getReviewId(),
                review.getEmployeeName() != null ? review.getEmployeeName() : "N/A",
                review.getReviewYear(),
                review.getSubmittedDate() != null ? "Yes" : "No",
                review.getSelfAssessmentRating());
        }
        MenuHelper.printDivider();
    }
    
    private void reviewPerformance() {
        MenuHelper.printHeader("REVIEW PERFORMANCE");
        
        Employee currentUser = SessionManager.getCurrentUser();
        
        // Show pending reviews
        List<PerformanceReview> pendingReviews = performanceService.getPendingReviewsForManager(
            currentUser.getEmployeeId());
        
        if (pendingReviews == null || pendingReviews.isEmpty()) {
            MenuHelper.printInfo("No pending performance reviews.");
            return;
        }
        
        viewPendingPerformanceReviews();
        
        int reviewId = MenuHelper.getIntInput(scanner, "\nEnter Review ID: ");
        
        // Find the review
        PerformanceReview review = null;
        for (PerformanceReview r : pendingReviews) {
            if (r.getReviewId() == reviewId) {
                review = r;
                break;
            }
        }
        
        if (review == null) {
            MenuHelper.printError("Invalid Review ID.");
            return;
        }
        
        // Display review details
        MenuHelper.printSubHeader("PERFORMANCE REVIEW DETAILS");
        System.out.println("Employee: " + review.getEmployeeName());
        System.out.println("Year: " + review.getReviewYear());
        System.out.println("Self-Assessment Rating: " + review.getSelfAssessmentRating());
        System.out.println();
        System.out.println("Key Deliverables:");
        System.out.println(review.getKeyDeliverables());
        System.out.println();
        System.out.println("Major Accomplishments:");
        System.out.println(review.getMajorAccomplishments());
        System.out.println();
        System.out.println("Areas of Improvement:");
        System.out.println(review.getAreasOfImprovement());
        MenuHelper.printDivider();
        
        // Get manager feedback
        System.out.println("\nProvide Your Feedback:");
        String feedback = MenuHelper.getStringInput(scanner, "Feedback: ");
        double rating = MenuHelper.getDoubleInput(scanner, "Rating (1.0 - 5.0): ");
        
        try {
            if (performanceService.addManagerFeedback(reviewId, currentUser.getEmployeeId(), 
                    feedback, rating)) {
                MenuHelper.printSuccess("Performance review feedback submitted successfully!");
            } else {
                MenuHelper.printError("Failed to submit feedback.");
            }
        } catch (Exception e) {
            MenuHelper.printError(e.getMessage());
        }
    }
    
    private void viewTeamGoals() {
        Employee currentUser = SessionManager.getCurrentUser();
        
        List<Goal> teamGoals = performanceService.getTeamGoals(currentUser.getEmployeeId());
        
        if (teamGoals == null || teamGoals.isEmpty()) {
            MenuHelper.printInfo("No team goals found.");
            return;
        }
        
        MenuHelper.printSubHeader("TEAM GOALS");
        System.out.printf("%-5s %-15s %-35s %-12s %-10s %-10s%n", 
            "ID", "Employee", "Description", "Deadline", "Priority", "Progress");
        MenuHelper.printDivider();
        
        for (Goal goal : teamGoals) {
            String description = goal.getGoalDescription();
            if (description.length() > 32) {
                description = description.substring(0, 32) + "...";
            }
            System.out.printf("%-5d %-15s %-35s %-12s %-10s %-10s%n",
                goal.getGoalId(),
                goal.getEmployeeId(),
                description,
                MenuHelper.formatDate(goal.getDeadline()),
                goal.getPriority(),
                goal.getProgressPercentage() + "%");
        }
        MenuHelper.printDivider();
    }
}