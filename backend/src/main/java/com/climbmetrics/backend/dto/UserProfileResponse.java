package com.climbmetrics.backend.dto;

public record UserProfileResponse(
        Long id,
        String email,
        String username,
        String boulder_grade,
        String description,
        String sport_grade,
        int total_climbs,
        int total_sessions
) {}