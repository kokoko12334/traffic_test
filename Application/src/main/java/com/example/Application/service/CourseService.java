package com.example.Application.service;

import com.example.Application.domain.Course;
import com.example.Application.repository.CourseJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseService {

    private final CourseJpa courseJpa;

    @Autowired
    public CourseService(CourseJpa courseJpa) {
        this.courseJpa = courseJpa;
    }

    public Long register(Course course) {
        courseJpa.save(course);
        return course.getCourseId();
    }

    public Optional<Course> findOne(Long courseId) {
        return courseJpa.findById(courseId);
    }

    public List<Course> findCourses() {
        return courseJpa.findAll();
    }
}
