package com.example.Application.service;

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
public class ProfessorServiceTest {

    @Autowired
    private ProfessorService professorService;

    @Test
    void joinSuccessTest() {
        Professor professor = new Professor();
        professor.setName("jube");
        professor.setEmail("abde@gmail.com");
        professor.setDepartment("computer");

        Long id = professorService.join(professor);
        Optional<Professor> findProfessor = professorService.findOne(id);

        findProfessor.ifPresent(professor1 -> {
            assertThat(professor1.getProfessorId()).isEqualTo(id);
        });
    }
}
