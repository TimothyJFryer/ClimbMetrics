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

export async function logout() {
    await api.post("/auth/logout");
}