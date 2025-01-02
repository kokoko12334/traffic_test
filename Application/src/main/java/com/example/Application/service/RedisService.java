package com.example.Application.service;

import com.example.Application.domain.Enrollment;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedissonClient redissonClient;

    public RedisService(RedisTemplate<String, Object> redisTemplate, RedissonClient redissonClient) {
        this.redisTemplate = redisTemplate;
        this.redissonClient = redissonClient;
    }

    private String getCurrentCountKey(Long courseId) {
        return String.format("course:%s:currentCount", courseId);
    }

    private String getCapacityKey(Long courseId) {
        return String.format("course:%s:capacity", courseId);
    }

    private String getStudentCourseKey(Long studentId, Long courseId) {
        return String.format("enrollment:%s:%s", studentId, courseId);
    }

    private int getCurrentCount(Long courseId) {
        String key = getCurrentCountKey(courseId);
        Integer count = (Integer) redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(count)
                .orElse(-1);
    }

    private int getCapacity(Long courseId) {
        String key = getCapacityKey(courseId);
        Integer capacity = (Integer) redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(capacity)
                .orElse(-1);
    }

    public boolean updateCourse(Long courseId) {
        String lockKey = String.format("lock:course:%s", courseId);
        RLock lock = redissonClient.getLock(lockKey);

        try {
            if (lock.tryLock(2, 5, TimeUnit.SECONDS)) { //(대기 5초, 유지 10초)
                try {
                    int currentCount = getCurrentCount(courseId);
                    int capacity = getCapacity(courseId);

                    if (currentCount < capacity) {
                        String countKey = getCurrentCountKey(courseId);
                        redisTemplate.opsForValue().set(countKey, currentCount + 1);
                        return true;
                    }

                    return false; // 정원 초과
                } finally {
                    lock.unlock();
                }
            } else {
                throw new RuntimeException("Unable to acquire lock for courseId: " + courseId);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while trying to acquire lock", e);
        }
    }

    public Boolean isDuplicate(Long studentId, Long courseId) {
        String key = getStudentCourseKey(studentId, courseId);
        return redisTemplate.hasKey(key);
    }

    public void updateEnrollment(Enrollment enrollment) {
        String key = getStudentCourseKey(enrollment.getStudent().getStudentId(), enrollment.getCourse().getCourseId());
        redisTemplate.opsForValue().set(key, 1);
    }
}