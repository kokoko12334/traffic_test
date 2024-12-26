package com.example.Application.service.unittest;

import com.example.Application.domain.Professor;
import com.example.Application.repository.ProfessorJpa;
import com.example.Application.service.ProfessorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ProfessorService.class)
public class ProfessorServiceTest {

    @Autowired
    private ProfessorService professorService;

    @MockitoBean
    private ProfessorJpa professorJpa;

    @Test
    void joinSuccessTest() {
        //given
        Professor professor = new Professor();
        professor.setName("jube");
        professor.setEmail("abde@gmail.com");
        professor.setDepartment("computer");

        when(professorJpa.save(any(Professor.class))).thenReturn(professor);
        when(professorJpa.findById(anyLong())).thenReturn(Optional.of(professor));

        //when
        Long id = professorService.join(professor);
        Optional<Professor> findProfessor = professorService.findOne(id);

        //then
        findProfessor.ifPresent(professor1 -> {
            assertThat(professor1.getName()).isEqualTo(professor.getName());
            assertThat(professor1.getEmail()).isEqualTo(professor.getEmail());
            assertThat(professor1.getDepartment()).isEqualTo(professor.getDepartment());
        });
    }
}
