import api from "./axios";


export async function login(
    email: string,
    password: string
) {

    const response = await api.post(
        "/auth/login",
        {
            email,
            password
        }
    );


    const token = response.data.token;


    localStorage.setItem(
        "token",
        token
    );


    return token;
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