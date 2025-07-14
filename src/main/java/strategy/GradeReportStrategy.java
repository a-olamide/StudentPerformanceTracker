package strategy;

import java.util.List;

import models.Grade;
import models.Student;

public interface GradeReportStrategy {
    void generateReport(Student student, List<Grade> grades);
}
