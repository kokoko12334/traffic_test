package com.example.Application.repository;

import com.example.Application.domain.Enrollment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

public class EnrollmentJpaCustomImpl implements EnrollmentJpaCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<List<Enrollment>> findByStuentId(Long studentId) {
        return Optional.ofNullable(
                em.createQuery("SELECT e FROM Enrollment e WHERE e.student_id = :condition", Enrollment.class)
                        .setParameter("condition", studentId)
                        .getResultList()
            );
    }

    @Override
    public Optional<List<Enrollment>> findByCourseId(Long courseId) {
        return Optional.ofNullable(
                em.createQuery("SELECT e FROM Enrollment e WHERE e.course_id = :condition", Enrollment.class)
                        .setParameter("condition", courseId)
                        .getResultList()
        );
    }
}
