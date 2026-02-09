# RevWorkForce - Database Design Documentation

## Table of Contents

- [Overview](#overview)
- [Database Schema](#database-schema)
- [Table Definitions](#table-definitions)
- [Relationships](#relationships)
- [Constraints](#constraints)
- [Indexes](#indexes)
- [Sequences](#sequences)
- [Sample Data](#sample-data)

## Overview

### Database Management System

- **DBMS:** Oracle Database 19c Express Edition (XE)
- **Service Name:** XEPDB1 (Pluggable Database)
- **Character Set:** UTF-8
- **Total Tables:** 14
- **Total Sequences:** 10

### Design Principles

1. **Normalization:** All tables are in 3NF (Third Normal Form)
2. **Referential Integrity:** Foreign keys with appropriate constraints
3. **Data Integrity:** Check constraints for data validation
4. **Audit Trail:** Timestamps on all tables (created_at, updated_at)
5. **Soft Deletes:** is_active flag instead of hard deletes

## Database Schema

### Entity Relationship Overview

![ERD Diagram](https://github.com/MastanSayyad/RevWorkForce/blob/main/docs/Diagrams%20and%20Charts/ER-Diagram.png)

## Table Definitions

### 1. employees

**Description:** Core employee information

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| employee_id | VARCHAR2(10) | PRIMARY KEY | Unique employee identifier (EMP001) |
| first_name | VARCHAR2(50) | NOT NULL | Employee first name |
| last_name | VARCHAR2(50) | NOT NULL | Employee last name |
| email | VARCHAR2(100) | UNIQUE, NOT NULL | Email address |
| phone | VARCHAR2(15) | NOT NULL | Phone number |
| address | VARCHAR2(200) | | Residential address |
| date_of_birth | DATE | NOT NULL | Date of birth |
| department_id | NUMBER(10) | FK → departments | Department reference |
| designation_id | NUMBER(10) | FK → designations | Designation reference |
| manager_id | VARCHAR2(10) | FK → employees | Reports-to manager |
| joining_date | DATE | NOT NULL | Date of joining |
| salary | NUMBER(10,2) | CHECK > 0 | Current salary |
| emergency_contact | VARCHAR2(100) | | Emergency contact info |
| password_hash | VARCHAR2(100) | NOT NULL | BCrypt hashed password |
| is_active | NUMBER(1) | DEFAULT 1 | Active status (1=active, 0=inactive) |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Record creation time |
| updated_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Last update time |

**Sample Data:**
```sql
INSERT INTO employees VALUES (
    'EMP001', 'Amit', 'Patel', 'amit.patel@revworkforce.com',
    '9876543210', '123 MG Road, Bengaluru', 
    TO_DATE('1990-05-15', 'YYYY-MM-DD'),
    1, 3, 'MGR001',
    TO_DATE('2020-01-15', 'YYYY-MM-DD'),
    60000, '9988776655',
    '$2a$12$...',  -- BCrypt hash
    1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);
```


### 2. departments

**Description:** Organizational departments

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| department_id | NUMBER(10) | PRIMARY KEY | Auto-generated ID |
| department_name | VARCHAR2(100) | UNIQUE, NOT NULL | Department name |
| description | VARCHAR2(500) | | Department description |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Record creation time |

**Sample Data:**
```sql
INSERT INTO departments VALUES (
    1, 'Information Technology', 
    'Software development and IT infrastructure',
    CURRENT_TIMESTAMP
);
```


### 3. designations

**Description:** Job roles and positions

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| designation_id | NUMBER(10) | PRIMARY KEY | Auto-generated ID |
| designation_name | VARCHAR2(100) | UNIQUE, NOT NULL | Designation name |
| description | VARCHAR2(500) | | Designation description |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Record creation time |


### 4. roles

**Description:** System access roles

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| role_id | NUMBER(10) | PRIMARY KEY | Auto-generated ID |
| role_name | VARCHAR2(50) | UNIQUE, NOT NULL | Role name (ADMIN, MANAGER, EMPLOYEE) |
| description | VARCHAR2(500) | | Role description |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Record creation time |

**Predefined Roles:**
```sql
INSERT INTO roles VALUES (1, 'EMPLOYEE', 'Standard employee access', CURRENT_TIMESTAMP);
INSERT INTO roles VALUES (2, 'MANAGER', 'Manager access with team management', CURRENT_TIMESTAMP);
INSERT INTO roles VALUES (3, 'ADMIN', 'Full administrative access', CURRENT_TIMESTAMP);
```


### 5. employee_roles

**Description:** Many-to-many mapping between employees and roles

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| employee_id | VARCHAR2(10) | FK → employees, NOT NULL | Employee reference |
| role_id | NUMBER(10) | FK → roles, NOT NULL | Role reference |
| assigned_date | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Role assignment date |

**Primary Key:** (employee_id, role_id)


### 6. leave_types

**Description:** Types of leave available

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| leave_type_id | NUMBER(10) | PRIMARY KEY | Auto-generated ID |
| leave_type_name | VARCHAR2(50) | UNIQUE, NOT NULL | Leave type (CASUAL, SICK, PAID, PRIVILEGE) |
| description | VARCHAR2(500) | | Leave type description |
| default_days | NUMBER(5) | CHECK > 0 | Default annual allocation |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Record creation time |

**Predefined Leave Types:**
```sql
INSERT INTO leave_types VALUES (1, 'CASUAL', 'Casual Leave', 12, CURRENT_TIMESTAMP);
INSERT INTO leave_types VALUES (2, 'SICK', 'Sick Leave', 10, CURRENT_TIMESTAMP);
INSERT INTO leave_types VALUES (3, 'PAID', 'Paid Leave', 15, CURRENT_TIMESTAMP);
INSERT INTO leave_types VALUES (4, 'PRIVILEGE', 'Privilege Leave', 5, CURRENT_TIMESTAMP);
```



### 7. leave_balances

**Description:** Employee leave balance tracking

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| employee_id | VARCHAR2(10) | FK → employees, NOT NULL | Employee reference |
| leave_type_id | NUMBER(10) | FK → leave_types, NOT NULL | Leave type reference |
| year | NUMBER(4) | NOT NULL | Calendar year |
| total_allocated | NUMBER(5) | CHECK >= 0 | Total allocated leaves |
| used_leaves | NUMBER(5) | DEFAULT 0, CHECK >= 0 | Used leaves |
| available_leaves | NUMBER(5) | CHECK >= 0 | Remaining leaves |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Record creation time |
| updated_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Last update time |

**Primary Key:** (employee_id, leave_type_id, year)

**Check Constraint:** `available_leaves = total_allocated - used_leaves`


### 8. leave_applications

**Description:** Leave application requests

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| leave_application_id | NUMBER(10) | PRIMARY KEY | Auto-generated ID |
| employee_id | VARCHAR2(10) | FK → employees, NOT NULL | Employee reference |
| leave_type_id | NUMBER(10) | FK → leave_types, NOT NULL | Leave type reference |
| start_date | DATE | NOT NULL | Leave start date |
| end_date | DATE | NOT NULL | Leave end date |
| total_days | NUMBER(5) | CHECK > 0 | Total leave days |
| reason | VARCHAR2(500) | NOT NULL | Leave reason |
| status | VARCHAR2(20) | DEFAULT 'PENDING', NOT NULL | PENDING, APPROVED, REJECTED, CANCELLED |
| reviewed_by | VARCHAR2(10) | FK → employees | Reviewing manager |
| review_comments | VARCHAR2(500) | | Manager's comments |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Application date |
| updated_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Last update time |

**Check Constraints:**
- `end_date >= start_date`
- `status IN ('PENDING', 'APPROVED', 'REJECTED', 'CANCELLED')`


### 9. holidays

**Description:** Company holiday calendar

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| holiday_id | NUMBER(10) | PRIMARY KEY | Auto-generated ID |
| holiday_name | VARCHAR2(100) | NOT NULL | Holiday name |
| holiday_date | DATE | NOT NULL | Holiday date |
| year | NUMBER(4) | NOT NULL | Calendar year |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Record creation time |

**Sample Data:**
```sql
INSERT INTO holidays VALUES (
    1, 'Republic Day', TO_DATE('2025-01-26', 'YYYY-MM-DD'), 
    2025, CURRENT_TIMESTAMP
);
```


### 10. goals

**Description:** Employee goal tracking

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| goal_id | NUMBER(10) | PRIMARY KEY | Auto-generated ID |
| employee_id | VARCHAR2(10) | FK → employees, NOT NULL | Employee reference |
| goal_description | VARCHAR2(1000) | NOT NULL | Goal description |
| deadline | DATE | NOT NULL | Target completion date |
| priority | VARCHAR2(20) | NOT NULL | HIGH, MEDIUM, LOW |
| success_metrics | VARCHAR2(1000) | | Success criteria |
| progress_percentage | NUMBER(3) | DEFAULT 0, CHECK 0-100 | Progress (0-100%) |
| status | VARCHAR2(20) | DEFAULT 'NOT_STARTED' | Goal status |
| manager_comments | VARCHAR2(1000) | | Manager feedback |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Record creation time |
| updated_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Last update time |

**Check Constraints:**
- `priority IN ('HIGH', 'MEDIUM', 'LOW')`
- `status IN ('NOT_STARTED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED')`
- `progress_percentage BETWEEN 0 AND 100`


### 11. performance_reviews

**Description:** Annual performance reviews

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| review_id | NUMBER(10) | PRIMARY KEY | Auto-generated ID |
| employee_id | VARCHAR2(10) | FK → employees, NOT NULL | Employee reference |
| review_year | NUMBER(4) | NOT NULL | Review year |
| key_deliverables | CLOB | | Key deliverables |
| major_accomplishments | CLOB | | Major accomplishments |
| areas_of_improvement | CLOB | | Areas for improvement |
| self_assessment_rating | NUMBER(3,1) | CHECK 1.0-5.0 | Self rating |
| manager_feedback | CLOB | | Manager's feedback |
| manager_rating | NUMBER(3,1) | CHECK 1.0-5.0 | Manager rating |
| status | VARCHAR2(20) | DEFAULT 'DRAFT' | Review status |
| submitted_date | TIMESTAMP | | Submission date |
| reviewed_date | TIMESTAMP | | Manager review date |
| reviewed_by | VARCHAR2(10) | FK → employees | Reviewing manager |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Record creation time |
| updated_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Last update time |

**Check Constraints:**
- `status IN ('DRAFT', 'SUBMITTED', 'REVIEWED')`
- `self_assessment_rating BETWEEN 1.0 AND 5.0`
- `manager_rating BETWEEN 1.0 AND 5.0`

**Unique Constraint:** (employee_id, review_year)


### 12. notifications

**Description:** System notifications

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| notification_id | NUMBER(10) | PRIMARY KEY | Auto-generated ID |
| employee_id | VARCHAR2(10) | FK → employees, NOT NULL | Recipient employee |
| notification_type | VARCHAR2(50) | NOT NULL | Notification type |
| message | VARCHAR2(1000) | NOT NULL | Notification message |
| is_read | NUMBER(1) | DEFAULT 0 | Read status (0=unread, 1=read) |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Notification time |
| read_at | TIMESTAMP | | Read timestamp |

**Check Constraint:** `notification_type IN ('LEAVE', 'PERFORMANCE', 'SYSTEM', 'ANNOUNCEMENT')`


### 13. announcements

**Description:** Company-wide announcements

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| announcement_id | NUMBER(10) | PRIMARY KEY | Auto-generated ID |
| title | VARCHAR2(200) | NOT NULL | Announcement title |
| content | CLOB | NOT NULL | Announcement content |
| posted_by | VARCHAR2(10) | FK → employees, NOT NULL | Posted by (admin) |
| posted_date | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Posting date |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Record creation time |

### 14. audit_logs

**Description:** System activity audit trail

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| log_id | NUMBER(10) | PRIMARY KEY | Auto-generated ID |
| employee_id | VARCHAR2(10) | FK → employees | Performing employee |
| action | VARCHAR2(50) | NOT NULL | Action type |
| table_name | VARCHAR2(50) | | Affected table |
| record_id | VARCHAR2(50) | | Affected record ID |
| old_value | VARCHAR2(1000) | | Previous value |
| new_value | VARCHAR2(1000) | | New value |
| ip_address | VARCHAR2(50) | | Client IP address |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Action timestamp |



## Relationships

### Foreign Key Relationships

```sql
-- Employee relationships
ALTER TABLE employees ADD CONSTRAINT fk_emp_dept 
    FOREIGN KEY (department_id) REFERENCES departments(department_id);

ALTER TABLE employees ADD CONSTRAINT fk_emp_desig 
    FOREIGN KEY (designation_id) REFERENCES designations(designation_id);

ALTER TABLE employees ADD CONSTRAINT fk_emp_manager 
    FOREIGN KEY (manager_id) REFERENCES employees(employee_id);

-- Employee roles
ALTER TABLE employee_roles ADD CONSTRAINT fk_emprole_emp 
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id) ON DELETE CASCADE;

ALTER TABLE employee_roles ADD CONSTRAINT fk_emprole_role 
    FOREIGN KEY (role_id) REFERENCES roles(role_id);

-- Leave management
ALTER TABLE leave_balances ADD CONSTRAINT fk_bal_emp 
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id) ON DELETE CASCADE;

ALTER TABLE leave_applications ADD CONSTRAINT fk_app_emp 
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id);

-- Performance management
ALTER TABLE goals ADD CONSTRAINT fk_goal_emp 
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id) ON DELETE CASCADE;

ALTER TABLE performance_reviews ADD CONSTRAINT fk_review_emp 
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id) ON DELETE CASCADE;
```

### Relationship Types

| Parent Table | Child Table | Relationship | Delete Rule |
|--------------|-------------|--------------|-------------|
| departments | employees | One-to-Many | RESTRICT |
| designations | employees | One-to-Many | RESTRICT |
| employees | employees | Self-referencing | SET NULL |
| employees | leave_balances | One-to-Many | CASCADE |
| employees | leave_applications | One-to-Many | RESTRICT |
| employees | goals | One-to-Many | CASCADE |
| roles | employee_roles | One-to-Many | RESTRICT |


## Constraints

### Check Constraints

```sql
-- Salary validation
ALTER TABLE employees ADD CONSTRAINT chk_salary 
    CHECK (salary > 0);

-- Leave balance calculation
ALTER TABLE leave_balances ADD CONSTRAINT chk_balance 
    CHECK (available_leaves = total_allocated - used_leaves);

-- Date validation
ALTER TABLE leave_applications ADD CONSTRAINT chk_dates 
    CHECK (end_date >= start_date);

-- Progress percentage
ALTER TABLE goals ADD CONSTRAINT chk_progress 
    CHECK (progress_percentage BETWEEN 0 AND 100);

-- Rating validation
ALTER TABLE performance_reviews ADD CONSTRAINT chk_rating 
    CHECK (self_assessment_rating BETWEEN 1.0 AND 5.0);
```



## Indexes

### Performance Indexes

```sql
-- Employee searches
CREATE INDEX idx_emp_email ON employees(email);
CREATE INDEX idx_emp_manager ON employees(manager_id);
CREATE INDEX idx_emp_dept ON employees(department_id);
CREATE INDEX idx_emp_active ON employees(is_active);

-- Leave management
CREATE INDEX idx_leave_emp ON leave_applications(employee_id);
CREATE INDEX idx_leave_status ON leave_applications(status);
CREATE INDEX idx_leave_dates ON leave_applications(start_date, end_date);

-- Performance
CREATE INDEX idx_goal_emp ON goals(employee_id);
CREATE INDEX idx_review_emp ON performance_reviews(employee_id);
CREATE INDEX idx_review_year ON performance_reviews(review_year);

-- Notifications
CREATE INDEX idx_notif_emp ON notifications(employee_id);
CREATE INDEX idx_notif_read ON notifications(is_read);

-- Audit logs
CREATE INDEX idx_audit_emp ON audit_logs(employee_id);
CREATE INDEX idx_audit_date ON audit_logs(created_at);
```


## Sequences

### Auto-increment Sequences

```sql
CREATE SEQUENCE seq_department_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_designation_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_role_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_leave_type_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_leave_app_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_holiday_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_goal_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_review_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_notification_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_announcement_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_audit_log_id START WITH 1 INCREMENT BY 1;
```



## Sample Data

### Default Users

```sql
-- Admin user
INSERT INTO employees VALUES (
    'ADM001', 'Admin', 'User', 'admin@revworkforce.com',
    '9876543210', 'Admin Office', TO_DATE('1985-01-01', 'YYYY-MM-DD'),
    5, 1, NULL, TO_DATE('2015-01-01', 'YYYY-MM-DD'),
    100000, '9999999999', '$2a$12$...', 1, 
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

-- Assign admin role
INSERT INTO employee_roles VALUES ('ADM001', 3, CURRENT_TIMESTAMP);
```


## Database Maintenance

### Routine Tasks

```sql
-- Update statistics
EXEC DBMS_STATS.GATHER_SCHEMA_STATS('REVWORKFORCE');

-- Rebuild indexes
ALTER INDEX idx_emp_email REBUILD;

-- Check for invalid objects
SELECT object_name, object_type, status 
FROM user_objects 
WHERE status = 'INVALID';

-- Archive old audit logs (keep last 6 months)
DELETE FROM audit_logs 
WHERE created_at < ADD_MONTHS(SYSDATE, -6);
COMMIT;
```

---

**For more information, see:**
- [README.md](https://github.com/MastanSayyad/RevWorkForce/blob/main/README.md) - *Project overview*
- [Project Report](https://github.com/MastanSayyad/RevWorkForce/blob/main/docs) - *Project report including objectives, methodology, and outcomes*
- [User Manual](https://github.com/MastanSayyad/RevWorkForce/blob/main/docs/User_Manual.md) - *Step-by-step project build and usage guide Instructions*
- [PPT Slides](https://github.com/MastanSayyad/RevWorkForce/blob/main/docs/Presentation_RevWorkForce.pdf) - *Project presentation slides*
- [Database Design Documentation](https://github.com/MastanSayyad/RevWorkForce/blob/main/docs/Database_Design_Documentation.md) -  *Database schema, ER diagrams, and relationships*
- [API Documentation](https://github.com/MastanSayyad/RevWorkForce/blob/main/docs/API_Documentation.md) - *Application workflows and module interactions*
- [Diagrams & Charts](https://github.com/MastanSayyad/RevWorkForce/tree/main/docs/Diagrams%20and%20Charts) - *Architecture and UML diagrams, ERD, and system design visuals*
- [Testing Documentation](https://github.com/MastanSayyad/RevWorkForce/blob/main/docs/Testing_Report.pdf) -  *Test strategy, coverage details, and reports*




<p align="right">
  <a href="https://github.com/MastanSayyad/RevWorkForce/edit/main/docs/Database_Design_Documentation.md">
    <img src="https://img.shields.io/badge/Scroll%20to%20Top-Purple?style=for-the-badge&color=ffffff" />
  </a>
</p>

