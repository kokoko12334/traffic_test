package com.example.Application.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@Setter
@RedisHash("course")
public class CourseRedis implements Serializable {
    private Long courseId;
    private int capacity;
    private int currentCount;

    // 생성자
    public CourseRedis(Long courseId, int capacity, int currentCount) {
        this.courseId = courseId;
        this.capacity = capacity;
        this.currentCount = currentCount;
    }

}
