package com.example.Application.dto;

import com.example.Application.domain.Enrollment;
import lombok.Getter;

@Getter
public class EnrollmentDTO {

    private final Long enrollmentId;
    private final String courseTitle;
    private final String professorName;
    private final String studentName;

    public EnrollmentDTO(Long enrollmentId, String courseTitle, String professorName, String studentName) {
        this.enrollmentId = enrollmentId;
        this.courseTitle = courseTitle;
        this.professorName = professorName;
        this.studentName = studentName;
    }

    public static EnrollmentDTO fromEntity(Enrollment enrollment) {
        return new EnrollmentDTO(
                enrollment.getEnrollmentId(),
                enrollment.getCourse().getTitle(),
                enrollment.getCourse().getProfessor().getName(),
                enrollment.getStudent().getName()
        );
    }
}
