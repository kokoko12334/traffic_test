package com.example.Application.service;

import com.example.Application.domain.Professor;
import com.example.Application.repository.ProfessorJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    private final ProfessorJpa professorJpa;

    @Autowired
    public ProfessorService(ProfessorJpa professorJpa) {
        this.professorJpa = professorJpa;
    }

    @Transactional
    public Long join(Professor professor) {
        professorJpa.save(professor);
        return professor.getProfessorId();
    }

    public Optional<Professor> findOne(Long professorId) {
        return professorJpa.findById(professorId);
    }

    public List<Professor> findProfessors() {
        return professorJpa.findAll();
    }
}
