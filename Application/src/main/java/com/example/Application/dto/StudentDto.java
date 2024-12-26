package com.example.Application.dto;

import com.example.Application.domain.Student;
import lombok.Getter;

@Getter
public class StudentDto {
    private final Long id;
    private final String name;
    private final String email;

    private StudentDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public static StudentDto fromEntity(Student student) {
        return new StudentDto(
                student.getStudentId(),
                student.getName(),
                student.getEmail()
        );
    }
}
