package repository;

import java.util.List;

import models.Student;

public interface StudentRepository {
    void save(Student student);
    void update(Student student);
    void delete(int studentId);
    Student findById(int studentId);
    List<Student> findAll();
}
