import { useEffect, useState } from "react";
import { getVideo } from "../api/auth.ts";

interface VideoPlayerProps {
    climbId: number;
}

export default function VideoPlayer({ climbId }: VideoPlayerProps) {
    const [videoUrl, setVideoUrl] = useState<string | null>(null);

    useEffect(() => {
        let url: string | null = null;

        const loadVideo = async () => {
            const blob = await getVideo(climbId);

            url = URL.createObjectURL(blob);
            setVideoUrl(url);
        };

        loadVideo();

        return () => {
            if (url) {
                URL.revokeObjectURL(url);
            }
        };
    }, [climbId]);

    if (!videoUrl) {
        return <p>Loading video...</p>;
    }

    return (
        <video
            src={videoUrl}
            controls
            width="600"
        />
    );
}