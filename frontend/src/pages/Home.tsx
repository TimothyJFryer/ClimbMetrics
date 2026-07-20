import "./Home.css";

function Home() {
    return (
        <div className="home">

            <section className="hero">

                <div className="hero-content">

                    <h1>
                        Understand your climbing.
                        <br />
                        Improve every attempt.
                    </h1>

                    <p>
                        ClimbMetrics helps climbers analyse their movement,
                        compare attempts, and track progression over time.
                    </p>

                    <div className="hero-buttons">
                        <button className="primary-button">
                            Analyse a Climb
                        </button>

                        <button className="secondary-button">
                            View Progress
                        </button>
                    </div>

                </div>


                <div className="hero-preview">

                    <div className="analysis-card">

                        <div className="video-placeholder">
              <span>
                Climbing Analysis
              </span>
                        </div>

                        <div className="metrics">

                            <div>
                                <strong>18</strong>
                                <span>Attempts</span>
                            </div>

                            <div>
                                <strong>V5</strong>
                                <span>Project</span>
                            </div>

                            <div>
                                <strong>72%</strong>
                                <span>Progress</span>
                            </div>

                        </div>

                    </div>

                </div>

            </section>


            <section className="features">

                <h2>
                    Built for climbers who want to improve
                </h2>


                <div className="feature-grid">

                    <div className="feature-card">

                        <h3>
                            🎥 Video Analysis
                        </h3>

                        <p>
                            Upload attempts, review movement,
                            and annotate key moments.
                        </p>

                    </div>


                    <div className="feature-card">

                        <h3>
                            📊 Performance Metrics
                        </h3>

                        <p>
                            Track grades, projects, attempts,
                            and improvements over time.
                        </p>

                    </div>


                    <div className="feature-card">

                        <h3>
                            🔄 Compare Attempts
                        </h3>

                        <p>
                            Understand what changed between
                            unsuccessful attempts and sends.
                        </p>

                    </div>

                </div>

            </section>


            <section className="workflow">

                <h2>
                    From attempt to improvement
                </h2>


                <div className="steps">

                    <div>
                        <span>1</span>
                        <h3>Record</h3>
                        <p>
                            Capture your climbing attempts.
                        </p>
                    </div>


                    <div>
                        <span>2</span>
                        <h3>Analyse</h3>
                        <p>
                            Review movement and identify improvements.
                        </p>
                    </div>


                    <div>
                        <span>3</span>
                        <h3>Progress</h3>
                        <p>
                            Track your development over time.
                        </p>
                    </div>

                </div>

            </section>


            <section className="cta">

                <h2>
                    Start tracking your climbing progression.
                </h2>

                <button className="primary-button">
                    Get Started
                </button>

            </section>


        </div>
    );
}

export default Home;

