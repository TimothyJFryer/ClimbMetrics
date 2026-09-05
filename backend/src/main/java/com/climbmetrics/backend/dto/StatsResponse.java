package com.climbmetrics.backend.dto;

import java.util.Map;

public record StatsResponse(
        int total,
        int completed,
        double completionRate,
        int totalAttempts,
        double averageAttempts,
        String highestGrade,
        Map<String, Integer> climbsByGrade,
        Map<String, Double> completionRateByGrade,
        Map<String, Double> averageAttemptsByGrade
) {}
