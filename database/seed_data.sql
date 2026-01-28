-- ============================================
-- RevWorkForce Seed Data
-- Initial data for testing
-- ============================================

-- ============================================
-- INSERT DATA
-- ============================================

-- Insert Departments
INSERT INTO departments (department_id, department_name) VALUES (seq_department_id.NEXTVAL, 'Engineering');
INSERT INTO departments (department_id, department_name) VALUES (seq_department_id.NEXTVAL, 'Human Resources');
INSERT INTO departments (department_id, department_name) VALUES (seq_department_id.NEXTVAL, 'Finance');
INSERT INTO departments (department_id, department_name) VALUES (seq_department_id.NEXTVAL, 'Marketing');
INSERT INTO departments (department_id, department_name) VALUES (seq_department_id.NEXTVAL, 'Sales');
INSERT INTO departments (department_id, department_name) VALUES (seq_department_id.NEXTVAL, 'Operations');

-- Insert Designations
INSERT INTO designations (designation_id, designation_name) VALUES (seq_designation_id.NEXTVAL, 'Software Engineer');
INSERT INTO designations (designation_id, designation_name) VALUES (seq_designation_id.NEXTVAL, 'Senior Software Engineer');
INSERT INTO designations (designation_id, designation_name) VALUES (seq_designation_id.NEXTVAL, 'Engineering Manager');
INSERT INTO designations (designation_id, designation_name) VALUES (seq_designation_id.NEXTVAL, 'HR Manager');
INSERT INTO designations (designation_id, designation_name) VALUES (seq_designation_id.NEXTVAL, 'HR Executive');
INSERT INTO designations (designation_id, designation_name) VALUES (seq_designation_id.NEXTVAL, 'Finance Manager');
INSERT INTO designations (designation_id, designation_name) VALUES (seq_designation_id.NEXTVAL, 'Accountant');
INSERT INTO designations (designation_id, designation_name) VALUES (seq_designation_id.NEXTVAL, 'System Administrator');

-- Insert Roles
INSERT INTO roles (role_id, role_name) VALUES (seq_role_id.NEXTVAL, 'EMPLOYEE');
INSERT INTO roles (role_id, role_name) VALUES (seq_role_id.NEXTVAL, 'MANAGER');
INSERT INTO roles (role_id, role_name) VALUES (seq_role_id.NEXTVAL, 'ADMIN');

-- Insert Leave Types
INSERT INTO leave_types (leave_type_id, leave_type_name, description) 
VALUES (seq_leave_type_id.NEXTVAL, 'CASUAL', 'Casual Leave');

INSERT INTO leave_types (leave_type_id, leave_type_name, description) 
VALUES (seq_leave_type_id.NEXTVAL, 'SICK', 'Sick Leave');

INSERT INTO leave_types (leave_type_id, leave_type_name, description) 
VALUES (seq_leave_type_id.NEXTVAL, 'PAID', 'Paid Leave');

INSERT INTO leave_types (leave_type_id, leave_type_name, description) 
VALUES (seq_leave_type_id.NEXTVAL, 'PRIVILEGE', 'Privilege Leave');

-- Insert Holidays for 2025
INSERT INTO holidays (holiday_id, holiday_name, holiday_date, year) 
VALUES (seq_holiday_id.NEXTVAL, 'New Year', DATE '2025-01-01', 2025);

INSERT INTO holidays (holiday_id, holiday_name, holiday_date, year) 
VALUES (seq_holiday_id.NEXTVAL, 'Republic Day', DATE '2025-01-26', 2025);

INSERT INTO holidays (holiday_id, holiday_name, holiday_date, year) 
VALUES (seq_holiday_id.NEXTVAL, 'Holi', DATE '2025-03-14', 2025);

INSERT INTO holidays (holiday_id, holiday_name, holiday_date, year) 
VALUES (seq_holiday_id.NEXTVAL, 'Good Friday', DATE '2025-04-18', 2025);

INSERT INTO holidays (holiday_id, holiday_name, holiday_date, year) 
VALUES (seq_holiday_id.NEXTVAL, 'Independence Day', DATE '2025-08-15', 2025);

