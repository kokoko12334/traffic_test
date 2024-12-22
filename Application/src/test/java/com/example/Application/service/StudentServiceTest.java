package com.example.Application.service;

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
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    void joinSuccessTest() {

        Student student = new Student();
        student.setName("abc123");
        student.setEmail("abc@google.com");

        Long id = studentService.join(student);
        Optional<Student> findStudent= studentService.findOne(id);

        findStudent.ifPresent(student1 -> {
            assertThat(student1.getStudentId()).isEqualTo(id);
        });
    }
}
