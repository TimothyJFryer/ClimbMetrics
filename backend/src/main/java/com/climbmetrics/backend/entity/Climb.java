package com.climbmetrics.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="climbs")
@Getter
@Setter
public class Climb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String date;

    private String grade;

    private String style;

    private int attempts;

    private boolean completed;

    private String notes;

    private LocalDateTime timestamp;

}

