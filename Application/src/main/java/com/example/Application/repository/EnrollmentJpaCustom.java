package com.example.Application.repository;

import com.example.Application.domain.Enrollment;

import java.util.List;
import java.util.Optional;

public interface EnrollmentJpaCustom {
    Optional<Enrollment> findByEnrollmentId(Long enrollmentId);
    Optional<List<Enrollment>> findByStuentId(Long studentId);
    Optional<List<Enrollment>> findByCourseId(Long courseId);
    Optional<Enrollment> findByUnique(Enrollment enrollment);
}
