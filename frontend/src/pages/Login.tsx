import { useState } from "react";
import "./Login.css";

function Login() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");





    return (
        <div className="login-page">

            <div className="login-card">

                <h1>
                    Welcome back
                </h1>

                <p className="subtitle">
                    Login
                </p>


                <form>

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


            </div>

        </div>
    );
}


export default Login;