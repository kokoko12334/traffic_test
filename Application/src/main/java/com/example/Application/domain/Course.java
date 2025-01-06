package com.example.Application.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "course")
public class Course {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "title", length = 50)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @Column(name = "current_cnt")
    private int currentCount;

    @Column(name = "capacity")
    private int capacity;

    @Version
    private Long version = 1L;

    public Course() {}

    public void increaseCount() {
        this.currentCount ++;
    }
}
