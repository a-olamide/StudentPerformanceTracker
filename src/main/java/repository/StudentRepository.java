package repository;

import model.Student;

import java.util.List;

public interface StudentRepository {
    void save(Student student);
    void update(Student student);
    void delete(int studentId);
    Student findById(int studentId);
    List<Student> findAll();
}
