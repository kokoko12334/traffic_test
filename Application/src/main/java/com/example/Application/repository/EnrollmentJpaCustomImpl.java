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
    public Optional<Enrollment> findByEnrollmentId(Long enrollmentId) {
        return Optional.ofNullable(
                em.createQuery("SELECT e FROM Enrollment e " +
                                "JOIN FETCH e.student " +
                                "JOIN FETCH e.course c " +
                                "JOIN FETCH c.professor " +
                                "WHERE e.enrollmentId = :enrollmentId", Enrollment.class)
                        .setParameter("enrollmentId", enrollmentId)
                        .getSingleResult()
        );
    }

    @Override
    public Optional<List<Enrollment>> findByStuentId(Long studentId) {
        return Optional.ofNullable(
                em.createQuery("SELECT e FROM Enrollment e " +
                                "JOIN FETCH e.student " +
                                "JOIN FETCH e.course c " +
                                "JOIN FETCH c.professor " +
                                "WHERE e.student.studentId = :studentId", Enrollment.class)
                        .setParameter("studentId", studentId)
                        .getResultList()
            );
    }

    @Override
    public Optional<List<Enrollment>> findByCourseId(Long courseId) {
        return Optional.ofNullable(
                em.createQuery("SELECT e FROM Enrollment e " +
                                "JOIN FETCH e.student " +
                                "JOIN FETCH e.course c " +
                                "JOIN FETCH c.professor " +
                                "WHERE e.course.courseId = :courseId", Enrollment.class)
                        .setParameter("courseId", courseId)
                        .getResultList()
        );
    }

    @Override
    public Optional<Enrollment> findByUnique(Enrollment enrollment) {
        List<Enrollment> resultList = em.createQuery("SELECT e FROM Enrollment e WHERE e.student = :student AND e.course = :course", Enrollment.class)
                .setParameter("student", enrollment.getStudent())
                .setParameter("course", enrollment.getCourse())
                .getResultList();

        if (resultList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(resultList.get(0));
    }
}
