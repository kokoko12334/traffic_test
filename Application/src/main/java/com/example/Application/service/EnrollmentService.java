package com.example.Application.service;

import com.example.Application.domain.Enrollment;
import com.example.Application.repository.EnrollmentJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    private final EnrollmentJpa enrollmentJpa;
    private final RedisSetService redisSetService;

    @Autowired
    public EnrollmentService(EnrollmentJpa enrollmentJpa, RedisSetService redisSetService) {
        this.enrollmentJpa = enrollmentJpa;

        this.redisSetService = redisSetService;
    }

    @Transactional
    public Long apply(Enrollment enrollment) {
        Long courseId = enrollment.getCourse().getCourseId();
        Long studentId = enrollment.getStudent().getStudentId();

        if (redisSetService.addElement(courseId.toString(), studentId)) {
            enrollmentJpa.save(enrollment);
            return enrollment.getEnrollmentId();
        }
        return -1L;
    }

    public Optional<Enrollment> findOne(Long enrollmentId) {
        return enrollmentJpa.findByEnrollmentId(enrollmentId);
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
