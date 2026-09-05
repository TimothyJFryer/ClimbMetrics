import "./ClimbHistory.css";
import {useEffect, useState} from "react";
import {getClimbHistory} from "../api/auth.ts";
import type {Climb} from "../types/Climb.ts";




function ClimbHistory() {

    const [climbs, setClimbs] = useState<Climb[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        async function loadClimbs() {
            try {
                const data = await getClimbHistory();
                setClimbs(data);
            } catch (err) {
                console.error(err);
                setError("Failed to load climb history");
            } finally {
                setLoading(false);
            }
        }

        loadClimbs();
    }, []);

    if (loading) {
        return <p>Loading climbs...</p>;
    }

    if (error) {
        return <p>{error}</p>;
    }

    return (
        <div className="climb-history-page">
            <div className="climb-history-container">

                <h1>Climb History</h1>
                <p className="climb-history-subtitle">
                    Your previous climbs
                </p>

                {climbs.length === 0 ? (
                    <div className="no-climbs">
                        No climbs logged yet.
                    </div>
                ) : (
                    climbs.map((climb) => (
                        <div className="climb-item" key={climb.id}>

                            <div className="climb-item-header">
                                <h2>{climb.grade}</h2>

                                <span
                                    className={`climb-completed ${
                                        climb.completed ? "completed" : "failed"
                                    }`}
                                >
                            {climb.completed ? "Completed" : "Not completed"}
                        </span>
                            </div>

                            <div className="climb-details">

                                <div className="climb-detail">
                            <span className="climb-detail-label">
                                Date
                            </span>
                                    <span className="climb-detail-value">
                                {climb.date}
                            </span>
                                </div>

                                <div className="climb-detail">
                            <span className="climb-detail-label">
                                Style
                            </span>
                                    <span className="climb-detail-value">
                                {climb.style}
                            </span>
                                </div>

                                <div className="climb-detail">
                            <span className="climb-detail-label">
                                Attempts
                            </span>
                                    <span className="climb-detail-value">
                                {climb.attempts}
                            </span>
                                </div>

                            </div>

                            {climb.notes && (
                                <div className="climb-notes">
                                    <strong>Notes</strong>
                                    {climb.notes}
                                </div>
                            )}

                        </div>
                    ))
                )}

            </div>
        </div>
    );
}
export default ClimbHistory;