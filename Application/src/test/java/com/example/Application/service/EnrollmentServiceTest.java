package com.example.Application.service;

import com.example.Application.domain.Course;
import com.example.Application.domain.Enrollment;
import com.example.Application.domain.Professor;
import com.example.Application.domain.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class EnrollmentServiceTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private EnrollmentService enrollmentService;

    private Long createTestCase() {
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

        studentService.join(student);
        professorService.join(professor);
        courseService.register(course);
        return enrollmentService.apply(enrollment);
    }

    @Test
    void applySuccessTest() {
        Long enrollmentId = createTestCase();
        Optional<Enrollment> findEnrollment = enrollmentService.findOne(enrollmentId);

        findEnrollment.ifPresent(enrollment1 -> {
            assertThat(enrollment1.getEnrollmentId()).isEqualTo(enrollmentId);
        });
    }

    @Test
    void applyErrorTest() {
        Long enrollmentId = createTestCase();

        Optional<Enrollment> enrollment = enrollmentService.findOne(enrollmentId);

        enrollment.ifPresent(enrollment1 -> {
            assertThatThrownBy(() -> {
                Student student = enrollment1.getStudent();
                Course course = enrollment1.getCourse();

                Enrollment newEnrollment = new Enrollment();
                newEnrollment.setStudent(student);
                newEnrollment.setCourse(course);

                enrollmentService.apply(newEnrollment);
            }).isInstanceOf(DataIntegrityViolationException.class);
        });
    }
}
