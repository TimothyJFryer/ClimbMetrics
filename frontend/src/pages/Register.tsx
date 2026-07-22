import { useState } from "react";
import "./Register.css";
import {register} from "../api/auth.ts";

function Register() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [passComf, setPassComf] = useState("");

    const [error, setError] = useState("");
    const [message, setMessage] = useState("");

    async function handleSubmit(e: React.FormEvent) {

        e.preventDefault();

        setError("");
        setMessage("");
        if (passComf != password) {
            setError("Passwords do not match")
        } else {
            try {

                const response = await register(
                    email,
                    password,
                );

                console.log(response);

                setMessage("Registration successful!");


            } catch (err) {

                console.error(err);

                setError("Invalid email or password");

            }
        }
    }



    return (
        <div className="register-page">

            <div className="register-card">

                <h1>
                    Welcome back
                </h1>

                <p className="subtitle">
                    Register
                </p>


                <form onSubmit={handleSubmit}>

                    <label>
                        Email
                    </label>

                    <input
                        type="email"
                        placeholder="climbing@rocks.com"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />


                    <label>
                        Password
                    </label>

                    <input
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />

                    <label>
                        Password Confirmation
                    </label>

                    <input
                        type="password"
                        placeholder="Password"
                        value={passComf}
                        onChange={(e) => setPassComf(e.target.value)}
                        required
                    />


                    <button type="submit">
                        Register
                    </button>

                </form>
                {error && (
                    <p className="error">
                        {error}
                    </p>
                )}


                {message && (
                    <p className="success">
                        {message}
                    </p>
                )}


            </div>

        </div>
    );
}


export default Register;