INSERT INTO holidays (holiday_id, holiday_name, holiday_date, year) 
VALUES (seq_holiday_id.NEXTVAL, 'Gandhi Jayanti', DATE '2025-10-02', 2025);

INSERT INTO holidays (holiday_id, holiday_name, holiday_date, year) 
VALUES (seq_holiday_id.NEXTVAL, 'Diwali', DATE '2025-10-20', 2025);

INSERT INTO holidays (holiday_id, holiday_name, holiday_date, year) 
VALUES (seq_holiday_id.NEXTVAL, 'Christmas', DATE '2025-12-25', 2025);

-- Insert Holidays for 2026
INSERT INTO holidays (holiday_id, holiday_name, holiday_date, year) 
VALUES (seq_holiday_id.NEXTVAL, 'New Year', DATE '2026-01-01', 2026);

INSERT INTO holidays (holiday_id, holiday_name, holiday_date, year) 
VALUES (seq_holiday_id.NEXTVAL, 'Republic Day', DATE '2026-01-26', 2026);

INSERT INTO holidays (holiday_id, holiday_name, holiday_date, year) 
VALUES (seq_holiday_id.NEXTVAL, 'Holi', DATE '2026-03-04', 2026);

INSERT INTO holidays (holiday_id, holiday_name, holiday_date, year) 
VALUES (seq_holiday_id.NEXTVAL, 'Good Friday', DATE '2026-04-03', 2026);

INSERT INTO holidays (holiday_id, holiday_name, holiday_date, year) 
VALUES (seq_holiday_id.NEXTVAL, 'Independence Day', DATE '2026-08-15', 2026);

INSERT INTO holidays (holiday_id, holiday_name, holiday_date, year) 
VALUES (seq_holiday_id.NEXTVAL, 'Gandhi Jayanti', DATE '2026-10-02', 2026);

INSERT INTO holidays (holiday_id, holiday_name, holiday_date, year) 
VALUES (seq_holiday_id.NEXTVAL, 'Diwali', DATE '2026-11-08', 2026);

INSERT INTO holidays (holiday_id, holiday_name, holiday_date, year) 
VALUES (seq_holiday_id.NEXTVAL, 'Christmas', DATE '2026-12-25', 2026);

COMMIT;

-- ============================================
-- INSERT EMPLOYEES
-- ============================================
-- Note: Password for all users is 'password123'
-- Hashed using BCrypt: $2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5GyYVKSVN5kRu
-- $2a$12$mBMdAJI53txYnDAbxJmkdudBCRm7JOaStt2./RViZL5Hyv4Hx74gu

-- Admin User
INSERT INTO employees (employee_id, first_name, last_name, email, phone, address, 
    date_of_birth, department_id, designation_id, manager_id, joining_date, salary, password_hash, is_active)
VALUES ('ADM001', 'Admin', 'User', 'admin@revworkforce.com', '9876543210', 
    'Bangalore, Karnataka', DATE '1985-01-15', 2, 8, NULL, DATE '2020-01-01', 
    100000, '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5GyYVKSVN5kRu', 1);

-- Engineering Manager
INSERT INTO employees (employee_id, first_name, last_name, email, phone, address, 
    date_of_birth, department_id, designation_id, manager_id, joining_date, salary, password_hash, is_active)
VALUES ('MGR001', 'Rajesh', 'Kumar', 'rajesh.kumar@revworkforce.com', '9876543211', 
    'Bangalore, Karnataka', DATE '1988-03-20', 1, 3, 'ADM001', DATE '2020-03-01', 
    85000, '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5GyYVKSVN5kRu', 1);

-- HR Manager
INSERT INTO employees (employee_id, first_name, last_name, email, phone, address, 
    date_of_birth, department_id, designation_id, manager_id, joining_date, salary, password_hash, is_active)
VALUES ('MGR002', 'Priya', 'Sharma', 'priya.sharma@revworkforce.com', '9876543212', 
    'Mumbai, Maharashtra', DATE '1990-05-10', 2, 4, 'ADM001', DATE '2020-04-01', 
    75000, '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5GyYVKSVN5kRu', 1);

-- Software Engineers under MGR001
INSERT INTO employees (employee_id, first_name, last_name, email, phone, address, 
    date_of_birth, department_id, designation_id, manager_id, joining_date, salary, password_hash, is_active)
