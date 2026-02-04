-- H2 Database Schema for RevWorkForce Tests
-- This is a simplified version compatible with H2 database

-- Drop tables if they exist (for clean testing)
DROP TABLE IF EXISTS audit_logs CASCADE;
DROP TABLE IF EXISTS announcements CASCADE;
DROP TABLE IF EXISTS notifications CASCADE;
DROP TABLE IF EXISTS goals CASCADE;
DROP TABLE IF EXISTS performance_reviews CASCADE;
DROP TABLE IF EXISTS leave_applications CASCADE;
DROP TABLE IF EXISTS leave_balances CASCADE;
DROP TABLE IF EXISTS holidays CASCADE;
DROP TABLE IF EXISTS leave_types CASCADE;
DROP TABLE IF EXISTS employee_roles CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS employees CASCADE;
DROP TABLE IF EXISTS designations CASCADE;
DROP TABLE IF EXISTS departments CASCADE;

-- Drop sequences if they exist
DROP SEQUENCE IF EXISTS seq_employee_id;
DROP SEQUENCE IF EXISTS seq_leave_application_id;
DROP SEQUENCE IF EXISTS seq_review_id;
DROP SEQUENCE IF EXISTS seq_goal_id;
DROP SEQUENCE IF EXISTS seq_notification_id;
DROP SEQUENCE IF EXISTS seq_announcement_id;
DROP SEQUENCE IF EXISTS seq_audit_log_id;
DROP SEQUENCE IF EXISTS seq_holiday_id;

