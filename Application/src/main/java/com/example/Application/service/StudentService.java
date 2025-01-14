package com.example.Application.service;

import com.example.Application.domain.Student;
import com.example.Application.repository.StudentJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentJpa studentJpa;

    @Autowired
    public StudentService(StudentJpa studentJpa) {
        this.studentJpa = studentJpa;
    }

    @Transactional
    public Long join(Student student) {
        studentJpa.save(student);
        return student.getStudentId();
    }

    @Cacheable(value = "students", key = "#p0")
    public Optional<Student> findOne(Long studentId) {
        return studentJpa.findById(studentId);
    }

    public Optional<Student> findOneNoRedis(Long studentId) {
        return studentJpa.findById(studentId);
    }

    public List<Student> findStudents() {
        return studentJpa.findAll();

    }
}
