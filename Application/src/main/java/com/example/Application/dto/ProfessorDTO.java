package com.example.Application.dto;

import com.example.Application.domain.Professor;
import lombok.Getter;

@Getter
public class ProfessorDTO {

    private Long professorId;
    private String name;
    private String department;

    private ProfessorDTO(Long professorId, String name, String department) {
        this.professorId = professorId;
        this.name = name;
        this.department = department;

    }

    public static ProfessorDTO fromEntity(Professor professor) {
        return new ProfessorDTO(
                professor.getProfessorId(),
                professor.getName(),
                professor.getDepartment()
        );
    }
}
