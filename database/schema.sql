-- ============================================
-- RevWorkForce Database Schema
-- Oracle Database
-- Author: Mastan Sayyad
-- Date: 26-01-2026
-- ============================================

-- ============================================
-- DROP EXISTING TABLES (if any)
-- ============================================

BEGIN
   FOR cur_rec IN (SELECT table_name FROM user_tables) LOOP
      EXECUTE IMMEDIATE 'DROP TABLE ' || cur_rec.table_name || ' CASCADE CONSTRAINTS';
   END LOOP;
   
   FOR cur_rec IN (SELECT sequence_name FROM user_sequences) LOOP
      EXECUTE IMMEDIATE 'DROP SEQUENCE ' || cur_rec.sequence_name;
   END LOOP;
END;
/

-- ============================================
-- CREATE TABLES
-- ============================================

-- DEPARTMENTS Table
CREATE TABLE departments (
    department_id NUMBER PRIMARY KEY,
    department_name VARCHAR2(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- DESIGNATIONS Table
CREATE TABLE designations (
    designation_id NUMBER PRIMARY KEY,
    designation_name VARCHAR2(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ROLES Table
CREATE TABLE roles (
    role_id NUMBER PRIMARY KEY,
    role_name VARCHAR2(50) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- EMPLOYEES Table
CREATE TABLE employees (
    employee_id VARCHAR2(20) PRIMARY KEY,
    first_name VARCHAR2(50) NOT NULL,
    last_name VARCHAR2(50) NOT NULL,
    email VARCHAR2(100) NOT NULL UNIQUE,
    phone VARCHAR2(15),
    address VARCHAR2(200),
    emergency_contact VARCHAR2(100),
    date_of_birth DATE,
    department_id NUMBER,
    designation_id NUMBER,
    manager_id VARCHAR2(20),
    joining_date DATE NOT NULL,
    salary NUMBER(10,2),
    password_hash VARCHAR2(255) NOT NULL,
    is_active NUMBER(1) DEFAULT 1 CHECK (is_active IN (0,1)),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_emp_dept FOREIGN KEY (department_id) REFERENCES departments(department_id),
    CONSTRAINT fk_emp_desig FOREIGN KEY (designation_id) REFERENCES designations(designation_id),
    CONSTRAINT fk_emp_manager FOREIGN KEY (manager_id) REFERENCES employees(employee_id)
);

-- EMPLOYEE_ROLES Table (Many-to-Many relationship)
CREATE TABLE employee_roles (
    employee_role_id NUMBER PRIMARY KEY,
    employee_id VARCHAR2(20) NOT NULL,
    role_id NUMBER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_emprole_emp FOREIGN KEY (employee_id) REFERENCES employees(employee_id) ON DELETE CASCADE,
    CONSTRAINT fk_emprole_role FOREIGN KEY (role_id) REFERENCES roles(role_id),
    CONSTRAINT uk_emp_role UNIQUE (employee_id, role_id)
);

-- LEAVE_TYPES Table
CREATE TABLE leave_types (
    leave_type_id NUMBER PRIMARY KEY,
    leave_type_name VARCHAR2(50) NOT NULL UNIQUE,
    description VARCHAR2(200),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- LEAVE_BALANCES Table
CREATE TABLE leave_balances (
    leave_balance_id NUMBER PRIMARY KEY,
    employee_id VARCHAR2(20) NOT NULL,
    leave_type_id NUMBER NOT NULL,
    total_allocated NUMBER(3) NOT NULL,
    used_leaves NUMBER(3) DEFAULT 0,
    available_leaves NUMBER(3) NOT NULL,
    year NUMBER(4) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_leavbal_emp FOREIGN KEY (employee_id) REFERENCES employees(employee_id) ON DELETE CASCADE,
    CONSTRAINT fk_leavbal_type FOREIGN KEY (leave_type_id) REFERENCES leave_types(leave_type_id),
    CONSTRAINT uk_emp_leave_year UNIQUE (employee_id, leave_type_id, year)
);

-- LEAVE_APPLICATIONS Table
CREATE TABLE leave_applications (
    leave_application_id NUMBER PRIMARY KEY,
    employee_id VARCHAR2(20) NOT NULL,
    leave_type_id NUMBER NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    total_days NUMBER(3) NOT NULL,
    reason VARCHAR2(500),
    status VARCHAR2(20) DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED', 'CANCELLED')),
    manager_comments VARCHAR2(500),
    applied_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    reviewed_date TIMESTAMP,
    reviewed_by VARCHAR2(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_leaveapp_emp FOREIGN KEY (employee_id) REFERENCES employees(employee_id) ON DELETE CASCADE,
    CONSTRAINT fk_leaveapp_type FOREIGN KEY (leave_type_id) REFERENCES leave_types(leave_type_id),
    CONSTRAINT fk_leaveapp_reviewer FOREIGN KEY (reviewed_by) REFERENCES employees(employee_id)
);

-- HOLIDAYS Table
CREATE TABLE holidays (
    holiday_id NUMBER PRIMARY KEY,
    holiday_name VARCHAR2(100) NOT NULL,
    holiday_date DATE NOT NULL,
    year NUMBER(4) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- PERFORMANCE_REVIEWS Table
CREATE TABLE performance_reviews (
    review_id NUMBER PRIMARY KEY,
    employee_id VARCHAR2(20) NOT NULL,
    review_year NUMBER(4) NOT NULL,
    key_deliverables CLOB,
    major_accomplishments CLOB,
    areas_of_improvement CLOB,
    self_assessment_rating NUMBER(2,1) CHECK (self_assessment_rating >= 1 AND self_assessment_rating <= 5),
    manager_feedback CLOB,
    manager_rating NUMBER(2,1) CHECK (manager_rating >= 1 AND manager_rating <= 5),
    status VARCHAR2(20) DEFAULT 'DRAFT' CHECK (status IN ('DRAFT', 'SUBMITTED', 'REVIEWED')),
    submitted_date TIMESTAMP,
    reviewed_date TIMESTAMP,
    reviewed_by VARCHAR2(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_perfreview_emp FOREIGN KEY (employee_id) REFERENCES employees(employee_id) ON DELETE CASCADE,
    CONSTRAINT fk_perfreview_reviewer FOREIGN KEY (reviewed_by) REFERENCES employees(employee_id),
    CONSTRAINT uk_emp_review_year UNIQUE (employee_id, review_year)
);

-- GOALS Table
CREATE TABLE goals (
    goal_id NUMBER PRIMARY KEY,
    employee_id VARCHAR2(20) NOT NULL,
    goal_description VARCHAR2(1000) NOT NULL,
    deadline DATE NOT NULL,
    priority VARCHAR2(20) DEFAULT 'MEDIUM' CHECK (priority IN ('HIGH', 'MEDIUM', 'LOW')),
    success_metrics VARCHAR2(500),
    progress_percentage NUMBER(3) DEFAULT 0 CHECK (progress_percentage >= 0 AND progress_percentage <= 100),
    status VARCHAR2(20) DEFAULT 'NOT_STARTED' CHECK (status IN ('NOT_STARTED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED')),
    manager_comments VARCHAR2(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_goal_emp FOREIGN KEY (employee_id) REFERENCES employees(employee_id) ON DELETE CASCADE
);

-- NOTIFICATIONS Table
CREATE TABLE notifications (
    notification_id NUMBER PRIMARY KEY,
    employee_id VARCHAR2(20) NOT NULL,
    notification_type VARCHAR2(50) NOT NULL CHECK (notification_type IN ('LEAVE', 'PERFORMANCE', 'BIRTHDAY', 'ANNIVERSARY', 'ANNOUNCEMENT', 'SYSTEM')),
    message VARCHAR2(1000) NOT NULL,
    is_read NUMBER(1) DEFAULT 0 CHECK (is_read IN (0,1)),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    read_at TIMESTAMP,
    CONSTRAINT fk_notif_emp FOREIGN KEY (employee_id) REFERENCES employees(employee_id) ON DELETE CASCADE
);

-- ANNOUNCEMENTS Table
CREATE TABLE announcements (
    announcement_id NUMBER PRIMARY KEY,
    title VARCHAR2(200) NOT NULL,
    content CLOB NOT NULL,
    posted_by VARCHAR2(20),
    posted_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_announce_emp FOREIGN KEY (posted_by) REFERENCES employees(employee_id)
);

-- AUDIT_LOGS Table
CREATE TABLE audit_logs (
    log_id NUMBER PRIMARY KEY,
    employee_id VARCHAR2(20),
    action VARCHAR2(100) NOT NULL,
    table_name VARCHAR2(50),
    record_id VARCHAR2(50),
    old_value CLOB,
    new_value CLOB,
    ip_address VARCHAR2(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_audit_emp FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);

-- ============================================
-- CREATE SEQUENCES
-- ============================================

CREATE SEQUENCE seq_department_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_designation_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_role_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_employee_role_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_leave_type_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_leave_balance_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_leave_application_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_holiday_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_review_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_goal_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_notification_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_announcement_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_audit_log_id START WITH 1 INCREMENT BY 1;

-- ============================================
-- CREATE INDEXES FOR PERFORMANCE
-- ============================================

CREATE INDEX idx_emp_dept ON employees(department_id);
CREATE INDEX idx_emp_manager ON employees(manager_id);
CREATE INDEX idx_emp_email ON employees(email);
CREATE INDEX idx_emp_active ON employees(is_active);

CREATE INDEX idx_leave_app_emp ON leave_applications(employee_id);
CREATE INDEX idx_leave_app_status ON leave_applications(status);
CREATE INDEX idx_leave_app_dates ON leave_applications(start_date, end_date);

CREATE INDEX idx_perf_review_emp ON performance_reviews(employee_id);
CREATE INDEX idx_perf_review_year ON performance_reviews(review_year);

CREATE INDEX idx_goal_emp ON goals(employee_id);
CREATE INDEX idx_goal_status ON goals(status);

CREATE INDEX idx_notif_emp ON notifications(employee_id);
CREATE INDEX idx_notif_read ON notifications(is_read);

-- ============================================
-- CREATE TRIGGERS
-- ============================================

-- Trigger to update updated_at timestamp on employees table
CREATE OR REPLACE TRIGGER trg_emp_updated_at
BEFORE UPDATE ON employees
FOR EACH ROW
BEGIN
    :NEW.updated_at := CURRENT_TIMESTAMP;
END;
/

-- Trigger to update updated_at timestamp on leave_balances table
CREATE OR REPLACE TRIGGER trg_leavbal_updated_at
BEFORE UPDATE ON leave_balances
FOR EACH ROW
BEGIN
    :NEW.updated_at := CURRENT_TIMESTAMP;
END;
/

-- Trigger to update updated_at timestamp on leave_applications table
CREATE OR REPLACE TRIGGER trg_leaveapp_updated_at
BEFORE UPDATE ON leave_applications
FOR EACH ROW
BEGIN
    :NEW.updated_at := CURRENT_TIMESTAMP;
END;
/

-- Trigger to update updated_at timestamp on performance_reviews table
CREATE OR REPLACE TRIGGER trg_perfreview_updated_at
BEFORE UPDATE ON performance_reviews
FOR EACH ROW
BEGIN
    :NEW.updated_at := CURRENT_TIMESTAMP;
END;
/

-- Trigger to update updated_at timestamp on goals table
CREATE OR REPLACE TRIGGER trg_goal_updated_at
BEFORE UPDATE ON goals
FOR EACH ROW
BEGIN
    :NEW.updated_at := CURRENT_TIMESTAMP;
END;
/

-- Trigger to automatically calculate available_leaves
CREATE OR REPLACE TRIGGER trg_calculate_available_leaves
BEFORE INSERT OR UPDATE ON leave_balances
FOR EACH ROW
BEGIN
    :NEW.available_leaves := :NEW.total_allocated - :NEW.used_leaves;
END;
/

-- ============================================
-- VERIFICATION
-- ============================================

-- Display all created tables
SELECT table_name FROM user_tables ORDER BY table_name;

-- Display all sequences
SELECT sequence_name FROM user_sequences ORDER BY sequence_name;

-- Display all triggers
SELECT trigger_name FROM user_triggers ORDER BY trigger_name;

COMMIT;

-- ============================================
-- Schema Creation Complete
-- ============================================