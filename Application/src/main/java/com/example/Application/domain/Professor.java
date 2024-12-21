package com.example.Application.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "professor")
public class Professor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professor_id")
    private Long professorId;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "department", length = 50)
    private String department;

    @Column(name = "email", length = 100)
    private String email;

    public Professor() {}
}