VALUES ('EMP001', 'Amit', 'Patel', 'amit.patel@revworkforce.com', '9876543213', 
    'Ahmedabad, Gujarat', DATE '1995-07-15', 1, 1, 'MGR001', DATE '2021-06-01', 
    55000, '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5GyYVKSVN5kRu', 1);

INSERT INTO employees (employee_id, first_name, last_name, email, phone, address, 
    date_of_birth, department_id, designation_id, manager_id, joining_date, salary, password_hash, is_active)
VALUES ('EMP002', 'Sneha', 'Reddy', 'sneha.reddy@revworkforce.com', '9876543214', 
    'Hyderabad, Telangana', DATE '1996-09-22', 1, 1, 'MGR001', DATE '2021-07-15', 
    52000, '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5GyYVKSVN5kRu', 1);

INSERT INTO employees (employee_id, first_name, last_name, email, phone, address, 
    date_of_birth, department_id, designation_id, manager_id, joining_date, salary, password_hash, is_active)
VALUES ('EMP003', 'Vikram', 'Singh', 'vikram.singh@revworkforce.com', '9876543215', 
    'Delhi, Delhi', DATE '1994-11-30', 1, 2, 'MGR001', DATE '2020-08-01', 
    65000, '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5GyYVKSVN5kRu', 1);

-- HR Executive under MGR002
INSERT INTO employees (employee_id, first_name, last_name, email, phone, address, 
    date_of_birth, department_id, designation_id, manager_id, joining_date, salary, password_hash, is_active)
VALUES ('EMP004', 'Kavya', 'Nair', 'kavya.nair@revworkforce.com', '9876543216', 
    'Kochi, Kerala', DATE '1997-02-14', 2, 5, 'MGR002', DATE '2022-01-10', 
    45000, '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5GyYVKSVN5kRu', 1);

-- ============================================
-- ASSIGN ROLES TO EMPLOYEES
-- ============================================

-- Admin roles
INSERT INTO employee_roles (employee_role_id, employee_id, role_id) 
VALUES (seq_employee_role_id.NEXTVAL, 'ADM001', 3); -- ADMIN

INSERT INTO employee_roles (employee_role_id, employee_id, role_id) 
VALUES (seq_employee_role_id.NEXTVAL, 'ADM001', 2); -- MANAGER

INSERT INTO employee_roles (employee_role_id, employee_id, role_id) 
VALUES (seq_employee_role_id.NEXTVAL, 'ADM001', 1); -- EMPLOYEE

-- Manager roles
INSERT INTO employee_roles (employee_role_id, employee_id, role_id) 
VALUES (seq_employee_role_id.NEXTVAL, 'MGR001', 2); -- MANAGER

INSERT INTO employee_roles (employee_role_id, employee_id, role_id) 
VALUES (seq_employee_role_id.NEXTVAL, 'MGR001', 1); -- EMPLOYEE

INSERT INTO employee_roles (employee_role_id, employee_id, role_id) 
VALUES (seq_employee_role_id.NEXTVAL, 'MGR002', 2); -- MANAGER

INSERT INTO employee_roles (employee_role_id, employee_id, role_id) 
VALUES (seq_employee_role_id.NEXTVAL, 'MGR002', 1); -- EMPLOYEE

-- Employee roles
INSERT INTO employee_roles (employee_role_id, employee_id, role_id) 
VALUES (seq_employee_role_id.NEXTVAL, 'EMP001', 1); -- EMPLOYEE

INSERT INTO employee_roles (employee_role_id, employee_id, role_id) 
VALUES (seq_employee_role_id.NEXTVAL, 'EMP002', 1); -- EMPLOYEE

INSERT INTO employee_roles (employee_role_id, employee_id, role_id) 
VALUES (seq_employee_role_id.NEXTVAL, 'EMP003', 1); -- EMPLOYEE

INSERT INTO employee_roles (employee_role_id, employee_id, role_id) 
VALUES (seq_employee_role_id.NEXTVAL, 'EMP004', 1); -- EMPLOYEE

-- ============================================
-- ASSIGN LEAVE BALANCES FOR 2025
-- ============================================

-- Procedure to assign leave balances
DECLARE
    v_year NUMBER := 2025;
    CURSOR emp_cursor IS SELECT employee_id FROM employees WHERE is_active = 1;
