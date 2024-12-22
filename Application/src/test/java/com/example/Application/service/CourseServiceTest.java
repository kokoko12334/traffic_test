package com.example.Application.service;

import com.example.Application.domain.Course;
import com.example.Application.domain.Professor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ProfessorService professorService;

    @Test
    void registerSuccessTest() {
        Professor professor = new Professor();
        professor.setName("jube");
        professor.setEmail("abde@gmail.com");
        professor.setDepartment("computer");

        Course course = new Course();
        course.setTitle("Algorithm");
        course.setProfessor(professor);
        course.setCurrentCount(0);
        course.setCapacity(10);

        Long professorId = professorService.join(professor);
        Long courseId = courseService.register(course);

        Optional<Course> findCourse = courseService.findOne(courseId);
        findCourse.ifPresent(course1 -> {
            Professor professor1 = course1.getProfessor();

            assertThat(course1.getCourseId()).isEqualTo(courseId);
            assertThat(professor1.getProfessorId()).isEqualTo(professorId);
        });
    }
}
