package com.example.Application.dto;

import com.example.Application.domain.Professor;
import lombok.Getter;

@Getter
public class ProfessorDto {
    private Long professorId;
    private String name;
    private String department;

    private ProfessorDto(Long professorId, String name, String department) {
        this.professorId = professorId;
        this.name = name;
        this.department = department;

    }

    public static ProfessorDto fromEntity(Professor professor) {
        return new ProfessorDto(
                professor.getProfessorId(),
                professor.getName(),
                professor.getDepartment()
        );
    }
}
