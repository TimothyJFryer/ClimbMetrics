import { Routes, Route } from "react-router-dom";

import Navbar from "./components/Navbar";

import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register.tsx";
import Profile from "./pages/Profile.tsx";


function App() {

    return (
        <>
            <Navbar />

            <Routes>

                <Route
                    path="/"
                    element={<Home />}
                />

                <Route
                    path="/login"
                    element={<Login />}
                />

                <Route
                    path="/register"
                    element={<Register />}
                />

                <Route
                    path="/profile"
                    element={<Profile />}
                />

            </Routes>
        </>
    );
}

export default App;