package com.example.Application.controller;

import com.example.Application.domain.Enrollment;
import com.example.Application.dto.EnrollmentDTO;
import com.example.Application.dto.responseformat.ApiResponse;
import com.example.Application.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping()
    public ApiResponse<Long> create(@RequestBody Enrollment enrollment) {
        Long enrollementId = enrollmentService.apply(enrollment);

        if (enrollementId == -1) {
            return ApiResponse.error("이미 등록한 수강신청입니다.", HttpStatus.BAD_REQUEST);
        }

        if (enrollementId == -2) {
            return ApiResponse.error("수강인원이 모두 찼습니다", HttpStatus.BAD_REQUEST);
        }

        return ApiResponse.success(enrollementId);
    }

    @GetMapping("/enrollment/{enrollmentId}")
    public ApiResponse<EnrollmentDTO> getEnrollmentById(@PathVariable("enrollmentId") Long enrollMentId) {
        return enrollmentService.findOne(enrollMentId)
                .map(enrollment -> ApiResponse.success(EnrollmentDTO.fromEntity(enrollment)))
                .orElse(ApiResponse.notFound("조회된 수강신청이 없습니다."));
    }

    @GetMapping("/student/{studentId}")
    public ApiResponse<List<EnrollmentDTO>> getEnrollmentByStudentId(@PathVariable("studentId") Long studentId) {
        return enrollmentService.findByStudentId(studentId)
                .map(enrollments -> {
                    List<EnrollmentDTO> enrollmentDTOS = new ArrayList<>();
                    for (Enrollment enrollment: enrollments) {
                        enrollmentDTOS.add(EnrollmentDTO.fromEntity(enrollment));
                    }
                    return ApiResponse.success(enrollmentDTOS);
                })
                .orElse(ApiResponse.notFound("조회된 내용이 없습니다."));

    }

    @GetMapping("/course/{courseId}")
    public ApiResponse<List<EnrollmentDTO>> getEnrollmentByCourseId(@PathVariable("courseId") Long courseId) {
        return enrollmentService.findByCourseId(courseId)
                .map(enrollments -> {
                    List<EnrollmentDTO> enrollmentDTOS = new ArrayList<>();
                    for (Enrollment enrollment: enrollments) {
                        enrollmentDTOS.add(EnrollmentDTO.fromEntity(enrollment));
                    }
                    return ApiResponse.success(enrollmentDTOS);

                })
                .orElse(ApiResponse.notFound("조회된 내용이 없습니다."));
    }
}
