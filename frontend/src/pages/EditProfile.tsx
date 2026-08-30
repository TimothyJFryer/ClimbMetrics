import "./EditProfile.css";
import { useEffect, useState } from "react";
import api from "../api/axios";
import {editProfile} from "../api/auth.ts";

interface UserProfile {
    id: number;
    email: string;
    username: string;
    boulder_grade: string;
    description: string;
    sport_grade: string;
    total_climbs: number;
    total_sessions: number;
}

function EditProfile() {

    const [profile, setProfile] = useState<UserProfile | null>(null);
    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [description, setDescription] = useState("");

    useEffect(() => {
        api.get<UserProfile>(
            "/profile")
            .then(response => {
                setProfile(response.data)
                setEmail(response.data.email)
                setUsername(response.data.username ?? "");
                setDescription(response.data.description)
            })
            .catch(error => {
                console.error(error);
            });
    }, []);

    async function handleSubmit(
        e: React.FormEvent
    ) {

        e.preventDefault();
        try {
            console.log({
                username,
                email,
                description
            });
            console.log("sending req")
            const response = await editProfile(
                username,
                email,
                description
            );
            console.log("UPDATE RESPONSE:", response);
        } catch (error) {
            console.log(error)
        }
        console.log("Username is: " + username)
        console.log("Email is: " + email)

    }

    if (!profile) {
        return <p>Loading...</p>;
    }

    return (
        <div className="edit-profile-page">

            <div className="edit-profile-card">

                <h1>Edit Profile</h1>

                <p className="subtitle">
                    Update your profile information
                </p>

                <form onSubmit={handleSubmit}>

                    <label>
                        Username
                    </label>

                    <input
                        type="text"
                        placeholder="Username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />

                    <label>
                        Email
                    </label>

                    <input
                        type="email"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />

                    <label>
                        Password
                    </label>

                    <input
                        type="text"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />

                    <label>
                        Description
                    </label>

                    <input
                        type="text"
                        placeholder="Description"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                    />

                    <button type="submit">
                        Save Changes
                    </button>

                </form>

            </div>

        </div>
    );
}

export default EditProfile;