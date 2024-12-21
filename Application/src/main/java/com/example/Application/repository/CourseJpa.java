package com.example.Application.repository;

import com.example.Application.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseJpa extends JpaRepository<Course, Long> {
}
