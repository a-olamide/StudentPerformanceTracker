package strategy;

import repository.SubjectRepository;
import repository.impl.SubjectRepositoryImpl;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import models.Grade;
import models.Student;

public class CsvReportStrategy implements GradeReportStrategy {
    private final SubjectRepository subjectRepo = new SubjectRepositoryImpl();

    @Override
    public void generateReport(Student student, List<Grade> grades) {
        try {
            String filename = student.getFirstName() + "_" + student.getLastName() + "_grades.csv";
            PrintWriter writer = new PrintWriter(new FileWriter(filename));

            writer.println("Subject,Score,Date");

            for (Grade g : grades) {
                String subjectName = subjectRepo.findById(g.getSubjectId()).getName();
                writer.printf("%s,%.2f,%s%n", subjectName, g.getScore(), g.getDateRecorded());
            }

            writer.close();
            System.out.println("CSV report generated: " + filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
