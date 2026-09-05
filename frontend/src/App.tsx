import { Routes, Route } from "react-router-dom";

import Navbar from "./components/Navbar";

import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register.tsx";
import Profile from "./pages/Profile.tsx";
import EditProfile from "./pages/EditProfile.tsx";
import ClimbLog from "./pages/ClimbLog.tsx";
import ClimbHistory from "./pages/ClimbHistory.tsx";
import ProgressStats from "./pages/ProgressStats.tsx";


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

                <Route
                    path="/profile/edit"
                    element={<EditProfile />}
                />

                <Route
                    path="/climbs/log"
                    element={<ClimbLog />}
                />

                <Route
                    path="/climbs"
                    element={<ClimbHistory />}
                />

                <Route
                    path="/climbs/stats"
                    element={<ProgressStats/>}
                />

            </Routes>
        </>
    );
}

export default App;