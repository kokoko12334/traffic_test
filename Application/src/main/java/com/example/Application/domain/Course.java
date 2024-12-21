package com.example.Application.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Course {

    @Id @GeneratedValue
    @Column(name = "course_id")
    private int courseId;

    @Column(name = "title", length = 50)
    private String title;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @Column(name = "current_cnt")
    private int currentCount;

    @Column(name = "capacity")
    private int capacity;
}
