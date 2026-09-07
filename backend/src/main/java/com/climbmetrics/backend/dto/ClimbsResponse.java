package com.climbmetrics.backend.dto;

import java.time.LocalDateTime;

public record ClimbsResponse(
        Long id,
        Long userId,
        String date,
        String grade,
        String style,
        int attempts,
        boolean completed,
        String notes,
        LocalDateTime created_at

) {}
