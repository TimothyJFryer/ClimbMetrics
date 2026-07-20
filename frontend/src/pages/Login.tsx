import { useState } from "react";
import "./Login.css";
import {login} from "../api/auth.ts";

function Login() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const [error, setError] = useState("");
    const [message, setMessage] = useState("");

    async function handleSubmit(e: React.FormEvent) {

        e.preventDefault();

        setError("");
        setMessage("");

        try {

            const response = await login(
                email,
                password
            );

            console.log(response);

            setMessage("Login successful!");

            // Later:
            // store JWT token here
            // redirect user to dashboard

        } catch (err) {

            console.error(err);

            setError("Invalid email or password");

        }
    }



    return (
        <div className="login-page">

            <div className="login-card">

                <h1>
                    Welcome back
                </h1>

                <p className="subtitle">
                    Login
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


                    <button type="submit">
                        Login
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


export default Login;