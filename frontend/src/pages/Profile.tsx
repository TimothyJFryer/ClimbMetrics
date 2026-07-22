import "./Profile.css";

function Profile() {
    return (
        <div className="profile-page">

            <section className="profile-header">

                <div className="profile-avatar">
                    TF
                </div>

                <div className="profile-info">
                    <h1>Tim Fryer</h1>
                    <p>@timfryer</p>
                    <p>🏔️ Boulder • Sport • Birmingham, UK</p>
                    <p>
                        Computer Science student and climbing enthusiast,
                        working towards V8.
                    </p>
                </div>

                <button className="edit-button">
                    Edit Profile
                </button>

            </section>


            <section className="stats-grid">

                <div className="stat-card">
                    <h3>Highest Boulder</h3>
                    <p>V6</p>
                </div>

                <div className="stat-card">
                    <h3>Highest Sport</h3>
                    <p>6c+</p>
                </div>

                <div className="stat-card">
                    <h3>Total Sessions</h3>
                    <p>48</p>
                </div>

                <div className="stat-card">
                    <h3>Total Climbs</h3>
                    <p>1,142</p>
                </div>

            </section>


            <section className="content-grid">

                <div className="card">
                    <h2>Recent Sessions</h2>

                    <ul>
                        <li>Depot Birmingham • 18 Jul • V5 Flash</li>
                        <li>Redpoint • 15 Jul • Board Session</li>
                        <li>Outdoor • 12 Jul • Stanage</li>
                    </ul>
                </div>


                <div className="card">
                    <h2>Current Goals</h2>

                    <ul>
                        <li>✔ Climb first V7</li>
                        <li>⬜ Flash a V6</li>
                        <li>⬜ Train twice per week</li>
                    </ul>
                </div>


                <div className="card">
                    <h2>Latest Videos</h2>

                    <ul>
                        <li>Moonboard Benchmark</li>
                        <li>V6 Project</li>
                        <li>Outdoor Session</li>
                    </ul>
                </div>


                <div className="card">
                    <h2>Personal Bests</h2>

                    <ul>
                        <li>Longest Session: 4h 20m</li>
                        <li>Most Climbs: 76</li>
                        <li>Flash Rate: 34%</li>
                    </ul>
                </div>

            </section>

        </div>
    );
}

export default Profile;