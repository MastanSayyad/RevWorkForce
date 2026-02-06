[![Release](https://img.shields.io/badge/Version-v1.2.0-white?logo=github&style=flat)](https://github.com/MastanSayyad/RevWorkForce/releases)

<div align="center">
<img width="450"alt="Rev WorkForce logo" src="https://github.com/user-attachments/assets/7031b55e-0641-499f-b8f4-057a7ce90df2" />
<br>
  <br>

[![Issues](https://img.shields.io/badge/Issues-80-yellow?logo=github&style=flat)](https://github.com/MastanSayyad/RevWorkForce/issues)
[![Pull Requests](https://img.shields.io/badge/Pull%20Requests-65-blue?logo=github&style=flat)](https://github.com/MastanSayyad/RevWorkForce/pulls?q=is%3Apr+is%3Aclosed)
[![CI](https://github.com/mastansayyad/revworkforce/actions/workflows/maven.yml/badge.svg)](https://github.com/MastanSayyad/RevWorkForce/actions)
[![Documentation](https://img.shields.io/badge/Documentation-Read%20Docs-blue?logo=read-the-docs&style=flat)](https://github.com/MastanSayyad/RevWorkForce#readme)

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


## Author
**This Project is Built and maintained by:** **[Mastan Sayyad](https://github.com/MastanSayyad)**

<table>
  <tr>
    <th>Author</th>
    <th colspan="3">Contact / Reach Out</th>
  </tr>
  <tr>
    <td align="center" >
      <a href="https://github.com/MastanSayyad" target="_blank">
        <img src="https://github.com/MastanSayyad.png" width="50" height="50" style="border-radius:50%;" alt="MastanSayyad"/>
      </a>
    </td>
    <td>
      <a href="https://github.com/MastanSayyad" target="_blank">
        <img src="https://img.shields.io/badge/GitHub-%23181717?style=flat&logo=github&logoColor=white" width="80" alt="GitHub"/>
      </a>
    </td>
    <td>
      <a href="https://www.linkedin.com/in/mastan-sayyad/" target="_blank">
        <img src="https://img.shields.io/badge/LinkedIn-%230077B5?style=flat&logo=linkedin&logoColor=white" width="80" alt="LinkedIn"/>
      </a>
    </td>
    <td>
      <a href="mailto:sayyadmastan78@gmail.com" target="_blank">
        <img src="https://img.shields.io/badge/Email-%23D14836?style=flat&logo=gmail&logoColor=white" width="80" alt="Email"/>
      </a>
    </td>
  </tr>
</table>

