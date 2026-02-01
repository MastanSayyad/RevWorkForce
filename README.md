
<div align="center">
<img width="350"alt="Rev WorkForce logo" src="https://github.com/MastanSayyad/RevWorkForce/blob/main/docs/RevWorkForce-Logo.png" />

  <br>
<br>


[![CI](https://github.com/mastansayyad/revworkforce/actions/workflows/maven.yml/badge.svg)](https://github.com/MastanSayyad/RevWorkForce/actions)
[![Issues](https://img.shields.io/badge/Issues-60-blue?logo=github&style=flat)](https://github.com/MastanSayyad/RevWorkForce/issues)
[![Pull Requests](https://img.shields.io/badge/Pull%20Requests-45-purple?logo=github&style=flat)](https://github.com/MastanSayyad/RevWorkForce/pulls?q=is%3Apr+is%3Aclosed)
[![Release](https://img.shields.io/badge/Version-v1.1.0-white?logo=github&style=flat)](https://github.com/MastanSayyad/RevWorkForce/releases)



<!-- Java -->
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white) ![Oracle](https://img.shields.io/badge/Oracle-JDBC-red?style=for-the-badge) ![Junit](https://img.shields.io/badge/5-Junit-green?style=for-the-badge) ![Log4j](https://img.shields.io/badge/Log4j-red?style=for-the-badge) ![github](https://img.shields.io/badge/GitHub%20Actions-black?style=for-the-badge&logo=github) ![Maven](https://img.shields.io/badge/MAVEN-000000?style=for-the-badge&logo=apachemaven&logoColor=red)
</div>

##  Welcome to RevWorkForce 
Rev Workforce is a **console-based** Human Resource Management (HRM) application designed to streamline **employee management, leave tracking, and performance review processes**. 
The system supports three user roles: 
- **Employees**
- **Managers**
- **Admins**

Each with specific functionalities. The application is designed with a **modular architecture** that can be extended to a microservices-based web application in future phases.

> This project is part of Revature training program.

## Project Structure
```
revworkforce/
├── src/main/java/
│   └── com/revature/revworkforce/
│       ├── model/          # Entity classes
│       ├── dao/            # Data Access Objects
│       ├── service/        # Business logic
│       ├── ui/             # Console UI
│       ├── util/           # Utility classes
│       └── exception/      # Custom exceptions
├── src/main/resources/
│   ├── log4j2.xml          # Logging configuration
│   └── db.properties       # Database configuration
├── src/test/java/          # JUnit tests
├── database/               # SQL scripts
├── docs/                   # Documentation & diagrams
└── pom.xml                 # Maven configuration
```

## Features Implemented
-  Role-based access control (Employee, Manager, Admin)
-  Leave management with approval workflow
-  Performance review system
-  Goal tracking
-  Notification system
-  Password hashing (BCrypt)
-  SQL injection prevention (PreparedStatements)
-  Comprehensive logging (Log4j)
-  Input validation
-  JUnit testing (60%+ coverage)

## Architecture

### Entity Relationship Diagram (ERD)
![ERD Diagram](https://github.com/MastanSayyad/RevWorkForce/blob/main/docs/RevWorkForce-ER-Diagram.png)

### Application Architecture
![Architecture Diagram](https://github.com/MastanSayyad/RevWorkForce/blob/main/docs/RevWorkForce-Layered-Architecture.png)

## System Design
The application follows a layered architecture pattern:
- **Presentation Layer**: Console-based UI (EmployeeMenu, ManagerMenu, AdminMenu)
- **Service Layer**: Business logic implementation
- **DAO Layer**: Data Access Objects for database operations
- **Util Layer**: Utility classes for validation, security, logging
- **Database Layer**: Oracle 19c with normalized schema

## Prerequisites
- JDK 11 or higher
- Oracle Database 19c/XE
- Maven 3.6+
- Eclipse IDE (optional)

## Installation & Setup

### 1. Clone Repository
```bash
git clone 
cd revworkforce
```

### 2. Database Setup
```sql
-- Connect as SYSTEM user
sqlplus system/password@localhost:1521/XEPDB1

-- Run setup scripts
@database/create_user.sql
@database/schema.sql
@database/seed_data.sql
```

### 3. Configure Database Connection
Edit `src/main/resources/db.properties`:
```properties
db.url=jdbc:oracle:thin:@localhost:1521/XEPDB1
db.username=user_name
db.password=password
```

### 4. Build Project
```bash
mvn clean install
```

### 5. Run Application
```bash
mvn exec:java -Dexec.mainClass="com.revature.revworkforce.Main"
```

Or in Eclipse:
```
Right-click Main.java → Run As → Java Application
```

## Default Login Credentials
- **Admin**: ADM001 / password123
- **Manager**: MGR001 / password123
- **Employee**: EMP001 / password123

## Testing

### Run Tests
```bash
mvn test
```

### View Coverage Report (JaCoCo)
```bash
mvn jacoco:report
```
**Report location:** `target/site/jacoco/index.html`

<img width="1919" height="485" alt="image" src="https://github.com/user-attachments/assets/82599e45-b28f-4b5f-8b94-55a10c3d5534" />


## Project Author
**Built and maintained by**
| Author  | GitHub | Linkedin | Contact |
|---|---|---|---|
| <a href="https://github.com/MastanSayyad"><img src="https://github.com/MastanSayyad.png" width="50" height="50" style="border-radius:50%;" alt="MastanSayyad"/></a> | <a href="https://github.com/MastanSayyad" style="text-decoration:none; color:inherit;"><b>MastanSayyad</b></a> | [mastan-sayyad](https://www.linkedin.com/in/mastan-sayyad/) | [sayyadmastan78@gmail.com](mailto:sayyadmastan78@gmail.com)|



<p align="center">
  <img src="https://capsule-render.vercel.app/api?type=waving&color=gradient&height=60&section=footer" alt="Footer"/>
</p>

