package com.example.Application.service;

import com.example.Application.domain.Course;
import com.example.Application.domain.Enrollment;
import com.example.Application.repository.EnrollmentJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EnrollmentService {

    private final EnrollmentJpa enrollmentJpa;

    @Autowired
    public EnrollmentService(EnrollmentJpa enrollmentJpa) {
        this.enrollmentJpa = enrollmentJpa;
    }

    public Long apply(Enrollment enrollment) {

        Course course = enrollment.getCourse();
        if (course.getCurrentCount() < course.getCapacity()) {
            enrollmentJpa.save(enrollment);
            return enrollment.getEnrollmentId();
        }

        return -1L;
    }

    public Optional<Enrollment> findOne(Long enrollmentId) {
        return enrollmentJpa.findById(enrollmentId);
    }

    public List<Enrollment> findEnrollments() {
        return enrollmentJpa.findAll();
    }

    public Optional<List<Enrollment>> findByCourseId(Long courseId) {
        return enrollmentJpa.findByCourseId(courseId);
    }

    public Optional<List<Enrollment>> findByStudentId(Long studentId) {
        return enrollmentJpa.findByStuentId(studentId);
    }

}
