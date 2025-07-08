# StudentPerformanceTracker — Student Perfomance Tracker Management System (Java Swing + MySQL)

StudentPerformanceTracker is a lightweight desktop application built with Java Swing, JDBC, and MySQL. It allows educators to manage students, subjects, and grades, and to export student reports. This project showcases advanced Java concepts including OOP principles and design patterns like Strategy, Factory, Repository, and Singleton.

---

## 📌 Features

- ✅ Add / Update / Delete Students
- ✅ Manage Subjects
- ✅ Assign Grades to Students
- ✅ Export Student Reports (CSV / Console)
- ✅ Login Authentication (basic)
- ✅ Auto Data Seeder for Testing
- ✅ Clean Architecture (Service + Repository Layers)
- ✅ Well-structured UI using Swing
- ✅ Fully tested with JUnit

---

## 🧠 OOP Concepts & Design Patterns Demonstrated

| Concept / Pattern     | Where Used                                           |
|------------------------|------------------------------------------------------|
| **Encapsulation**     | All model classes (e.g., `Student`, `Subject`)       |
| **Abstraction**       | Repositories & Services (`StudentRepository`, etc.)  |
| **Polymorphism**      | `GradeReportStrategy` interface & implementations    |
| **Composition**       | Models containing other objects via IDs              |
| **Association**       | Student ↔️ Grades, Subject ↔️ Grades                   |
| **Repository Pattern**| CRUD operations for DB access                        |
| **Factory Pattern**   | `ViewFactory` returns correct form screen            |
| **Strategy Pattern**  | Export logic switch (CSV vs Console)                 |
| **Singleton Pattern** | `DbConnectionManager` ensures single DB instance     |

---

## 🧱 Tech Stack

- **Java SE 8+**
- **Swing (UI)**
- **MySQL**
- **JDBC**
- **JUnit 5** (Testing)

---

## 📁 Project Structure

---

1. SYSTEM REQUIREMENTS & SPECIFICATIONS (SRS)
   1.1. Overview
   EduTrack is a desktop application that allows educators to register students, assign subjects, record grades, and analyze academic performance. It provides a user-friendly GUI, persistent storage via MySQL, file export of reports, and basic analytics. The design follows OOP principles and the Repository pattern for maintainability.

1.2. System Features
Register, update, delete students

Create and manage subjects

Record grades per subject per student

View performance statistics

Export report as CSV

Persist all data using MySQL via JDBC

Simple login system for educator access

1.3. Non-Functional Requirements
Platform: Java 8+

GUI Framework: Java Swing

Persistence: JDBC + MySQL

File Export: CSV (optional PDF in future)

Performance: CRUD under 1 second

Maintainability: Repository pattern, modular architecture

Scalability: Designed to support future multi-user access

🧑‍💼 2. USER STORIES
As an Educator, I want to register a student with personal info so I can track their grades.

As an Educator, I want to add subjects so I can assign them to students.

As an Educator, I want to record grades per subject per student.

As an Educator, I want to view a student’s full academic record.

As an Educator, I want to export a student report as a CSV file.

As an Educator, I want to login to protect the system from unauthorized access.