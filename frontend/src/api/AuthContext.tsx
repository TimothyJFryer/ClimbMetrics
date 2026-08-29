import { createContext, useContext, useEffect, useState } from "react";
import api from "../api/axios";

type AuthContextType = {
    isLoggedIn: boolean;
    loginUser: () => void;
    logoutUser: () => void;
};

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: React.ReactNode }) {

    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {

        api.get("/profile")
            .then(() => {
                setIsLoggedIn(true);
            })
            .catch(() => {
                setIsLoggedIn(false);
            });

    }, []);

    function loginUser() {
        setIsLoggedIn(true);
    }

    function logoutUser() {
        setIsLoggedIn(false);
    }

    return (
        <AuthContext.Provider
            value={{
                isLoggedIn,
                loginUser,
                logoutUser
            }}
        >
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {

    const context = useContext(AuthContext);

    if (!context) {
        throw new Error("useAuth must be used inside AuthProvider");
    }

    return context;
}