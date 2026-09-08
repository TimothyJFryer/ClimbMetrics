package com.climbmetrics.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "videos")
@Getter
@Setter
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long climbId;

    private String storageKey;

    private String originalFilename;

    private String contentType;

    private Long fileSize;

    private LocalDateTime uploadedAt;
}