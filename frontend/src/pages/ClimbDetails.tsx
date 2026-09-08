import { useParams } from "react-router-dom";
import {useEffect, useState} from "react";
import type {Climb} from "../types/Climb.ts";
import VideoPlayer from "../components/VideoPlayer.tsx";
import {getClimb} from "../api/auth.ts";
import "./ClimbDetails.css"

function ClimbDetail() {
    const { id } = useParams<{ id: string }>();
    const [climb, setClimb] = useState<Climb>();
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        async function loadClimb() {
            try {
                const data = await getClimb(Number(id));
                setClimb(data);
            } catch (err) {
                console.error(err);
                setError("Failed to load climb");
            } finally {
                setLoading(false);
            }
        }

        loadClimb();

        }, [id]);

        if (loading) {
            return (
                <div className="climb-detail-page">
                    <div className="climb-detail-card">
                        <p>Loading climb...</p>
                    </div>
                </div>
            );
        }

        if (error || !climb) {
            return (
                <div className="climb-detail-page">
                    <div className="climb-detail-card">
                        <h1>Climb not found</h1>
                        <p className="error-message">
                            {error ?? "This climb could not be found."}
                        </p>
                    </div>
                </div>
            );
        }

        return (
            <div className="climb-detail-page">
                <div className="climb-detail-card">

                    <h1>{climb.grade}</h1>
                    <p className="subtitle">Climb Details</p>

                    <div className="climb-info">

                        <div className="climb-info-row">
                            <span className="climb-info-label">Date</span>
                            <span className="climb-info-value">
                            {climb.date}
                        </span>
                        </div>

                        <div className="climb-info-row">
                            <span className="climb-info-label">Style</span>
                            <span className="climb-info-value">
                            {climb.style}
                        </span>
                        </div>

                        <div className="climb-info-row">
                            <span className="climb-info-label">Result</span>
                            <span className="climb-info-value">
                            {climb.completed}
                        </span>
                        </div>

                        <div className="climb-info-row">
                            <span className="climb-info-label">Attempts</span>
                            <span className="climb-info-value">
                            {climb.attempts}
                        </span>
                        </div>

                    </div>

                    <div className="video-section">
                        <h2>Video</h2>

                        <div className="video-container">
                            <VideoPlayer climbId={climb.id} />
                        </div>
                    </div>

                </div>
            </div>
        );
    }

export default ClimbDetail;