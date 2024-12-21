package com.example.Application.domain;

import jakarta.persistence.*;

@Entity
public class Enrollment {

    @Id @GeneratedValue
    @Column(name = "enrollment_id")
    private int enrollmentId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
