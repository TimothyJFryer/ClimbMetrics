
import { useState } from "react";
import { uploadVideo } from "../api/auth.ts";

function VideoUpload() {
    const [file, setFile] = useState<File | null>(null);
    const [climbId, setClimbId] = useState("");
    const [uploading, setUploading] = useState(false);
    const [message, setMessage] = useState("");

    const handleUpload = async () => {
        if (!file) {
            setMessage("Please select a file.");
            return;
        }

        if (!climbId) {
            setMessage("Please enter a climb ID.");
            return;
        }

        try {
            setUploading(true);
            setMessage("");

            const result = await uploadVideo(
                Number(climbId),
                file
            );

            console.log("Upload result:", result);

            setMessage("Upload successful!");
        } catch (error) {
            console.error("Upload failed:", error);
            setMessage("Upload failed.");
        } finally {
            setUploading(false);
        }
    };

    return (
        <div style={{ padding: "2rem" }}>
            <h1>Video Upload Test</h1>

            <div>
                <label>
                    Climb ID
                </label>

                <input
                    type="number"
                    value={climbId}
                    onChange={(e) => setClimbId(e.target.value)}
                    placeholder="e.g. 1"
                />
            </div>

            <br />

            <div>
                <label>
                    Video
                </label>

                <input
                    type="file"
                    accept="video/*"
                    onChange={(e) => {
                        setFile(e.target.files?.[0] ?? null);
                    }}
                />
            </div>

            <br />

            {file && (
                <p>
                    Selected: {file.name}
                </p>
            )}

            <button
                onClick={handleUpload}
                disabled={uploading}
            >
                {uploading ? "Uploading..." : "Upload Video"}
            </button>

            {message && (
                <p>{message}</p>
            )}
        </div>
    );
}
export default VideoUpload;
