package com.example.Application.service;

import com.example.Application.domain.Enrollment;
import com.example.Application.repository.EnrollmentJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    private final EnrollmentJpa enrollmentJpa;
    private final CourseService courseService;
    private final CacheManager cacheManager;

    @Autowired
    public EnrollmentService(EnrollmentJpa enrollmentJpa, CourseService courseService, CacheManager cacheManager) {
        this.enrollmentJpa = enrollmentJpa;
        this.courseService = courseService;
        this.cacheManager = cacheManager;
    }

    @Transactional
    public Long apply(Enrollment enrollment) {

        if (isDuplicate(enrollment.getStudent().getStudentId(), enrollment.getCourse().getCourseId())) {
            return -1L;
        }

        Long result = courseService.updateCourse(enrollment.getCourse().getCourseId());
        if (result != -2) {
            enrollmentJpa.save(enrollment);
            return  enrollment.getEnrollmentId();
        }
        return -2L;
    }

    public Optional<Enrollment> findOne(Long enrollmentId) {
        return enrollmentJpa.findByEnrollmentId(enrollmentId);
    }

    public List<Enrollment> findEnrollments() {
        return enrollmentJpa.findAll();
    }

    public Optional<List<Enrollment>> findByCourseId(Long courseId) {
        return enrollmentJpa.findByCourseId(courseId);
    }

    public Optional<List<Enrollment>> findByStudentId(Long studentId) {
        return enrollmentJpa.findByStuentId(studentId);
    }

    public Boolean isDuplicate(Long studentId, Long courseId) {
        Optional<Enrollment> findEnrollment = enrollmentJpa.findByUnique(studentId, courseId);
        return findEnrollment.isPresent();
    }

//    public Boolean isDuplicate(Long studentId, Long courseId) {
//        Cache cache = cacheManager.getCache("enrollment");
//        String cacheKey = studentId + "_" + courseId;
//
//        // 캐시에서 데이터가 있으면 바로 반환
//        if (cache != null && cache.get(cacheKey) != null) {
//            return (Boolean) cache.get(cacheKey).get();
//        }
//
//        // 캐시에 값이 없으면 DB에서 조회
//        Optional<Enrollment> findEnrollment = enrollmentJpa.findByUnique(studentId, courseId);
//        Boolean isDuplicate = findEnrollment.isPresent();
//
//        // 조회 결과를 캐시에 저장
//        if (cache != null) {
//            cache.put(cacheKey, isDuplicate);
//        }
//
//        return isDuplicate;
//    }
}
