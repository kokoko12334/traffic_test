package com.example.Application.service;

import com.example.Application.domain.Course;
import com.example.Application.domain.Enrollment;
import com.example.Application.domain.Professor;
import com.example.Application.domain.Student;
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
public class IntegrationTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private EnrollmentService enrollmentService;

    @Test
    void IntegrationSuccessTest() {
        //given
        Student student = new Student();
        student.setName("abc123");
        student.setEmail("abc@google.com");

        Professor professor = new Professor();
        professor.setName("jube");
        professor.setEmail("abde@gmail.com");
        professor.setDepartment("computer");

        Course course = new Course();
        course.setTitle("Algorithm");
        course.setProfessor(professor);
        course.setCurrentCount(0);
        course.setCapacity(10);

        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);

        //when
        Long studentId = studentService.join(student);
        Optional<Student> findStudent = studentService.findOne(studentId);

        Long professorId = professorService.join(professor);
        Optional<Professor> findProfessor = professorService.findOne(professorId);

        Long courseId = courseService.register(course);
        Optional<Course> findCourse = courseService.findOne(courseId);

        Long enrollmentId = enrollmentService.apply(enrollment);
        Optional<Enrollment> findEnrollment = enrollmentService.findOne(enrollmentId);

        //then
        findStudent.ifPresent(student1 -> {
            assertThat(student1.getName()).isEqualTo(student.getName());
        });

        findProfessor.ifPresent(professor1 -> {
            assertThat(professor1.getName()).isEqualTo(professor.getName());
        });

        findCourse.ifPresent(course1 -> {
            assertThat(course1.getTitle()).isEqualTo(course.getTitle());
        });

        findEnrollment.ifPresent(enrollment1 -> {
            assertThat(enrollment1.getStudent().getName()).isEqualTo(enrollment.getStudent().getName());
            assertThat(enrollment1.getCourse().getTitle()).isEqualTo(enrollment.getCourse().getTitle());
        });
    }

}
