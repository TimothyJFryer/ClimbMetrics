import "./Navbar.css";

function Navbar() {
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

            <button className="login-button">
                Login
            </button>
        </nav>
    );
}

export default Navbar;