package com.example.Application.repository;

import com.example.Application.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseJpa extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c JOIN FETCH c.professor")
    List<Course> findAllWithProfessor();
}
