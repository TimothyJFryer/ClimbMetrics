import api from "./axios";


export async function login(
    email: string,
    password: string
) {
    await api.post("/auth/login", {
        email,
        password
    });
}

export async function register(
    username: string,
    email: string,
    password: string
) {

    const response = await api.post(
        "/auth/register",
        {
            username,
            email,
            password
        }
    );


    return response.data;
}

export async function editProfile(
    username: string,
    email: string,
    description: string,
) {

    const response = await api.put(
        "/profile/editProfile",
        {
            username,
            email,
            description
        }
    );

    return response.data

}

export async function getClimbHistory() {
    const response = await api.get("/climbs");

    return response.data;
}

export async function logout() {
    await api.post("/auth/logout");
}


export async function logClimb(
    date: string,
    grade: string,
    style: string,
    attempts: number,
    completed: boolean

) {
    const response = await api.post("/climbs/log",
        {
            date,
            grade,
            style,
            attempts,
            completed
        })
    return response.data;
}

export async function getStats() {
    const response = await api.get("/climbs/stats");

    return response.data;
}


export async function updateClimb(climb: {
    id: number;
    date: string;
    grade: string;
    style: string;
    attempts: number;
    completed: boolean;
}) {
    const response = await api.put("/climbs/edit", climb);

    return response.data;
}

export async function deleteClimb(climbId: number) {
    const response = await api.delete(`/climbs/${climbId}`);
    return response.data;
}