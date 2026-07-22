import axios from "axios";


const API_URL = "http://localhost:8080/api/auth";


export async function login(email: string, password: string) {

    const response = await axios.post(
        `${API_URL}/login`,
        {
            email,
            password
        }
    );

    return response.data;
}

export async function register(email: string, password: string) {

    const response = await axios.post(
        `${API_URL}/register`,
        {
            email,
            password
        }
    );

    return response.data;
}