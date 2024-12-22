package com.example.Application.service;

import com.example.Application.domain.Student;
import com.example.Application.repository.StudentJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {

    private final StudentJpa studentJpa;

    @Autowired
    public StudentService(StudentJpa studentJpa) {
        this.studentJpa = studentJpa;
    }

    public Long join(Student student) {
        studentJpa.save(student);
        return student.getStudentId();
    }

    public Optional<Student> findOne(Long studentId) {
        return studentJpa.findById(studentId);
    }

    public List<Student> findStudents() {
        return studentJpa.findAll();
    }
}