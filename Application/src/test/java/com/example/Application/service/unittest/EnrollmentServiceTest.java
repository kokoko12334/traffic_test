package com.example.Application.service.unittest;

import com.example.Application.domain.Course;
import com.example.Application.domain.Enrollment;
import com.example.Application.domain.Professor;
import com.example.Application.domain.Student;
import com.example.Application.repository.EnrollmentJpa;
import com.example.Application.service.EnrollmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = EnrollmentService.class)
public class EnrollmentServiceTest {

    @Autowired
    private EnrollmentService enrollmentService;

    @MockitoBean
    private EnrollmentJpa enrollmentJpa;

    private Enrollment createTestCase() {
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

        return enrollment;
    }

    @Test
    void applySuccessTest() {
        //given
        Enrollment enrollment = createTestCase();

        when(enrollmentJpa.save(any(Enrollment.class))).thenReturn(enrollment);
        when(enrollmentJpa.findById(anyLong())).thenReturn(Optional.of(enrollment));

        //when
        Long enrollmentId = enrollmentService.apply(enrollment);
        Optional<Enrollment> findEnrollment = enrollmentService.findOne(enrollmentId);

        //then
        findEnrollment.ifPresent(enrollment1 -> {
            assertThat(enrollment1.getCourse().getTitle()).isEqualTo(enrollment.getCourse().getTitle());
            assertThat(enrollment1.getStudent().getName()).isEqualTo(enrollment.getStudent().getName());
        });
    }

    @Test
    void applyErrorTest() {
        //given
        Enrollment enrollment = createTestCase();

        //when
        when(enrollmentJpa.save(any(Enrollment.class))).thenReturn(enrollment);
        when(enrollmentJpa.findById(anyLong())).thenReturn(Optional.of(enrollment));
        Long enrollmentId = enrollmentService.apply(enrollment);
        Optional<Enrollment> findEnrollment = enrollmentService.findOne(enrollmentId);

        //then
        findEnrollment.ifPresent(enrollment1 -> {
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
