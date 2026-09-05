export interface ClimbStats {
    total: number;
    completed: number;
    completionRate: number;
    totalAttempts: number;
    averageAttempts: number;
    highestGrade: string;

    climbsByGrade: Record<string, number>;
    completionRateByGrade: Record<string, number>;
    averageAttemptsByGrade: Record<string, number>;
}