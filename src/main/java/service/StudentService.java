package service;

import model.Student;
import repository.StudentRepository;
import repository.impl.StudentRepositoryImpl;

import java.util.List;

public class StudentService {
    private final StudentRepository studentRepo = new StudentRepositoryImpl();

    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    public void addStudent(Student student) {
        studentRepo.save(student);
    }

    public void updateStudent(Student student) {
        studentRepo.update(student);
    }

    public void deleteStudent(int id) {
        studentRepo.delete(id);
    }

    public Student findById(int id) {
        return studentRepo.findById(id);
    }
}
