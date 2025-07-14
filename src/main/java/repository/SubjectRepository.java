package repository;

import java.util.List;

import models.Subject;

public interface SubjectRepository {
    void save(Subject subject);
    void update(Subject subject);
    void delete(int subjectId);
    Subject findById(int subjectId);
    List<Subject> findAll();
}
