package com.climbmetrics.backend.dto;

public record ClimbsResponse(
        Long id,
        Long userId,
        String date,
        String grade,
        String style,
        int attempts,
        boolean completed,
        String notes,
        String created_at

) {}
