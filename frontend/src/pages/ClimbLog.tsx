import {useState} from "react";
import "./ClimbLog.css";
import {logClimb} from "../api/auth.ts";


function ClimbLog() {
    const today = new Date().toISOString().split("T")[0];
    const [date, setDate] = useState<string>(today);
    const [grade, setGrade] = useState("V0");
    const [style, setStyle] = useState("Crimpy");
    const [attempts, setAttempts] = useState<number>(0);
    const [completed, setCompleted] = useState<boolean>(true);
    const [success, setSuccess] = useState("")

    async function handleSubmit(
        e: React.FormEvent
    ) {

        e.preventDefault();


        try {
            console.log("Submitting...");
            console.log(date,grade,style,attempts,completed);

            const response = await logClimb(
                date,
                grade,
                style,
                attempts,
                completed
            )

            console.log(response)



        } catch(error) {

            console.error(error);
            setSuccess("Climb Log failed :(")

        }

    }

    return (
        <div className="climb-log-page">

            <div className="climb-log-card">

                <h1>
                    Log Climb
                </h1>

                <p className="subtitle">
                    new climb
                </p>


                <form onSubmit={handleSubmit}>

                    <label>
                        Date
                    </label>

                    <input
                        type="date"
                        value={today}
                        onChange={(e) => setDate(e.target.value)}
                        required
                    />


                    <label>
                        Grade
                    </label>

                    <input
                        type="text"
                        placeholder="V0"
                        value={grade}
                        onChange={(e) => setGrade(e.target.value)}
                    />

                    <label>
                        Style
                    </label>

                    <input
                        type="text"
                        placeholder="Crimpy"
                        value={style}
                        onChange={(e) => setStyle(e.target.value)}
                    />

                    <label>
                        Attempts
                    </label>

                    <input
                        type="number"
                        value={attempts}
                        onChange={(e) => setAttempts(Number(e.target.value))}
                    />

                    <label>
                        Success?
                    </label>

                    <input
                        type="checkbox"
                        checked={completed}
                        onChange={(e) => setCompleted(e.target.checked)}
                    />


                    <button type="submit">
                        Submit
                    </button>

                </form>
                <p>
                    {success}
                </p>

            </div>

        </div>
    );
}


export default ClimbLog;