-- Create Departments table
CREATE TABLE departments (
    department_id INT PRIMARY KEY,
    department_name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Designations table
CREATE TABLE designations (
    designation_id INT PRIMARY KEY,
    designation_name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Employees table
CREATE TABLE employees (
    employee_id VARCHAR(10) PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15) NOT NULL,
    address VARCHAR(500),
    emergency_contact VARCHAR(100),
    date_of_birth DATE NOT NULL,
    department_id INT,
    designation_id INT,
    manager_id VARCHAR(10),
    joining_date DATE NOT NULL,
    salary DECIMAL(10, 2) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (department_id) REFERENCES departments(department_id),
    FOREIGN KEY (designation_id) REFERENCES designations(designation_id),
    FOREIGN KEY (manager_id) REFERENCES employees(employee_id)
);

-- Create Roles table
CREATE TABLE roles (
    role_id INT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Employee_Roles junction table
CREATE TABLE employee_roles (
    employee_id VARCHAR(10),
    role_id INT,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (employee_id, role_id),
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id),
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

-- Create Leave_Types table
CREATE TABLE leave_types (
    leave_type_id INT PRIMARY KEY,
    leave_type_name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(500),
    default_days INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Holidays table
CREATE TABLE holidays (
    holiday_id INT PRIMARY KEY AUTO_INCREMENT,
    holiday_name VARCHAR(100) NOT NULL,
    holiday_date DATE NOT NULL,
    year INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Leave_Balances table
CREATE TABLE leave_balances (
    employee_id VARCHAR(10),
    leave_type_id INT,
    year INT NOT NULL,
    total_allocated INT NOT NULL,
    used_leaves INT DEFAULT 0,
    available_leaves INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (employee_id, leave_type_id, year),
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id),
    FOREIGN KEY (leave_type_id) REFERENCES leave_types(leave_type_id)
);

-- Create Leave_Applications table
CREATE TABLE leave_applications (
    leave_application_id INT PRIMARY KEY AUTO_INCREMENT,
    employee_id VARCHAR(10) NOT NULL,
    leave_type_id INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    total_days INT NOT NULL,
    reason VARCHAR(500) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    reviewed_by VARCHAR(10),
    review_comments VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id),
    FOREIGN KEY (leave_type_id) REFERENCES leave_types(leave_type_id),
    FOREIGN KEY (reviewed_by) REFERENCES employees(employee_id)
);

-- Create Performance_Reviews table
CREATE TABLE performance_reviews (
    review_id INT PRIMARY KEY AUTO_INCREMENT,
    employee_id VARCHAR(10) NOT NULL,
    review_year INT NOT NULL,
    key_deliverables CLOB,
    major_accomplishments CLOB,
    areas_of_improvement CLOB,
    self_assessment_rating DECIMAL(3, 2),
    manager_feedback CLOB,
    manager_rating DECIMAL(3, 2),
    status VARCHAR(20) DEFAULT 'DRAFT',
    submitted_date TIMESTAMP,
    reviewed_date TIMESTAMP,
    reviewed_by VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id),
    FOREIGN KEY (reviewed_by) REFERENCES employees(employee_id)
);

-- Create Goals table
CREATE TABLE goals (
    goal_id INT PRIMARY KEY AUTO_INCREMENT,
    employee_id VARCHAR(10) NOT NULL,
    goal_description CLOB NOT NULL,
    deadline DATE NOT NULL,
    priority VARCHAR(20) NOT NULL,
    success_metrics CLOB,
    progress_percentage INT DEFAULT 0,
    status VARCHAR(20) DEFAULT 'NOT_STARTED',
    manager_comments CLOB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);

-- Create Notifications table
CREATE TABLE notifications (
    notification_id INT PRIMARY KEY AUTO_INCREMENT,
    employee_id VARCHAR(10) NOT NULL,
    notification_type VARCHAR(50) NOT NULL,
    message VARCHAR(1000) NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    read_at TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);

-- Create Announcements table
CREATE TABLE announcements (
    announcement_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content CLOB NOT NULL,
    posted_by VARCHAR(10) NOT NULL,
    posted_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (posted_by) REFERENCES employees(employee_id)
);

-- Create Audit_Logs table
CREATE TABLE audit_logs (
    log_id INT PRIMARY KEY AUTO_INCREMENT,
    employee_id VARCHAR(10),
    action VARCHAR(100) NOT NULL,
    table_name VARCHAR(100) NOT NULL,
    record_id VARCHAR(50) NOT NULL,
    old_value CLOB,
    new_value CLOB,
    ip_address VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);

-- Create Sequences (H2 syntax)
CREATE SEQUENCE seq_employee_id START WITH 5;
CREATE SEQUENCE seq_leave_application_id START WITH 1;
CREATE SEQUENCE seq_review_id START WITH 1;
CREATE SEQUENCE seq_goal_id START WITH 1;
CREATE SEQUENCE seq_notification_id START WITH 1;
CREATE SEQUENCE seq_announcement_id START WITH 1;
CREATE SEQUENCE seq_audit_log_id START WITH 1;
CREATE SEQUENCE seq_holiday_id START WITH 11;

-- Insert seed data
-- Departments
INSERT INTO departments VALUES (1, 'IT', 'Information Technology', CURRENT_TIMESTAMP);
INSERT INTO departments VALUES (2, 'HR', 'Human Resources', CURRENT_TIMESTAMP);
INSERT INTO departments VALUES (3, 'Finance', 'Finance and Accounting', CURRENT_TIMESTAMP);
INSERT INTO departments VALUES (4, 'Sales', 'Sales and Marketing', CURRENT_TIMESTAMP);

-- Designations
INSERT INTO designations VALUES (1, 'Software Engineer', 'Software Development', CURRENT_TIMESTAMP);
INSERT INTO designations VALUES (2, 'Senior Software Engineer', 'Senior Software Development', CURRENT_TIMESTAMP);
INSERT INTO designations VALUES (3, 'Manager', 'Team Management', CURRENT_TIMESTAMP);
INSERT INTO designations VALUES (4, 'Admin', 'System Administrator', CURRENT_TIMESTAMP);

-- Roles
INSERT INTO roles VALUES (1, 'EMPLOYEE', 'Regular Employee', CURRENT_TIMESTAMP);
INSERT INTO roles VALUES (2, 'MANAGER', 'Team Manager', CURRENT_TIMESTAMP);
INSERT INTO roles VALUES (3, 'ADMIN', 'System Administrator', CURRENT_TIMESTAMP);

-- Employees (password123 hashed with BCrypt)
INSERT INTO employees VALUES ('ADM001', 'Rajesh', 'Kumar', 'rajesh.kumar@revworkforce.com', '9876543210', 
    '123 Main St, Bangalore', '9876543211', '1985-06-15', 2, 4, NULL, '2020-01-01', 80000.00, TRUE, 
    '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5GyYIq5YK5unm', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO employees VALUES ('MGR001', 'Priya', 'Sharma', 'priya.sharma@revworkforce.com', '9876543220', 
    '456 Park Ave, Bangalore', '9876543221', '1988-03-20', 1, 3, NULL, '2020-02-01', 70000.00, TRUE, 
    '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5GyYIq5YK5unm', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO employees VALUES ('MGR002', 'Vijay', 'Reddy', 'vijay.reddy@revworkforce.com', '9876543230', 
    '789 Lake Rd, Bangalore', '9876543231', '1987-11-10', 3, 3, NULL, '2020-03-01', 70000.00, TRUE, 
    '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5GyYIq5YK5unm', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO employees VALUES ('EMP001', 'Amit', 'Patel', 'amit.patel@revworkforce.com', '9876543240', 
    '321 Oak St, Bangalore', '9876543241', '1990-07-25', 1, 1, 'MGR001', '2021-01-15', 50000.00, TRUE, 
    '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5GyYIq5YK5unm', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO employees VALUES ('EMP002', 'Sneha', 'Iyer', 'sneha.iyer@revworkforce.com', '9876543250', 
    '654 Pine St, Bangalore', '9876543251', '1992-02-14', 1, 2, 'MGR001', '2021-03-20', 60000.00, TRUE, 
    '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5GyYIq5YK5unm', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO employees VALUES ('EMP003', 'Karthik', 'Nair', 'karthik.nair@revworkforce.com', '9876543260', 
    '987 Elm St, Bangalore', '9876543261', '1991-09-05', 3, 1, 'MGR002', '2021-06-10', 48000.00, TRUE, 
    '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5GyYIq5YK5unm', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO employees VALUES ('EMP004', 'Divya', 'Menon', 'divya.menon@revworkforce.com', '9876543270', 
    '159 Maple Ave, Bangalore', '9876543271', '1993-12-18', 4, 1, 'MGR001', '2022-01-05', 52000.00, TRUE, 
    '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5GyYIq5YK5unm', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Employee Roles
INSERT INTO employee_roles VALUES ('ADM001', 1, CURRENT_TIMESTAMP);
INSERT INTO employee_roles VALUES ('ADM001', 3, CURRENT_TIMESTAMP);
INSERT INTO employee_roles VALUES ('MGR001', 1, CURRENT_TIMESTAMP);
INSERT INTO employee_roles VALUES ('MGR001', 2, CURRENT_TIMESTAMP);
INSERT INTO employee_roles VALUES ('MGR002', 1, CURRENT_TIMESTAMP);
INSERT INTO employee_roles VALUES ('MGR002', 2, CURRENT_TIMESTAMP);
INSERT INTO employee_roles VALUES ('EMP001', 1, CURRENT_TIMESTAMP);
INSERT INTO employee_roles VALUES ('EMP002', 1, CURRENT_TIMESTAMP);
INSERT INTO employee_roles VALUES ('EMP003', 1, CURRENT_TIMESTAMP);
INSERT INTO employee_roles VALUES ('EMP004', 1, CURRENT_TIMESTAMP);

-- Leave Types
INSERT INTO leave_types VALUES (1, 'CASUAL', 'Casual Leave', 12, CURRENT_TIMESTAMP);
INSERT INTO leave_types VALUES (2, 'SICK', 'Sick Leave', 10, CURRENT_TIMESTAMP);
INSERT INTO leave_types VALUES (3, 'PAID', 'Paid Leave', 15, CURRENT_TIMESTAMP);
INSERT INTO leave_types VALUES (4, 'PRIVILEGE', 'Privilege Leave', 5, CURRENT_TIMESTAMP);

-- Holidays
INSERT INTO holidays VALUES (1, 'New Year', '2025-01-01', 2025, CURRENT_TIMESTAMP);
INSERT INTO holidays VALUES (2, 'Republic Day', '2025-01-26', 2025, CURRENT_TIMESTAMP);
INSERT INTO holidays VALUES (3, 'Holi', '2025-03-14', 2025, CURRENT_TIMESTAMP);
INSERT INTO holidays VALUES (4, 'Good Friday', '2025-04-18', 2025, CURRENT_TIMESTAMP);
INSERT INTO holidays VALUES (5, 'Independence Day', '2025-08-15', 2025, CURRENT_TIMESTAMP);
INSERT INTO holidays VALUES (6, 'Gandhi Jayanti', '2025-10-02', 2025, CURRENT_TIMESTAMP);
INSERT INTO holidays VALUES (7, 'Diwali', '2025-10-20', 2025, CURRENT_TIMESTAMP);
INSERT INTO holidays VALUES (8, 'Christmas', '2025-12-25', 2025, CURRENT_TIMESTAMP);

-- Leave Balances for 2025
INSERT INTO leave_balances VALUES ('EMP001', 1, 2025, 12, 0, 12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO leave_balances VALUES ('EMP001', 2, 2025, 10, 0, 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO leave_balances VALUES ('EMP001', 3, 2025, 15, 0, 15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO leave_balances VALUES ('EMP001', 4, 2025, 5, 0, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO leave_balances VALUES ('EMP002', 1, 2025, 12, 2, 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO leave_balances VALUES ('EMP002', 2, 2025, 10, 1, 9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO leave_balances VALUES ('EMP002', 3, 2025, 15, 3, 12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO leave_balances VALUES ('EMP002', 4, 2025, 5, 0, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
