package repository;

import model.Subject;

import java.util.List;

public interface SubjectRepository {
    void save(Subject subject);
    void update(Subject subject);
    void delete(int subjectId);
    Subject findById(int subjectId);
    List<Subject> findAll();
}
