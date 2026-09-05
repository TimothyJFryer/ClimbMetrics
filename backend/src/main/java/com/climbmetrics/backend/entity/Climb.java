package com.climbmetrics.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    private String timestamp;

}

