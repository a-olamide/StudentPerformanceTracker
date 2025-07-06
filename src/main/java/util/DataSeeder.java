package util;

import model.Grade;
import model.Student;
import model.Subject;
import service.GradeService;
import service.StudentService;
import service.SubjectService;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class DataSeeder {
    private static final StudentService studentService = new StudentService();
    private static final SubjectService subjectService = new SubjectService();
    private static final GradeService gradeService = new GradeService();

    public static void seed() {
        seedStudents();
        seedSubjects();
        seedGrades();
    }

    private static void seedStudents() {
        if (!studentService.getAllStudents().isEmpty()) return;

        for (int i = 1; i <= 10; i++) {
            Student s = new Student();
            s.setFirstName("First" + i);
            s.setLastName("Last" + i);
            s.setGender(i % 2 == 0 ? "Male" : "Female");
            s.setDob(LocalDate.of(2000 + (i % 5), (i % 12) + 1, (i % 28) + 1));
            s.setUserId(1);
            studentService.addStudent(s);
        }
        System.out.println("Seeded Students");
    }

    private static void seedSubjects() {
        if (!subjectService.getAllSubjects().isEmpty()) return;

        String[] subjects = {"Math", "Science", "English", "History", "Geography"};
        for (String name : subjects) {
            Subject subject = new Subject();
            subject.setName(name);
            subjectService.addSubject(subject);
        }
        System.out.println("Seeded Subjects");
    }

    private static void seedGrades() {
        List<Student> students = studentService.getAllStudents();
        List<Subject> subjects = subjectService.getAllSubjects();
        Random random = new Random();

        for (Student student : students) {
            for (Subject subject : subjects) {
                Grade grade = new Grade();
                grade.setStudentId(student.getId());
                grade.setSubjectId(subject.getId());
                grade.setScore(50 + random.nextDouble() * 50); // Between 50-100
                grade.setDateRecorded(LocalDate.now());
                gradeService.addGrade(grade);
            }
        }
        System.out.println("Seeded Grades");
    }
}
