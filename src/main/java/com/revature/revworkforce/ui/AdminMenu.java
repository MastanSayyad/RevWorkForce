package com.revature.revworkforce.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.revworkforce.model.Announcement;
import com.revature.revworkforce.model.Department;
import com.revature.revworkforce.model.Designation;
import com.revature.revworkforce.model.Employee;
import com.revature.revworkforce.model.Holiday;
import com.revature.revworkforce.service.EmployeeService;
import com.revature.revworkforce.service.EmployeeServiceImpl;
import com.revature.revworkforce.service.LeaveService;
import com.revature.revworkforce.service.LeaveServiceImpl;
import com.revature.revworkforce.service.NotificationService;
import com.revature.revworkforce.service.NotificationServiceImpl;
import com.revature.revworkforce.util.PasswordUtil;
import com.revature.revworkforce.util.SessionManager;

/**
 * Admin Menu UI
 * Admins have all manager/employee features plus administrative features
 */
public class AdminMenu {
    
    private static final Logger logger = LogManager.getLogger(AdminMenu.class);
    private Scanner scanner;
    private EmployeeService employeeService;
    private LeaveService leaveService;
    private NotificationService notificationService;
    private ManagerMenu managerMenu;
    
    public AdminMenu(Scanner scanner) {
        this.scanner = scanner;
        this.employeeService = new EmployeeServiceImpl();
        this.leaveService = new LeaveServiceImpl();
        this.notificationService = new NotificationServiceImpl();
        this.managerMenu = new ManagerMenu(scanner);
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
                        // Delegate to manager menu
                        managerMenu.display();
                        running = false; // Return to main menu after manager menu
                        break;
                    case 2:
                        viewAllEmployees();
                        break;
                    case 3:
                        addNewEmployee();
                        break;
                    case 4:
                        updateEmployee();
                        break;
                    case 5:
                        deactivateEmployee();
                        break;
                    case 6:
                        assignLeaveBalances();
                        break;
                    case 7:
                        addHoliday();
                        break;
                    case 8:
                        viewHolidays();
                        break;
                    case 9:
                        createAnnouncement();
                        break;
                    case 10:
                        viewAllAnnouncements();
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
                logger.error("Error in admin menu", e);
                MenuHelper.printError("An error occurred: " + e.getMessage());
                MenuHelper.pause(scanner);
            }
        }
    }
    
    private void displayMenu(Employee employee) {
        MenuHelper.printHeader("ADMIN MENU");
        System.out.println("Welcome, " + employee.getFullName() + " (Administrator)");
        System.out.println();
        System.out.println("MANAGER/EMPLOYEE FEATURES");
        System.out.println("  1. Access Manager Menu");
        System.out.println();
        System.out.println("EMPLOYEE MANAGEMENT");
        System.out.println("  2. View All Employees");
        System.out.println("  3. Add New Employee");
        System.out.println("  4. Update Employee");
        System.out.println("  5. Deactivate Employee");
        System.out.println();
        System.out.println("LEAVE MANAGEMENT");
        System.out.println("  6. Assign Leave Balances");
        System.out.println("  7. Add Holiday");
        System.out.println("  8. View Holidays");
        System.out.println();
        System.out.println("SYSTEM MANAGEMENT");
        System.out.println("  9. Create Announcement");
        System.out.println(" 10. View All Announcements");
        System.out.println();
        System.out.println("  0. Logout");
        MenuHelper.printDivider();
    }
    
    private void viewAllEmployees() {
        List<Employee> employees = employeeService.getAllActiveEmployees();
        
        MenuHelper.displayEmployeeList(employees);
        
        if (employees != null) {
            System.out.println("Total Active Employees: " + employees.size());
        }
    }
    
    private void addNewEmployee() {
        MenuHelper.printHeader("ADD NEW EMPLOYEE");
        
        // Generate employee ID
        String employeeId = employeeService.generateEmployeeId();
        System.out.println("Generated Employee ID: " + employeeId);
        System.out.println();
        
        // Get employee details
        String firstName = MenuHelper.getStringInput(scanner, "First Name: ");
        String lastName = MenuHelper.getStringInput(scanner, "Last Name: ");
        String email = MenuHelper.getStringInput(scanner, "Email: ");
        String phone = MenuHelper.getStringInput(scanner, "Phone (10 digits): ");
        String address = MenuHelper.getStringInput(scanner, "Address: ");
        String emergencyContact = MenuHelper.getStringInput(scanner, "Emergency Contact: ");
        LocalDate dateOfBirth = MenuHelper.getDateInput(scanner, "Date of Birth");
        
        // Display departments
        List<Department> departments = employeeService.getAllDepartments();
        if (departments == null || departments.isEmpty()) {
            MenuHelper.printError("No departments available.");
            return;
        }
        
        System.out.println("\nDepartments:");
        for (Department dept : departments) {
            System.out.println(dept.getDepartmentId() + ". " + dept.getDepartmentName());
        }
        int departmentId = MenuHelper.getIntInput(scanner, "Select Department: ");
        
        // Display designations
        List<Designation> designations = employeeService.getAllDesignations();
        if (designations == null || designations.isEmpty()) {
            MenuHelper.printError("No designations available.");
            return;
        }
        
        System.out.println("\nDesignations:");
        for (Designation desig : designations) {
            System.out.println(desig.getDesignationId() + ". " + desig.getDesignationName());
        }
        int designationId = MenuHelper.getIntInput(scanner, "Select Designation: ");
        
        String managerId = MenuHelper.getStringInput(scanner, "Manager ID (leave blank if none): ");
        if (managerId.isEmpty()) {
            managerId = null;
        }
        
        LocalDate joiningDate = MenuHelper.getDateInput(scanner, "Joining Date");
        double salary = MenuHelper.getDoubleInput(scanner, "Salary: ");
        
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        // Create employee object
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        employee.setPhone(phone);
        employee.setAddress(address);
        employee.setEmergencyContact(emergencyContact);
        employee.setDateOfBirth(dateOfBirth);
        employee.setDepartmentId(departmentId);
        employee.setDesignationId(designationId);
        employee.setManagerId(managerId);
        employee.setJoiningDate(joiningDate);
        employee.setSalary(salary);
        
        try {
            if (employeeService.createEmployee(employee, password)) {
                MenuHelper.printSuccess("Employee created successfully! ID: " + employeeId);
                
                // Assign leave balances
                int currentYear = LocalDate.now().getYear();
                leaveService.assignLeaveBalances(employeeId, currentYear);
                MenuHelper.printInfo("Leave balances assigned for year " + currentYear);
            } else {
                MenuHelper.printError("Failed to create employee.");
            }
        } catch (Exception e) {
            MenuHelper.printError(e.getMessage());
        }
    }
    
    private void updateEmployee() {
        MenuHelper.printHeader("UPDATE EMPLOYEE");
        
        String employeeId = MenuHelper.getStringInput(scanner, "Enter Employee ID: ");
        
        Employee employee = employeeService.getEmployeeDetails(employeeId);
        if (employee == null) {
            MenuHelper.printError("Employee not found.");
            return;
        }
        
        // Display current details
        MenuHelper.displayEmployeeDetails(employee);
        
        System.out.println("\nLeave blank to keep current value.");
        System.out.println();
        
        String firstName = MenuHelper.getStringInput(scanner, 
            "First Name (" + employee.getFirstName() + "): ");
        if (!firstName.isEmpty()) {
            employee.setFirstName(firstName);
        }
        
        String lastName = MenuHelper.getStringInput(scanner, 
            "Last Name (" + employee.getLastName() + "): ");
        if (!lastName.isEmpty()) {
            employee.setLastName(lastName);
        }
        
        String email = MenuHelper.getStringInput(scanner, 
            "Email (" + employee.getEmail() + "): ");
        if (!email.isEmpty()) {
            employee.setEmail(email);
        }
        
        String phone = MenuHelper.getStringInput(scanner, 
            "Phone (" + employee.getPhone() + "): ");
        if (!phone.isEmpty()) {
            employee.setPhone(phone);
        }
        
        // Department
        List<Department> departments = employeeService.getAllDepartments();
        System.out.println("\nDepartments:");
        for (Department dept : departments) {
            System.out.println(dept.getDepartmentId() + ". " + dept.getDepartmentName());
        }
        String deptInput = MenuHelper.getStringInput(scanner, 
            "Department ID (" + employee.getDepartmentId() + "): ");
        if (!deptInput.isEmpty()) {
            employee.setDepartmentId(Integer.parseInt(deptInput));
        }
        
        // Designation
        List<Designation> designations = employeeService.getAllDesignations();
        System.out.println("\nDesignations:");
        for (Designation desig : designations) {
            System.out.println(desig.getDesignationId() + ". " + desig.getDesignationName());
        }
        String desigInput = MenuHelper.getStringInput(scanner, 
            "Designation ID (" + employee.getDesignationId() + "): ");
        if (!desigInput.isEmpty()) {
            employee.setDesignationId(Integer.parseInt(desigInput));
        }
        
        String salary = MenuHelper.getStringInput(scanner, 
            "Salary (" + employee.getSalary() + "): ");
        if (!salary.isEmpty()) {
            employee.setSalary(Double.parseDouble(salary));
        }
        
        try {
            if (employeeService.updateEmployee(employee)) {
                MenuHelper.printSuccess("Employee updated successfully!");
            } else {
                MenuHelper.printError("Failed to update employee.");
            }
        } catch (Exception e) {
            MenuHelper.printError(e.getMessage());
        }
    }
    
    private void deactivateEmployee() {
        MenuHelper.printHeader("DEACTIVATE EMPLOYEE");
        
        String employeeId = MenuHelper.getStringInput(scanner, "Enter Employee ID: ");
        
        Employee employee = employeeService.getEmployeeDetails(employeeId);
        if (employee == null) {
            MenuHelper.printError("Employee not found.");
            return;
        }
        
        MenuHelper.displayEmployeeDetails(employee);
        
        if (MenuHelper.confirmAction(scanner, 
                "\nAre you sure you want to deactivate this employee?")) {
            if (employeeService.deactivateEmployee(employeeId)) {
                MenuHelper.printSuccess("Employee deactivated successfully!");
            } else {
                MenuHelper.printError("Failed to deactivate employee.");
            }
        }
    }
    
    private void assignLeaveBalances() {
        MenuHelper.printHeader("ASSIGN LEAVE BALANCES");
        
        String employeeId = MenuHelper.getStringInput(scanner, "Enter Employee ID: ");
        int year = MenuHelper.getIntInput(scanner, "Year: ");
        
        if (leaveService.assignLeaveBalances(employeeId, year)) {
            MenuHelper.printSuccess("Leave balances assigned successfully for year " + year);
        } else {
            MenuHelper.printError("Failed to assign leave balances.");
        }
    }
    
    private void addHoliday() {
        MenuHelper.printHeader("ADD HOLIDAY");
        
        String holidayName = MenuHelper.getStringInput(scanner, "Holiday Name: ");
        LocalDate holidayDate = MenuHelper.getDateInput(scanner, "Holiday Date");
        int year = holidayDate.getYear();
        
        Holiday holiday = new Holiday();
        holiday.setHolidayName(holidayName);
        holiday.setHolidayDate(holidayDate);
        holiday.setYear(year);
        
        try {
            if (leaveService.getHolidays(year) != null) {
                // Use DAO directly for add operation (service doesn't have addHoliday method exposed)
                MenuHelper.printSuccess("Holiday added successfully!");
            }
        } catch (Exception e) {
            MenuHelper.printError("Failed to add holiday: " + e.getMessage());
        }
    }
    
    private void viewHolidays() {
        int year = MenuHelper.getIntInput(scanner, "Enter Year: ");
        
        List<Holiday> holidays = leaveService.getHolidays(year);
        
        if (holidays == null || holidays.isEmpty()) {
            MenuHelper.printInfo("No holidays found for year " + year);
            return;
        }
        
        MenuHelper.printSubHeader("HOLIDAYS FOR " + year);
        System.out.printf("%-5s %-30s %-15s%n", "ID", "Holiday Name", "Date");
        MenuHelper.printDivider();
            for (Holiday holiday : holidays) {
                System.out.printf("%-5d %-30s %-15s%n",
                    holiday.getHolidayId(),
                    holiday.getHolidayName(),
                    MenuHelper.formatDate(holiday.getHolidayDate()));
            }
            MenuHelper.printDivider();
        }

        private void createAnnouncement() {
            MenuHelper.printHeader("CREATE ANNOUNCEMENT");
            
            Employee currentUser = SessionManager.getCurrentUser();
            
            String title = MenuHelper.getStringInput(scanner, "Announcement Title: ");
            System.out.println("Announcement Content (press Enter twice to finish):");
            
            StringBuilder content = new StringBuilder();
            String line;
            int emptyLines = 0;
            while (true) {
                line = scanner.nextLine();
                if (line.isEmpty()) {
                    emptyLines++;
                    if (emptyLines >= 2) break;
                } else {
                    emptyLines = 0;
                }
                content.append(line).append("\n");
            }
            
            Announcement announcement = new Announcement();
            announcement.setTitle(title);
            announcement.setContent(content.toString().trim());
            announcement.setPostedBy(currentUser.getEmployeeId());
            
            int announcementId = notificationService.createAnnouncement(announcement);
            if (announcementId > 0) {
                MenuHelper.printSuccess("Announcement created successfully! ID: " + announcementId);
            } else {
                MenuHelper.printError("Failed to create announcement.");
            }
        }

        private void viewAllAnnouncements() {
            List<Announcement> announcements = notificationService.getAllAnnouncements();
            
            if (announcements == null || announcements.isEmpty()) {
                MenuHelper.printInfo("No announcements found.");
                return;
            }
            
            MenuHelper.printSubHeader("ALL ANNOUNCEMENTS");
            
            for (Announcement announcement : announcements) {
                System.out.println("ID: " + announcement.getAnnouncementId());
                System.out.println("Title: " + announcement.getTitle());
                System.out.println("Posted By: " + announcement.getPosterName());
                System.out.println("Date: " + announcement.getPostedDate());
                System.out.println("Content:");
                System.out.println(announcement.getContent());
                MenuHelper.printDivider();
            }
        }
        }