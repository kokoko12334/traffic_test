package com.example.Application.repository;

import com.example.Application.domain.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentJpa extends JpaRepository<Enrollment, Long> {
}
