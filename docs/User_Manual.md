# RevWorkForce User Manual & Documentation

**Version:** v1.2.1  
**Last Updated:** February 2026  
**System:** Human Resource Management System  
**Platform:** Console Application  

## Table of Contents

1. [RevWorkForce](https://github.com/MastanSayyad/RevWorkForce/blob/docs/docs/User_Manual.md#1-revworkforce)
2. [Getting Started Knowing System](https://github.com/MastanSayyad/RevWorkForce/blob/docs/docs/User_Manual.md#2-getting-started)
3. [User Roles & Permissions](https://github.com/MastanSayyad/RevWorkForce/blob/docs/docs/User_Manual.md#3-user-roles--permissions)
4. [Employee User Guide](https://github.com/MastanSayyad/RevWorkForce/blob/docs/docs/User_Manual.md#4-employee-user-guide)
5. [Manager User Guide](https://github.com/MastanSayyad/RevWorkForce/blob/docs/docs/User_Manual.md#5-manager-user-guide)
6. [Administrator User Guide](https://github.com/MastanSayyad/RevWorkForce/blob/docs/docs/User_Manual.md#6-administrator-user-guide)
7. [Common Tasks](https://github.com/MastanSayyad/RevWorkForce/blob/docs/docs/User_Manual.md#7-common-tasks)
8. [Troubleshooting](https://github.com/MastanSayyad/RevWorkForce/buserlob/docs/docs/User_Manual.md#8-troubleshooting)
9. [Glossary](https://github.com/MastanSayyad/RevWorkForce/blob/docs/docs/User_Manual.md#9-glossary)
10. [Appendices](https://github.com/MastanSayyad/RevWorkForce/blob/docs/docs/User_Manual.md#10-appendices)

---

### 1. RevWorkForce

RevWorkForce is a comprehensive Human Resource Management (HRM) system designed to streamline and automate HR processes within organizations. The system provides a centralized platform for managing employees, leave applications, performance reviews, and organizational goals.

### 1.1 System Requirements

**1.1.1 Minimum Requirements:**
- Operating System: Windows 10, macOS 10.14+, or Linux
- Java Runtime Environment (JRE): Version 11 or higher
- Memory: 2GB RAM minimum
- Storage: 500MB available space
- Network: Access to Oracle database server
- Display: Console/Terminal application

**1.1.2 Software Requirements:**
- Java 11 or higher installed
- Oracle 19c Express Edition (for database)
- Terminal/Command Prompt access

### 1.2 Who Should Use This Manual

This manual is designed for:
- **Employees** - For day-to-day HR tasks (leave applications, profile updates, performance reviews)
- **Managers** - For team management, leave approvals, performance reviews
- **Administrators** - For system administration, employee management, configuration
- **IT Support** - For installation, troubleshooting, and maintenance


## 2. Getting Started

### 2.1 System Access

#### 2.1.1 First Time Login

1. Launch the RevWorkForce application from your terminal/command prompt
2. You will see the login screen:

```
================================================
    WELCOME TO REVWORKFORCE HRM SYSTEM
================================================

Please login to continue:

Employee ID: _
Password: _
```

3. Enter your credentials:
   - **Employee ID**: Provided by your administrator (format: EMP001, MGR001, ADM001)
   - **Password**: Your assigned password (default passwords should be changed immediately)
4. Press Enter to login

**Default Credentials for Testing:**
```
Admin:    ADM001 / password123
Manager:  MGR001 / password123
Employee: EMP001 / password123
```

>[!WARNING]
>Change your password immediately after first login!

#### 2.1.2 Login Troubleshooting

**Problem:** "Invalid credentials" error  
**Solution:** 
- Check CAPS LOCK is off
- Verify Employee ID is correct (case-sensitive)
- Contact your administrator if password forgotten

**Problem:** "Account is inactive"  
**Solution:** Contact your system administrator to reactivate your account

### 2.1.3 Understanding the Interface

After successful login, you'll see a menu based on your role:

```
================================================
EMPLOYEE MENU - Welcome, Mastan Sayyad
================================================

PROFILE & SETTINGS
  1. View My Profile
  2. Update Profile

LEAVE MANAGEMENT
  3. View Leave Balance
  4. Apply for Leave
  5. View My Leave Applications
  6. Cancel Pending Leave
  7. View Holiday Calendar

PERFORMANCE & GOALS
  8. View My Goals
  9. Add New Goal
 10. Update Goal Progress

NOTIFICATIONS
 11. View Notifications

 12. Change Password
  0. Logout
================================================

Enter your choice: ___
```

**Navigation Tips:**
- Enter the number corresponding to your desired action
- Press Enter to confirm
- Enter 0 to return to previous menu or logout
- The system will guide you through each process

### 2.1.4 Changing Your Password

**For Security:** Change your password after first login

**Steps:**
1. From the main menu, select **Change Password**
2. Enter your **current password**
3. Enter your **new password** (minimum 8 characters)
4. **Confirm** your new password
5. Press Enter to save

**Password Requirements:**
- Minimum 8 characters
- Cannot be the same as your current password
- Should not be easily guessable

> [!TIP]
> Use a strong password combining letters, numbers, and special characters

### 2.1.5 Logging Out

To safely exit the system:
1. From any menu, select **[0] Logout**
2. Your session will be terminated
3. You'll return to the login screen

> [!NOTE]
> Always logout when finished to protect your account

---

### 2.1.6 Leave Types

The system supports multiple leave types:

| Leave Type | Code | Default Days/Year | Description |
|------------|------|-------------------|-------------|
| **Casual Leave** | CL | 12 days | For personal matters, short notice |
| **Sick Leave** | SL | 12 days | For illness or medical appointments |
| **Paid Leave** | PL | 18 days | Annual vacation leave |
| **Privilege Leave** | PL | 15 days | Earned leave, can be carried forward |


> [!TIP]
> Check with your HR department for your organization's specific leave policies

## 3. User Roles & Permissions

### 3.1 Role Hierarchy

<img width="1473" height="764" alt="image" src="https://github.com/MastanSayyad/RevWorkForce/blob/docs/docs/RevWorkForce_Role_Hierarchy.png" />

### 3.2 Employee Role

**What Employees Can Do:**

**Profile Management**
- View personal profile
- Update contact information (phone, address, emergency contact)
- Change password

**Leave Management**
- Check leave balances
- Apply for leave
- View leave application history
- Cancel pending leave requests
- View holiday calendar

**Performance & Goals**
- Create performance review (self-assessment)
- Submit performance reviews
- Create personal goals
- Update goal progress
- View historical reviews

**Notifications**
- View notifications
- Mark notifications as read

**What Employees Cannot Do:**
- Approve/reject leave requests
- Add or modify other employees
- View other employees' data
- Access admin functions

---

### 3.3 Manager Role

**Managers have all Employee capabilities PLUS:**

**Team Management**
- View team members
- Access team leave calendar
- View team goals and performance

**Leave Approval**
- View pending leave requests from team
- Approve/reject leave applications
- Add comments to leave decisions

**Performance Management**
- Review team member performance reviews
- Provide manager feedback
- Add performance ratings
- View team goals

**What Managers Cannot Do:**
- Add/remove employees from system
- Assign leave balances
- View audit logs
- Access other teams' data (only their direct reports)

---

### 3.4 Administrator Role

**Administrators have all Manager capabilities PLUS:**

**System Administration**
- Add new employees
- Update employee information
- Deactivate/reactivate employees
- Assign roles to users

**Leave Configuration**
- Assign leave balances for the year
- Add/modify holidays
- Configure leave types

**System Management**
- Create company-wide announcements
- View complete audit logs
- Generate system reports
- Reset user passwords

**Data Management**
- View all employees across organization
- Access all departments and designations
- Full system configuration

---

## 4. Employee User Guide

### 4.1 Managing Your Profile

#### 4.1.1 Viewing Your Profile

**Steps:**
1. Login to the system
2. Select **[1] View My Profile**
3. Your complete profile information will be displayed:

```
================================================
EMPLOYEE PROFILE
================================================

Personal Information:
  Employee ID    : EMP001
  Name           : John Doe
  Email          : john.doe@company.com
  Phone          : 9876543210
  Date of Birth  : 1990-05-15
  Address        : 123 Main Street, City

Employment Details:
  Department     : IT
  Designation    : Software Engineer
  Manager        : Jane Smith (MGR001)
  Joining Date   : 2020-01-15
  Status         : Active

System Access:
  Roles          : EMPLOYEE
================================================
```

#### 4.1.2 Updating Your Profile

Employees can update certain profile fields:

**Steps:**
1. Select **[2] Update Profile**
2. System shows current values - press Enter to keep, or enter new value
3. Update these fields:
   - Phone number
   - Address
   - Emergency contact
4. Review changes and confirm
5. System saves and confirms update

**Example:**
```
================================================
UPDATE PROFILE
================================================
Leave blank to keep current value.

Phone (9876543210): 9999999999
Address (123 Main Street): [Press Enter to keep]
Emergency Contact (Jane Doe - 9876543211): 
[Press Enter to keep]

Confirm changes? (Y/N): Y

✓ Profile updated successfully!
================================================
```

> [!TIP]
> You cannot update your name, email, department, or designation. Contact HR for these changes.

### 4.2 Leave Management

#### 4.2.1 Checking Leave Balance

**Steps:**
1. Select **[3] View Leave Balance**
2. System displays current year balances:

```
================================================
LEAVE BALANCE - 2026
================================================

Leave Type        Total  Used  Available
-----------------------------------------------
Casual Leave        12     3       9
Sick Leave          12     1      11
Paid Leave          18     5      13
Privilege Leave     15     0      15
-----------------------------------------------
TOTAL               57     9      48
================================================
```

> [!NOTE]
> Balances are updated in real-time when leaves are approved

#### 4.2.2. Applying for Leave

**Steps:**
1. Select **[4] Apply for Leave**
2. System shows available leave types
3. Select your desired leave type (enter number)
4. Enter **start date** (format: YYYY-MM-DD, e.g., 2026-03-15)
5. Enter **end date**
6. Provide **reason** for leave
7. Review and confirm

**Example:**
```
================================================
APPLY FOR LEAVE
================================================

Available Leave Types:
1. CASUAL (12 days) - For personal matters
2. SICK (12 days) - For illness
3. PAID (18 days) - Annual vacation
4. PRIVILEGE (15 days) - Earned leave

Select leave type: 1

Start date (YYYY-MM-DD): 2026-03-15
End date (YYYY-MM-DD): 2026-03-17
Reason: Family function

Summary:
  Leave Type  : Casual Leave
  Dates       : 2026-03-15 to 2026-03-17
  Total Days  : 3 days (excluding weekends)
  Available   : 9 days
  After Apply : 6 days

Confirm application? (Y/N): Y

✓ Leave application submitted successfully!
  Application ID: 145
  Status: PENDING
  Your manager will be notified.
================================================
```

**System Validations:**
- Sufficient balance available
- No overlapping leaves
- Valid date range
- Future dates only
- Excludes weekends and holidays

**Common Errors:**

**"Insufficient leave balance"**
- You don't have enough days for this leave type
- Solution: Choose different dates or leave type

**"Overlapping leave application exists"**
- You have another leave during these dates
- Solution: Cancel previous leave or choose different dates

**"Invalid date range"**
- Dates are in the past or end date is before start date
- Solution: Enter correct dates

#### 4.2.3 Viewing Leave Applications

**Steps:**
1. Select **[5] View My Leave Applications**
2. System displays all your applications:

```
================================================
MY LEAVE APPLICATIONS
================================================

ID   Type    From        To          Days  Status    Reviewed By
-----------------------------------------------------------------
145  Casual  2026-03-15  2026-03-17   3   PENDING   -
142  Sick    2026-02-10  2026-02-10   1   APPROVED  Jane Smith
138  Paid    2026-01-20  2026-01-24   5   APPROVED  Jane Smith
-----------------------------------------------------------------

Total Applications: 3
Pending: 1 | Approved: 2 | Rejected: 0
================================================
```

**Status Meanings:**
- **PENDING**: Awaiting manager approval
- **APPROVED**: Manager approved, balance deducted
- **REJECTED**: Manager rejected with comments
- **CANCELLED**: You cancelled the application

#### 4.2.4 Cancelling Pending Leave

You can only cancel PENDING leaves (not yet approved/rejected):

**Steps:**
1. Select **[6] Cancel Pending Leave**
2. System shows your pending applications
3. Enter **Application ID** to cancel
4. Confirm cancellation

**Example:**
```
================================================
CANCEL LEAVE
================================================

Pending Applications:
ID   Type    From        To          Days
-------------------------------------------
145  Casual  2026-03-15  2026-03-17   3
-------------------------------------------

Enter Leave Application ID to cancel: 145

Are you sure you want to cancel this leave? (Y/N): Y

✓ Leave application cancelled successfully!
================================================
```

> [!NOTE]
> You cannot cancel APPROVED or REJECTED leaves. Contact your manager.

#### 4.2.5 Viewing Holiday Calendar

**Steps:**
1. Select **[7] View Holiday Calendar**
2. System shows public holidays for current year:

```
================================================
HOLIDAY CALENDAR - 2026
================================================

Holiday                    Date
-------------------------------------------
New Year's Day            2026-01-01
Republic Day              2026-01-26
Holi                      2026-03-14
Good Friday               2026-04-03
Independence Day          2026-08-15
Gandhi Jayanti            2026-10-02
Diwali                    2026-10-21
Christmas                 2026-12-25
-------------------------------------------
Total Holidays: 8
================================================
```

> [!NOTE]
> These holidays are automatically excluded when calculating working days

### 4.3 Performance Management

#### 4.3.1 Creating Performance Review

Performance reviews are typically done annually. You'll create a self-assessment that your manager will review.

**Steps:**
1. Select **[8] Create Performance Review** (only available during review period)
2. Enter review details:
   - Review Year
   - Key Deliverables (what you accomplished)
   - Major Accomplishments
   - Areas of Improvement
   - Self-Assessment Rating (1.0 to 5.0)
3. Save as DRAFT or Submit immediately

**Example:**
```
================================================
CREATE PERFORMANCE REVIEW - 2025
================================================

Key Deliverables:
> Led development of new employee module
> Completed Java 11 migration project
> Mentored 2 junior developers

Major Accomplishments:
> Reduced application response time by 40%
> Zero production bugs in Q3 and Q4
> Earned Java certification

Areas of Improvement:
> Public speaking skills
> Advanced database optimization
> Cloud deployment knowledge

Self-Assessment Rating (1.0 - 5.0): 4.0

Status:
1. Save as DRAFT (can edit later)
2. Submit for Manager Review (cannot edit)

Choose option: 2

✓ Performance review submitted successfully!
  Your manager will be notified.
================================================
```

**Rating Scale:**
- **5.0**: Exceptional - Far exceeds expectations
- **4.0**: Exceeds Expectations - Consistently high performance
- **3.0**: Meets Expectations - Solid, reliable performance
- **2.0**: Needs Improvement - Below expected performance
- **1.0**: Unsatisfactory - Significant improvement needed

> [!NOTE]
> Be honest and specific in your self-assessment. Provide concrete examples.

#### 4.3.2 Viewing Performance Reviews

**Steps:**
1. Select **[9] View Performance Reviews**
2. System shows all your reviews:

```
================================================
MY PERFORMANCE REVIEWS
================================================

Year  Status      Self Rating  Manager Rating  Manager
-------------------------------------------------------
2025  REVIEWED       4.0           4.5         Jane Smith
2024  REVIEWED       3.5           4.0         Jane Smith
2023  REVIEWED       3.0           3.5         John Brown
-------------------------------------------------------

Select a review to view details (or 0 to return): 2025

================================================
PERFORMANCE REVIEW - 2025
================================================

Employee: John Doe (EMP001)
Year: 2025
Status: REVIEWED

Self-Assessment:
  Rating: 4.0
  Key Deliverables: Led development of...
  Major Accomplishments: Reduced application...
  Areas of Improvement: Public speaking...

Manager Feedback:
  Manager: Jane Smith (MGR001)
  Rating: 4.5
  Feedback: Excellent performance throughout the
  year. John has shown exceptional technical
  skills and leadership. His mentoring of junior
  developers was particularly valuable. 
  
  Recommendation for promotion consideration.

Reviewed Date: 2026-01-15
================================================
```

### 4.4 Goal Management

#### 4.4.1 Creating Goals

Set SMART goals (Specific, Measurable, Achievable, Relevant, Time-bound):

**Steps:**
1. Select **[10] Add New Goal**
2. Enter goal details:
   - Description
   - Deadline
   - Priority (High/Medium/Low)
   - Success Metrics
3. Save goal

**Example:**
```
================================================
ADD NEW GOAL
================================================

Goal Description:
> Complete AWS Cloud Practitioner Certification

Deadline (YYYY-MM-DD): 2026-06-30

Priority:
1. HIGH
2. MEDIUM
3. LOW
Select priority: 1

Success Metrics:
> Pass AWS exam with score of 850+
> Complete 3 practice projects
> Present learnings to team

✓ Goal created successfully!
  Goal ID: 78
  Status: NOT_STARTED
  Progress: 0%
================================================
```

#### 4.4.2 Updating Goal Progress

Track your progress regularly:

**Steps:**
1. Select **[11] View My Goals**
2. System shows active goals
3. Select goal to update
4. Enter new progress (0-100%)

**Example:**
```
================================================
MY GOALS
================================================

ID   Description              Deadline    Priority  Progress  Status
-----------------------------------------------------------------------
78   Complete AWS Cert...    2026-06-30   HIGH       50%     IN_PROGRESS
76   Learn React.js          2026-09-30   MEDIUM     75%     IN_PROGRESS
74   Improve code quality    2026-12-31   HIGH      100%     COMPLETED
-----------------------------------------------------------------------

Enter Goal ID to update progress: 78

Current Progress: 50%
Enter new progress (0-100): 75

Manager Comments (if any): Great progress! Keep it up!

✓ Goal progress updated successfully!
  Status automatically changed to: IN_PROGRESS
================================================
```

**Progress Guidelines:**
- **0%**: Not started
- **1-99%**: In progress
- **100%**: Completed (status auto-changes to COMPLETED)

> [!NOTE]
> Update your goals monthly to stay on track

### 4.5 Notifications

#### 4.5.1 Viewing Notifications

**Steps:**
1. Select **[12] View Notifications**
2. System shows unread notifications first:

```
================================================
NOTIFICATIONS
================================================

UNREAD (3):
---------------------------------------
Your leave application #145 has been APPROVED
   2026-02-08 10:30 AM

Performance review period is now open
   2026-02-01 09:00 AM

Happy Birthday, John! 
   2026-01-15 00:01 AM
---------------------------------------

READ (5):
---------------------------------------
   Your goal progress updated successfully
   2026-01-10 03:45 PM
   
   [More...]
---------------------------------------

Mark all as read? (Y/N): Y

✓ All notifications marked as read
================================================
```

**Notification Types:**
- Leave Management (applications, approvals, rejections)
- Performance Reviews (submissions, feedback)
- Goals (progress updates, deadline reminders)
- Personal (birthdays, anniversaries)
- Announcements (company-wide messages)

---

## 5. Manager User Guide

### 5.1 Team Management

#### 5.1.1 Viewing Team Members

**Steps:**
1. Login as Manager
2. Select **[Team Members]** from Manager menu
3. System shows your direct reports:

```
================================================
MY TEAM MEMBERS
================================================

ID      Name            Designation         Status    Joining Date
-------------------------------------------------------------------
EMP001  John Doe        Software Engineer   Active    2020-01-15
EMP002  Alice Smith     Senior Developer    Active    2019-06-20
EMP003  Bob Johnson     QA Engineer         Active    2021-03-10
EMP004  Carol White     Business Analyst    Active    2020-11-05
-------------------------------------------------------------------
Total Team Members: 4
Active: 4 | Inactive: 0
================================================
```

#### 5.1.2 Viewing Team Leave Calendar

See all team leaves at a glance:

**Steps:**
1. Select **[View Team Leave Calendar]**
2. Choose date range or current month
3. System shows team leave schedule:

```
================================================
TEAM LEAVE CALENDAR - MARCH 2026
================================================

Date         Employee       Type      Status
-----------------------------------------------
2026-03-15   John Doe       Casual    APPROVED
2026-03-16   John Doe       Casual    APPROVED
2026-03-17   John Doe       Casual    APPROVED
2026-03-20   Alice Smith    Sick      PENDING
2026-03-25   Bob Johnson    Paid      APPROVED
2026-03-26   Bob Johnson    Paid      APPROVED
2026-03-27   Bob Johnson    Paid      APPROVED
-----------------------------------------------

Team Coverage: 75% available on 2026-03-15
================================================
```

> [!TIP]
> Use this to ensure adequate team coverage before approving leaves

### 5.2 Leave Approval

#### 5.2.1 Viewing Pending Leave Requests

**Steps:**
1. Select **[View Pending Leaves]**
2. System shows all pending approvals:

```
================================================
PENDING LEAVE REQUESTS
================================================

ID   Employee      Type    From        To          Days  Reason
-----------------------------------------------------------------
148  Alice Smith   Sick    2026-03-20  2026-03-20   1   Fever
147  Bob Johnson   Casual  2026-03-18  2026-03-19   2   Personal
145  John Doe      Casual  2026-03-15  2026-03-17   3   Family
-----------------------------------------------------------------
Total Pending: 3
================================================
```

#### 5.2.2 Approving Leave

**Steps:**
1. From pending leaves list, enter **Application ID**
2. Review leave details
3. Choose **[Approve]**
4. Add optional comments
5. Confirm

**Example:**
```
================================================
APPROVE LEAVE REQUEST
================================================

Application ID: 145

Employee: John Doe (EMP001)
Leave Type: Casual Leave
Period: 2026-03-15 to 2026-03-17 (3 days)
Reason: Family function

Current Balance: 9 days
After Approval: 6 days

Team Impact:
  Date         Coverage
  2026-03-15   75% (3/4 available)
  2026-03-16   75% (3/4 available)
  2026-03-17   75% (3/4 available)

Comments (optional):
> Approved. Ensure handover is complete before leave.

Approve this leave? (Y/N): Y

✓ Leave approved successfully!
  Employee will be notified
  Balance updated automatically
================================================
```

**Manager Tips:**
- Check team coverage before approving
- Ensure critical work is covered
- Communicate with team about leaves
- Add meaningful comments for record

#### 5.2.3 Rejecting Leave

**Steps:**
1. From pending leaves list, enter **Application ID**
2. Review leave details
3. Choose **[Reject]**
4. **Must provide comments** explaining rejection
5. Confirm

**Example:**
```
================================================
REJECT LEAVE REQUEST
================================================

Application ID: 148

Employee: Alice Smith (EMP002)
Leave Type: Sick Leave
Period: 2026-03-20 to 2026-03-20 (1 day)
Reason: Fever

Rejection Reason (Required):
> Project deadline on 2026-03-21. Please apply after 
> deadline or provide medical certificate for emergency.

Reject this leave? (Y/N): Y

✓ Leave rejected successfully!
  Employee will be notified with comments
================================================
```

> [!NOTE]
> **Important:** Always provide clear, constructive comments when rejecting

### 5.3 Performance Reviews

#### 5.3.1 Reviewing Team Performance

**Steps:**
1. Select **[Pending Reviews]**
2. System shows submitted reviews awaiting your feedback
3. Select review to evaluate
4. Provide manager feedback and rating
5. Submit final review

**Example:**
```
================================================
REVIEW PERFORMANCE - John Doe (2025)
================================================

Employee Self-Assessment:
  Rating: 4.0
  
  Key Deliverables:
  • Led development of new employee module
  • Completed Java 11 migration project
  • Mentored 2 junior developers
  
  Major Accomplishments:
  • Reduced application response time by 40%
  • Zero production bugs in Q3 and Q4
  • Earned Java certification
  
  Areas of Improvement:
  • Public speaking skills
  • Advanced database optimization
  • Cloud deployment knowledge

Manager Feedback:
> Excellent performance throughout the year. John has
> shown exceptional technical skills and leadership.
> His mentoring of junior developers was particularly
> valuable. The 40% performance improvement demonstrates
> his technical expertise.
> 
> Areas for development: Public speaking (already
> identified), consider leadership training for
> potential team lead role.

Manager Rating (1.0 - 5.0): 4.5

Submit Review? (Y/N): Y

✓ Performance review completed!
  Employee will be notified
  Review stored in system
================================================
```

**Rating Guidelines:**
- Compare against role expectations
- Consider accomplishments AND growth areas
- Be fair and objective
- Provide constructive feedback
- Align with organizational standards

**Best Practices:**
- Review self-assessment thoroughly
- Provide specific examples
- Be timely with reviews
- Discuss with employee in person
- Document everything

#### 5.3.2 Viewing Team Goals

**Steps:**
1. Select **[View Team Goals]**
2. System shows all active goals for your team:

```
================================================
TEAM GOALS
================================================

Employee      Goal                  Deadline    Priority  Progress
-------------------------------------------------------------------
John Doe      AWS Certification     2026-06-30   HIGH       75%
Alice Smith   React Training        2026-08-31   MEDIUM     60%
Bob Johnson   Test Automation       2026-12-31   HIGH       40%
Carol White   Business Analysis     2026-09-30   MEDIUM     55%
-------------------------------------------------------------------

Filter by:
1. All Goals
2. High Priority
3. Behind Schedule
4. Completed

Select option: 2

HIGH PRIORITY GOALS:
-------------------------------------------------------------------
John Doe      AWS Certification     2026-06-30   75%  ✓ On Track
Bob Johnson   Test Automation       2026-12-31   40%   Needs Focus
-------------------------------------------------------------------
================================================
```

> [!TIP]
> Regularly review team goals and provide support

---

## 6. Administrator User Guide

### 6.1 Employee Management

#### 6.1.1 Adding New Employee

**Steps:**
1. Select **[Add New Employee]** from Admin menu
2. Enter employee details:
   - Employee ID (system validates uniqueness)
   - Personal information
   - Employment details
   - System access
3. System generates default password
4. Confirm and save

**Example:**
```
================================================
ADD NEW EMPLOYEE
================================================

Employee ID: EMP005
First Name: Sarah
Last Name: Wilson
Email: sarah.wilson@company.com
Phone: 9988776655
Date of Birth (YYYY-MM-DD): 1992-08-20
Address: 456 Park Avenue, City

Department:
1. IT
2. HR
3. Finance
4. Operations
Select department: 1

Designation:
1. Software Engineer
2. Senior Developer
3. Team Lead
4. Manager
Select designation: 1

Manager ID: MGR001

Joining Date (YYYY-MM-DD): 2026-03-01
Salary: 65000

Assign Roles:
[X] EMPLOYEE (default)
[ ] MANAGER
[ ] ADMIN
Select additional roles (comma-separated, or Enter for default): 

Review Details:
  ID: EMP005
  Name: Sarah Wilson
  Email: sarah.wilson@company.com
  Department: IT
  Designation: Software Engineer
  Manager: Jane Smith (MGR001)
  Joining: 2026-03-01
  Roles: EMPLOYEE

Default Password: password123
(Employee must change on first login)

Confirm creation? (Y/N): Y

✓ Employee created successfully!
  
  IMPORTANT: Share these credentials securely:
  Employee ID: EMP005
  Password: password123
  
  Email sent to: sarah.wilson@company.com
================================================
```

> [!WARNING]
> **Security:** Never share passwords via email. Use secure communication channels.

#### 6.1.2 Updating Employee Information

**Steps:**
1. Select **[Update Employee]**
2. Enter Employee ID to update
3. System shows current details
4. Update fields as needed
5. Confirm changes

**Fields You Can Update:**
- Personal information (phone, address, DOB)
- Employment details (department, designation, manager)
- Salary
- Status (Active/Inactive)
- Roles

**Example:**
```
================================================
UPDATE EMPLOYEE
================================================

Enter Employee ID: EMP005

Current Details:
  Name: Sarah Wilson
  Email: sarah.wilson@company.com
  Phone: 9988776655
  Department: IT
  Designation: Software Engineer
  Manager: MGR001
  Salary: 65000
  Status: Active

Update Fields (leave blank to keep current):

Phone: 9900112233
Department (1-IT, 2-HR, 3-Finance, 4-Operations): [Keep]
Designation: 2 (Senior Developer)
Manager ID: [Keep]
Salary: 75000
Status (1-Active, 2-Inactive): [Keep]

Confirm updates? (Y/N): Y

✓ Employee updated successfully!
  Changes logged in audit trail
================================================
```

#### 6.1.3 Deactivating Employee

When an employee leaves:

**Steps:**
1. Select **[Deactivate Employee]**
2. Enter Employee ID
3. Confirm deactivation
4. Employee account is marked inactive

**Example:**
```
================================================
DEACTIVATE EMPLOYEE
================================================

Enter Employee ID: EMP003

Employee: Bob Johnson
Department: IT
Designation: QA Engineer
Status: Active

Warning: This will:
  • Disable login access
  • Cancel all pending leaves
  • Mark account as inactive
  • Preserve all historical data

Reason for deactivation:
> Resigned - Last day 2026-03-31

Confirm deactivation? (Y/N): Y

✓ Employee deactivated successfully!
  
  Follow-up actions:
  • Collect company assets
  • Conduct exit interview
  • Process final settlement
  • Archive documents
================================================
```

> [!NOTE]
> Deactivated employees' data is preserved for compliance and reporting

#### 6.1.4 Assigning Roles

**Steps:**
1. Select **[Assign Roles]**
2. Enter Employee ID
3. Select roles to assign
4. Confirm

**Example:**
```
================================================
ASSIGN ROLES
================================================

Enter Employee ID: EMP002

Current Roles: EMPLOYEE

Available Roles:
[ ] EMPLOYEE (already assigned)
[ ] MANAGER
[ ] ADMIN

Select roles to assign (comma-separated): MANAGER

New Roles: EMPLOYEE, MANAGER

This will grant manager privileges:
  • View team members
  • Approve/reject leaves
  • Review performance

Confirm role assignment? (Y/N): Y

✓ Roles assigned successfully!
  Employee: Alice Smith
  New Access: Manager Menu available
================================================
```

### 6.2 Leave Configuration

#### 6.2.1 Assigning Leave Balances

At the start of each year, assign leave balances:

**Steps:**
1. Select **[Assign Leave Balances]**
2. Choose:
   - Specific employee, OR
   - All employees
3. Select year
4. System assigns default balances per leave type

**Example:**
```
================================================
ASSIGN LEAVE BALANCES - 2026
================================================

Options:
1. Assign to specific employee
2. Assign to all active employees
3. Assign to department

Select option: 2

Active Employees: 45

Default Allocations:
  Casual Leave:     12 days
  Sick Leave:       12 days
  Paid Leave:       18 days
  Privilege Leave:  15 days

Total per employee: 57 days

This will assign balances to 45 employees

Proceed? (Y/N): Y

Processing...
✓ EMP001 - Assigned
✓ EMP002 - Assigned
✓ EMP003 - Assigned
[...]
✓ EMP045 - Assigned

✓ Leave balances assigned successfully!
  Employees: 45
  Total days allocated: 2,565
================================================
```

#### 6.2.2 Adding Holidays

**Steps:**
1. Select **[Add Holiday]**
2. Enter holiday details
3. Save

**Example:**
```
================================================
ADD HOLIDAY
================================================

Holiday Name: Dussehra
Holiday Date (YYYY-MM-DD): 2026-10-15
Year: 2026

Confirm? (Y/N): Y

✓ Holiday added successfully!
  This holiday will be excluded from leave calculations
================================================
```

### 6.3 System Management

#### 6.3.1 Creating Announcements

**Steps:**
1. Select **[Create Announcement]**
2. Enter title and content
3. Announcement is sent to all employees

**Example:**
```
================================================
CREATE ANNOUNCEMENT
================================================

Title: Annual Company Picnic

Content:
> Join us for our annual company picnic on March 30, 2026
> at Green Valley Resort. All employees and families are
> invited. Transportation will be provided.
> 
> Please RSVP by March 20.
> 
> Contact HR for more details.

Posted By: ADM001 (Admin)

Confirm? (Y/N): Y

✓ Announcement created!
  Notification sent to all employees
  Total recipients: 45
================================================
```

#### 6.3.2 Viewing Audit Logs

**Steps:**
1. Select **[View Audit Logs]**
2. Filter by:
   - Employee
   - Action type
   - Date range
   - Table/Module
3. View detailed activity log

**Example:**
```
================================================
AUDIT LOGS
================================================

Filter Options:
1. All logs
2. By employee
3. By action type
4. By date range
5. By table

Select filter: 2

Enter Employee ID: EMP001

================================================
AUDIT LOG - EMP001 (John Doe)
================================================

Date/Time           Action    Table              Details
-----------------------------------------------------------------
2026-02-08 14:30   UPDATE    leave_applications  Applied for leave
2026-02-08 09:15   UPDATE    employees           Updated profile
2026-02-05 16:45   INSERT    goals               Created new goal
2026-02-01 10:20   INSERT    performance_reviews Submitted review
2026-01-28 11:30   LOGIN     -                   User logged in
-----------------------------------------------------------------

Total Records: 5

Export to CSV? (Y/N): N
================================================
```

> [!NOTE]
> Audit logs are essential for regulatory compliance and security

---

## 7. Common Tasks

### How to Apply for Emergency Leave

For urgent situations:

1. **Apply through system** (if possible):
   - Login and apply normally
   - Select "SICK" or "CASUAL" leave
   - Mark as urgent in reason
   - Manager will be notified immediately

2. **If unable to access system**:
   - Contact manager via phone/email
   - Provide dates and reason
   - Apply in system when possible
   - Regularize the application

> [!TIP]
> Always inform your manager, even if applying through system

---

### How to Handle Leave Balance Discrepancies

If your balance doesn't match your records:

1. **Check application history**:
   - Select [View My Leave Applications]
   - Verify all approved leaves

2. **Verify leave types**:
   - Ensure you're checking correct leave type
   - Check the correct year

3. **Contact HR if mismatch persists**:
   - Provide application IDs
   - Mention expected vs actual balance

---

### How to Request Password Reset

If you forget your password:

1. **Contact System Administrator**
2. **Verify your identity** (provide Employee ID, email)
3. **Administrator resets password** to default
4. **Login with default password**
5. **Change password immediately**

> [!WARNING]
> **Security:** Never share your password with anyone, including administrators

---

### How to Handle Rejected Leave

If your leave is rejected:

1. **Read manager's comments** carefully
2. **Understand the reason** (project deadline, team coverage, etc.)
3. **Options:**
   - **Re-apply** for different dates
   - **Discuss with manager** for alternatives
   - **Escalate to HR** if you believe rejection is unfair

---

### How to Track Goal Progress

Best practices for goal management:

1. **Set SMART goals** initially:
   - **S**pecific
   - **M**easurable
   - **A**chievable
   - **R**elevant
   - **T**ime-bound

2. **Update monthly**:
   - Review progress
   - Update percentage
   - Note any blockers

3. **Communicate with manager**:
   - Discuss challenges
   - Seek support if needed
   - Celebrate milestones

---

### How to Prepare Performance Review

Effective self-assessment tips:

**Before Starting:**
1. **Gather data**:
   - Review your goals
   - List completed projects
   - Note quantifiable achievements
   - Collect feedback received

2. **Be honest**:
   - Don't exaggerate
   - Don't be too modest
   - Use specific examples

**While Writing:**
1. **Key Deliverables**:
   - List major projects/responsibilities
   - Focus on completed items
   - Quantify where possible

2. **Major Accomplishments**:
   - Highlight exceptional work
   - Include metrics (time saved, bugs fixed, etc.)
   - Mention recognition received

3. **Areas of Improvement**:
   - Show self-awareness
   - Identify growth opportunities
   - Demonstrate learning attitude

4. **Self-Rating**:
   - Be realistic
   - Align with achievements
   - Consider role expectations

**Example Good vs Bad:**

 **Bad:**
```
Key Deliverables: Worked on projects
Major Accomplishments: Did good work
Areas of Improvement: None
Rating: 5.0
```

**Good:**
```
Key Deliverables:
• Led development of Employee Management module (8 weeks)
• Migrated codebase to Java 11 (reduced tech debt)
• Mentored 2 junior developers

Major Accomplishments:
• Reduced API response time from 800ms to 480ms (40% improvement)
• Achieved zero critical bugs in production for 6 months
• Earned Oracle Certified Java Programmer certification

Areas of Improvement:
• Public speaking - plan to join Toastmasters
• Advanced database optimization techniques
• Cloud deployment (AWS/Azure)

Rating: 4.0 (Exceeds Expectations)
```

---

## 8 Troubleshooting

### Login Issues

#### Problem: "Invalid Credentials"

**Possible Causes:**
- Incorrect Employee ID or password
- CAPS LOCK is on
- Account is inactive

**Solutions:**
1. Verify Employee ID (case-sensitive)
2. Check CAPS LOCK
3. Ensure correct password
4. Contact administrator if account inactive

#### Problem: "Session Expired"

**Cause:** Inactive for 30 minutes

**Solution:**
1. Simply login again
2. Your previous work is saved

### Leave Application Issues

#### Problem: "Insufficient Balance"

**Cause:** Not enough leave days available

**Solutions:**
1. Check balance: [View Leave Balance]
2. Reduce leave duration
3. Choose different leave type
4. Wait for next year allocation

#### Problem: "Overlapping Leave Exists"

**Cause:** Another leave application for same dates

**Solutions:**
1. Check existing applications
2. Cancel previous leave if needed
3. Choose different dates

#### Problem: "Cannot Apply for Past Dates"

**Cause:** System prevents backdating

**Solutions:**
1. For emergency past leave, contact HR
2. Apply for future dates only

---

## 9. Glossary

- **Administrator (Admin)**: System user with full access to all features and configuration settings.
- **Audit Log**: Record of all actions performed in the system, used for compliance and security.
- **BCrypt**: Industry-standard password hashing algorithm used for security.
- **Business Day**: Monday through Friday, excluding public holidays.
- **CRUD**: Create, Read, Update, Delete - basic database operations.
- **DAO (Data Access Object)**: Software component that handles database operations.
- **Deactivate**: Disable an employee account while preserving historical data.
- **Designation**: Job title or position (e.g., Software Engineer, Manager).
- **Employee ID**: Unique identifier for each employee (e.g., EMP001).
- **Leave Balance**: Number of leave days available for each leave type.
- **Leave Type**: Category of leave (Casual, Sick, Paid, Privilege).
- **Manager**: Employee with team management responsibilities and approval authority.
- **Notification**: System alert sent to users for important events.
- **Pending**: Status indicating an action awaits approval.
- **Performance Review**: Annual evaluation of employee performance.
- **Privilege Leave (PL)**: Earned leave that typically carries forward.
- **RBAC (Role-Based Access Control)**: Security model restricting access based on user roles.
- **Self-Assessment**: Employee's own evaluation of their performance.
- **Session**: Period of active system use after login until logout.
- **SMART Goals**: Specific, Measurable, Achievable, Relevant, Time-bound objectives.
- **Status**: Current state of an application or review (Pending, Approved, Rejected, etc.).
- **Working Days**: Business days excluding weekends and holidays.

---

## 10. Appendices

### Appendix A: Default Credentials

**For Testing Only - Change Immediately:**

| Role | Employee ID | Password |
|------|-------------|----------|
| Admin | ADM001 | password123 |
| Manager | MGR001 | password123 |
| Employee | EMP001 | password123 |

> [!Caution]
> **Production:** All default passwords must be changed before go-live.

### Appendix B: Leave Type Details

| Leave Type | Code | Annual Quota | Carry Forward | Notice Period | Valid For |
|------------|------|--------------|---------------|---------------|-----------|
| Casual Leave | CL | 12 days | No | 1 day | Short-term personal needs |
| Sick Leave | SL | 12 days | No | Same day | Illness, medical appointments |
| Paid Leave | PL | 18 days | No | 7 days | Planned vacation |
| Privilege Leave | PL | 15 days | Yes (max 30) | 15 days | Long vacations |

> [!NOTE]
> Your organization may have different policies. Check with HR.

### Appendix C: Performance Rating Scale

| Rating | Description | Meaning |
|--------|-------------|---------|
| 5.0 | Exceptional | Consistently exceeds all expectations; outstanding performance |
| 4.0 | Exceeds Expectations | Regularly surpasses objectives; high-quality work |
| 3.0 | Meets Expectations | Consistently meets job requirements; reliable performance |
| 2.0 | Needs Improvement | Sometimes fails to meet expectations; requires support |
| 1.0 | Unsatisfactory | Consistently fails to meet requirements; immediate improvement needed |

### Appendix D: Common Error Codes

| Error Code | Message | Solution |
|------------|---------|----------|
| ERR_001 | Invalid credentials | Check Employee ID and password |
| ERR_002 | Insufficient balance | Not enough leave days available |
| ERR_003 | Overlapping dates | Leave already exists for these dates |
| ERR_004 | Invalid date range | End date before start date or past dates |
| ERR_005 | Session expired | Login again |
| ERR_006 | Access denied | Contact administrator for role assignment |
| ERR_007 | Database connection failed | Contact IT support |
| ERR_008 | Duplicate employee ID | Employee ID already exists |

### Appendix E: Keyboard Shortcuts

| Key | Action |
|-----|--------|
| Enter | Confirm/Submit |
| 0 | Back/Logout |
| Ctrl+C | Cancel operation (force exit) |


---

## Document Version History

| Version | Date | Author | Changes |
|---------|------|--------|---------|
| 1.0 | Feb 2026 | System Team | Initial release |

---

**END OF USER MANUAL**
**For more information, see:**
- [README.md](https://github.com/MastanSayyad/RevWorkForce/blob/main/README.md) - *Project overview*
- [Project Report](https://github.com/MastanSayyad/RevWorkForce/blob/main/docs/Project_Report.pdf) - *Project report including objectives, methodology, and outcomes*
- [User Manual](https://github.com/MastanSayyad/RevWorkForce/blob/main/README.md#prerequisites-) - *Step-by-step project build and usage guide Instructions*
- [PPT Slides](https://github.com/MastanSayyad/RevWorkForce/blob/main/docs/Project_Report.pdf) - *Project presentation slides*
- [Database Design Documentation](https://github.com/MastanSayyad/RevWorkForce/blob/main/docs/Database_Design_Documentation.md) -  *Database schema, ER diagrams, and relationships*
- [API Documentation](https://github.com/MastanSayyad/RevWorkForce/blob/main/docs/Database_Design_Documentation.md) - *Application workflows and module interactions*
- [Diagrams & Charts](https://github.com/MastanSayyad/RevWorkForce/blob/main/docs/Database_Design_Documentation.md) - *Architecture diagrams, ERD, and system design visuals*
- [Testing Documentation](https://github.com/MastanSayyad/RevWorkForce/blob/main/docs/Database_Design_Documentation.md) -  *Test strategy, coverage details, and reports*


<p align="right">
  <a href="https://github.com/MastanSayyad/RevWorkForce/blob/main/docs/User_Manual.md">
    <img src="https://img.shields.io/badge/Scroll%20to%20Top-Purple?style=for-the-badge&color=ffffff" />
  </a>
</p>
