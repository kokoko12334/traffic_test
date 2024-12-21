package com.example.Application.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Professor {

    @Id @GeneratedValue
    @Column(name = "professor_id")
    private int professorId;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "department", length = 50)
    private String department;

    @Column(name = "email", length = 100)
    private String email;
}
