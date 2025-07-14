package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import models.Grade;
import models.Student;
import models.Subject;
import service.GradeService;
import service.StudentService;
import service.SubjectService;
import service.UserService;

public class DataSeeder {
    private static final StudentService studentService = new StudentService();
    private static final SubjectService subjectService = new SubjectService();
    private static final GradeService gradeService = new GradeService();

    public static void seed() {
        createTables();
        seedAdminUser();
        seedStudents();
        seedSubjects();
        seedGrades();
    }

    private static void createTables() {
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement()) {
            // Users table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                    "user_id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(50) NOT NULL UNIQUE," +
                    "password_hash VARCHAR(255) NOT NULL" +
                    ")");
            // Students table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS students (" +
                    "student_id INT AUTO_INCREMENT PRIMARY KEY," +
                    "first_name VARCHAR(50) NOT NULL," +
                    "last_name VARCHAR(50) NOT NULL," +
                    "gender VARCHAR(10)," +
                    "dob DATE," +
                    "user_id INT," +
                    "FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE SET NULL" +
                    ")");
            // Subjects table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS subjects (" +
                    "subject_id INT AUTO_INCREMENT PRIMARY KEY," +
                    "subject_name VARCHAR(100) NOT NULL UNIQUE" +
                    ")");
            // Grades table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS grades (" +
                    "grade_id INT AUTO_INCREMENT PRIMARY KEY," +
                    "student_id INT NOT NULL," +
                    "subject_id INT NOT NULL," +
                    "score DOUBLE NOT NULL," +
                    "date_recorded DATE NOT NULL," +
                    "FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE," +
                    "FOREIGN KEY (subject_id) REFERENCES subjects(subject_id) ON DELETE CASCADE" +
                    ")");
            // Add any additional model tables here if needed in the future
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void seedAdminUser() {
        UserService userService = new UserService();
        if (!userService.login(util.Config.ADMIN_USER, util.Config.ADMIN_PASSWORD)) {
            models.User admin = new models.User();
            admin.setUsername(util.Config.ADMIN_USER);
            admin.setPasswordHash(util.Config.ADMIN_PASSWORD); // In real apps, hash the password!
            userService.addUser(admin);
            System.out.println("Seeded admin user: " + util.Config.ADMIN_USER);
        }
    }

    private static void seedStudents() {
        if (!studentService.getAllStudents().isEmpty()) return;

        // Use Stream API to seed students
        java.util.stream.IntStream.rangeClosed(1, 10).forEach(i -> {
            Student s = new Student();
            s.setFirstName("First" + i);
            s.setLastName("Last" + i);
            s.setGender(i % 2 == 0 ? "Male" : "Female");
            s.setDob(java.time.LocalDate.of(2000 + (i % 5), (i % 12) + 1, (i % 28) + 1));
            s.setUserId(1);
            studentService.addStudent(s);
        });
        System.out.println("Seeded Students");
    }

    private static void seedSubjects() {
        if (!subjectService.getAllSubjects().isEmpty()) return;

        String[] subjects = {"Math", "Science", "English", "History", "Geography"};
        // Use Stream API to seed subjects
        java.util.Arrays.stream(subjects).forEach(name -> {
            Subject subject = new Subject();
            subject.setName(name);
            subjectService.addSubject(subject);
        });
        System.out.println("Seeded Subjects");
    }

    private static void seedGrades() {
        List<Student> students = studentService.getAllStudents();
        List<Subject> subjects = subjectService.getAllSubjects();
        java.util.Random random = new java.util.Random();

        // Use Stream API to seed grades
        students.stream().forEach(student ->
            subjects.stream().forEach(subject -> {
                Grade grade = new Grade();
                grade.setStudentId(student.getId());
                grade.setSubjectId(subject.getId());
                grade.setScore(50 + random.nextDouble() * 50); // Between 50-100
                grade.setDateRecorded(java.time.LocalDate.now());
                gradeService.addGrade(grade);
            })
        );
        System.out.println("Seeded Grades");
    }
}
