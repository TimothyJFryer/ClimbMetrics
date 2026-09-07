import "./ClimbHistory.css";
import {useEffect, useState} from "react";
import {deleteClimb, getClimbHistory, updateClimb} from "../api/auth.ts";
import type {Climb} from "../types/Climb.ts";




function ClimbHistory() {

    const [climbs, setClimbs] = useState<Climb[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [editingId, setEditingId] = useState<number | null>(null);
    const [editingClimb, setEditingClimb] = useState<Climb | null>(null);

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
    const handleEdit = (climb : Climb) => {
        setEditingId(climb.id);
        setEditingClimb({...climb})
    }
    const handleCancel = () => {
        setEditingId(null);
        setEditingClimb(null);
    }

    const handleSubmit = async () => {
        if (!editingClimb) {
            return;
        }

        try {
            await updateClimb({
                id: editingClimb.id,
                date: editingClimb.date,
                grade: editingClimb.grade,
                style: editingClimb.style,
                attempts: editingClimb.attempts,
                completed: editingClimb.completed
            });

            setClimbs(prev =>
                prev.map(c =>
                    c.id === editingClimb.id
                        ? editingClimb
                        : c
                )
            );

            setEditingId(null);
            setEditingClimb(null);

        } catch (error) {
            console.error("Failed to update climb:", error);
        }

    }

    const handleDelete = async (climbId: number) => {
        try {
            await deleteClimb(climbId);

            setClimbs(prev =>
                prev.filter(climb => climb.id !== climbId)
            );

        } catch (err) {
            console.error(err);
        }

    }

    const updateAttempts = async (climb: Climb, change: number) => {
        const newAttempts = Math.max(1, climb.attempts + change);

        try {
            await updateClimb({
                id: climb.id,
                date: climb.date,
                grade: climb.grade,
                style: climb.style,
                attempts: newAttempts,
                completed: climb.completed
            });

            setClimbs(prev =>
                prev.map(c =>
                    c.id === climb.id
                        ? { ...c, attempts: newAttempts }
                        : c
                )
            );
        } catch (error) {
            console.error("Failed to update climb:", error);
        }
    };

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
                    climbs.map((climb) => {

                        const isEditing = editingId === climb.id;


                        const displayedClimb =
                            isEditing && editingClimb
                                ? editingClimb
                                : climb;

                        return (
                            <div
                                className="climb-item"
                                key={climb.id}
                            >

                                {/* HEADER */}

                                <div className="climb-item-header">

                                    {isEditing ? (
                                        <input
                                            className="edit-input grade-input"
                                            type="text"
                                            value={displayedClimb.grade}
                                            onChange={(e) =>
                                                setEditingClimb({
                                                    ...displayedClimb,
                                                    grade: e.target.value
                                                })
                                            }
                                        />
                                    ) : (
                                        <h2>{climb.grade}</h2>
                                    )}

                                    {isEditing ? (
                                        <select
                                            className="edit-input status-input"
                                            value={
                                                displayedClimb.completed
                                                    ? "completed"
                                                    : "failed"
                                            }
                                            onChange={(e) =>
                                                setEditingClimb({
                                                    ...displayedClimb,
                                                    completed:
                                                        e.target.value === "completed"
                                                })
                                            }
                                        >
                                            <option value="completed">
                                                Completed
                                            </option>

                                            <option value="failed">
                                                Not completed
                                            </option>
                                        </select>
                                    ) : (
                                        <span
                                            className={`climb-completed ${
                                                climb.completed
                                                    ? "completed"
                                                    : "failed"
                                            }`}
                                        >
                                            {climb.completed
                                                ? "Completed"
                                                : "Not completed"}
                                        </span>
                                    )}

                                </div>



                                <div className="climb-details">


                                    <div className="climb-detail">

                                        <span className="climb-detail-label">
                                            Date
                                        </span>

                                        {isEditing ? (
                                            <input
                                                className="edit-input"
                                                type="date"
                                                value={displayedClimb.date}
                                                onChange={(e) =>
                                                    setEditingClimb({
                                                        ...displayedClimb,
                                                        date: e.target.value
                                                    })
                                                }
                                            />
                                        ) : (
                                            <span className="climb-detail-value">
                                                {climb.date}
                                            </span>
                                        )}

                                    </div>



                                    <div className="climb-detail">

                                        <span className="climb-detail-label">
                                            Style
                                        </span>

                                        {isEditing ? (

                                            <textarea
                                                className="edit-input"
                                                value={displayedClimb.style ?? ""}
                                                onChange={(e) =>
                                                    setEditingClimb({
                                                        ...displayedClimb,
                                                        style: e.target.value
                                                    })
                                                }
                                            />
                                        ) : (
                                            <span className="climb-detail-value">
                                                {climb.style}
                                            </span>
                                        )}

                                    </div>



                                    <div className="climb-detail">

                                        <span className="climb-detail-label">
                                            Attempts
                                        </span>

                                        {isEditing ? (
                                            <input
                                                className="edit-input attempts-input"
                                                type="number"
                                                min="1"
                                                value={displayedClimb.attempts}
                                                onChange={(e) =>
                                                    setEditingClimb({
                                                        ...displayedClimb,
                                                        attempts: Math.max(
                                                            1,
                                                            Number(e.target.value)
                                                        )
                                                    })
                                                }
                                            />
                                        ) : (
                                            <div className="attempts-control">

                                                <button
                                                    className="attempt-button"
                                                    onClick={() =>
                                                        updateAttempts(
                                                            climb,
                                                            -1
                                                        )
                                                    }
                                                    disabled={
                                                        climb.attempts <= 1
                                                    }
                                                >
                                                    −
                                                </button>

                                                <span className="climb-detail-value">
                                                    {climb.attempts}
                                                </span>

                                                <button
                                                    className="attempt-button"
                                                    onClick={() =>
                                                        updateAttempts(
                                                            climb,
                                                            1
                                                        )
                                                    }
                                                >
                                                    +
                                                </button>

                                            </div>
                                        )}

                                    </div>

                                </div>



                                {isEditing ? (
                                    <div className="climb-notes">

                                        <span className="climb-detail-label">
                                            Notes
                                        </span>

                                        <textarea
                                            className="edit-input notes-input"
                                            value={displayedClimb.notes ?? ""}
                                            onChange={(e) =>
                                                setEditingClimb({
                                                    ...displayedClimb,
                                                    notes: e.target.value
                                                })
                                            }
                                        />

                                    </div>
                                ) : (
                                    climb.notes && (
                                        <div className="climb-notes">
                                            <strong>Notes</strong>
                                            {climb.notes}
                                        </div>
                                    )
                                )}



                                <div className="climb-actions">

                                    {isEditing ? (
                                        <>
                                            <button
                                                className="submit-button"
                                                onClick={handleSubmit}
                                            >
                                                Submit
                                            </button>

                                            <button
                                                className="cancel-button"
                                                onClick={handleCancel}
                                            >
                                                Cancel
                                            </button>
                                        </>
                                    ) : (
                                        <>
                                        <button
                                            className="edit-button"
                                            onClick={() =>
                                                handleEdit(climb)
                                            }
                                        >
                                            Edit
                                        </button>
                                        <button
                                            className="delete-button"
                                            onClick={() =>
                                                handleDelete(climb.id)
                                            }
                                        >
                                            Delete
                                        </button>
                                        </>

                                    )}

                                </div>

                            </div>
                        );
                    })
                )}

            </div>
        </div>
    );
}

export default ClimbHistory;