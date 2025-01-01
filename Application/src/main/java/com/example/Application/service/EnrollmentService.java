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

    private final CourseService courseService;

    @Autowired
    public EnrollmentService(EnrollmentJpa enrollmentJpa, CourseService courseService) {
        this.enrollmentJpa = enrollmentJpa;
        this.courseService = courseService;
    }

    @Transactional
    public Long apply(Enrollment enrollment) {

        if (isDuplicate(enrollment)) {
            return -1L; // 1
        }

        Long result = courseService.updateCourse(enrollment.getCourse().getCourseId());
        if (result != -2L) {
            enrollmentJpa.save(enrollment);
            return enrollment.getEnrollmentId();
        }
        return -2L;
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

    private Boolean isDuplicate(Enrollment enrollment) {
        Optional<Enrollment> findEnrollment = enrollmentJpa.findByUnique(enrollment);
        return findEnrollment.isPresent();
    }
}
