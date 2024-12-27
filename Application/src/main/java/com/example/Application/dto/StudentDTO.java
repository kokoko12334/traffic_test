package com.example.Application.dto;

import com.example.Application.domain.Student;
import lombok.Getter;

@Getter
public class StudentDTO {
    private final Long id;
    private final String name;
    private final String email;

    private StudentDTO(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public static StudentDTO fromEntity(Student student) {
        return new StudentDTO(
                student.getStudentId(),
                student.getName(),
                student.getEmail()
        );
    }
}
