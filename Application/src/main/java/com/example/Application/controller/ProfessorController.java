package com.example.Application.controller;

import com.example.Application.domain.Professor;
import com.example.Application.dto.ProfessorDTO;
import com.example.Application.dto.responseformat.ApiResponse;
import com.example.Application.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/professors")
public class ProfessorController {

    private final ProfessorService professorService;

    @Autowired
    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @PostMapping("")
    public ApiResponse<Long> create(@RequestBody Professor professor) {
        Long professorId = professorService.join(professor);
        return ApiResponse.success(professorId);
    }

    @GetMapping("/{professorId}")
    public ApiResponse<ProfessorDTO> getProfessor(@PathVariable("professorId") Long professorId) {
        return professorService.findOne(professorId)
                .map(professor -> ApiResponse.success(ProfessorDTO.fromEntity(professor)))
                .orElse(ApiResponse.notFound("조회된 교수가 없습니다."));
    }

    @GetMapping("/all")
    public ApiResponse<List<ProfessorDTO>> getAllProfessor() {
        List<Professor> professors = professorService.findProfessors();

        List<ProfessorDTO> professorDTOS = new ArrayList<>();
        for (Professor professor: professors) {
            professorDTOS.add(ProfessorDTO.fromEntity(professor));
        }
        return ApiResponse.success(professorDTOS);
    }
}
