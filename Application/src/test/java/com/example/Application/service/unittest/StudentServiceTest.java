package com.example.Application.service.unittest;

import com.example.Application.domain.Student;
import com.example.Application.repository.StudentJpa;
import com.example.Application.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = StudentService.class)
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @MockitoBean // 대체할 부분만 Mock
    private StudentJpa studentJpa;

    @Test
    void joinSuccessTest() {
        //given
        Student student = new Student();
        student.setName("abc123");
        student.setEmail("abc@google.com");

        when(studentJpa.save(any(Student.class))).thenReturn(student);
        when(studentJpa.findById(anyLong())).thenReturn(Optional.of(student));

        //when
        Long id = studentService.join(student);
        Optional<Student> findStudent= studentService.findOne(id);

        //then
        findStudent.ifPresent(student1 -> {
            assertThat(student1.getName()).isEqualTo(student.getName());
            assertThat(student1.getEmail()).isEqualTo(student.getEmail());
        });
    }
}
