package com.climbmetrics.backend.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    private String description = "my default description";

    private String boulderGrade = "V0";

    private String sportGrade = "1";

    private Integer totalSessions = 0;

    private Integer totalClimbs = 0;
}