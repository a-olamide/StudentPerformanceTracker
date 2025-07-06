package strategy;

import model.Grade;
import model.Student;

import java.util.List;

public class GradeReportContext {
    private GradeReportStrategy strategy;

    public void setStrategy(GradeReportStrategy strategy) {
        this.strategy = strategy;
    }

    public void generateReport(Student student, List<Grade> grades) {
        if (strategy == null) {
            throw new IllegalStateException("No report strategy set.");
        }
        strategy.generateReport(student, grades);
    }
}
