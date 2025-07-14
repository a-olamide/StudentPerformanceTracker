package strategy;

import repository.SubjectRepository;
import repository.impl.SubjectRepositoryImpl;

import java.util.List;

import models.Grade;
import models.Student;

public class ConsoleReportStrategy implements GradeReportStrategy {
    private final SubjectRepository subjectRepo = new SubjectRepositoryImpl();

    @Override
    public void generateReport(Student student, List<Grade> grades) {
        System.out.printf("Grade Report for %s %s%n", student.getFirstName(), student.getLastName());
        System.out.println("-----------------------------------");
        for (Grade g : grades) {
            String subjectName = subjectRepo.findById(g.getSubjectId()).getName();
            System.out.printf("Subject: %-15s | Score: %.2f | Date: %s%n", subjectName, g.getScore(), g.getDateRecorded());
        }
        System.out.println("-----------------------------------");
    }
}
