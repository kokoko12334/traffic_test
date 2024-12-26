package com.example.Application.dto;

import com.example.Application.domain.Course;
import lombok.Getter;

@Getter
public class CourseDto {

    private final Long courseId;
    private final String courseTitle;
    private final String professorName;

    public CourseDto(Long courseId, String courseTitle, String professorName) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.professorName = professorName;
    }

    public static CourseDto fromEntity(Course course) {
        return new CourseDto(
                course.getCourseId(),
                course.getTitle(),
                course.getProfessor().getName()

        );
    }
}
