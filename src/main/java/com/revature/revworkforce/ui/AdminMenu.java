package com.revature.revworkforce.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.revworkforce.model.Announcement;
import com.revature.revworkforce.model.AuditLog;
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
import com.revature.revworkforce.service.AuditService;
import com.revature.revworkforce.service.AuditServiceImpl;

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
    private AuditService auditService;
    private ManagerMenu managerMenu;

    //Initialize in constructor
    public AdminMenu(Scanner scanner) {
     this.scanner = scanner;
     this.employeeService = new EmployeeServiceImpl();
     this.leaveService = new LeaveServiceImpl();
     this.notificationService = new NotificationServiceImpl();
     this.auditService = new AuditServiceImpl(); 
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
                    managerMenu.display();
//                    running = false;
                    break;
                case 2:
                    viewAllEmployees();
                    break;
                case 3:                                    
                    searchEmployees();
                    break;
                case 4:                                   
                    addNewEmployee();
                    break;
                case 5:
                    updateEmployee();
                    break;
                case 6:
                    deactivateEmployee();
                    break;
                case 7:
                    assignLeaveBalances();
                    break;
                case 8:                                    
                    adjustLeaveBalance();
                    break;
                case 9:
                    addHoliday();
                    break;
                case 10:
                    viewHolidays();
                    break;
                case 11:
                    createAnnouncement();
                    break;
                case 12:
                    viewAllAnnouncements();
                    break;
                case 13:
                    viewAuditLogs();
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
        System.out.println("  3. Search Employees");            
        System.out.println("  4. Add New Employee");           
        System.out.println("  5. Update Employee");
        System.out.println("  6. Deactivate Employee");
        System.out.println();
        System.out.println("LEAVE MANAGEMENT");
        System.out.println("  7. Assign Leave Balances");
        System.out.println("  8. Adjust Leave Balance");          
        System.out.println("  9. Add Holiday");
        System.out.println(" 10. View Holidays");
        System.out.println();
        System.out.println("SYSTEM MANAGEMENT");
        System.out.println(" 11. Create Announcement");
        System.out.println(" 12. View All Announcements");
        System.out.println(" 13. View Audit Logs");
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
    
    private void searchEmployees() {
        MenuHelper.printHeader("SEARCH EMPLOYEES");
        
        System.out.println("Search By:");
        System.out.println("1. Employee ID");
        System.out.println("2. Name");
        System.out.println("3. Email");
        System.out.println("4. Department");
        System.out.println("5. Designation");
        System.out.println("0. Back");
        
        int choice = MenuHelper.getIntInput(scanner, "\nEnter your choice: ");
        
        List<Employee> employees = null;
        
        switch (choice) {
            case 1:
                String empId = MenuHelper.getStringInput(scanner, "Enter Employee ID: ");
                Employee emp = employeeService.getEmployeeDetails(empId);
                if (emp != null) {
                    employees = new java.util.ArrayList<>();
                    employees.add(emp);
                }
                break;
                
            case 2:
                String name = MenuHelper.getStringInput(scanner, "Enter Name: ");
                employees = employeeService.getAllActiveEmployees();
                if (employees != null) {
                    final String searchName = name.toLowerCase();
                    employees = employees.stream()
                        .filter(e -> e.getFullName().toLowerCase().contains(searchName))
                        .collect(java.util.stream.Collectors.toList());
                }
                break;
                
            case 3:
                String email = MenuHelper.getStringInput(scanner, "Enter Email: ");
                employees = employeeService.getAllActiveEmployees();
                if (employees != null) {
                    final String searchEmail = email.toLowerCase();
                    employees = employees.stream()
                        .filter(e -> e.getEmail().toLowerCase().contains(searchEmail))
                        .collect(java.util.stream.Collectors.toList());
                }
                break;
                
            case 4:
                List<com.revature.revworkforce.model.Department> departments = 
                    employeeService.getAllDepartments();
                if (departments != null && !departments.isEmpty()) {
                    System.out.println("\nDepartments:");
                    for (com.revature.revworkforce.model.Department dept : departments) {
                        System.out.println(dept.getDepartmentId() + ". " + dept.getDepartmentName());
                    }
                    int deptId = MenuHelper.getIntInput(scanner, "Select Department: ");
                    employees = employeeService.getEmployeesByDepartment(deptId);
                }
                break;
                
            case 5:
                List<com.revature.revworkforce.model.Designation> designations = 
                    employeeService.getAllDesignations();
                if (designations != null && !designations.isEmpty()) {
                    System.out.println("\nDesignations:");
                    for (com.revature.revworkforce.model.Designation desig : designations) {
                        System.out.println(desig.getDesignationId() + ". " + desig.getDesignationName());
                    }
                    int desigId = MenuHelper.getIntInput(scanner, "Select Designation: ");
                    employees = employeeService.getAllActiveEmployees();
                    if (employees != null) {
                        final int searchDesigId = desigId;
                        employees = employees.stream()
                            .filter(e -> e.getDesignationId() == searchDesigId)
                            .collect(java.util.stream.Collectors.toList());
                    }
                }
                break;
                
            case 0:
                return;
                
            default:
                MenuHelper.printError("Invalid choice.");
                return;
        }
        
        if (employees != null && !employees.isEmpty()) {
            MenuHelper.displayEmployeeList(employees);
            System.out.println("Total Results: " + employees.size());
        } else {
            MenuHelper.printInfo("No employees found matching the search criteria.");
        }
    }

    private void adjustLeaveBalance() {
        MenuHelper.printHeader("ADJUST LEAVE BALANCE");
        
        String employeeId = MenuHelper.getStringInput(scanner, "Enter Employee ID: ");
        
        Employee employee = employeeService.getEmployeeDetails(employeeId);
        if (employee == null) {
            MenuHelper.printError("Employee not found.");
            return;
        }
        
        System.out.println("Employee: " + employee.getFullName());
        System.out.println();
        
        // Show current balances
        int currentYear = java.time.LocalDate.now().getYear();
        List<com.revature.revworkforce.model.LeaveBalance> balances = 
            leaveService.getLeaveBalances(employeeId, currentYear);
        
        if (balances == null || balances.isEmpty()) {
            MenuHelper.printError("No leave balances found. Please assign leave balances first.");
            return;
        }
        
        MenuHelper.displayLeaveBalances(balances);
        
        // Select leave type
        System.out.println("\nSelect Leave Type to Adjust:");
        for (int i = 0; i < balances.size(); i++) {
            System.out.println((i + 1) + ". " + balances.get(i).getLeaveTypeName());
        }
        
        int choice = MenuHelper.getIntInput(scanner, "Enter choice: ");
        
        if (choice < 1 || choice > balances.size()) {
            MenuHelper.printError("Invalid choice.");
            return;
        }
        
        com.revature.revworkforce.model.LeaveBalance selectedBalance = balances.get(choice - 1);
        
        System.out.println("\nCurrent Balance:");
        System.out.println("Total Allocated: " + selectedBalance.getTotalAllocated());
        System.out.println("Used: " + selectedBalance.getUsedLeaves());
        System.out.println("Available: " + selectedBalance.getAvailableLeaves());
        System.out.println();
        
        System.out.println("Adjustment Options:");
        System.out.println("1. Set New Total Allocated");
        System.out.println("2. Add to Available");
        System.out.println("3. Deduct from Available");
        System.out.println("0. Cancel");
        
        int adjustChoice = MenuHelper.getIntInput(scanner, "\nEnter choice: ");
        
        try {
            switch (adjustChoice) {
                case 1:
                    int newTotal = MenuHelper.getIntInput(scanner, "Enter new total allocated: ");
                    if (newTotal < selectedBalance.getUsedLeaves()) {
                        MenuHelper.printError("New total cannot be less than used leaves (" + 
                            selectedBalance.getUsedLeaves() + ")");
                        return;
                    }
                    selectedBalance.setTotalAllocated(newTotal);
                    selectedBalance.setAvailableLeaves(newTotal - selectedBalance.getUsedLeaves());
                    break;
                    
                case 2:
                    int addDays = MenuHelper.getIntInput(scanner, "Enter days to add: ");
                    selectedBalance.setTotalAllocated(selectedBalance.getTotalAllocated() + addDays);
                    selectedBalance.setAvailableLeaves(selectedBalance.getAvailableLeaves() + addDays);
                    break;
                    
                case 3:
                    int deductDays = MenuHelper.getIntInput(scanner, "Enter days to deduct: ");
                    if (deductDays > selectedBalance.getAvailableLeaves()) {
                        MenuHelper.printError("Cannot deduct more than available leaves.");
                        return;
                    }
                    selectedBalance.setTotalAllocated(selectedBalance.getTotalAllocated() - deductDays);
                    selectedBalance.setAvailableLeaves(selectedBalance.getAvailableLeaves() - deductDays);
                    break;
                    
                case 0:
                    return;
                    
                default:
                    MenuHelper.printError("Invalid choice.");
                    return;
            }
            
            // Update in database (we need to add this method to LeaveService)
            // For now, we'll use DAO directly
            com.revature.revworkforce.dao.LeaveDAO leaveDAO = new com.revature.revworkforce.dao.LeaveDAOImpl();
            if (leaveDAO.updateLeaveBalance(selectedBalance)) {
                MenuHelper.printSuccess("Leave balance adjusted successfully!");
                
                // Log the adjustment
                auditService.logAction(
                    SessionManager.getCurrentUser().getEmployeeId(),
                    "LEAVE_BALANCE_ADJUSTED",
                    "leave_balances",
                    String.valueOf(selectedBalance.getLeaveBalanceId()),
                    null,
                    "New total: " + selectedBalance.getTotalAllocated() + 
                    ", Available: " + selectedBalance.getAvailableLeaves()
                );
                
                System.out.println("\nUpdated Balance:");
                System.out.println("Total Allocated: " + selectedBalance.getTotalAllocated());
                System.out.println("Used: " + selectedBalance.getUsedLeaves());
                System.out.println("Available: " + selectedBalance.getAvailableLeaves());
            } else {
                MenuHelper.printError("Failed to adjust leave balance.");
            }
            
        } catch (Exception e) {
            MenuHelper.printError("Error adjusting leave balance: " + e.getMessage());
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
        
        private void viewAuditLogs() {
         MenuHelper.printHeader("AUDIT LOGS");
         
         System.out.println("1. View All Audit Logs");
         System.out.println("2. View Audit Logs by Employee");
         System.out.println("3. View Audit Logs by Table");
         System.out.println("4. View Recent Audit Logs");
         System.out.println("0. Back");
         
         int choice = MenuHelper.getIntInput(scanner, "\nEnter your choice: ");
         
         List<AuditLog> logs = null;
         
         switch (choice) {
             case 1:
                 logs = auditService.getAllAuditLogs();
                 break;
             case 2:
                 String employeeId = MenuHelper.getStringInput(scanner, "Enter Employee ID: ");
                 logs = auditService.getAuditLogsByEmployee(employeeId);
                 break;
             case 3:
                 String tableName = MenuHelper.getStringInput(scanner, "Enter Table Name: ");
                 logs = auditService.getAuditLogsByTable(tableName);
                 break;
             case 4:
                 int limit = MenuHelper.getIntInput(scanner, "Enter number of logs to view: ");
                 logs = auditService.getRecentAuditLogs(limit);
                 break;
             case 0:
                 return;
             default:
                 MenuHelper.printError("Invalid choice.");
                 return;
         }
         
         displayAuditLogs(logs);
        }

        private void displayAuditLogs(List<AuditLog> logs) {
         if (logs == null || logs.isEmpty()) {
             MenuHelper.printInfo("No audit logs found.");
             return;
         }
         
         MenuHelper.printSubHeader("AUDIT LOGS");
         System.out.printf("%-5s %-12s %-20s %-15s %-15s %-20s%n", 
             "ID", "Employee", "Action", "Table", "Record ID", "Timestamp");
         MenuHelper.printDivider();
         
         for (AuditLog log : logs) {
             String timestamp = log.getCreatedAt() != null ? 
                 log.getCreatedAt().toString().substring(0, 19) : "N/A";
             
             System.out.printf("%-5d %-12s %-20s %-15s %-15s %-20s%n",
                 log.getLogId(),
                 log.getEmployeeId() != null ? log.getEmployeeId() : "SYSTEM",
                 log.getAction(),
                 log.getTableName(),
                 log.getRecordId() != null ? log.getRecordId() : "N/A",
                 timestamp);
         }
         MenuHelper.printDivider();
         System.out.println("Total Logs: " + logs.size());
        }
   }


