package service;

import model.Grade;
import repository.GradeRepository;
import repository.impl.GradeRepositoryImpl;

import java.util.List;

public class GradeService {
    private final GradeRepository gradeRepo = new GradeRepositoryImpl();

    public void addGrade(Grade grade) {
        gradeRepo.save(grade);
    }

    public void updateGrade(Grade grade) {
        gradeRepo.update(grade);
    }

    public void deleteGrade(int id) {
        gradeRepo.delete(id);
    }

    public List<Grade> getGradesByStudent(int studentId) {
        return gradeRepo.findByStudentId(studentId);
    }
}
