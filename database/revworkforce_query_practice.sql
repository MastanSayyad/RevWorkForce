desc announcements;

desc employee_roles;
desc leave_balances WHERE employee_id = 'EMP001';
desc leave_types;
desc employees;
desc audit_logs;
desc departments;
desc designations;
desc employees;
desc goals;
desc holidays;
desc performance_reviews;
desc roles;


select sequence_name from user_sequences;
select table_name from user_tables;

select * from audit_logs;
select * from departments;
select * from designations;
select * from employees;
-- ADM001	Admin	User	admin@revworkforce.com	9876543210	Bangalore, Karnataka		15-01-85	2	8		01-01-20	100000	$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5GyYVKSVN5kRu	1	27-01-26 2:44:19.479000000 AM	27-01-26 2:44:19.479000000 AM

Update employees set first_name = 'Mastan', Last_name = 'Sayyad' Where employee_id = 'ADM001';

select * from goals;
select * from holidays;
select * from leave_balances;
select * from leave_applications;
select * from leave_types;

select lt.leave_type_id, lt.leave_type_name, e.Employee_id, e.first_name ||' '|| e.Last_name as  full_name, lb.available_leaves from leave_types lt
join leave_balances lb on lt.leave_type_id = lb.leave_type_id
join employees e on lb.employee_id = e.employee_id
WHERE e.employee_id like 'EMP%' and lt.leave_type_name = 'SICK';

select * from notifications;

select * from performance_reviews;

select * from roles;

select * from employees;
select * from designations;

select e.first_name || ' ' || e.Last_name as Full_name, d.designation_name, roles.role_name
From employees e join designations d 
on e.designation_id = d.designation_id
join employee_roles er on e.employee_id = er.employee_id
join roles on er.role_id = roles.role_id;



select * from employee_roles;
SELECT * FROM leave_balances WHERE employee_id = 'EMP001';
select * from leave_types;
select * from employees;
select * from audit_logs;
select * from departments;
select * from designations;
select * from employees;
select * from goals;
select * from holidays;
select * from performance_reviews;
select * from roles;


select r.role_name, count(er.employee_id) as employee_count
FROM roles r
LEFT JOIN employee_roles er ON r.role_id = er.role_id
group by r.role_name;




