package strategy;

import model.Grade;
import model.Student;

import java.util.List;

public interface GradeReportStrategy {
    void generateReport(Student student, List<Grade> grades);
}
