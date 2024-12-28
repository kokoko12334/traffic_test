package com.example.Application.service;

import com.example.Application.domain.Enrollment;
import com.example.Application.repository.CourseJpa;
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

    private final CourseJpa courseJpa;

    @Autowired
    public EnrollmentService(EnrollmentJpa enrollmentJpa, CourseJpa courseJpa) {
        this.enrollmentJpa = enrollmentJpa;
        this.courseJpa = courseJpa;
    }

    public Long apply(Enrollment enrollment) {

        if (isDuplicate(enrollment)) {
            return -1L; // 1
        }

        return courseJpa.findById(enrollment.getCourse().getCourseId()) // 2
                .filter(course -> course.getCurrentCount() < course.getCapacity())
                .map(course -> {
                    course.increaseCount();
                    enrollmentJpa.save(enrollment); // 3
                    return enrollment.getEnrollmentId();
                })
                .orElse(-2L);
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
