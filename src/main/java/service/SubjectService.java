package service;

import repository.SubjectRepository;
import repository.impl.SubjectRepositoryImpl;

import java.util.List;

import models.Subject;

public class SubjectService {
    private final SubjectRepository subjectRepo = new SubjectRepositoryImpl();

    public List<Subject> getAllSubjects() {
        return subjectRepo.findAll();
    }

    public void addSubject(Subject subject) {
        subjectRepo.save(subject);
    }

    public void updateSubject(Subject subject) {
        subjectRepo.update(subject);
    }

    public void deleteSubject(int id) {
        subjectRepo.delete(id);
    }

    public Subject findById(int id) {
        return subjectRepo.findById(id);
    }
}
