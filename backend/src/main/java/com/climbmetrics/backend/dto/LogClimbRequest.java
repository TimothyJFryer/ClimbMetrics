package com.climbmetrics.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record LogClimbRequest(
        Long id,
        Long userId,

        @NotBlank
        String date,
        @NotBlank
        String grade,
        @NotBlank
        String style,

        @Min(1)
        int attempts,

        boolean completed,
        String notes,
        String created_at

) {}