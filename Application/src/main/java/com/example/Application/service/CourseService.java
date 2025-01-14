package com.example.Application.service;

import com.example.Application.domain.Course;
import com.example.Application.repository.CourseJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseJpa courseJpa;

    @Autowired
    public CourseService(CourseJpa courseJpa) {
        this.courseJpa = courseJpa;
    }

    @Transactional
    public Long register(Course course) {
        courseJpa.save(course);
        return course.getCourseId();
    }

    public Optional<Course> findOne(Long courseId) {
        return courseJpa.findById(courseId);
    }

    public List<Course> findCourses() {
        return courseJpa.findAllWithProfessor();
    }

    public Long updateCourse(Long courseId) {
        return courseJpa.findByIdLock(courseId)
                .filter(course -> course.getCurrentCount() < course.getCapacity())
                .map(course -> {
                    course.increaseCount();
                    return courseId;
                }).orElse(-2L);
    }
}
