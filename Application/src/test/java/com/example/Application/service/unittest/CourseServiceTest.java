package com.example.Application.service.unittest;

import com.example.Application.domain.Course;
import com.example.Application.domain.Professor;
import com.example.Application.repository.CourseJpa;
import com.example.Application.service.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CourseService.class)
public class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @MockitoBean
    private CourseJpa courseJpa;

    @Test
    void registerSuccessTest() {
        //given
        Professor professor = new Professor();
        professor.setName("jube");
        professor.setEmail("abde@gmail.com");
        professor.setDepartment("computer");

        Course course = new Course();
        course.setTitle("Algorithm");
        course.setProfessor(professor);
        course.setCurrentCount(0);
        course.setCapacity(10);

        when(courseJpa.save(any(Course.class))).thenReturn(course);
        when(courseJpa.findById(anyLong())).thenReturn(Optional.of(course));

        //when
        Long courseId = courseService.register(course);
        Optional<Course> findCourse = courseService.findOne(courseId);

        //then
        findCourse.ifPresent(course1 -> {
            Professor professor1 = course1.getProfessor();
            assertThat(course1.getTitle()).isEqualTo(course.getTitle());
            assertThat(course1.getProfessor().getName()).isEqualTo(course1.getProfessor().getName());
        });
    }
}
