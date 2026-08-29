import { useState } from "react";
import "./Login.css";
import {login} from "../api/auth.ts";

function Login() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [success, setSuccess] = useState("")

    async function handleSubmit(
        e: React.FormEvent
    ) {

        e.preventDefault();


        try {

            const token = await login(
                email,
                password
            );


            console.log(
                "Logged in:",
                token
            );
            setSuccess("Logged in!");

        } catch(error) {

            console.error(error);
            setSuccess("Login failed :(")

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
                <p>
                    {success}
                </p>
                <p className="register-link"><a href = "/register">Register</a></p>

            </div>

        </div>
    );
}


export default Login;