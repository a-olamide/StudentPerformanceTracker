package repository;

import java.util.List;

import models.Grade;

public interface GradeRepository {
    void save(Grade grade);
    void update(Grade grade);
    void delete(int gradeId);
    Grade findById(int gradeId);
    List<Grade> findByStudentId(int studentId);
}
