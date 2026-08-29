import { useState } from "react";
import "./Register.css";
import {login, register} from "../api/auth.ts";
import {useAuth} from "../api/AuthContext.tsx";

function Register() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [passComf, setPassComf] = useState("");
    const { loginUser } = useAuth();
    const [success, setSuccess] = useState("")

    async function handleRegister(e: React.FormEvent) {

        e.preventDefault();

        console.log("1. Starting registration");

        try {

            await register(
                "username",
                email,
                password
            );

            console.log("2. Registration finished");
            setSuccess("Registered!")
            const token = await login(
                email,
                password
            );

            loginUser();

            console.log("3. Logged in:", token);
            setSuccess("Registered and logged in!")

        } catch (error) {

            console.error("REGISTER/LOGIN ERROR:", error);
            setSuccess("Failed to register")

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


                <form onSubmit={handleRegister}>

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

                    <p>
                        {success}
                    </p>

                </form>


            </div>

        </div>
    );
}


export default Register;