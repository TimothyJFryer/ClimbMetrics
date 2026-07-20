import { Link } from "react-router-dom";

function Home() {
    return (
        <div>
            <h1>SafeStep</h1>

            <p>
                Find safer walking and cycling routes based on community
                reports.
            </p>

            <Link to="/map">
                View Map
            </Link>

            <br />

            <Link to="/report">
                Report a Hazard
            </Link>
        </div>
    );
}

export default Home;