# RevWorkForce - API Documentation

## Table of Contents

- [Overview](#overview)
- [Service Layer APIs](#service-layer-apis)
  - [AuthService](#authservice)
  - [EmployeeService](#employeeservice)
  - [LeaveService](#leaveservice)
  - [PerformanceService](#performanceservice)
  - [NotificationService](#notificationservice)
  - [AuditService](#auditservice)
- [DAO Layer APIs](#dao-layer-apis)
- [Utility Classes](#utility-classes)
- [Exception Handling](#exception-handling)



## Overview

This document provides comprehensive API documentation for the RevWorkForce system. All service methods are designed to handle business logic, validation, and error handling.

### Package Structure

```
com.revature.revworkforce
├── service          # Business logic interfaces and implementations
├── dao              # Data access interfaces and implementations
├── model            # Entity classes
├── util             # Utility classes
└── exception        # Custom exception classes
```

---

## Service Layer APIs

### AuthService

**Package:** `com.revature.revworkforce.service`

Handles user authentication and authorization.

#### Methods

##### `login()`

Authenticate user credentials and create session.

```java
Employee login(String employeeId, String password) throws AuthenticationException
```

**Parameters:**
- `employeeId` (String) - Employee ID (e.g., "EMP001")
- `password` (String) - Plain text password

**Returns:**
- `Employee` object with roles populated

**Throws:**
- `AuthenticationException` - Invalid credentials, inactive account

**Example:**
```java
AuthService authService = new AuthServiceImpl();
try {
    Employee employee = authService.login("EMP001", "password123");
    System.out.println("Welcome " + employee.getFullName());
} catch (AuthenticationException e) {
    System.out.println("Login failed: " + e.getMessage());
}
```

##### `changePassword()`

Change employee password.

```java
boolean changePassword(String employeeId, String currentPassword, String newPassword) 
    throws AuthenticationException
```

**Parameters:**
- `employeeId` (String) - Employee ID
- `currentPassword` (String) - Current password
- `newPassword` (String) - New password (min 8 characters)

**Returns:**
- `true` if password changed successfully

**Throws:**
- `AuthenticationException` - Invalid current password, password too short

**Validation Rules:**
- New password must be at least 8 characters
- New password must differ from current password
- Current password must be correct

##### `hasRole()`

Check if employee has specific role.

```java
boolean hasRole(Employee employee, String roleName)
```

**Parameters:**
- `employee` (Employee) - Employee object
- `roleName` (String) - Role name ("ADMIN", "MANAGER", "EMPLOYEE")

**Returns:**
- `true` if employee has the role

##### `logout()`

End current session.

```java
void logout()
```

**No parameters or return value.**

---

### EmployeeService

**Package:** `com.revature.revworkforce.service`

Manages employee data and operations.

#### Methods

##### `createEmployee()`

Create new employee record.

```java
boolean createEmployee(Employee employee, String plainPassword) throws ValidationException
```

**Parameters:**
- `employee` (Employee) - Employee object
- `plainPassword` (String) - Initial password

**Returns:**
- `true` if employee created successfully

**Throws:**
- `ValidationException` - Validation errors

**Validations:**
- Employee ID format (e.g., EMP001)
- Email format
- Phone number (10 digits)
- Unique email and employee ID
- All required fields present

##### `updateEmployee()`

Update employee information (Admin only).

```java
boolean updateEmployee(Employee employee) throws ValidationException
```

##### `updateProfile()`

Update employee profile (Employee can update own).

```java
boolean updateProfile(Employee employee) throws ValidationException
```

**Updatable Fields:**
- Phone number
- Address
- Emergency contact

##### `getEmployeeDetails()`

Get complete employee details.

```java
Employee getEmployeeDetails(String employeeId)
```

**Returns:**
- Employee object with department, designation, manager info

##### `getAllActiveEmployees()`

Get all active employees.

```java
List<Employee> getAllActiveEmployees()
```

**Returns:**
- List of active employees

##### `getTeamMembers()`

Get employees reporting to a manager.

```java
List<Employee> getTeamMembers(String managerId)
```

**Parameters:**
- `managerId` (String) - Manager's employee ID

**Returns:**
- List of direct reportees

##### `deactivateEmployee()`

Deactivate an employee.

```java
boolean deactivateEmployee(String employeeId)
```

##### `getAllDepartments()`

Get all departments.

```java
List<Department> getAllDepartments()
```

##### `getAllDesignations()`

Get all designations.

```java
List<Designation> getAllDesignations()
```

##### `generateEmployeeId()`

Generate next available employee ID.

```java
String generateEmployeeId()
```

**Returns:**
- Next employee ID (e.g., "EMP008")

---

### LeaveService

**Package:** `com.revature.revworkforce.service`

Manages leave applications and balances.

#### Methods

##### `applyForLeave()`

Submit leave application.

```java
int applyForLeave(LeaveApplication leaveApplication) 
    throws LeaveException, ValidationException
```

**Parameters:**
- `leaveApplication` (LeaveApplication) - Leave application object

**Returns:**
- Leave application ID if successful

**Throws:**
- `LeaveException` - Insufficient balance, overlapping leaves
- `ValidationException` - Invalid dates, missing data

**Business Rules:**
- Start date must be in the future
- End date must be >= start date
- No overlapping leaves
- Sufficient leave balance
- Reason required

**Example:**
```java
LeaveApplication leave = new LeaveApplication();
leave.setEmployeeId("EMP001");
leave.setLeaveTypeId(1); // CASUAL
leave.setStartDate(LocalDate.now().plusDays(5));
leave.setEndDate(LocalDate.now().plusDays(7));
leave.setReason("Personal work");

try {
    int leaveId = leaveService.applyForLeave(leave);
    System.out.println("Leave applied successfully. ID: " + leaveId);
} catch (LeaveException e) {
    System.out.println("Error: " + e.getMessage());
}
```

##### `approveLeave()`

Approve leave application (Manager).

```java
boolean approveLeave(int leaveApplicationId, String managerId, String comments) 
    throws LeaveException
```

**Parameters:**
- `leaveApplicationId` (int) - Leave application ID
- `managerId` (String) - Manager's employee ID
- `comments` (String) - Approval comments

**Side Effects:**
- Updates leave balance
- Creates notification for employee

##### `rejectLeave()`

Reject leave application (Manager).

```java
boolean rejectLeave(int leaveApplicationId, String managerId, String comments) 
    throws LeaveException
```

**Parameters:**
- `leaveApplicationId` (int) - Leave application ID
- `managerId` (String) - Manager's employee ID
- `comments` (String) - Rejection reason (required)

##### `cancelLeave()`

Cancel pending leave application.

```java
boolean cancelLeave(int leaveApplicationId, String employeeId) throws LeaveException
```

**Business Rules:**
- Only pending leaves can be cancelled
- Only application owner can cancel

##### `getEmployeeLeaveApplications()`

Get employee's leave applications.

```java
List<LeaveApplication> getEmployeeLeaveApplications(String employeeId)
```

##### `getPendingLeavesForManager()`

Get pending leave applications for manager's team.

```java
List<LeaveApplication> getPendingLeavesForManager(String managerId)
```

##### `getLeaveBalances()`

Get employee's leave balances.

```java
List<LeaveBalance> getLeaveBalances(String employeeId, int year)
```

**Parameters:**
- `employeeId` (String) - Employee ID
- `year` (int) - Year (e.g., 2025)

##### `getAllLeaveTypes()`

Get all leave types.

```java
List<LeaveType> getAllLeaveTypes()
```

**Returns:**
- List of leave types (CASUAL, SICK, PAID, PRIVILEGE)

##### `getHolidays()`

Get holidays for a year.

```java
List<Holiday> getHolidays(int year)
```

##### `calculateWorkingDays()`

Calculate working days between dates.

```java
int calculateWorkingDays(LocalDate startDate, LocalDate endDate)
```

**Excludes:**
- Weekends (Saturday, Sunday)
- Company holidays

---

### PerformanceService

**Package:** `com.revature.revworkforce.service`

Manages performance reviews and goals.

#### Performance Review Methods

##### `createPerformanceReview()`

Create new performance review (draft).

```java
int createPerformanceReview(PerformanceReview review) throws ValidationException
```

**Fields:**
- `employeeId` (String) - Required
- `reviewYear` (int) - Required
- `keyDeliverables` (String) - Required
- `majorAccomplishments` (String) - Required
- `areasOfImprovement` (String) - Required
- `selfAssessmentRating` (double) - 1.0 to 5.0
- `status` (String) - "DRAFT"

##### `submitPerformanceReview()`

Submit review for manager approval.

```java
boolean submitPerformanceReview(int reviewId, String employeeId) 
    throws ValidationException
```

**Business Rules:**
- Only draft reviews can be submitted
- Self-assessment rating required
- Status changes from DRAFT → SUBMITTED

##### `addManagerFeedback()`

Add manager feedback to submitted review.

```java
boolean addManagerFeedback(int reviewId, String managerId, String feedback, double rating) 
    throws ValidationException
```

**Parameters:**
- `reviewId` (int) - Review ID
- `managerId` (String) - Manager's employee ID
- `feedback` (String) - Manager's feedback
- `rating` (double) - Manager rating (1.0 to 5.0)

**Business Rules:**
- Only submitted reviews can receive feedback
- Rating must be 1.0 to 5.0
- Status changes to REVIEWED

##### `getEmployeeReviews()`

Get employee's performance reviews.

```java
List<PerformanceReview> getEmployeeReviews(String employeeId)
```

##### `getPendingReviewsForManager()`

Get pending reviews for manager's team.

```java
List<PerformanceReview> getPendingReviewsForManager(String managerId)
```

#### Goal Management Methods

##### `createGoal()`

Create new goal.

```java
int createGoal(Goal goal) throws ValidationException
```

**Fields:**
- `employeeId` (String) - Required
- `goalDescription` (String) - Required (max 1000 chars)
- `deadline` (LocalDate) - Required
- `priority` (String) - HIGH, MEDIUM, LOW
- `successMetrics` (String) - Required
- `progressPercentage` (int) - 0-100
- `status` (String) - NOT_STARTED, IN_PROGRESS, COMPLETED, CANCELLED

##### `updateGoalProgress()`

Update goal progress percentage.

```java
boolean updateGoalProgress(int goalId, int progressPercentage) 
    throws ValidationException
```

**Parameters:**
- `goalId` (int) - Goal ID
- `progressPercentage` (int) - 0 to 100

**Auto-updates status:**
- 0% → NOT_STARTED
- 1-99% → IN_PROGRESS
- 100% → COMPLETED

##### `getEmployeeGoals()`

Get all employee goals.

```java
List<Goal> getEmployeeGoals(String employeeId)
```

##### `getActiveGoals()`

Get active employee goals (excluding completed/cancelled).

```java
List<Goal> getActiveGoals(String employeeId)
```

##### `getTeamGoals()`

Get goals for manager's team.

```java
List<Goal> getTeamGoals(String managerId)
```

##### `deleteGoal()`

Delete a goal.

```java
boolean deleteGoal(int goalId, String employeeId)
```

**Business Rules:**
- Only goal owner can delete

---

### NotificationService

**Package:** `com.revature.revworkforce.service`

Manages notifications and announcements.

#### Methods

##### `createNotification()`

Create notification for employee.

```java
boolean createNotification(String employeeId, String type, String message)
```

**Parameters:**
- `employeeId` (String) - Employee ID
- `type` (String) - LEAVE, PERFORMANCE, SYSTEM, ANNOUNCEMENT
- `message` (String) - Notification message

##### `getNotifications()`

Get all employee notifications.

```java
List<Notification> getNotifications(String employeeId)
```

##### `getUnreadNotifications()`

Get unread notifications.

```java
List<Notification> getUnreadNotifications(String employeeId)
```

##### `getUnreadCount()`

Get count of unread notifications.

```java
int getUnreadCount(String employeeId)
```

##### `markAsRead()`

Mark notification as read.

```java
boolean markAsRead(int notificationId)
```

##### `markAllAsRead()`

Mark all notifications as read.

```java
boolean markAllAsRead(String employeeId)
```

##### `createAnnouncement()`

Create company-wide announcement (Admin).

```java
int createAnnouncement(Announcement announcement)
```

##### `getAllAnnouncements()`

Get all announcements.

```java
List<Announcement> getAllAnnouncements()
```

##### `getRecentAnnouncements()`

Get recent announcements.

```java
List<Announcement> getRecentAnnouncements(int limit)
```

---

### AuditService

**Package:** `com.revature.revworkforce.service`

Manages audit trail logging.

#### Methods

##### `logAction()`

Log system action.

```java
boolean logAction(String employeeId, String action, String tableName, 
                  String recordId, String oldValue, String newValue)
```

**Parameters:**
- `employeeId` (String) - Who performed action
- `action` (String) - Action type (LOGIN, INSERT, UPDATE, DELETE)
- `tableName` (String) - Affected table
- `recordId` (String) - Affected record ID
- `oldValue` (String) - Previous value (optional)
- `newValue` (String) - New value (optional)

##### `getAuditLogsByEmployee()`

Get audit logs for employee.

```java
List<AuditLog> getAuditLogsByEmployee(String employeeId)
```

##### `getAuditLogsByTable()`

Get audit logs for table.

```java
List<AuditLog> getAuditLogsByTable(String tableName)
```

##### `getRecentAuditLogs()`

Get recent audit logs.

```java
List<AuditLog> getRecentAuditLogs(int limit)
```

---

## Utility Classes

### PasswordUtil

Password hashing and verification using BCrypt.

```java
// Hash password
String hash = PasswordUtil.hashPassword("plainPassword");

// Verify password
boolean isValid = PasswordUtil.verifyPassword("plainPassword", hash);
```

### InputValidator

Input validation utilities.

```java
// Email validation
boolean isValid = InputValidator.isValidEmail("user@example.com");

// Phone validation (10 digits)
boolean isValid = InputValidator.isValidPhone("1234567890");

// Employee ID validation (e.g., EMP001)
boolean isValid = InputValidator.isValidEmployeeId("EMP001");

// Rating validation (1.0 to 5.0)
boolean isValid = InputValidator.isValidRating(4.5);

// Sanitize input (remove special characters)
String clean = InputValidator.sanitizeInput("<script>alert('xss')</script>");
```

### SessionManager

Session management for current user.

```java
// Set current user
SessionManager.setCurrentUser(employee);

// Get current user
Employee current = SessionManager.getCurrentUser();

// Check if logged in
boolean loggedIn = SessionManager.isLoggedIn();

// Logout
SessionManager.logout();
```

### DatabaseUtil

Database connection management.

```java
// Get connection
Connection conn = DatabaseUtil.getConnection();

// Use connection
try (Connection conn = DatabaseUtil.getConnection()) {
    // Database operations
}
```

---

## Exception Handling

### Custom Exceptions

#### AuthenticationException

Thrown for authentication failures.

```java
throw new AuthenticationException("Invalid credentials");
```

#### ValidationException

Thrown for validation failures.

```java
throw new ValidationException("Invalid email format");
```

#### LeaveException

Thrown for leave-related business rule violations.

```java
throw new LeaveException("Insufficient leave balance");
```

#### DatabaseException

Thrown for database connectivity issues.

```java
throw new DatabaseException("Failed to connect to database");
```

### Exception Handling Pattern

```java
try {
    // Service method call
    int result = service.performOperation(params);
} catch (ValidationException e) {
    // Handle validation errors
    System.out.println("Validation Error: " + e.getMessage());
} catch (BusinessException e) {
    // Handle business rule violations
    System.out.println("Business Rule Error: " + e.getMessage());
} catch (Exception e) {
    // Handle unexpected errors
    logger.error("Unexpected error", e);
    System.out.println("System error occurred");
}
```

---

## Best Practices

### Service Layer

1. **Always validate inputs** before processing
2. **Use transactions** for multi-step operations
3. **Log all important actions** using audit service
4. **Return meaningful error messages**
5. **Keep business logic in service layer**

### DAO Layer

1. **Always use PreparedStatements** (prevent SQL injection)
2. **Close resources** in try-with-resources
3. **Handle SQLExceptions** appropriately
4. **Use connection pooling** in production
5. **Never expose SQL details** to upper layers

### Security

1. **Never store plain text passwords**
2. **Always validate and sanitize inputs**
3. **Use role-based access control**
4. **Log security-related events**
5. **Implement session timeout**

---

## API Usage Examples

### Complete Workflow Example

```java
// 1. Login
AuthService authService = new AuthServiceImpl();
Employee employee = authService.login("EMP001", "password123");

// 2. Apply for leave
LeaveService leaveService = new LeaveServiceImpl();
LeaveApplication leave = new LeaveApplication();
leave.setEmployeeId(employee.getEmployeeId());
leave.setLeaveTypeId(1);
leave.setStartDate(LocalDate.now().plusDays(5));
leave.setEndDate(LocalDate.now().plusDays(7));
leave.setReason("Personal work");

int leaveId = leaveService.applyForLeave(leave);

// 3. Create goal
PerformanceService perfService = new PerformanceServiceImpl();
Goal goal = new Goal();
goal.setEmployeeId(employee.getEmployeeId());
goal.setGoalDescription("Complete Java certification");
goal.setDeadline(LocalDate.now().plusMonths(3));
goal.setPriority(Goal.PRIORITY_HIGH);
goal.setSuccessMetrics("Pass exam with 90%+ score");
goal.setProgressPercentage(0);
goal.setStatus(Goal.STATUS_NOT_STARTED);

int goalId = perfService.createGoal(goal);

// 4. Logout
authService.logout();
```


**For more information, see:**
- [README.md](https://github.com/MastanSayyad/RevWorkForce/blob/main/README.md) - Project overview
- [DATABASE_DESIGN.md](https://github.com/MastanSayyad/RevWorkForce/blob/main/docs/Database_Design_Documentation.md) - Database documentation
