[![Release](https://img.shields.io/badge/Version-v1.2.0-white?logo=github&style=flat)](https://github.com/MastanSayyad/RevWorkForce/releases)
<div align="center">
<img width="450"alt="Rev WorkForce logo" src="https://github.com/MastanSayyad/RevWorkForce/blob/main/docs/revworkforce-gif.gif" />
<br>
  <br>

[![Issues](https://img.shields.io/badge/Issues-80-yellow?logo=github&style=flat)](https://github.com/MastanSayyad/RevWorkForce/issues)
[![Pull Requests](https://img.shields.io/badge/Pull%20Requests-65-blue?logo=github&style=flat)](https://github.com/MastanSayyad/RevWorkForce/pulls?q=is%3Apr+is%3Aclosed)
[![CI](https://github.com/mastansayyad/revworkforce/actions/workflows/maven.yml/badge.svg)](https://github.com/MastanSayyad/RevWorkForce/actions)
[![Documentation](https://img.shields.io/badge/Documentation-Read%20Docs-blue?logo=read-the-docs&style=flat)](https://github.com/MastanSayyad/RevWorkForce/tree/main/docs)

<!-- Java -->
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white) ![Oracle](https://img.shields.io/badge/Oracle-JDBC-red?style=for-the-badge) ![Junit](https://img.shields.io/badge/5-Junit-green?style=for-the-badge) ![Log4j](https://img.shields.io/badge/Log4j-red?style=for-the-badge) ![github](https://img.shields.io/badge/GitHub%20Actions-black?style=for-the-badge&logo=github) ![Maven](https://img.shields.io/badge/MAVEN-000000?style=for-the-badge&logo=apachemaven&logoColor=red)
</div>

##  Welcome to RevWorkForce 
Rev Workforce is a **console-based** Human Resource Management (HRM) application designed to streamline **employee management, leave tracking, and performance review processes**. 
The system supports three user roles: 
1. **Employees**
2. **Managers**
3. **Admins**

Each with specific functionalities. The application is designed with a **modular architecture** that can be extended to a microservices-based application in future phases.

### Key Objectives ✦

- **Centralized Employee Management:** Single source of truth for all employee data
- **Automated Leave Processing:** Streamlined leave application and approval workflow
- **Performance Tracking:** Systematic performance review and goal management
- **Role-Based Access Control:** Secure access with Admin, Manager, and Employee roles
- **Audit Trail:** Complete logging of all system activities

## Project Structure 🗁
> [!NOTE]
> The project is organized to clearly separate **business logic, persistence, and presentation layers.**

```
RevWorkForce/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/revature/revworkforce/
│   │   │       ├── dao/                # Data Access Layer
│   │   │       ├── exception/          # Custom Exceptions
│   │   │       ├── model/              # Entity Classes
│   │   │       ├── service/            # Business Logic
│   │   │       ├── ui/                 # User Interface
│   │   │       ├── util/               # Utility Classes
│   │   │       └── Main.java           # Application Entry Point
│   │   └── resources/
│   │       ├── db.properties           # Database Configuration
│   │       ├── application.properties  # App Configuration
│   │       └── log4j2.xml              # Logging Configuration
│   └── test/
│       └── java/                       # Test Classes
├── database/
│   ├── schema.sql                      # Database Schema
│   └── seed_data.sql                   # Sample Data
├── docs/                               # Documentation & diagrams
├── target/                             # Build Output
├── .gitignore                          # Git Ignore File
└── pom.xml                             # Maven Configuration
```

## Architecture 모

### Entity Relationship Diagram (ERD)
![ERD Diagram](https://github.com/MastanSayyad/RevWorkForce/blob/main/docs/RevWorkForce-ER-Diagram.png)

### Application Architecture
![Architecture Diagram](https://github.com/MastanSayyad/RevWorkForce/blob/main/docs/RevWorkForce-Layered-Architecture.png)

> [!TIP]
> RevWorkForce follows a clean layered architecture, making it easy to extend into a web or microservices-based application.

## System Design ⚙︎
The application follows a layered architecture pattern:
- **Presentation Layer**: Console-based UI (EmployeeMenu, ManagerMenu, AdminMenu)
- **Service Layer**: Business logic implementation
- **DAO Layer**: Data Access Objects for database operations
- **Util Layer**: Utility classes for validation, security, logging
- **Database Layer**: Oracle 19c with normalized schema

## Technology Stack **</>**

#### Backend
- **Java 11 or Higher** - Core programming language
- **JDBC** - Database connectivity
- **Maven** - Build and dependency management
- **Log4j 2** - Logging framework
- **BCrypt** - Password encryption

#### Database
- **Oracle Database 19c/XE** - Primary data store
- **PL/SQL** - Stored procedures and triggers
- **XEPDB1** - Pluggable database

#### Testing
- **JUnit 5** - Unit testing framework
- **Mockito** - Mocking framework (planned)
- **JaCoCo** - Code coverage reporting

#### Tools
- **Eclipse/IntelliJ IDEA** - IDE
- **Git** - Version control
- **GitHub Actions** - CI/CD pipeline


## Features Implemented ✦

> [!NOTE]
> The features listed in this table are **core functionalities** of RevWorkForce.  

<table>
  <thead>
    <tr>
      <th align="left"> Employee Management</th>
      <th align="left"> Leave Management</th>
      <th align="left"> Performance Management</th>
      <th align="left"> Notifications & Communication</th>
      <th align="left"> Security & Audit</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>Complete employee lifecycle management</td>
      <td>Multiple leave types (Casual, Sick, Paid, Privilege)</td>
      <td>Annual performance review cycles</td>
      <td>Real-time notification system</td>
      <td>BCrypt password hashing</td>
    </tr>
    <tr>
      <td>Department and designation hierarchies</td>
      <td>Leave balance tracking and auto calculation</td>
      <td>Self-assessment and manager feedback</td>
      <td>Leave application status updates</td>
      <td>Role-based access control (RBAC)</td>
    </tr>
    <tr>
      <td>Manager–employee relationship tracking</td>
      <td>Multi-level approval workflow</td>
      <td>Goal setting and progress tracking</td>
      <td>Performance review reminders</td>
      <td>Session management with timeout</td>
    </tr>
    <tr>
      <td>Contact & emergency information management</td>
      <td>Holiday calendar management</td>
      <td>Performance rating system (1.0 – 5.0)</td>
      <td>Company-wide announcements</td>
      <td>Comprehensive audit logging</td>
    </tr>
    <tr>
      <td>Salary and compensation tracking</td>
      <td>Working days calculation (excludes weekends & holidays)</td>
      <td>Team performance reporting</td>
      <td>Unread notification tracking</td>
      <td>Input validation & sanitization</td>
    </tr>
    
  </tbody>
</table>

## Prerequisites 🗎
- JDK 11 or higher
- Oracle Database 19c/XE
- Maven 3.6+
- Eclipse IDE (optional)

## Installation & Setup ⓘ

### 1. Clone Repository ⿻
```bash
git clone 
cd revworkforce
```

### 2. Database Setup ⛁

```sql
-- Connect as SYSTEM user
sqlplus system/password@localhost:1521/XEPDB1

-- Run setup scripts
@database/create_user.sql

-- after creating a user
@database/schema.sql  
@database/seed_data.sql
```

> [!CAUTION]
> Running these scripts on an existing schema may overwrite data.

### 3. Configure Database Connection ⛁

Edit `src/main/resources/db.properties`:
```properties
db.url=jdbc:oracle:thin:@localhost:1521/XEPDB1
db.username=user_name
db.password=password
```

> [!WARNING]
> Never commit real database credentials to a public repository.


### 4. Build Project 🛠

> [!NOTE]
> This project uses **Maven** for build and dependency management.  
> Maven automatically handles dependency downloads, classpath configuration, and build lifecycle using `pom.xml`.

| Artifact       | Meaning                |   Configuration |
| ---------- | ---------------------- | --------------- |
| groupId    | Company / organization |  com.revature   |
| artifactId | Project name           |  revworkforce  |
| version    | Project version        |  0.0.1 SNAPSHOT |
| packaging  | jar / war              |    jar |


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

> Maven is also required for running **CI/CD pipelines** via GitHub Actions.


## Default Login Credentials ➜]

| **Role**     | **Employee ID** | **Password**    |
| -------- | ----------- | ----------- |
| **Admin**    | ADM001      | password123 |
|**Manager**  | MGR001      | password123 |
| **Employee**| EMP001      | password123 |

> [!TIP]
> Change default passwords immediately in production environments.



## Testing 𖢥

### Run Tests
```bash
mvn test
```

### View Coverage Report (JaCoCo) ılıı
```bash
mvn jacoco:report
```
> [!NOTE]
> Coverage reports are generated at:
> `target/site/jacoco/index.html`

<img width="1919" height="485" alt="image" src="https://github.com/user-attachments/assets/82599e45-b28f-4b5f-8b94-55a10c3d5534" />


## Author ©
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

---

**For more information, see:**
- [README.md](https://github.com/MastanSayyad/RevWorkForce/blob/main/README.md) - *Project overview*
- [Project Report](https://github.com/MastanSayyad/RevWorkForce/blob/main/docs/Project_Report.pdf) - *Project report including objectives, methodology, and outcomes*
- [User Manual](https://github.com/MastanSayyad/RevWorkForce/blob/main/README.md#prerequisites-) - *Step-by-step project build and usage guide Instructions*
- [PPT](https://github.com/MastanSayyad/RevWorkForce/blob/main/docs/Project_Report.pdf) - *Project presentation slides*
- [Database Design Documentation](https://github.com/MastanSayyad/RevWorkForce/blob/main/docs/Database_Design_Documentation.md) -  *Database schema, ER diagrams, and relationships*
- [API Documentation](https://github.com/MastanSayyad/RevWorkForce/blob/main/docs/Database_Design_Documentation.md) - *Application workflows and module interactions*
- [Diagrams & Charts](https://github.com/MastanSayyad/RevWorkForce/blob/main/docs/Database_Design_Documentation.md) - *Architecture diagrams, ERD, and system design visuals*
- [Testing Documentation](https://github.com/MastanSayyad/RevWorkForce/blob/main/docs/Database_Design_Documentation.md) -  *Test strategy, coverage details, and reports*


<p align="right">
  <a href="#top">
    <img 
      src="https://github.com/user-attachments/assets/c739895c-9004-4c9b-95aa-148c8d595250" 
      alt="go to top"
      width="50"
    />
  </a>
</p>






