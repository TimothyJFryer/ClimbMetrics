import { useState } from "react";
import "./Register.css";
import {register} from "../api/auth.ts";

function Register() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [passComf, setPassComf] = useState("");


    async function handleRegister() {

        try {

            await register(
                "username",
                email,
                password
            );

            console.log("Account created");

        } catch(error) {

            console.error(error);

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

                </form>


            </div>

        </div>
    );
}


export default Register;