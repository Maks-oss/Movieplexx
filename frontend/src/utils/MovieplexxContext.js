import React, { createContext, useContext, useMemo, useState } from 'react';
import {useLocalStorage} from "./LocalStorage";
import {useNavigate} from "react-router-dom";

const MovieplexxContext = createContext();

export const MovieplexxProvider = ({ children }) => {
    // const [user, setUser] = useLocalStorage("user",null);
    const [user, setUser] = useState(null);
    const navigation = useNavigate();
    const login = async (data) => {
        setUser(data);
        navigation("/movies");
    };

    // call this function to sign out logged in user
    const logout = () => {
        setUser(null);
        navigation("/", { replace: true });
    };

    const value = useMemo(
        () => ({
            user,
            login,
            logout,
        }),
        [user]
    );
    return (
        <MovieplexxContext.Provider value={value}>
            {children}
        </MovieplexxContext.Provider>
    );
};

export const useMovieplexxContext = () => {
    return useContext(MovieplexxContext);
};

