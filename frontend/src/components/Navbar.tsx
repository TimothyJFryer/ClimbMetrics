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
                <Link to="/">Home</Link>
                <Link to="/climbs/log">Log</Link>
                <Link to="/profile">Profile</Link>
                <Link to="/climbs">Climb History</Link>
                <Link to="/climbs/stats">Climb Stats</Link>
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