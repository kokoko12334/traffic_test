package com.example.Application.repository;

import com.example.Application.domain.Student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentJpa extends JpaRepository<Student, Long> {
}
