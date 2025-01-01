package com.example.Application.repository;

import com.example.Application.domain.Course;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseJpa extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c JOIN FETCH c.professor")
    List<Course> findAllWithProfessor();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Course c WHERE c.courseId = :courseId")
    Optional<Course> findByIdLock(Long courseId);

}
