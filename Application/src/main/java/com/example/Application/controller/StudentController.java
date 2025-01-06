package com.example.Application.controller;

import com.example.Application.domain.Student;
import com.example.Application.dto.responseformat.ApiResponse;
import com.example.Application.dto.StudentDTO;
import com.example.Application.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("")
    public ApiResponse<Long> create(@RequestBody Student student) {
        Long studentId = studentService.join(student);
        return ApiResponse.success(studentId);
    }

    @GetMapping("/{studentId}")
    public ApiResponse<StudentDTO> getStudent(@PathVariable("studentId") Long studentId) {
        return studentService.findOne(studentId)
                .map(student -> ApiResponse.success(StudentDTO.fromEntity(student)))
                .orElse(ApiResponse.notFound("조회된 학생이 없습니다."));
    }

    @GetMapping("/all")
    public ApiResponse<List<StudentDTO>> getAllStudent() {
        List<Student> students = studentService.findStudents();

        List<StudentDTO> studentDTOS = new ArrayList<>();
        for (Student student: students) {
            studentDTOS.add(StudentDTO.fromEntity(student));
        }
        return ApiResponse.success(studentDTOS);
    }
}