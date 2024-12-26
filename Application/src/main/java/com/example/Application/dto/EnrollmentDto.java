package com.example.Application.dto;

import com.example.Application.domain.Enrollment;

public class EnrollmentDto {
    private final Long enrollmentId;
    private final String courseTitle;
    private final String professorName;
    private final String studentName;

    public EnrollmentDto(Long enrollmentId, String courseTitle, String professorName, String studentName) {
        this.enrollmentId = enrollmentId;
        this.courseTitle = courseTitle;
        this.professorName = professorName;
        this.studentName = studentName;
    }

    public static EnrollmentDto fromEntity(Enrollment enrollment) {
        return new EnrollmentDto(
                enrollment.getEnrollmentId(),
                enrollment.getCourse().getTitle(),
                enrollment.getCourse().getProfessor().getName(),
                enrollment.getStudent().getName()
        );
    }
}
