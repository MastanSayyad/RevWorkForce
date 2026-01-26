-- Check all tables
SELECT table_name FROM user_tables ORDER BY table_name;

-- Display all sequences
SELECT sequence_name FROM user_sequences ORDER BY sequence_name;

-- Display all triggers
SELECT trigger_name FROM user_triggers ORDER BY trigger_name;

-- Check employee count
SELECT COUNT(*) FROM employees;

-- Check leave balances
SELECT e.employee_id, e.first_name, lt.leave_type_name, lb.available_leaves
FROM employees e
JOIN leave_balances lb ON e.employee_id = lb.employee_id
JOIN leave_types lt ON lb.leave_type_id = lt.leave_type_id
WHERE e.employee_id = 'ADM001'
ORDER BY lt.leave_type_name;

-- Test login credential (should return 1 row)
SELECT employee_id, first_name, last_name, email 
FROM employees 
WHERE employee_id = 'EMP001';

-- ============================================
-- VERIFICATION QUERIES
-- ============================================
-- Count records (Oracle compatible)
SELECT 'Departments' AS table_name, COUNT(*) AS count FROM departments
UNION ALL
SELECT 'Designations', COUNT(*) FROM designations
UNION ALL
SELECT 'Roles', COUNT(*) FROM roles
UNION ALL
SELECT 'Employees', COUNT(*) FROM employees
UNION ALL
SELECT 'Employee Roles', COUNT(*) FROM employee_roles
UNION ALL
SELECT 'Leave Types', COUNT(*) FROM leave_types
UNION ALL
SELECT 'Leave Balances', COUNT(*) FROM leave_balances
UNION ALL
SELECT 'Leave Applications', COUNT(*) FROM leave_applications
UNION ALL
SELECT 'Holidays', COUNT(*) FROM holidays
UNION ALL
SELECT 'Notifications', COUNT(*) FROM notifications
UNION ALL
SELECT 'Announcements', COUNT(*) FROM announcements;

-- Display sample employee with roles
SELECT e.employee_id, e.first_name, e.last_name, e.email,
d.department_name, dg.designation_name,
LISTAGG(r.role_name, ', ') WITHIN GROUP (ORDER BY r.role_name) AS roles
FROM employees e
LEFT JOIN departments d ON e.department_id = d.department_id
LEFT JOIN designations dg ON e.designation_id = dg.designation_id
LEFT JOIN employee_roles er ON e.employee_id = er.employee_id
LEFT JOIN roles r ON er.role_id = r.role_id
GROUP BY e.employee_id, e.first_name, e.last_name, e.email, d.department_name, dg.designation_name
ORDER BY e.employee_id;

COMMIT;
