package com.example.Application.controller;

import com.example.Application.domain.Course;
import com.example.Application.dto.CourseDTO;
import com.example.Application.dto.responseformat.ApiResponse;
import com.example.Application.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("")
    public ApiResponse<Long> create(@RequestBody Course course) {
        Long courseId =  courseService.register(course);
        return ApiResponse.success(courseId);
    }

    @GetMapping("/{courseId}")
    public ApiResponse<CourseDTO> getCourse(@PathVariable("courseId") Long courseId) {
        return courseService.findOne(courseId)
                .map(course -> ApiResponse.success(CourseDTO.fromEntity(course)))
                .orElse(ApiResponse.notFound("조회된 과목이 없습니다."));
    }

    @GetMapping("/all")
    public ApiResponse<List<CourseDTO>> getAllCourse() {
        List<Course> courses = courseService.findCourses();

        List<CourseDTO> courseDTOS = new ArrayList<>();
        for (Course course: courses) {
            courseDTOS.add(CourseDTO.fromEntity(course));
        }
        return ApiResponse.success(courseDTOS);
    }
}
