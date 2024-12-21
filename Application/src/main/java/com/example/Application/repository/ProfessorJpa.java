package com.example.Application.repository;

import com.example.Application.domain.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorJpa extends JpaRepository<Professor, Long> {
}
