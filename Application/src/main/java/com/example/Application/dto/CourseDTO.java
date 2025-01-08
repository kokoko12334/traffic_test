package com.example.Application.dto;

import com.example.Application.domain.Course;
import lombok.Getter;

@Getter
public class CourseDTO {

    private final Long courseId;
    private final String courseTitle;
    private final String professorName;

    public CourseDTO(Long courseId, String courseTitle, String professorName) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.professorName = professorName;
    }

    public static CourseDTO fromEntity(Course course) {
        return new CourseDTO(
                course.getCourseId(),
                course.getTitle(),
                course.getProfessor().getName()
        );
    }
}
