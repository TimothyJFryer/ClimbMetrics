package com.climbmetrics.backend.dto;

public record ApiError(
        String code,
        String message
) {}