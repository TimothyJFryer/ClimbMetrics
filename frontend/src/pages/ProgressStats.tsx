
import { useEffect, useState } from "react";
import type { ClimbStats } from "../types/ClimbStats.ts";
import { getStats } from "../api/auth.ts";
import "./ProgressStats.css";

function ProgressStats() {
    const [stats, setStats] = useState<ClimbStats | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        async function loadStats() {
            try {
                const data = await getStats();
                setStats(data);
            } catch (err) {
                console.error(err);
                setError("Failed to load statistics");
            } finally {
                setLoading(false);
            }
        }

        loadStats();
    }, []);

    if (loading) {
        return (
            <div className="stats-page">
                <div className="stats-container">
                    <p>Loading statistics...</p>
                </div>
            </div>
        );
    }

    if (error) {
        return (
            <div className="stats-page">
                <div className="stats-container">
                    <p>{error}</p>
                </div>
            </div>
        );
    }

    if (!stats) {
        return (
            <div className="stats-page">
                <div className="stats-container">
                    <p>No statistics available.</p>
                </div>
            </div>
        );
    }

    return (
        <div className="stats-page">
            <div className="stats-container">

                <h1>Climbing Progress</h1>

                <p className="stats-subtitle">
                    Your climbing performance at a glance
                </p>

                {/* Main statistics */}
                <div className="stats-grid">

                    <div className="stat-card">
                        <span className="stat-label">Highest Grade</span>
                        <span className="stat-value">
                            {stats.highestGrade || "N/A"}
                        </span>
                    </div>

                    <div className="stat-card">
                        <span className="stat-label">Total Climbs</span>
                        <span className="stat-value">
                            {stats.total}
                        </span>
                    </div>

                    <div className="stat-card">
                        <span className="stat-label">Successful</span>
                        <span className="stat-value">
                            {stats.completed}
                        </span>
                    </div>

                    <div className="stat-card">
                        <span className="stat-label">Completion Rate</span>
                        <span className="stat-value">
                            {stats.completionRate.toFixed(1)}%
                        </span>
                    </div>

                    <div className="stat-card">
                        <span className="stat-label">Total Attempts</span>
                        <span className="stat-value">
                            {stats.totalAttempts}
                        </span>
                    </div>

                    <div className="stat-card">
                        <span className="stat-label">Average Attempts</span>
                        <span className="stat-value">
                            {stats.averageAttempts.toFixed(1)}
                        </span>
                    </div>

                </div>

                {/* Performance by grade */}
                <div className="stats-section">
                    <h2>Performance by Grade</h2>

                    <div className="grade-header">
                        <span>Grade</span>
                        <span>Climbs</span>
                        <span>Success Rate</span>
                        <span>Avg. Attempts</span>
                    </div>

                    <div className="grade-list">
                        {Object.entries(stats.climbsByGrade).map(
                            ([grade, count]) => (
                                <div className="grade-row" key={grade}>

                                    <span className="grade-name">
                                        {grade}
                                    </span>

                                    <span>
                                        {count}
                                    </span>

                                    <span>
                                        {stats.completionRateByGrade[grade]?.toFixed(1) ?? "0.0"}%
                                    </span>

                                    <span>
                                        {stats.averageAttemptsByGrade[grade]?.toFixed(1) ?? "0.0"}
                                    </span>

                                </div>
                            )
                        )}
                    </div>
                </div>

            </div>
        </div>
    );
}

export default ProgressStats;

