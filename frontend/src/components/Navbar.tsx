import "./Navbar.css";
import { Link } from "react-router-dom";
import { useAuth} from "../api/AuthContext.tsx";

function Navbar() {

    const { isLoggedIn, logoutUser } = useAuth();

    return (
        <nav className="navbar">
            <div className="logo">
                CruxVision
            </div>

            <div className="nav-links">
                <a href="/">Home</a>
                <a href="/dashboard">Dashboard</a>
                <a href="/upload">Upload</a>
                <a href="/projects">Projects</a>
                <a href="/profile">Profile</a>
            </div>

            {isLoggedIn ? (
                <button onClick={logoutUser}>
                    Logout
                </button>
            ) : (
                <Link to="/login" className="login-button">
                    Login
                </Link>
            )}

        </nav>
    );
}

export default Navbar;