BEGIN
    FOR emp_rec IN emp_cursor LOOP
        -- Casual Leave (CL) - 12 days
        INSERT INTO leave_balances (leave_balance_id, employee_id, leave_type_id, 
            total_allocated, used_leaves, available_leaves, year)
        VALUES (seq_leave_balance_id.NEXTVAL, emp_rec.employee_id, 1, 12, 0, 12, v_year);
        
        -- Sick Leave (SL) - 10 days
        INSERT INTO leave_balances (leave_balance_id, employee_id, leave_type_id, 
            total_allocated, used_leaves, available_leaves, year)
        VALUES (seq_leave_balance_id.NEXTVAL, emp_rec.employee_id, 2, 10, 0, 10, v_year);
        
        -- Paid Leave (PL) - 15 days
        INSERT INTO leave_balances (leave_balance_id, employee_id, leave_type_id, 
            total_allocated, used_leaves, available_leaves, year)
        VALUES (seq_leave_balance_id.NEXTVAL, emp_rec.employee_id, 3, 15, 0, 15, v_year);
        
        -- Privilege Leave - 5 days
        INSERT INTO leave_balances (leave_balance_id, employee_id, leave_type_id, 
            total_allocated, used_leaves, available_leaves, year)
        VALUES (seq_leave_balance_id.NEXTVAL, emp_rec.employee_id, 4, 5, 0, 5, v_year);
    END LOOP;
    
    COMMIT;
END;
/

-- ============================================
-- INSERT SAMPLE LEAVE APPLICATIONS
-- ============================================

-- EMP001 applies for leave (Pending)
INSERT INTO leave_applications (leave_application_id, employee_id, leave_type_id, 
    start_date, end_date, total_days, reason, status)
VALUES (seq_leave_application_id.NEXTVAL, 'EMP001', 1, 
    DATE '2025-02-10', DATE '2025-02-12', 3, 'Family function', 'PENDING');

-- EMP002 applies for leave (Approved)
INSERT INTO leave_applications (leave_application_id, employee_id, leave_type_id, 
    start_date, end_date, total_days, reason, status, reviewed_by, reviewed_date, manager_comments)
VALUES (seq_leave_application_id.NEXTVAL, 'EMP002', 2, 
    DATE '2025-01-20', DATE '2025-01-21', 2, 'Medical checkup', 'APPROVED', 
    'MGR001', CURRENT_TIMESTAMP, 'Approved. Take care.');

-- Update leave balance for EMP002
UPDATE leave_balances 
SET used_leaves = used_leaves + 2, available_leaves = available_leaves - 2
WHERE employee_id = 'EMP002' AND leave_type_id = 2 AND year = 2025;

-- ============================================
-- INSERT SAMPLE NOTIFICATIONS
-- ============================================

INSERT INTO notifications (notification_id, employee_id, notification_type, message)
VALUES (seq_notification_id.NEXTVAL, 'EMP002', 'LEAVE',
'Your leave application from 2025-01-20 to 2025-01-21 has been APPROVED by your manager.');

INSERT INTO notifications (notification_id, employee_id, notification_type, message)
VALUES (seq_notification_id.NEXTVAL, 'MGR001', 'LEAVE',
'New leave application from Amit Patel (EMP001) is pending your approval.');

INSERT INTO notifications (notification_id, employee_id, notification_type, message)
VALUES (seq_notification_id.NEXTVAL, 'EMP001', 'SYSTEM',
'Welcome to RevWorkForce! Please update your profile information.');


-- ============================================
-- INSERT SAMPLE ANNOUNCEMENT
-- ============================================
INSERT INTO announcements (announcement_id, title, content, posted_by)
VALUES (seq_announcement_id.NEXTVAL, 'Welcome to RevWorkForce',
'Dear Team, Welcome to our new HR Management System. You can now manage leaves, performance reviews, and goals efficiently. For any queries, contact HR.',
'ADM001');

COMMIT;

-- ============================================
-- Seed Data Complete!
-- ============================================
-- Default Login Credentials:
-- Admin:    ADM001 / password123
-- Manager:  MGR001 / password123
-- Employee: EMP001 / password123
-- ============================================